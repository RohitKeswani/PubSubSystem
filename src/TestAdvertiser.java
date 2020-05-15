import Controller.AdvertiserController;
import models.Packet;

import java.util.UUID;

public class TestAdvertiser {
    public static void main(String[] args) {
        AdvertiserController advertiserController = new AdvertiserController("localhost", 5000);
        Packet packet = new Packet();
        packet.setGuid(UUID.randomUUID().toString());
        packet.setType("Advertiser");
        packet.setTopicName("Topic 1");
        advertiserController.connectToServer(packet);
    }
}
