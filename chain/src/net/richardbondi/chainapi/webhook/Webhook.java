package net.richardbondi.chainapi.webhook;

import net.richardbondi.chainapi.ChainObject;
import org.json.simple.JSONObject;

public class Webhook extends ChainObject{
    private String id;
    private String url;

    public Webhook(JSONObject json) {
        this.id = this.getString(json, "id");
        this.url = this.getString(json, "url");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
