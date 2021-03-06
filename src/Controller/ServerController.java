package Controller;

import models.Packets.*;
import models.TypeOfPacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerController implements Runnable{
    private int port;
    private List<String> topicList = new ArrayList<>();
    private HashMap<String, List<SubscriberPacket>> topicNameToSubscribers = new HashMap<>();
    private HashMap<String, List<String>> offlineSubcribersToPendingContent = new HashMap<>();

    public ServerController(int port)
    {
        this.port = port;
    }

    public ServerController() {

    }

    public void waitForClientConnection()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Server: Started");
                System.out.println("Server: Waiting for client ...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server: Client Connected on " + clientSocket.getPort());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                Packet packet = (Packet) objectInputStream.readObject();
                System.out.println("Server: Client Type " + packet.getType());
                if(packet.getType() == TypeOfPacket.Advertiser){
                    handleAdvertiser((AdvertiserPacket) packet);
                }
                else
                    if (packet.getType() == TypeOfPacket.Publisher){
                        handlePublisher((PublisherPacket) packet);
                    }
                    else{
                        handleSubscriber((SubscriberPacket) packet);
                    }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleSubscriber(SubscriberPacket subscriberPacket) {
        if(subscriberPacket.getTopicName()==null){
            //TODO: send pending messages to subscriber, before sending topiclist
            sendTopicListToClient(TypeOfPacket.Subscriber.toString());
        }
        else {
            System.out.println("Server: "+subscriberPacket.getTopicName());
            bindSubscriberToTopic(subscriberPacket);
        }
    }

    private void bindSubscriberToTopic(SubscriberPacket subscriberPacket) {
        String topicName = subscriberPacket.getTopicName();
        //TODO: store in a hashmap <topicName, subscriberId>
        List<SubscriberPacket>  temp = new ArrayList<>();
        if(topicNameToSubscribers.containsKey(topicName)) {
            temp = topicNameToSubscribers.get(topicName);
        }
        temp.add(subscriberPacket);
        topicNameToSubscribers.put(topicName, temp);
    }

    private void sendTopicListToClient(String clientType) {
        String propertiesLookupAddressValue = null;
        String propertiesLookupPortValue = null;
        if(clientType.equals(TypeOfPacket.Subscriber.toString())){
            propertiesLookupAddressValue = "subscriberAddress";
            propertiesLookupPortValue = "subscriberPort";
        }
        else{
            propertiesLookupAddressValue ="publisherAddress";
            propertiesLookupPortValue = "publisherPort";
        }
        ServerPacket serverPacket = (ServerPacket)
                new Common().createPacketForCommunication(TypeOfPacket.Server.toString());
        serverPacket.setTopicList(topicList);
        try{
            Properties properties = new Common().lookUpProperty();
            String address = properties.getProperty(propertiesLookupAddressValue);
            int port = Integer.parseInt(properties.getProperty(propertiesLookupPortValue));
            Socket socket = new Socket(address, port);
            System.out.println("Server: Connected to "+clientType+" to send topic list");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(serverPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handlePublisher(PublisherPacket publisherPacket) {
        if(publisherPacket.getTopicName()==null){
            ServerController serverController = new ServerController();
            serverController.topicList = this.topicList;
            Thread thread = new Thread(serverController);
            thread.start();
        }
        else {
            System.out.println(publisherPacket.getContent());
            publishContentToSubcribers(publisherPacket);
        }
    }

    private void publishContentToSubcribers(PublisherPacket publisherPacket) {
        /**
         * TODO: send message ot all subscribers in list for value of topicName in hashmap of <topicName, subscriberList>
         * TODO: if any subscriber offline, store the packet in hashmap of subscriber <subscriberId, packet<List>>
         * TODO: try re-transmitting message with exponential timeoff method.
         */
        String topicName = publisherPacket.getTopicName();
        String content = publisherPacket.getContent();
        List<SubscriberPacket> listOfSubcribedClients = topicNameToSubscribers.get(topicName);
        for(SubscriberPacket subscriberPacket : listOfSubcribedClients){
            ServerPacket serverPacket = (ServerPacket)
                    new Common().createPacketForCommunication(TypeOfPacket.Server.toString());
            serverPacket.setTopicName(topicName);
            serverPacket.setContent(content);
            try{
                String address = "localhost";
                int port = subscriberPacket.getSubscriberPort();
                Socket socket = new Socket(address, port);
                System.out.println("Server: Connected to "+subscriberPacket.getType().toString()+" to send content");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(serverPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleAdvertiser(AdvertiserPacket advertiserPacket) {
        registerTopic(advertiserPacket.getTopicName().toLowerCase());
        System.out.println("Server: Topic Name " + advertiserPacket.getTopicName() +" successfully registered");
    }

    private void registerTopic(String topicName) {
        System.out.println("Server: Registering topic");
        if(!topicList.contains(topicName)){
            topicList.add(topicName);
        }
    }

    @Override
    public void run() {
        sendTopicListToClient(TypeOfPacket.Publisher.toString());
    }
}
