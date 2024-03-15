package com.distrib.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListenerThread (int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
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
        super.run();
    }

    

}
