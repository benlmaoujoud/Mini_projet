package com.example.mini_projet;

import java.util.ArrayList;
import java.util.List;

public class MobileNetwork {
//+addDevice(device: Mobile):bool
//	+removeDevice(device: Mobile): void
//	+getDevices(): List<Mobile>
//	+getConnectedDevices(): List<Mobile>
//	+findBestConnection():Mobile
    List<Mobile> devices ;
    public boolean addDevice(Mobile device ){
        return false ;
    }

    public boolean removeDevice(Mobile device ){
        return false ;
    }

    public List<Mobile> getDevices() {
        return devices;
    }

    public void setDevices(List<Mobile> devices) {
        this.devices = devices;
    }

    public List<Mobile> getConnectedDevices(){
        return devices;
    }

    public Mobile findBestConnection(){
        return new Mobile();
    }


}
