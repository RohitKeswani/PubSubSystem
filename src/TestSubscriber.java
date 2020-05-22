import Controller.*;

import models.Packets.SubscriberPacket;
import models.TypeOfPacket;

import java.util.List;
import java.util.Scanner;

public class TestSubscriber {
    public static void main(String[] args) {
        SubscriberController subscriberController = (SubscriberController)
                new Common().getConnectionObject(TypeOfPacket.Subscriber.toString());
        SubscriberPacket subscriberPacket = (SubscriberPacket)
                new Common().createPacketForCommunication(TypeOfPacket.Subscriber.toString());
        subscriberController.connectToServer(subscriberPacket);
        //printAvailableTopics();
        acceptInputToSend(subscriberController);
    }

    private static void acceptInputToSend(SubscriberController subscriberController) {
        SubscriberPacket subscriberPacket = (SubscriberPacket)
                new Common().createPacketForCommunication(TypeOfPacket.Subscriber.toString());
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the topic name from list");
        String topicName = sc.nextLine();
        subscriberPacket.setTopicName(topicName);
        subscriberController.connectToServer(subscriberPacket);
    }
}
