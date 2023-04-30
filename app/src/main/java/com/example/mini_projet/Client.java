package com.example.mini_projet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread{
    Socket socket;
    String hostAdd ;
    ConnectionManager sr;

    public Client(InetAddress inet , ConnectionManager sr) {
        hostAdd = inet.getHostAddress();
        socket = new Socket();
        this.sr =sr;
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(hostAdd,8888),555);
            sr = new ConnectionManager(socket);
            sr.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ConnectionManager getSr() {
        return sr;
    }
}
