package net.richardbondi.chainapi.transaction;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Input extends ChainObject{
    private String transactionHash;
    private String outputHash;
    private int outputIndex;
    private Long value;
    private ArrayList<String> addresses;
    private String scriptSignature;

    public Input(JSONObject json) {
        this.transactionHash = this.getString(json, "transaction_hash");
        this.outputHash = this.getString(json, "output_hash");
        this.outputIndex = this.getInt(json, "output_index");
        this.value = this.getLong(json, "value") ;
        this.addresses = new ArrayList<String>();
        JSONArray addresses = (JSONArray) json.get("addresses");
        for(int i=0; i<addresses.size(); i++) {
            this.addresses.add(addresses.get(i).toString());
        }
        this.scriptSignature = this.getString(json, "script_signature");

    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getOutputHash() {
        return outputHash;
    }

    public void setOutputHash(String outputHash) {
        this.outputHash = outputHash;
    }

    public int getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(int outputIndex) {
        this.outputIndex = outputIndex;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }

    public String getScriptSignature() {
        return scriptSignature;
    }

    public void setScriptSignature(String scriptSignature) {
        this.scriptSignature = scriptSignature;
    }
}

