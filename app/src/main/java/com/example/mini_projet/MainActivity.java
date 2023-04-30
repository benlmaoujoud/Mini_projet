package com.example.mini_projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.*;
import android.os.Handler;
import android.os.Message;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WifiManager wifiManager;
    WifiP2pManager mManager ;
    WifiP2pManager.Channel mChannel;
    Brodcasting mReciver;
    IntentFilter mIntentFilter;
    List <WifiP2pDevice> peers = new ArrayList<>();
    String []deviceNameArray;
    WifiP2pDevice[] deviceArray;
    ListView listView;
    Button btndis;
    TextView myTextView;
    TextView editText;
    static final int MESSAGE_READ=1;

    ServerClass serverClass;
    ClientClass clientClass;
    SendReceive sendReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = findViewById(R.id.myTextView);

        wifiManager =(WifiManager)  getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(),null);
        mReciver = new Brodcasting(mManager,mChannel,this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        btndis = findViewById(R.id.disc);
        editText = findViewById(R.id.qosi);
        listView = findViewById(R.id.listView);
        btndis.setOnClickListener(v -> mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess() {
                myTextView.setText("Dicsovering started");
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(int i) {
                String errorMessage;
                switch (i) {
                    case WifiP2pManager.BUSY:
                        errorMessage = "Busy";
                        break;
                    case WifiP2pManager.ERROR:
                        errorMessage = "Internal Error.";
                        break;
                    case WifiP2pManager.P2P_UNSUPPORTED:
                        errorMessage = "wifi direct not exisst ";
                        break;
                    default:
                        errorMessage = "unknown";
                        break;
                }
                myTextView.setText("Discovering started Failed : "+ errorMessage);            }

        }));
        listView.setOnItemClickListener((parent, view, i, l) -> {
             final WifiP2pDevice device = deviceArray[i];
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;
            mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(),"Connect to "+device.deviceName,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(int i) {
                    Toast.makeText(getApplicationContext(),"Not connected Connect to ",Toast.LENGTH_SHORT).show();
                }
            });
        });

        String msg= "this is a qos 444444";
        sendReceive.write(msg.getBytes());

    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case MESSAGE_READ:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    Toast.makeText(getApplicationContext(),"TESTTT "+tempMsg,Toast.LENGTH_SHORT).show();

                    break;
            }
            return true;
        }
    });
    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){

                peers.addAll(peerList.getDeviceList());
                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index  = 0;
                for( WifiP2pDevice device : peerList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] =  device;
                    index++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }
            if(peers.size() == 0){
                 Toast.makeText(getApplicationContext(),"No Device Found",Toast.LENGTH_SHORT).show();

            }
        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;
            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
                myTextView.setText("i'm the server machine " );
                ServerClass server = new ServerClass();
                server.start();
            }else if(wifiP2pInfo.groupFormed){
                myTextView.setText("I'm the client");
                ClientClass client = new ClientClass(groupOwnerAddress);
                client.start();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReciver,mIntentFilter);
        mManager.discoverPeers(mChannel, null);
        mManager.requestPeers(mChannel, peerListListener);

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReciver);
        mManager.stopPeerDiscovery(mChannel, null);
    }
    public class ServerClass extends Thread{
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                serverSocket=new ServerSocket(8888);
                socket=serverSocket.accept();
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class SendReceive extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt)
        {
            socket=skt;
            try {
                inputStream=socket.getInputStream();
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] buffer=new byte[1024];
            int bytes;

            while (socket!=null)
            {
                try {
                    bytes=inputStream.read(buffer);
                    if(bytes>0)
                    {
                        handler.obtainMessage(MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class ClientClass extends Thread{
        Socket socket;
        String hostAdd;

        public  ClientClass(InetAddress hostAddress)
        {
            hostAdd=hostAddress.getHostAddress();
            socket=new Socket();
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd,8888),500);
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}