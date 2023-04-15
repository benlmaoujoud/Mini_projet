package com.example.mini_projet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread{
    Socket socket;
    String hostAdd ;

    public Client(InetAddress inet) {
        hostAdd = inet.getHostAddress();
        socket = new Socket();
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(hostAdd,8888),555);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
