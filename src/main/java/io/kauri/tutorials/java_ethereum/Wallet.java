package io.kauri.tutorials.java_ethereum;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class Wallet {
    public static void main(String args[]) throws RuntimeException, Exception{
        String walletPassword = "1";
        String walletDirectory = "keystore";
        String walletName = "UTC--2019-11-05T00-31-36.742212500Z--811f6a5f5e13b294e35e58bf7b9dd02ad36c9490";

// Load the JSON encryted wallet
        Credentials credentials = WalletUtils.loadCredentials("1", "keystore" + "/" + "UTC--2019-11-05T00-31-36.742212500Z--811f6a5f5e13b294e35e58bf7b9dd02ad36c9490");

// Get the account address
        String accountAddress = credentials.getAddress();

// Get the unencrypted private key into hexadecimal
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        System.out.println("Account address : " + accountAddress);
        System.out.println("Private key : " + privateKey);

    }
}