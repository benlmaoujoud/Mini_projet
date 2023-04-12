package com.example.mini_projet;
import android.annotation.SuppressLint;
import android.net.TrafficStats;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Mobile {
    private String ssid ;
    private double signalStrength;
    private double batteryLevel;
    private double debit;
    private int receivedMessages;
    private int transmittedMessages;
    private boolean isConnected;
    private double rxBytesStart;
    private double txBytesStart;
    private double startTime;
    public void startSpeedTest() {
        // This method is empty since we don't need to do anything to prepare for the speed test
    }
    public String endSpeedTest() {
        // Download a file to measure the download speed
        try {
            URL url = new URL("https://speedtest.net/images/telecom-bg.png");
            URLConnection connection = url.openConnection();
            long startTime = System.currentTimeMillis();
            connection.connect();
            try (InputStream inputStream = connection.getInputStream()) {
                long endTime = System.currentTimeMillis();
                long contentLength = connection.getContentLengthLong();
                double downloadSpeed = (double) contentLength / ((endTime - startTime) / 1000.0);
                String downloadSpeedString = formatSpeed(downloadSpeed);
                return "Download speed: " + downloadSpeedString + "/s";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @SuppressLint("DefaultLocale")
    private String formatSpeed(double speed) {
        String[] units = {"B/s", "KB/s", "MB/s", "GB/s"};
        int unitIndex = 0;
        while (speed > 1024 && unitIndex < units.length - 1) {
            speed /= 1024;
            unitIndex++;
        }
        return String.format("%.2f", speed) + " " + units[unitIndex];
    }





    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public int getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(int receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public int getTransmittedMessages() {
        return transmittedMessages;
    }

    public void setTransmittedMessages(int transmittedMessages) {
        this.transmittedMessages = transmittedMessages;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }


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

    public boolean connectTo(Mobile device){
        return false ;
    }
    public boolean disconnect(){
        return false ;
    }
}
