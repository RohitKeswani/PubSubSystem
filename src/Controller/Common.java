package Controller;

import models.Packets.*;
import models.TypeOfPacket;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class Common implements Controller {
    public Properties lookUpProperty(){
        try{
            File propertiesFile = new File("src/application.properties");
            FileReader reader = new FileReader(propertiesFile);
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Controller getConnectionObject(String clientType) {
        Properties properties = new Common().lookUpProperty();
        String address = properties.getProperty("serverAddress");
        int serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        if(clientType.equals(TypeOfPacket.Subscriber.toString())){
            return new SubscriberController(address, serverPort);
        }
        else
        if(clientType.equals(TypeOfPacket.Advertiser.toString())){
            return new AdvertiserController(address, serverPort);
        }
        else
            return new PublisherController(address, serverPort);

    }

    public Packet createPacketForCommunication(String clientType) {
        if(clientType.equals(TypeOfPacket.Advertiser.toString()))
            return generateAdvertiserPacket();
        else
            if(clientType.equals(TypeOfPacket.Publisher.toString()))
                return generatePublisherPacket();
            else
                if(clientType.equals(TypeOfPacket.Subscriber.toString()))
                    return generateSubscriberPacket();
                else
                    return generateServerPacket();
    }

    private ServerPacket generateServerPacket() {
        ServerPacket serverPacket = new ServerPacket();
        serverPacket.setGuid(UUID.randomUUID().toString());
        serverPacket.setType(TypeOfPacket.Server);
        return serverPacket;
    }

    private SubscriberPacket generateSubscriberPacket() {
        SubscriberPacket subscriberPacket = new SubscriberPacket();
        subscriberPacket.setGuid(UUID.randomUUID().toString());
        subscriberPacket.setType(TypeOfPacket.Subscriber);
        return subscriberPacket;
    }

    private PublisherPacket generatePublisherPacket() {
        PublisherPacket publisherPacket = new PublisherPacket();
        publisherPacket.setGuid(UUID.randomUUID().toString());
        publisherPacket.setType(TypeOfPacket.Publisher);
        return publisherPacket;
    }

    private AdvertiserPacket generateAdvertiserPacket() {
        AdvertiserPacket advertiserPacket = new AdvertiserPacket();
        advertiserPacket.setGuid(UUID.randomUUID().toString());
        advertiserPacket.setType(TypeOfPacket.Advertiser);
        return advertiserPacket;
    }
}
