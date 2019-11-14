//Main.java
package io.kauri.tutorials.java_ethereum;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.BasicConfigurator;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        System.out.println("Connecting to Ethereum ...");
        Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
        System.out.println("Successfuly connected to Ethereum");

        try {
            // web3_clientVersion returns the current client version.
            Web3ClientVersion clientVersion = web3.web3ClientVersion().send();

            // eth_blockNumber returns the number of most recent block.
            EthBlockNumber blockNumber = web3.ethBlockNumber().send();

            // eth_gasPrice, returns the current price per gas in wei.
            EthGasPrice gasPrice = web3.ethGasPrice().send();

            /* 계정의 최신 잔액 검색 */
            EthGetBalance balanceWei = web3.ethGetBalance("0x811f6a5f5e13b294e35e58bf7b9dd02ad36c9490", DefaultBlockParameterName.LATEST).send();
            BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER);

            // 계정의 난스값 받기
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x811f6a5f5e13b294e35e58bf7b9dd02ad36c9490", DefaultBlockParameterName.LATEST).send();
            BigInteger nonce =  ethGetTransactionCount.getTransactionCount();


            // Print result
            System.out.println("Client version: " + clientVersion.getWeb3ClientVersion());
            System.out.println("Block number: " + blockNumber.getBlockNumber());
            System.out.println("Gas price: " + gasPrice.getGasPrice());
            System.out.println("balance in wei: " + balanceWei.getBalance()); // 계정의 최신 잔액 검색
            System.out.println("balance in ether: " + balanceInEther);
            System.out.println("nonce : " + nonce);


            //  WalletUtils.generateNewWalletFile("", "");
        } catch (IOException ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        }
    }
}