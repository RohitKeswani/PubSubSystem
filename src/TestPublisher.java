import Controller.PublisherController;
import models.Packet;
import models.TypeOfPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

public class TestPublisher {
    public static void main(String[] args) {
        PublisherController publisherController = getPublisherObjectToSend();
        List<String> topicList = pingServerToGetTopics(publisherController);
        printAvailableTopics(topicList);
        createContentAndSend(publisherController);
    }

    private static void createContentAndSend(PublisherController publisherController) {
        Packet packet = new Packet();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the topic name from list");
        String topicName = sc.nextLine();
        System.out.println("Enter the text for topic you would like to publish");
        String content = sc.nextLine();
        packet.setType(TypeOfPacket.Publisher);
        packet.setTopicName(topicName);
        packet.setContent(content);
        publisherController.connectToServer(packet);
    }

    private static void printAvailableTopics(List<String> topicList) {
        System.out.println("Publisher: Available topics are:");
        for(String topic : topicList){
            System.out.print(topic+"\t");
        }
        System.out.println();
    }

    private static List<String> pingServerToGetTopics(PublisherController publisherController) {
        Packet packet = new Packet();
        packet.setGuid(UUID.randomUUID().toString());
        packet.setType(TypeOfPacket.Publisher);
        return publisherController.connectToServer(packet);
    }

    private static PublisherController getPublisherObjectToSend() {
        String address = null;
        int serverPort = 0;
        try{
            File propertiesFile = new File("src/application.properties");
            FileReader reader = new FileReader(propertiesFile);
            Properties properties = new Properties();
            properties.load(reader);
            address = properties.getProperty("serverAddress");
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PublisherController(address, serverPort);
    }
}
