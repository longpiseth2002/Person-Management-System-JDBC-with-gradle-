package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class    PropertyUtils {
    public static Properties loadProperty() {
        String propertiesLocation = "src/main/resources/application.properties";
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(propertiesLocation))
        ) {
            Properties properties = new Properties();
            properties.load(bufferedReader);
            return properties;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        Properties properties = loadProperty();
//        System.out.println(properties.getProperty("USERNAME"));
//        System.out.println(properties.getProperty("PASSWORD"));
//    }

}
