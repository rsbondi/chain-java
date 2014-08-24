package net.richardbondi.chainapi.webhook;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONObject;

public class WebhookEvent extends ChainObject{
    private String id;
    private String webhookId;
    private String event;
    private String blockChain;
    private String address;
    private int confirmations;

    public  WebhookEvent () {

    }

    public WebhookEvent(JSONObject json) {
        this.id = this.getString(json, "id");
        this.webhookId = this.getString(json, "webhook_id");
        this.event = this.getString(json, "event");
        this.blockChain = this.getString(json, "block_chain");
        this.address = this.getString(json, "address");
        this.confirmations = this.getInt(json, "confirmations");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(String blockChain) {
        this.blockChain = blockChain;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }
}
