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

            //manual read from inputStream

            // int _byte;

            // while ( (_byte = inputStream.read()) >= 0) {
            //     System.out.print((char)_byte);
            // }

            //sample request received from browser:
            // GET / HTTP/1.1
            // Host: localhost:8080
            // Connection: keep-alive
            // Cache-Control: max-age=0
            // sec-ch-ua: "Chromium";v="122", "Not(A:Brand";v="24", "Google Chrome";v="122"
            // sec-ch-ua-mobile: ?0
            // sec-ch-ua-platform: "macOS"
            // Upgrade-Insecure-Requests: 1
            // User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36
            // Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
            // Sec-Fetch-Site: none
            // Sec-Fetch-Mode: navigate
            // Sec-Fetch-User: ?1
            // Sec-Fetch-Dest: document
            // Accept-Encoding: gzip, deflate, br, zstd
            // Accept-Language: en-GB,en-US;q=0.9,en;q=0.8

            //write to outputStream

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
