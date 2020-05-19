import Controller.AdvertiserController;
import Controller.Common;
import models.Packets.AdvertiserPacket;
import models.TypeOfPacket;

import java.util.Scanner;

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
            AdvertiserPacket advertiserPacket = (AdvertiserPacket)
                    new Common().createPacketForCommunication(TypeOfPacket.Advertiser.toString());
            System.out.println("Enter the name of the topic");
            String topicName = sc.nextLine();
            advertiserPacket.setTopicName(topicName);
            advertiserController.connectToServer(advertiserPacket);
            System.out.println("What would like you to do? Select the option");
            System.out.println("1. Enter topic name to register with server");
            System.out.println("2. Exit");
            input = Integer.parseInt(sc.nextLine());
        }
    }
}
