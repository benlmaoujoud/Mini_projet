package com.example.mini_projet;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionManager extends Thread{

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    static final int MESSAGE_READ = 1;

    public ConnectionManager(Socket skt) throws IOException {
        socket = skt;


        try{
            inputStream = socket.getInputStream();
            outputStream=socket.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);
                    break;

            }
            return true;
        }
    });


    @Override
    public void run() {
        byte [] buffer = new byte[1024];
        int bytes;

        while (socket != null){
            try {
                bytes = inputStream.read(buffer);
                if(bytes >0){
                    handler.obtainMessage(MESSAGE_READ,bytes, -1, buffer).sendToTarget();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void write(byte[] bytes) throws IOException {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private QOS qos ;

    public QOS getQos() {
        return qos;
    }
    public void setQos(QOS qos) {
        this.qos = qos;
    }
    public boolean connectedToBest(){
        return false ;

    }

    public boolean isConnectedTo(Mobile device ){
        return false ;
    }




    public boolean disconnect(){
        return  false;
    }



}
