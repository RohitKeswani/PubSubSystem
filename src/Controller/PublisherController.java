package Controller;

import Controller;
import models.publusPacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

public class PublisherController implements Controller {
    private int serverPort;
    private String address;

    public PublisherController(String address, int serverPort)
    {
        this.serverPort = serverPort;
        this.address = address;
    }

    public List<String> waitForServerConnection()
    {
        try {
            Properties properties = new Common().lookUpProperty();
            int publisherPort = Integer.parseInt(properties.getProperty("publisherPort"));
            ServerSocket serverSocket = new ServerSocket(publisherPort);
            System.out.println("Publisher: Started");
            System.out.println("Publisher: Waiting for topics ...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Publisher: Server Connected on " + clientSocket.getPort());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            publusPacket packet = (publusPacket) objectInputStream.readObject();
            System.out.println("Publisher: Client Type " + packet.getType());
            return packet.getTopicList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> connectToServer(publusPacket packet)
    {
        try{
            Socket socket = new Socket(address, serverPort);
            System.out.println("Publisher: Connected");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
            if(packet.getTopicName()==null){
                return waitForServerConnection();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
