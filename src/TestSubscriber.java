import Controller.*;
import models.Packets.PublisherPacket;
import models.Packets.ServerPacket;
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
        List<String> topicList = subscriberController.connectToServer(subscriberPacket);
        printAvailableTopics(topicList);
        acceptInputToSend(subscriberController);
    }

    private static void printAvailableTopics(List<String> topicList) {
        System.out.println("Subscriber: Available topics are:");
        for(String topic : topicList){
            System.out.print(topic+"\t");
        }
        System.out.println();
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
