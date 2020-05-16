package Controller;

import models.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

//write the logic for handling server
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
        //write code to receive a connection from client.
        //receive packet instead of string;
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
                if(packet.getType().equals("Advertiser")){
                    handleAdvertiser(packet);
                }
                else
                    if (packet.getType().equals("Publisher")){
                        handlePublisher(packet);
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //return true;
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
    }

    private void sendTopicListToPublisher() {
        System.out.println("Sending");
        for(String s : topicList){
            System.out.println(s);
        }
        Packet packet = new Packet();
        packet.setType("Server");
        packet.setTopicList(topicList);
        try{
            Socket socket = new Socket("localhost", 4000);
            System.out.println("Server: Connected to Publisher to send topic list");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAdvertiser(Packet packet) {
        registerTopic(packet.getTopicName());
        System.out.println("Server: Topic Name " + packet.getTopicName() +" successfully registered");
    }

    private void registerTopic(String topicName) {
        System.out.println("Registering topic");
        if(!topicList.contains(topicName)){
            topicList.add(topicName);
        }
        for(String topic:topicList){
            System.out.println(topic);
        }
    }

    @Override
    public void run() {
        for(String s: topicList){
            System.out.println(s);
        }
        sendTopicListToPublisher();
    }
}
