package io.kauri.tutorials.java_ethereum;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class wallet1 {
    public static void main(String args[]) throws RuntimeException, Exception{
        String walletPassword = "2";
        String walletDirectory = "keystore";
        String walletName = "UTC--2019-11-05T00-32-20.245084000Z--9c49326525ad14fcf3307000bc940992e4614306";

// Load the JSON encryted wallet
        Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory + "/" + walletName);

// Get the account address
        String accountAddress = credentials.getAddress();

// Get the unencrypted private key into hexadecimal
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        System.out.println(privateKey);
    }
}