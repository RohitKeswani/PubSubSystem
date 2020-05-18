package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Common {
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
}
