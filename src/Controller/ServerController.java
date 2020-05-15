package Controller;

import models.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

//write the logic for handling server
public class ServerController {
    private int port;
    public ServerController(int port)
    {
        this.port = port;
    }

    public void waitForClientConnection()
    {
        //write code to receive a connection from client.
        //receive packet instead of string;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server: Started");
            System.out.println("Server: Waiting for client ...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server: Client Connected on "+clientSocket.getPort());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            Packet packet = (Packet) objectInputStream.readObject();
            System.out.println("Server: Client Type "+packet.getType());
            System.out.println("Server: Topic Name "+packet.getTopicName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //return true;
    }
}
