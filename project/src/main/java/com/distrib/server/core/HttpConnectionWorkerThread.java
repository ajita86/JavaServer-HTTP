package com.distrib.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // I/O for the socket
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

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
        
        // serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Closing all resources
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (Exception e) {}
            }
            
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {}
            }

            if(socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {}
            }

            System.out.println("Connection Closed");
        }
    }
}
