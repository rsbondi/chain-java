package net.richardbondi.chainapi;

import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChainObject {
    public String getString(JSONObject json, String key) {
        return json.get(key) != null ? json.get(key).toString() : "";
    }

    public int getInt(JSONObject json, String key) {
        return json.get(key) == null ? null : Integer.valueOf(json.get(key).toString());
    }

    public Long getLong(JSONObject json, String key) {
        return json.get(key) == null ? null : (Long) json.get(key);
    }

    public Date getDate(JSONObject json, String key) {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).parse(json.get(key).toString().replace("Z", ""));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BigDecimal getBigDecimal(JSONObject json, String key) {
        return json.get(key) == null ? null : new BigDecimal(json.get(key).toString());
    }
}
