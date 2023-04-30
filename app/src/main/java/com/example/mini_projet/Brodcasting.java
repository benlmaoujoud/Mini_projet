package com.example.mini_projet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

public class Brodcasting extends BroadcastReceiver {

    /*
    * logic : khass broadcast to every device connected to wifi
    * objet string
    *  */
    private WifiP2pManager mManager ;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public Brodcasting(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity mActivity) {
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mActivity = mActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wi-Fi Direct mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context,"WIFI is ON",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,"WIFI is OFF",Toast.LENGTH_SHORT).show();
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed! We should proba bly do something about
            // that.
            if(mManager!=null){
                mManager.requestPeers(mChannel,mActivity.peerListListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if(mManager!=null){ 
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if(networkInfo.isConnected()){
                    mManager.requestConnectionInfo(mChannel,mActivity.connectionInfoListener);
                }else{
                    mActivity.myTextView.setText("Device disconnect");
                }
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {


        }
    }
}