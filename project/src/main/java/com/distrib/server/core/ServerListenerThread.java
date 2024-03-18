package com.distrib.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(ServerListenerThread.class);

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
                
                LOGGER.info("Connection accepted: " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }

        }
                
        super.run();
    }

}
