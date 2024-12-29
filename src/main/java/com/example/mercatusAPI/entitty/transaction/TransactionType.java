package com.example.mercatusAPI.entitty.transaction;

public enum TransactionType {
    ITEM_PURCHASE("ITEM_PURCHASE"),
    ITEM_TRANSFER("ITEM_TRANSFER");

    private String type;

    TransactionType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
