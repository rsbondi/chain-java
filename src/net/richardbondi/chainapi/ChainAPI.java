package net.richardbondi.chainapi;

import net.richardbondi.chainapi.address.Address;
import net.richardbondi.chainapi.block.Block;
import net.richardbondi.chainapi.transaction.OpReturn;
import net.richardbondi.chainapi.transaction.Output;
import net.richardbondi.chainapi.transaction.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.richardbondi.chainapi.webhook.Webhook;
import net.richardbondi.chainapi.webhook.WebhookEvent;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChainAPI {

    private String keyId;
    private String keySecret;
    public final static String VERSION = "v1";
    public final static String MAIN_NET_API_URL = "https://api.chain.com/" + ChainAPI.VERSION+ "/bitcoin/";
    public final static String TEST_NET_API_URL = "https://api.chain.com/" + ChainAPI.VERSION+ "/testnet3/";
    public final static String WEBHOOK_URL = "https://api.chain.com/" + ChainAPI.VERSION+ "/webhooks";
    private String baseUrl;

    /**
     * initialize a ChainAPI object on the main bitcoin network
     * @param keyId the developer id for access to the chain.com api
     * @param keySecret the developer secret for access to the chain.com api
     */
    public ChainAPI(String keyId, String keySecret) {
        this.init(keyId, keySecret, false);
    }

    /**
     * initialize a ChainAPI object on the main or test network
     * @param keyId the developer id for access to the chain.com api
     * @param keySecret the developer secret for access to the chain.com api
     * @param test true if using testnet
     */
    public ChainAPI(String keyId, String keySecret, boolean test) {
        this.init(keyId, keySecret, test);
    }

    private void init(String keyId, String keySecret, boolean test) {
        this.keyId = keyId;
        this.keySecret = keySecret;
        this.baseUrl = test ? ChainAPI.TEST_NET_API_URL : ChainAPI.MAIN_NET_API_URL;
    }

    /**
     * basic balance details for an address
     * @param address the address whose details you want to retrieve
     * @return the address details
     */
    public Address getAddress(String address) {
        try {
            URL url = new URL(this.baseUrl + "addresses/" + address);
            JSONObject obj=(JSONObject)this._apiRequest(url);
            return new Address(obj);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * basic balance details for multiple addresses
     * @param address
     * @return list of address details
     */
    public List<Address> getAddress(String[] address) {
        try {
            URL url = new URL(this.baseUrl+ "addresses/" + ChainAPI.addressSlug(address));
            JSONArray array=(JSONArray)this._apiRequest(url);
            List<Address> addresses = new ArrayList<Address>();
            for(int a=0; a<array.size(); a++) {
                JSONObject json = (JSONObject)array.get(a);
                addresses.add(new Address(json));
            }
            return addresses;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * list transactions for multiple addresses with default limit
     * @param address array of addresses
     * @return list of transactions
     */
    public List<Transaction> getAddressTransactions(String[] address) {
        return this.getAddressTransactions(this.addressSlug(address), 50);
    }

    /**
     * list transactions for multiple addresses with specified limit
     * @param address array of addresses
     * @param limit the maximum number of addresses to return
     * @return list of transactions
     */
    public List<Transaction> getAddressTransactions(String[] address, int limit) {
        return this.getAddressTransactions(this.addressSlug(address), limit);
    }

    /**
     * list transactions for a single addresses with default limit
     * @param address array of addresses
     * @return list of transactions
     */
    public List<Transaction> getAddressTransactions(String address) {
        return this.getAddressTransactions(address, 50);
    }

    /**
     * list transactions for a single addresses with specified limit
     * @param address array of addresses
     * @param limit the maximum number of addresses to return
     * @return list of transactions
     */
    public List<Transaction> getAddressTransactions(String address, int limit) {
        try {
            URL url = new URL(this.baseUrl + "addresses/" + address +"/transactions");
            JSONArray array=(JSONArray)this._apiRequest(url);
            List<Transaction> transactions = new ArrayList<Transaction>();
            for(int o=0; o<array.size(); o++) {
                JSONObject json = (JSONObject)array.get(o);
                transactions.add(new Transaction(json));
            }
            return transactions;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get unspent outputs for multiple addresses
     * @param address the address
     * @return the outputs
     */
    public List<Output> getUnspents(String[] address) {
        return this.getUnspents(this.addressSlug(address));
    }

    /**
     * get unspent outputs for an address
     * @param address the address
     * @return the outputs
     */
    public List<Output> getUnspents(String address) {
        try {
            URL url = new URL(this.baseUrl + "addresses/" + address +"/unspents");
            JSONArray array=(JSONArray)this._apiRequest(url);
            List<Output> outputs = new ArrayList<Output>();
            for(int o=0; o<array.size(); o++) {
                JSONObject output = (JSONObject)array.get(o);
                outputs.add(new Output(output));
            }
            return outputs;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get OP_RETURN for an address
     * @param address the address
     * @return OP_RETURN
     */
    public List<OpReturn> getAddressOpReturns(String address) {
        return this._geOpReturns("addresses", address);
    }

    /**
     * get a transaction from a hash
     * @param hash the transaction hash
     * @return the transaction
     */
    public Transaction getTransaction(String hash) {
        try {
            URL url = new URL(this.baseUrl+ "transactions/" + hash);
            JSONObject json = (JSONObject)this._apiRequest(url);
            return new Transaction(json);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get OP_RETURN for a transaction
     * @param hash transaction hash
     * @return OP_RETURN
     */
    public OpReturn getTransactionOpReturn(String hash) {
        try {
            URL url = new URL(this.baseUrl+ "transactions/" + hash +"/op-return");
            JSONObject json = (JSONObject)this._apiRequest(url);
            return new OpReturn(json);
    } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * send a transactioin
     * @param transaction a signed hex transaction
     * @return the newly created transaction hash
     */
    public String sendTransaction(String transaction) {
        try {
            URL url = new URL(this.baseUrl + "transactions");
            JSONObject body = new JSONObject();
            body.put("hex", transaction);
            JSONObject json = (JSONObject) this._apiRequest(url, "POST", body.toJSONString());
            return json.get("transaction_hash") != null ? json.get("transaction_hash").toString() : null;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get a block from hash
     * @param hash the block hash
     * @return the block
     */
    public Block getBlock(String hash) {
        return this._getBlock(hash);
    }

    /**
     * get a block from the block height
     * @param height the block height
     * @return the block
     */
    public Block getBlock(Long height) {
        return this._getBlock(height.toString());
    }

    private Block _getBlock(String hash) {
        try {
            URL url = new URL(this.baseUrl + "blocks/" + hash);
            JSONObject json = (JSONObject) this._apiRequest(url);
            return new Block(json);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get OP_RETURNs for a block by block height
     * @param height block height
     * @return list of OP_RETURN
     */
    public  List<OpReturn> getBlockOpReturns(Long height) {
        return this._geOpReturns("blocks", height.toString());
    }

    /**
     * get OP_RETURNs for a block by block hash
     * @param hash the block hash
     * @return list of OP_RETURNs
     */
    public  List<OpReturn> getBlockOpReturns(String hash) {
        return this._geOpReturns("blocks", hash);
    }

    /**
     * create a webhook
     * @param url the url where the webhook is directed
     * @return a webhook with newly created id
     */
    public Webhook createWebhook(String url) {
        return this.createWebhook("", url);
    }

    /**
     * create a webhook
     * @param id user specified id
     * @param url the url where the webhook is directed
     * @return a webhook with newly created id
     */
    public Webhook createWebhook(String id, String url) {
        try {
            URL apiurl = new URL(ChainAPI.WEBHOOK_URL);
            JSONObject body = new JSONObject();
            if(id != null && ! "".equals(id))
                body.put("id", id);
            body.put("url", url);

            JSONObject json = (JSONObject) this._apiRequest(apiurl, "POST", body.toJSONString());
            return new Webhook(json);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * list all webhooks tied to the developer id
     * @return list of webhooks
     */
    public List<Webhook> listWebhooks() {
        try {
            URL url = new URL(ChainAPI.WEBHOOK_URL);
            JSONArray array=(JSONArray)this._apiRequest(url);
            List<Webhook> webhooks = new ArrayList<Webhook>();
            for(int h=0; h<array.size();h++) {
                JSONObject webhook = (JSONObject)array.get(h);
                webhooks.add(new Webhook(webhook));
            }
            return webhooks;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * point an existing webhook to a new location
     * @param id webhook id
     * @param url new location
     * @return the updated webhook
     */
    public Webhook updateWebhook(String id, String url) {
        try {
            URL apiurl = new URL(ChainAPI.WEBHOOK_URL + "/"+id);
            JSONObject body = new JSONObject();
            body.put("url", url);
            JSONObject json = (JSONObject) this._apiRequest(apiurl, "PUT", body.toJSONString());
            return new Webhook(json);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * deletes a webhook
     * @param id the id of the webhook to be deleted
     * @return the deleted webhook
     */
    public Webhook deleteWebhook(String id) {
        try {
            URL apiurl = new URL(ChainAPI.WEBHOOK_URL + "/"+id);
            JSONObject body = new JSONObject();
            JSONObject json = (JSONObject) this._apiRequest(apiurl, "DELETE", null);
            return new Webhook(json);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * create a webhook event
     * @param event a webhook event with webhook id, event, block_chain(main or test), address and confirmations
     * @return newly created event
     */
    public WebhookEvent createWebhookEvent(WebhookEvent event) {
        try {
            URL apiurl = new URL(ChainAPI.WEBHOOK_URL + "/" + event.getWebhookId() + "/events");
            JSONObject body = new JSONObject();
            body.put("event", event.getEvent());
            body.put("block_chain", event.getBlockChain());
            body.put("address", event.getAddress());
            body.put("confirmations", event.getConfirmations());
            JSONObject json = (JSONObject) this._apiRequest(apiurl, "POST", body.toJSONString());
            return new WebhookEvent(json);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }

    /**
     * list webhook events for a specified webhook id
     * @param id the webhook id
     * @return the events
     */
    public List<WebhookEvent> listWebhookEvents(String id) {
        try {
            URL url = new URL(ChainAPI.WEBHOOK_URL + "/" + id + "/events");
            JSONArray array=(JSONArray)this._apiRequest(url);
            List<WebhookEvent> events = new ArrayList<WebhookEvent>();
            for(int h=0; h<array.size();h++) {
                JSONObject event = (JSONObject)array.get(h);
                events.add(new WebhookEvent(event));
            }
            return events;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * delete webhook event for a type and address
     * @param id webhook id
     * @param eventType the type of event to delete
     * @param address the address of the event to be deleted
     * @return a list of webhook events
     */
    public List<WebhookEvent> deleteWebhookEvent(String id, String eventType, String address) {
        try {
            URL apiurl = new URL(ChainAPI.WEBHOOK_URL + "/"+id+"/events/"+eventType+"/"+address);
            JSONObject body = new JSONObject();
            JSONArray json = (JSONArray) this._apiRequest(apiurl, "DELETE", null);
            List<WebhookEvent> events = new ArrayList<WebhookEvent>();
            for(int h=0; h<json.size();h++) {
                JSONObject event = (JSONObject)json.get(h);
                events.add(new WebhookEvent(event));
            }
            return events;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* helper functions */

    private List<OpReturn> _geOpReturns(String source, String item) {
        try {
            URL url = new URL(this.baseUrl+ source + "/" + item +"/op-returns");
            JSONArray array=(JSONArray)this._apiRequest(url);
            ArrayList<OpReturn> returns = new ArrayList<OpReturn>();
            for(int o=0; o<array.size(); o++) {
                JSONObject json = (JSONObject)array.get(o);
                returns.add(new OpReturn(json));
            }
            return returns;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static BigDecimal BTCvalue(Long amount) {
        return new BigDecimal(amount).divide(new BigDecimal(100000000));
    }

    public static BigDecimal mBTCvalue(Long amount) {
        return new BigDecimal(amount).divide(new BigDecimal(100000));
    }

    private Object _apiRequest(URL url) {
        return this._apiRequest(url, "GET", null);
    }

    private Object _apiRequest(URL url, String method, String urlParameters) {
        String authStr = this.keyId + ":" + this.keySecret;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
            String authStringEnc = new String(authEncBytes);
            System.out.println("Base64 encoded auth string: " + authStringEnc);
            connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            if("POST".equals(method) || "PUT".equals(method)) {
                connection.setRequestProperty("User-Agent", "chain-java/0");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

            } else {
                connection.setDoOutput(true);
            }
            InputStream in = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();

            JSONParser parser=new JSONParser();
            return parser.parse(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
         return null;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    private static String addressSlug(String[] addresses) {
        StringBuilder slug = new StringBuilder();
        for(String address: addresses) {
            slug.append(",").append(address);
        }
        System.out.println(slug.length() > 0 ? slug.toString().substring(1) : null);
        return slug.length() > 0 ? slug.toString().substring(1) : null;
    }
}
