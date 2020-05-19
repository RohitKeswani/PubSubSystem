import Controller.AdvertiserController;
import Controller.Common;
import models.Packet;
import models.TypeOfPacket;

import java.util.Scanner;
import java.util.UUID;

public class TestAdvertiser {
    public static void main(String[] args) {
        AdvertiserController advertiserController = (AdvertiserController)
                new Common().getConnectionObject(TypeOfPacket.Advertiser.toString());
        acceptInputToSendTopic(advertiserController);
    }

    private static void acceptInputToSendTopic(AdvertiserController advertiserController) {
        System.out.println("What would like you to do? Select the option");
        System.out.println("1. Enter topic name to register with server");
        System.out.println("2. Exit");
        Scanner sc = new Scanner(System.in);
        int input = Integer.parseInt(sc.nextLine());
        while(input != 2){
            Packet packet = new Packet();
            packet.setGuid(UUID.randomUUID().toString());
            packet.setType(TypeOfPacket.Advertiser);
            System.out.println("Enter the name of the topic");
            String topicName = sc.nextLine();
            packet.setTopicName(topicName);
            advertiserController.connectToServer(packet);
            System.out.println("What would like you to do? Select the option");
            System.out.println("1. Enter topic name to register with server");
            System.out.println("2. Exit");
            input = Integer.parseInt(sc.nextLine());
        }
    }
}
