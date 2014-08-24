A java library for accessing the bitcoin blockchain via the [chain](https://chain.com/docs/v1/) API.

Dependencies
------------

[json-simple](https://code.google.com/p/json-simple/downloads/list)

[commons-codec](http://commons.apache.org/proper/commons-codec/download_codec.cgi)

Example usage
---------------------
```java
import net.richardbondi.chainapi.ChainAPI;
import net.richardbondi.chainapi.transaction.Transaction;
```

```java
ChainAPI chain = new ChainAPI("YOUR-API-KEY", "YOUR-API-SECRET", true); // true for testnet, false or ommit for mainnet
List<Transaction> transactions = chain.getAddressTransactions("YOUR-ADDRESS");
for(Transaction transaction: transactions) {
    System.out.println("Transaction amount BTC: "+ChainAPI.BTCvalue(transaction.getAmount()));
}
```

for additional examples see the test directory.

The tests require [junit](https://github.com/junit-team/junit/wiki/Download-and-Install)

The testSendTransaction test requires [bitcoin-json-rpc-client](https://bitbucket.org/azazar/bitcoin-json-rpc-client/downloads) and
the [bitcoin core](https://bitcoin.org/en/download) server running.
