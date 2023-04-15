package com.example.mini_projet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Mobile extends Activity {

    private Context context;

    public Mobile(Context context) {
        this.context = context;
    }

    private String ssid;
    private double signalStrength;
    private double batteryLevel;
    private double debit;
    private int receivedMessages;
    private int transmittedMessages;
    private boolean isConnected;

    public String getSsid(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }


    public double getSignalStrength(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            return wifiInfo.getRssi();
        } else {
            return 0;
        }
    }


    public double getBatteryLevel(Context context) {
        Intent batteryIntent = context.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return (double) (level * 100 / (float) scale);
    }


    public double getDebit() {
        return (double) (TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes());
    }


    public int getReceivedMessages() {
        return receivedMessages;
    }

    public int getTransmittedMessages() {
        return transmittedMessages;
    }


    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }






    public boolean connectTo(Mobile device){
        return false ;
    }
    public boolean disconnect(){
        return false ;
    }

    @NotNull
    @Override
    public String toString() {
        return "Mobile{" +
                "ssid='" + ssid + '\'' +
                ", signalStrength=" + signalStrength +
                ", batteryLevel=" + batteryLevel +
                ", debit=" + debit +
                ", receivedMessages=" + receivedMessages +
                ", transmittedMessages=" + transmittedMessages +
                ", isConnected=" + isConnected +
                '}';
    }
}
