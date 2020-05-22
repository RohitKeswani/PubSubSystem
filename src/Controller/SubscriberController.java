package Controller;

import models.Packets.ServerPacket;
import models.Packets.SubscriberPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

public class SubscriberController implements Controller, Runnable {
    private int port;
    private String address;

    public SubscriberController(String address, int port)
    {
        this.port = port;
        this.address = address;
    }

    public SubscriberController() {

    }

    private static void printAvailableTopics(List<String> topicList) {
        System.out.println("Subscriber: Available topics are:");
        for(String topic : topicList){
            System.out.print(topic+"\t");
        }
        System.out.println();
    }

    public void waitForServerConnection()
    {
        try {
            Properties properties = new Common().lookUpProperty();
            int subscriberPort = Integer.parseInt(properties.getProperty("subscriberPort"));
            ServerSocket serverSocket = new ServerSocket(subscriberPort);
            System.out.println("Subscriber: Started");
            System.out.println("Subscriber: Waiting for topics ...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Subscriber: Server Connected on " + clientSocket.getPort());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ServerPacket serverPacket = (ServerPacket) objectInputStream.readObject();
            System.out.println("Subscriber: Client Type " + serverPacket.getType());
            printAvailableTopics(serverPacket.getTopicList());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void connectToServer(SubscriberPacket subscriberPacket)
    {
        try{
            if(subscriberPacket.getTopicName()==null){
                System.out.println("Subscriber: Receiving topics from Server now…");
                Thread thread = new Thread(new SubscriberController());
                thread.start();
            }
            Socket socket = new Socket(address, port);
            System.out.println("Subscriber: Connected");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(subscriberPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        waitForServerConnection();
    }
}
