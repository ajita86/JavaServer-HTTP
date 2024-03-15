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

            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                
                //connecting to the socket: this is the actual socket to be used
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted...");

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
                
        super.run();
    }

}
