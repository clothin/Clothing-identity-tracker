package org.web3j.sample;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import my.test.ClothingTrackingV6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class Application {

	private static final Logger log = LoggerFactory
			.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		new Application().run();
//		new Application().init();
	}

	private void init() throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://178.62.45.240:8545"));  
        Credentials credentials = WalletUtils.loadCredentials("test", "src/UTC--2018-01-23T17-11-18.065080379Z--9132a1d20e5f17d5a974fd53c0c28f3f590e46f3");
        ClothingTrackingV6 contract =  ClothingTrackingV6.load("0x5dd26a62db3b73fd984b6d14ad8511ed3c29adf7", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
	}
	
	private void run() throws Exception {

        Web3j web3j = Web3j.build(new HttpService("http://178.62.45.240:8545"));  
        
        Credentials credentials = WalletUtils.loadCredentials("test", "src/UTC--2018-01-23T17-11-18.065080379Z--9132a1d20e5f17d5a974fd53c0c28f3f590e46f3");
        log.info("Credentials loaded");

        System.out.println("Deploying smart contract");
        ClothingTrackingV6 contract =  ClothingTrackingV6.load("0x5dd26a62db3b73fd984b6d14ad8511ed3c29adf7", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        ecKeyPair.getPrivateKey();
        System.out.println("PRIV KEY -- "  + ecKeyPair.getPrivateKey());
        
        String contractAddress = contract.getContractAddress();
        System.out.println("Smart contract deployed to address " + contractAddress);
        
        for (int i =1; i<=11; i++) {
    		BigInteger bi = BigInteger.valueOf(i);
        	RemoteCall<Tuple4<String, String, BigInteger, String>> factory = contract.getFactory(bi);
        	CompletableFuture<Tuple4<String, String, BigInteger, String>> send3 = factory.sendAsync();
        	Tuple4<String, String, BigInteger, String> tuple4 = send3.get();
        	System.out.println(tuple4.getValue1() + " - " + tuple4.getValue2() + " - " + tuple4.getValue3() + " - " + tuple4.getValue4());
        }
        
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        
        for (int i =1; i<=5; i++) {
    		BigInteger bi = BigInteger.valueOf(i);
        	RemoteCall<Tuple6<String, String, String, String, String, String>> factory = contract.getProductInfo(bi);
        	CompletableFuture<Tuple6<String, String, String, String, String, String>> send3 = factory.sendAsync();
        	Tuple6<String, String, String, String, String, String> tuple4 = send3.get();
        	System.out.println(tuple4.getValue1() + " - " + tuple4.getValue2() + " - " + tuple4.getValue3() + " - " + tuple4.getValue4() + " - " + tuple4.getValue5() + " - " + tuple4.getValue6());
        }
        
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        
        
        for (int i =1; i<=5; i++) {
    		BigInteger bi = BigInteger.valueOf(i);
        	RemoteCall<Tuple2<String, BigInteger>> factory = contract.getProductFlowInfo(bi);
        	Tuple2<String, BigInteger> send3 = factory.send();
        	System.out.println(send3.getValue1() + "  " + send3.getValue2());
        }
    }
}
