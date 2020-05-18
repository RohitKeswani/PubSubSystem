package Controller;

import models.Packet;
import models.TypeOfPacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerController implements Runnable{
    private int port;
    private List<String> topicList = new ArrayList<>();
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
                    handleAdvertiser(packet);
                }
                else
                    if (packet.getType() == TypeOfPacket.Publisher){
                        handlePublisher(packet);
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handlePublisher(Packet packet) {
        if(packet.getTopicName()==null){
            ServerController serverController = new ServerController();
            serverController.topicList = this.topicList;
            Thread thread = new Thread(serverController);
            thread.start();
        }
        else {
            System.out.println(packet.getContent());
            publishContentToSubcribers();
        }
    }

    private void publishContentToSubcribers() {
        /**
         * TODO: send message ot all subscribers in list for value of topicName in hashmap of <topicName, subscriberList>
         * TODO: if any subscriber offline, store the packet in hashmap of subscriber <subscriberId, packet<List>>
         * TODO: try re-transmitting message with exponential timeoff method.
         */
    }

    private void sendTopicListToPublisher() {
        Packet packet = new Packet();
        packet.setType(TypeOfPacket.Server);
        packet.setTopicList(topicList);
        try{
            Properties properties = new Common().lookUpProperty();
            String address = properties.getProperty("publisherAddress");
            int publisherPort = Integer.parseInt(properties.getProperty("publisherPort"));
            Socket socket = new Socket(address, publisherPort);
            System.out.println("Server-thread: Connected to Publisher to send topic list");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAdvertiser(Packet packet) {
        registerTopic(packet.getTopicName().toLowerCase());
        System.out.println("Server: Topic Name " + packet.getTopicName() +" successfully registered");
    }

    private void registerTopic(String topicName) {
        System.out.println("Server: Registering topic");
        if(!topicList.contains(topicName)){
            topicList.add(topicName);
        }
    }

    @Override
    public void run() {
        sendTopicListToPublisher();
    }
}
