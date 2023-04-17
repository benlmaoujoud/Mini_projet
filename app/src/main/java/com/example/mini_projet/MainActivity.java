package com.example.mini_projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.InetAddresses;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.*;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.net.InetAddress;
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
        btndis =(Button) findViewById(R.id.disc);

        listView = (ListView)findViewById(R.id.listView);
        btndis.setOnClickListener(v -> mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                myTextView.setText("Dicsovering started");
            }
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
                myTextView.setText("Dicsovering started Failed : "+ errorMessage);            }

        }));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
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
            }
        });
    }



    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index  = 0;
                for( WifiP2pDevice device : peerList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] =  device;
                    index++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }
            if(peers.size() == 0){
                Toast.makeText(getApplicationContext(),"No Device Found",Toast.LENGTH_SHORT).show();
            }

        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;
            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
                myTextView.setText("i'm the server machine " );
            }else if(wifiP2pInfo.groupFormed){
                myTextView.setText("I'm the client");
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


}