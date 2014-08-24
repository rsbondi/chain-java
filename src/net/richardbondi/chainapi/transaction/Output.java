package net.richardbondi.chainapi.transaction;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Output extends ChainObject{
    private String transactionHash;
    private int outputIndex;
    private Long value;
    private ArrayList<String> addresses;
    private String script;
    private String scriptHex;
    private String scriptType;
    private int requiredSignatures;
    private boolean spent;

    public Output(JSONObject json) {
        this.transactionHash = this.getString(json, "transaction_hash");
        this.outputIndex = this.getInt(json, "output_index");
        this.value = this.getLong(json, "value");
        this.addresses = new ArrayList<String>();
        JSONArray addresses = (JSONArray) json.get("addresses");
        if(addresses != null)
            for(int i=0; i<addresses.size(); i++) {
                this.addresses.add(addresses.get(i).toString());
            }
        this.script = this.getString(json, "script");
        this.scriptHex = this.getString(json, "script_hex");
        this.scriptType = this.getString(json, "script_type");

        this.requiredSignatures = this.getInt(json, "required_signatures");
        this.spent = "true".equals(this.getString(json, "spent"));
    }
    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptHex() {
        return scriptHex;
    }

    public void setScriptHex(String scriptHex) {
        this.scriptHex = scriptHex;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public int getRequiredSignatures() {
        return requiredSignatures;
    }

    public void setRequiredSignatures(int requiredSignatures) {
        this.requiredSignatures = requiredSignatures;
    }

    public boolean isSpent() {
        return spent;
    }

    public void setSpent(boolean spent) {
        this.spent = spent;
    }
}

