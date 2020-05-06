package Controller;

import models.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//write the logic for handling client
public class ClientController {
    private int port;
    private String address;

    public ClientController(String address, int port)
    {
        this.port = port;
        this.address = address;
    }

    public void connectToServer(Packet packet)
    {
        //write the code to connect client to server
        //send packet object instead of String msg
        boolean sentConnection=false;
        try{
            Socket socket = new Socket(address, port);
            System.out.println("Client: Connected");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //return sentConnection;
    }

}
