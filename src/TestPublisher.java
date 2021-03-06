import Controller.Common;
import Controller.PublisherController;
import models.Packets.PublisherPacket;
import models.TypeOfPacket;

import java.util.List;
import java.util.Scanner;

public class TestPublisher {
    public static void main(String[] args) {
        PublisherController publisherController = (PublisherController)
                new Common().getConnectionObject(TypeOfPacket.Publisher.toString());
        List<String> topicList = pingServerToGetTopics(publisherController);
        printAvailableTopics(topicList);
        createContentAndSend(publisherController);
    }

    private static void createContentAndSend(PublisherController publisherController) {
        PublisherPacket publisherPacket = (PublisherPacket)
                new Common().createPacketForCommunication(TypeOfPacket.Publisher.toString());
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the topic name from list");
        String topicName = sc.nextLine();
        System.out.println("Enter the text for topic you would like to publish");
        String content = sc.nextLine();
        publisherPacket.setTopicName(topicName);
        publisherPacket.setContent(content);
        publisherController.connectToServer(publisherPacket);
    }

    private static void printAvailableTopics(List<String> topicList) {
        System.out.println("Publisher: Available topics are:");
        for(String topic : topicList){
            System.out.print(topic+"\t");
        }
        System.out.println();
    }

    private static List<String> pingServerToGetTopics(PublisherController publisherController) {
        PublisherPacket publisherPacket = (PublisherPacket)
                new Common().createPacketForCommunication(TypeOfPacket.Publisher.toString());
        return publisherController.connectToServer(publisherPacket);
    }
}
