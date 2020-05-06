import Controller.ClientController;
import models.Packet;

import java.util.UUID;

public class TestClient {
    public static void main(String[] args) {
        ClientController clientController = new ClientController("localhost", 5000);
        Packet packet = new Packet();
        packet.setGuid(UUID.randomUUID().toString());
        packet.setType("Publisher");
        clientController.connectToServer(packet);
    }
}
