import models.Packet;

import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        //Packet packet = new Packet();
        //UUID uuid = UUID.randomUUID();
        //packet.setGuid(uuid.toString());
        for(int i = 0; i < 10; i++)
            System.out.println(UUID.randomUUID().toString());
    }
}
