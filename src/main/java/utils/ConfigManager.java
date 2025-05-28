package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum ConfigManager {
    INSTANCE;

    private static final Properties PROPERTIES = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("./config.properties")) {
            PROPERTIES.load(fis);
        } catch (IOException e) {
            System.err.println("Couldn't load config properties!");
        }
    }

    public String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public Properties getAllProperties() {
        Properties props = new Properties();
        props.putAll(PROPERTIES);

        return props;
    }

}
