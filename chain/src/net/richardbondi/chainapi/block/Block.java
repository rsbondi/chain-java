package net.richardbondi.chainapi.block;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Block extends ChainObject{
    private String hash;
    private String previousBlockHash;
    private Long height;
    private int confirmations;
    private String merkleRoor;
    private Date time;
    private Long nonce;
    private BigDecimal difficulty;
    private ArrayList<String> transactionHashes;

    public Block(JSONObject json) {
        this.hash = this.getString(json, "hash");
        this.previousBlockHash = this.getString(json, "previous_block_hash");
        this.height = this.getLong(json, "height");
        this.confirmations = this.getInt(json, "confirmations");
        this.merkleRoor = this.getString(json, "merkle_root");
        this.time = this.getDate(json, "time");
        this.nonce = this.getLong(json, "nonce");
        this.difficulty = this.getBigDecimal(json, "difficulty");
        this.transactionHashes = new ArrayList<String>();
        JSONArray hashes = (JSONArray) json.get("transaction_hashes");
        for(int i=0; i<hashes.size(); i++) {
            this.transactionHashes.add(hashes.get(i).toString());
        }
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    public String getMerkleRoor() {
        return merkleRoor;
    }

    public void setMerkleRoor(String merkleRoor) {
        this.merkleRoor = merkleRoor;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<String> getTransactionHashes() {
        return transactionHashes;
    }

    public void setTransactionHashes(ArrayList<String> transactionHashes) {
        this.transactionHashes = transactionHashes;
    }
}
