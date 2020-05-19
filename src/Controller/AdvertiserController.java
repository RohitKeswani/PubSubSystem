package Controller;

import models.Packets.AdvertiserPacket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AdvertiserController implements Controller {
    private int port;
    private String address;

    public AdvertiserController(String address, int port)
    {
        this.port = port;
        this.address = address;
    }

    public void connectToServer(AdvertiserPacket advertiserPacket)
    {
        try{
            Socket socket = new Socket(address, port);
            System.out.println("Advertiser: Connected");
            System.out.println("Advertiser: Sending topic "+advertiserPacket.getTopicName()+" now.");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(advertiserPacket);
            System.out.println("Advertiser: Topic sent to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
