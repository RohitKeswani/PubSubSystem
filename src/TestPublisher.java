import Controller.PublisherController;
import models.Packet;

import java.util.UUID;

public class TestPublisher {
    public static void main(String[] args) {
        PublisherController publisherController = new PublisherController("localhost", 5000);
        Packet packet = new Packet();
        packet.setGuid(UUID.randomUUID().toString());
        packet.setType("Publisher");
        publisherController.connectToServer(packet);
    }
}
