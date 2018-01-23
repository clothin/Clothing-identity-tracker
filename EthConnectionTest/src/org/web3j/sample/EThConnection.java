package org.web3j.sample;

import java.io.IOException;

import my.test.ClothingTrackingV5;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class EThConnection {

	public static Web3j web3j;
	public static Credentials creds;
	public static ClothingTrackingV5 contract;
	
	public static void setUp() throws IOException, CipherException {
		Web3j.build(new HttpService("http://178.62.45.240:8545"));
		creds = WalletUtils.loadCredentials("test","/home/mare/workspace/UTC--2018-01-23T17-11-18.065080379Z--9132a1d20e5f17d5a974fd53c0c28f3f590e46f3");
		contract = ClothingTrackingV5.load("0xce4de22ca299cd07e037cc5a3f4922189c8c1428",web3j, creds, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
	}
}
