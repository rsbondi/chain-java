package net.richardbondi.chainapi.transaction;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/*
  {
    "transaction_hash":"ac88...",
    "hex":"4067...",
    "text":"Yo Adam!",
    "sender_addresses": ["1Bj5..."],
    "receiver_addresses": ["1def..."]
  },

 */
public class OpReturn extends ChainObject{
    private String transactionHash;
    private String hex;
    private String text;
    private ArrayList<String> senderAddresses;
    private ArrayList<String> receiverAddresses;

    public OpReturn(JSONObject json) {
        this.transactionHash = this.getString(json, "transaction_hash");
        this.hex = this.getString(json, "hex");
        this.text = this.getString(json, "text");
        JSONArray senderAddresses = (JSONArray) json.get("sender_addresses");
        this.senderAddresses = new ArrayList<String>();
        if(senderAddresses != null)
            for(int i=0; i<senderAddresses.size(); i++) {
                this.senderAddresses.add(senderAddresses.get(i).toString());
            }
        JSONArray receiverAddresses = (JSONArray) json.get("receiver_addresses");
        this.receiverAddresses = new ArrayList<String>();
        if(receiverAddresses != null)
            for(int i=0; i<receiverAddresses.size(); i++) {
                this.receiverAddresses.add(receiverAddresses.get(i).toString());
            }

    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList getSenderAddresses() {
        return senderAddresses;
    }

    public void setSenderAddresses(ArrayList senderAddresses) {
        this.senderAddresses = senderAddresses;
    }

    public ArrayList getReceiverAddresses() {
        return receiverAddresses;
    }

    public void setReceiverAddresses(ArrayList receiverAddresses) {
        this.receiverAddresses = receiverAddresses;
    }
}
