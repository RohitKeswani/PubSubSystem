package Controller;

import models.Packet;
import models.TypeOfPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class PublisherController {
    private int serverPort;
    private String address;
    //private int selfPort;

    public PublisherController(String address, int serverPort)
    {
        this.serverPort = serverPort;
        this.address = address;
        //this.selfPort = selfPort;
    }

    public void waitForServerConnection()
    {
        //write code to receive a connection from client.
        //receive packet instead of string;
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("Publisher: Started");
            System.out.println("Publisher: Waiting for topics ...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Publisher: Server Connected on " + clientSocket.getPort());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            Packet packet = (Packet) objectInputStream.readObject();
            System.out.println("Publisher: Client Type " + packet.getType());
            printAvailableTopics(packet);
            packet = createContent();
            sendContent(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //return true;
    }

    private void printAvailableTopics(Packet packet) {
        System.out.println("Available topics are:");
        for(String topic : packet.getTopicList()){
            System.out.print(topic+"\t");
        }
        System.out.println();
    }

    private Packet createContent() {
        Packet packet = new Packet();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the topic name from list");
        String topicName = sc.nextLine();
        System.out.println("Enter the text for topic you would like to publish");
        String content = sc.nextLine();
        packet.setType(TypeOfPacket.Publisher);
        packet.setTopicName(topicName);
        packet.setContent(content);
        return packet;
    }

    public void sendContent(Packet packet){
        try{
            Socket socket = new Socket(address, serverPort);
            System.out.println("Publisher: Connected to send topics");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
            if(packet.getTopicName()==null){
                waitForServerConnection();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connectToServer(Packet packet)
    {
        //write the code to connect publisher to server
        try{
            Socket socket = new Socket(address, serverPort);
            System.out.println("Publisher: Connected");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
            if(packet.getTopicName()==null){
                waitForServerConnection();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return sentConnection;
    }
}
