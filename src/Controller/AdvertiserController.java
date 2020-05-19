package Controller;

import Controller;
import models.publusPacket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AdvertiserController implements Controller {
    private int port;
    private String address;

    public AdvertiserController(String address, int port)
    {
        this.port = port;
        this.address = address;
    }

    public void connectToServer(publusPacket packet)
    {
        try{
            Socket socket = new Socket(address, port);
            System.out.println("Advertiser: Connected");
            System.out.println("Advertiser: Sending topic "+packet.getTopicName()+" now.");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
            System.out.println("Advertiser: Topic sent to server.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
