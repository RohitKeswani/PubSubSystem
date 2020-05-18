import Controller.PublisherController;
import models.Packet;
import models.TypeOfPacket;

import java.util.UUID;

public class TestPublisher {
    public static void main(String[] args) {
        PublisherController publisherController = new PublisherController("localhost", 5000);
        Packet packet = new Packet();
        packet.setGuid(UUID.randomUUID().toString());
        packet.setType(TypeOfPacket.Publisher);
        publisherController.connectToServer(packet);
    }
}
