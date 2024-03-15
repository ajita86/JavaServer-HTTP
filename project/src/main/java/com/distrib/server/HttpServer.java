package com.distrib.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.distrib.server.config.Configuration;
import com.distrib.server.config.ConfigurationManager;

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
            //creating a server socket object: used only to accept a connection
            ServerSocket serverSocket = new ServerSocket(conf.getPort());

            //connecting to the socket: this is the actual socket to be used
            Socket socket = serverSocket.accept();

            //I/O for the socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            //TODO: read from inputStram

            //TODO: write to outputStream

            final String CRLF = "\n\r"; // 13, 10

            String htmlString = "<html><head><title>This is head</title></head><body><h1>This is body</h1></body></html>";

            String response = 
                "HTTP1.1 200 OK" + CRLF +  // STATUS LINE : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                "Content-Length: " + htmlString.getBytes().length + CRLF + // HEADER
                CRLF + htmlString + CRLF + CRLF
                ;

            outputStream.write(response.getBytes());

            // Closing all resources
            inputStream.close();
            outputStream.close();

            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 
