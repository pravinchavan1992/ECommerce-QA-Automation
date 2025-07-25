package com.config;

import com.Exceptions.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private final Properties prop;

    private static volatile ConfigurationManager instance = null;

    private ConfigurationManager()  {
        //File file = new File(System.getProperty("user.dir")+"/src/test/java/resources/config-qa.properties");
        String environment =  System.getProperty("environment");
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config-"+environment+".properties")) {
            if (input == null) {
                throw new FileNotFoundException("Config file not found in resources");
            }
            prop = new Properties();
            prop.load(input);
        }
        catch (IOException e) {
            throw new FileNotFoundException("Config file not found");
        }
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null){
            synchronized (ConfigurationManager.class) {
                if(instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
