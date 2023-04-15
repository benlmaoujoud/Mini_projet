package com.example.mini_projet;

public class QOS {


    private String ssid ;
    private int puissSignal ;
    private int batteryLevel;
    private int nbrMessageRecu;
    private int nbrMeassageSent;

    private int debit;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getPuissSignal() {
        return puissSignal;
    }

    public void setPuissSignal(int puissSignal) {
        this.puissSignal = puissSignal;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getNbrMessageRecu() {
        return nbrMessageRecu;
    }

    public void setNbrMessageRecu(int nbrMessageRecu) {
        this.nbrMessageRecu = nbrMessageRecu;
    }

    public int getNbrMeassageSent() {
        return nbrMeassageSent;
    }

    public void setNbrMeassageSent(int nbrMeassageSent) {
        this.nbrMeassageSent = nbrMeassageSent;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }
}
