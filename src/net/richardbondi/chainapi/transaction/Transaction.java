package net.richardbondi.chainapi.transaction;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Transaction extends ChainObject{
    private String hash;
    private String blockHash;
    private Long blockHeight;
    private Date blockTime;
    private int confirmations;
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    private Long fees;
    private Long amount;

    public Transaction(JSONObject json) {
        this.hash = this.getString(json, "hash");
        this.blockHash = this.getString(json, "block_hash");
        this.blockHeight = this.getLong(json, "block_height");
        this.confirmations = this.getInt(json, "confirmations");
        this.blockTime = this.getDate(json,"block_time" );
        JSONArray inputs=(JSONArray)json.get("inputs");
        this.inputs = new ArrayList<Input>();
        for(int i=0; i<inputs.size(); i++) {
            JSONObject input = (JSONObject)inputs.get(i);
            this.inputs.add(new Input(input));
        }
        JSONArray outputs=(JSONArray)json.get("outputs");
        this.outputs = new ArrayList<Output>();
        for(int o=0; o<outputs.size(); o++) {
            JSONObject output = (JSONObject)outputs.get(o);
            this.outputs.add(new Output(output));
        }
        this.fees = this.getLong(json, "fees");
        this.amount = this.getLong(json, "amount");
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public Date getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Date blockTime) {
        this.blockTime = blockTime;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    public ArrayList<Input> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Input> inputs) {
        this.inputs = inputs;
    }

    public ArrayList<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList<Output> outputs) {
        this.outputs = outputs;
    }

    public Long getFees() {
        return fees;
    }

    public void setFees(Long fees) {
        this.fees = fees;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
