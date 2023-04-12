package com.example.mini_projet;

public class ConnectionManager {


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

    public Mobile getConnectedDevice(){
        return new Mobile();
    }



    public boolean disconnect(){
        return  false;
    }
}
