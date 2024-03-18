package com.distrib.server;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import com.distrib.server.config.Configuration;
import com.distrib.server.config.ConfigurationManager;
import com.distrib.server.core.ServerListenerThread;

import ch.qos.logback.classic.Logger;

/**
 * 
 * Driver class for the HTTP server
 *
 */
public class HttpServer 
{

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(HttpServer.class);
    public static void main( String[] args )
    {
        LOGGER.info( "Server starting..." );

        //Using configuration manager
        ConfigurationManager.getInstance().loadConfigurationFile("project/src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrConfig();

        LOGGER.info("Using port: " + conf.getPort());

        // //Using command line 
        // int portNo;
        // if (args.length == 0) {
        //     System.out.println("Error: No command-line arguments provided.");
        //     System.out.println("Command-line arguments needed: <port number>");
        // } else {
        //     portNo = Integer.parseInt(args[0]);
        //     conf.setPort(portNo);
        // }

        try {

            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
} 
