import Controller.AdvertiserController;
import models.Packet;
import models.TypeOfPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

public class TestAdvertiser {
    public static void main(String[] args) {
        AdvertiserController advertiserController = getAdvertiserObjectToConnect();
        acceptInputToSendTopic(advertiserController);
    }

    private static void acceptInputToSendTopic(AdvertiserController advertiserController) {
        System.out.println("What would like you to do? Select the option");
        System.out.println("1. Enter topic name to register with server");
        System.out.println("2. Exit");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        while(input != 2){
            System.out.println("What would like you to do? Select the option");
            System.out.println("1. Enter topic name to register with server");
            System.out.println("2. Exit");
            Packet packet = new Packet();
            packet.setGuid(UUID.randomUUID().toString());
            packet.setType(TypeOfPacket.Advertiser);
            System.out.println("Enter the name of the topic");
            String topicName = sc.nextLine();
            packet.setTopicName(topicName);
            advertiserController.connectToServer(packet);
        }
    }

    private static AdvertiserController getAdvertiserObjectToConnect() {
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
        return new AdvertiserController(address, serverPort);
    }
}
