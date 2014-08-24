package net.richardbondi.chainapi.address;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONObject;

public class Address extends ChainObject {
    private String hash;
    private Long balance;
    private Long received;
    private Long sent;
    private Long unconfirmedReceived;
    private Long unconfirmedSent;
    private Long unconfirmedBalance;

    public Address(JSONObject json) {
        this.hash = this.getString(json, "hash");
        this.balance = this.getLong(json, "balance");
        this.received = this.getLong(json, "received");
        this.sent = (Long) this.getLong(json, "sent");
        this.unconfirmedReceived = this.getLong(json, "unconfirmed_received");
        this.unconfirmedSent = this.getLong(json, "unconfirmed_sent");
        this.unconfirmedBalance = this.getLong(json, "unconfirmed_balance");

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getReceived() {
        return received;
    }

    public void setReceived(Long received) {
        this.received = received;
    }

    public Long getSent() {
        return sent;
    }

    public void setSent(Long sent) {
        this.sent = sent;
    }

    public Long getUnconfirmedReceived() {
        return unconfirmedReceived;
    }

    public void setUnconfirmedReceived(Long unconfirmedReceived) {
        this.unconfirmedReceived = unconfirmedReceived;
    }

    public Long getUnconfirmedSent() {
        return unconfirmedSent;
    }

    public void setUnconfirmedSent(Long unconfirmedSent) {
        this.unconfirmedSent = unconfirmedSent;
    }

    public Long getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(Long unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }
}

