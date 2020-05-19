package Controller;

import models.TypeOfPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Common implements Controller{
    public Properties lookUpProperty(){
        try{
            File propertiesFile = new File("src/application.properties");
            FileReader reader = new FileReader(propertiesFile);
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            return sendSubscriberObject(address, serverPort);
        }
        else
        if(clientType.equals(TypeOfPacket.Advertiser.toString())){
            return sendAdvertiserObject(address, serverPort);
        }
        else
            return sendPublisherObject(address, serverPort);

    }

    private AdvertiserController sendAdvertiserObject(String address, int serverPort) {
        return new AdvertiserController(address, serverPort);
    }

    private SubscriberController sendSubscriberObject(String address, int serverPort) {
        return new SubscriberController(address, serverPort);
    }

    private PublisherController sendPublisherObject(String address, int serverPort) {
        return new PublisherController(address, serverPort);
    }
}
