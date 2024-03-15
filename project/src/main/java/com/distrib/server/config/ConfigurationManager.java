package com.distrib.server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.distrib.server.util.Json;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigurationManager {

    private static ConfigurationManager configurationManager;
    private static Configuration currentConfig;
    
    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if(configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    /*
     * Used to load a configuration by the path provided
     */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
       }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ( (i = fileReader.read()) != -1)
                sb.append((char)i);
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode config = null;
        try {
            config = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the configuration file", e);
        }
        try {
            currentConfig = Json.fromJson(config, Configuration.class);
        } catch (Exception e) {
            throw new HttpConfigurationException("Error parsing the configuration file, internal", e);
        }
     
    }

    /*
     * Returns the current loaded configuration
     */
    public Configuration getCurrConfig() {
        if (currentConfig == null) {
            throw new HttpConfigurationException("No current confirguartion set");
        }
        return currentConfig;
    }

}
