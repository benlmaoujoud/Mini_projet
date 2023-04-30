package com.example.mini_projet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    Socket socket ;
    ServerSocket serverSocket ;
    ConnectionManager sendReceive;

    public Server(ConnectionManager sendReceive) {
        this.sendReceive = sendReceive;
    }



    @Override
    public void run() {
        try{
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
            sendReceive = new ConnectionManager(socket);
            sendReceive.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ConnectionManager getSendReceive() {
        return sendReceive;
    }
}
