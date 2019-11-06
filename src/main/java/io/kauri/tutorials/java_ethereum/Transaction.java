package io.kauri.tutorials.java_ethereum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

public class Transaction {

    public static void main(String[] args)  {

        System.out.println("Connecting to Ethereum ...");
        Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io"));
        //Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
        System.out.println("Successfuly connected to Ethereum");

        try {
            String pk = "3501E8287CA1EF187C89FD68CFE23FA846E724E6D53A6516BC6A35AC3FFFE33C"; // 개인키 넣기

            // Decrypt and open the wallet into a Credential object
            Credentials credentials = Credentials.create(pk);
            System.out.println("Account address: " + credentials.getAddress());
            System.out.println("Balance: " + Convert.fromWei(web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Unit.ETHER));

            // Get the latest nonce
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
             BigInteger nonce =  ethGetTransactionCount.getTransactionCount();

            System.out.println("nonce : " + nonce);
            // Recipient address
            String recipientAddress = "0x9C49326525Ad14fCf3307000bc940992E4614306"; //

            // Value to transfer (in wei)
            BigInteger value = Convert.toWei("0.1", Unit.ETHER).toBigInteger();

            // Gas Parameters
            BigInteger gasLimit = BigInteger.valueOf(210000);
            BigInteger gasPrice = Convert.toWei("1", Unit.GWEI).toBigInteger();

            // Prepare the rawTransaction
            /*RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                    nonce,
                    gasPrice,
                    gasLimit,
                    recipientAddress,
                    value,);*/
            RawTransaction rawTransaction  = RawTransaction.createTransaction(
                    nonce,
                    gasPrice,
                    gasLimit,
                    recipientAddress,
                    //value,String.valueOf("ec9588eb8595ed9598ec84b8ec9a94ebb894eba19decb2b4ec9db8eab3b5ebb680eca491ec9e85eb8b88eb8ba42e").trim()); //hex값 입력
                    value,String.valueOf("EC 95 88 EB 85 95 ED 95 98 EC 84 B8 EC 9A 94 20 EB B8 94 EB A1 9D EC B2 B4 EC 9D B8 20 EA B3 B5 EB B6 80 20 EC A4 91 20EC 9E 85 EB 8B 88 EB 8B A4 2E").replaceAll("\\p{Z}", "")); //hex값 입력

            // Sign the transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials); // nonce, gasprice, gaslimit, re~~, value, pk 값을 서명
            String hexValue = Numeric.toHexString(signedMessage); // 서명한값을 hex값 변환
            System.out.println("hexValue : " + hexValue);  // hex값 출력

            // Send transaction

            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();  // ******
                    String transactionHash = ethSendTransaction.getTransactionHash();
            System.out.println("transactionHash: " + transactionHash);

            // Wait for transaction to be mined
            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + transactionHash + " is mined....");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();  // 보내주는 부분
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                //transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                Thread.sleep(3000); // Wait 3 sec
            } while(!transactionReceipt.isPresent());

            System.out.println("Transaction : " + transactionHash + " was mined in block # " + transactionReceipt.get().getBlockNumber());
            System.out.println("Balance: " + Convert.fromWei(web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Unit.ETHER));




        } catch (IOException | InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}