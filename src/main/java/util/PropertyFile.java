package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile implements FrameworkConstants{

//    adding logger
    public static final Logger log = LogManager.getLogger(PropertyFile.class);

    public String  getValueProperty(String key) {
        FileInputStream file;
        Properties properties = null;
        try {
            file = new FileInputStream(PROPERTYFILE_PATH);
            properties=new Properties();
            properties.load(file);
        } catch (FileNotFoundException e) {
            log.error("config.properties not found: ", e);
        } catch (IOException e) {
            log.error("Error reading config.properties: ", e);
        }
        return properties.getProperty(key);
    }

}
