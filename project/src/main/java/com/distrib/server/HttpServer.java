package com.distrib.server;

import java.io.IOException;

import com.distrib.server.config.Configuration;
import com.distrib.server.config.ConfigurationManager;
import com.distrib.server.core.ServerListenerThread;

/**
 * 
 * Driver class for the HTTP server
 *
 */
public class HttpServer 
{
    public static void main( String[] args )
    {
        System.out.println( "Server starting..." );

        //Using configuration manager
        ConfigurationManager.getInstance().loadConfigurationFile("project/src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrConfig();

        System.out.println("Using port: " + conf.getPort());

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
