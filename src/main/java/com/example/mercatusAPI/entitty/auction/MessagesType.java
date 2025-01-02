package com.example.mercatusAPI.entitty.auction;

public enum MessagesType {
    BID("BID"),
    MESSAGE("MESSAGE");

    private String type;

    MessagesType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
