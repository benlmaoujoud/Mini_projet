package com.example.mini_projet;

import java.util.ArrayList;
import java.util.List;

public class MobileNetwork {



//+addDevice(device: Mobile):bool
//	+removeDevice(device: Mobile): void
//	+getDevices(): List<Mobile>
//	+getConnectedDevices(): List<Mobile>
//	+findBestConnection():Mobile


    List<Mobile> networks ;

    public List<Mobile> getNetworks() {
        return networks;
    }
    public boolean addNetwork(Mobile net ){
        return false;
    }

    public boolean removeNetwork(Mobile net ){
        return false ;
    }



    public void setNetworks(List<Mobile> devices) {
        this.networks = devices;
    }

    public List<Mobile> getConnectedDevices(){
        return networks;
    }

    //must return devcie
    public void findBestConnection(){

    }


}
