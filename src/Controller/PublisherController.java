package Controller;

import models.Packets.PublisherPacket;
import models.Packets.ServerPacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
            ServerPacket serverPacket = (ServerPacket) objectInputStream.readObject();
            System.out.println("Publisher: Client Type " + serverPacket.getType());
            return serverPacket.getTopicList();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> connectToServer(PublisherPacket publisherPacket)
    {
        try{
            Socket socket = new Socket(address, serverPort);
            System.out.println("Publisher: Connected");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(publisherPacket);
            if(publisherPacket.getTopicName()==null){
                return waitForServerConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
