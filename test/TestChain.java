import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import com.azazar.bitcoin.jsonrpcclient.BitcoinException;
import com.azazar.bitcoin.jsonrpcclient.BitcoinJSONRPCClient;
import junit.framework.Assert;
import junit.framework.TestCase;
import net.richardbondi.chainapi.ChainAPI;
import net.richardbondi.chainapi.address.Address;
import net.richardbondi.chainapi.block.Block;
import net.richardbondi.chainapi.transaction.OpReturn;
import net.richardbondi.chainapi.transaction.Output;
import net.richardbondi.chainapi.transaction.Transaction;
import net.richardbondi.chainapi.webhook.Webhook;
import net.richardbondi.chainapi.webhook.WebhookEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestChain extends TestCase {
    ChainAPI chain;
    String[] addresses;

    private void initTests(boolean test) {
        chain = new ChainAPI("YOUR-API-KEY", "YOUR-API-SECRET", test);
        addresses =  new String[2]  ;
        if(test) {
            addresses[0] = "YOUR_TEST_ADDRESS" ;
            addresses[1] ="ANOTHER_TEST_ADDRESS";
        }  else {
            addresses[0] = "YOUR_ADDRESS" ;
            addresses[1] ="ANOTHER_ADDRESS";
        }
    }

    public void testAddress() {
        initTests(true);
        List<Address> addressList = chain.getAddress(addresses) ;
        for(Address address : addressList) {
            System.out.println("Balance BTC: " + ChainAPI.BTCvalue(address.getBalance())) ;
            System.out.println("Balance mBTC: " + ChainAPI.mBTCvalue(address.getBalance())) ;
        }
    }

    public void testAddressTransactions() {
        initTests(true);
        List<Transaction> transactions = chain.getAddressTransactions(addresses[0]);
        for(Transaction transaction: transactions) {
            System.out.println("Transaction amount BTC: "+ChainAPI.BTCvalue(transaction.getAmount()));
        }
    }

    public void testUnspents() {
        initTests(true);
        List<Output> outs = chain.getUnspents(addresses[0]) ;
        for(Output out : outs) {
            System.out.println("Balance BTC: " + ChainAPI.BTCvalue(out.getValue())) ;
            System.out.println("Transaction hash: " + out.getTransactionHash()) ;
        }
    }

    public void testAddressOpReturn() {
        initTests(true);
        List<OpReturn> returns = chain.getAddressOpReturns(addresses[1]);
        for(OpReturn ret: returns)
            System.out.println("text: " + ret.getText()) ;
    }

    public void testTransaction() {
        initTests(true);
        Transaction transaction = chain.getTransaction("TRANSACTION-HASH") ;
        System.out.println("Transaction amount BTC: "+ChainAPI.BTCvalue(transaction.getAmount()));
    }

    public void testTransactionOpReturn() {
        initTests(true);
        OpReturn op = chain.getTransactionOpReturn("TRANSACTION-HASH");
        System.out.println("text: " + op.getText()) ;
    }

    public void testSendTransaction() {
        initTests(true);
        try {
            Bitcoin bitcoin = new BitcoinJSONRPCClient(true);
            List<Bitcoin.Unspent> unspents = bitcoin.listUnspent();
            Bitcoin.Unspent unspent = unspents.get(0);
            Double prevAmount = unspent.amount();
            int vout = unspent.vout();

            Bitcoin.TxInput input = new Bitcoin.BasicTxInput(unspent.txid(), vout);
            ArrayList<Bitcoin.TxInput> inputs = new ArrayList<Bitcoin.TxInput>();
            inputs.add(input);

            String sendTo = "mgB6YfxjYvxr8c98rtBfWHuEDWE6RwSrRc"; // faucet
            String changeTo = addresses[0]; // should generate new address in different context
            Double spend = 0.0999;

            Bitcoin.TxOutput to = new Bitcoin.BasicTxOutput(sendTo, spend);
            BigDecimal bigToAmount = new BigDecimal(prevAmount.toString());

            Bitcoin.TxOutput change = new Bitcoin.BasicTxOutput(changeTo, bigToAmount.subtract(new BigDecimal(spend.toString())).doubleValue());
            ArrayList<Bitcoin.TxOutput> outputs = new ArrayList<Bitcoin.TxOutput>();
            outputs.add(to);
            outputs.add(change);
            String raw = bitcoin.createRawTransaction(inputs, outputs);
            String signed = bitcoin.signRawTransaction(raw);
            System.out.println(raw);
            System.out.println(signed);
            chain.sendTransaction(signed);

            //
        } catch (BitcoinException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testBlock() {
        initTests(true);
        Block block = chain.getBlock(276618L);
        String hash = block.getHash();
        System.out.println("hash returned: " + block.getHash()) ;

        Block block2 = chain.getBlock(hash);
        String hash2 = block2.getHash();
        System.out.println("height returned: " + block.getHeight()) ;

        Assert.assertEquals(hash, hash2);

    }

    public void testBlockOpReturns () {
        initTests(false);
        Long height = 316322L;
        List<OpReturn> op = chain.getBlockOpReturns(height);
        for(OpReturn r : op)
            System.out.println(r.getText());

        Block block = chain.getBlock(height);
        String hash = block.getHash();
        List<OpReturn> op2 = chain.getBlockOpReturns(hash);

        Assert.assertEquals(op.get(0).getTransactionHash(), op2.get(0).getTransactionHash());
    }

    public void testCreateWebhook () {
        initTests(true);
        Webhook webhook = chain.createWebhook("http://username:password@yourdomain.com");
        System.out.println("Webhook ID: " + webhook.getId()) ;

    }

    public void testListWebhooks() {
        initTests(true);
        List<Webhook> webhooks = chain.listWebhooks();
        for(Webhook webhook : webhooks) {
            System.out.println("id: "+webhook.getId()+"  url: "+webhook.getUrl());
        }
    }

    public void testUpdateWebhook() {
        initTests(true);
        Webhook webhook = chain.updateWebhook("WEBHOOK-ID","http://username:password@updateddomain.com");
    }

    public void testDeleteWebhook() {
        initTests(true);
        Webhook webhook = chain.deleteWebhook("WEBHOOK-ID");
        System.out.println(webhook.getId());
    }

    public void testCreateWebhookEvent() {
        initTests(true);
        WebhookEvent createEvent = new WebhookEvent();
        createEvent.setWebhookId("WEBHOOK-ID");
        createEvent.setEvent("address-transaction");
        createEvent.setBlockChain("testnet3");
        createEvent.setAddress(addresses[0]);
        createEvent.setConfirmations(1);
        WebhookEvent webhookEvent = chain.createWebhookEvent(createEvent);
        System.out.println("Event Id: " + webhookEvent.getId());
    }

    public void testListWebhookEvents() {
        initTests(true);
        List<WebhookEvent> events = chain.listWebhookEvents("WEBHOOK-ID");
        if(events.size() == 0) {
            System.out.println("no events found");
        }
        for(WebhookEvent event: events) {
            System.out.println("Event id: " + event.getId());
        }
    }

    public void testDeleteWebhookEvent () {
        initTests(true);
        List<WebhookEvent> events = chain.deleteWebhookEvent("WEBHOOK-ID", "address-transaction", addresses[0]) ;
        for(WebhookEvent event: events) {
            System.out.println("Deleted Event id: " + event.getId());
        }
    }
}
