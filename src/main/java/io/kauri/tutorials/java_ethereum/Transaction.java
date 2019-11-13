package io.kauri.tutorials.java_ethereum;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

class Transation {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Connecting to Ethereum ...");
        Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io"));
        System.out.println("Successfuly connected to Ethereum");

        try {
            String pk = "3501E8287CA1EF187C89FD68CFE23FA846E724E6D53A6516BC6A35AC3FFFE33C";

            // Decrypt and open the wallet into a Credential object
            Credentials credentials = Credentials.create(pk); //
            System.out.println("Account address: " + credentials.getAddress());
            System.out.println("Balance: " + Convert.fromWei(web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Unit.ETHER));

            // Get the latest nonce
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            System.out.println("nonce : " + nonce);
            // Recipient address

            String recipientAddress = "0x811f6a5f5E13b294e35E58bf7b9DD02ad36C9490";

            // Value to transfer (in wei)
            BigInteger value = Convert.toWei("0", Unit.ETHER).toBigInteger();

            // Gas Parameters
            BigInteger gasLimit = BigInteger.valueOf(210000);
            BigInteger gasPrice = Convert.toWei("1", Unit.GWEI).toBigInteger();

            // string -> Hex
            // 프론트단에서 사용자가 입력하는 기능을 db에서 받아오는 기능추가     사용자 입력 : 안녕하세요 블록체인 공부 중 입니다
            byte[] message = "안녕하세요 블록체인 공부 중 입니다.".getBytes(StandardCharsets.UTF_8);
            String encoded = Base64.getEncoder().encodeToString(message);
            System.out.println("textHex : " + encoded);

            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasPrice,
                    gasLimit,
                    recipientAddress,
                    value,String.valueOf(encoded).replaceAll("\\p{Z}", ""));

            // Sign the transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials); // rawTransaction , pk 값을 서명
            String hexValue = Numeric.toHexString(signedMessage); // 서명한값을 hex값 변환
            System.out.println("hexValue : " + hexValue);  // hex값 출력

            // Send transaction
            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();  // ******
            String transactionHash = ethSendTransaction.getTransactionHash(); // ethSendTransaction에서 transactionHash값을 생성하여 transactionHash에 값을 대입.
            System.out.println("transactionHash: " + transactionHash);

            // Wait for transaction to be mined
            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + transactionHash + " is mined....");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();  // 보내주는 부분
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                Thread.sleep(3000); // Wait 3 sec
            } while (!transactionReceipt.isPresent());

            System.out.println("Transaction : " + transactionHash + " was mined in block # " + transactionReceipt.get().getBlockNumber());
            System.out.println("Balance: " + Convert.fromWei(web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Unit.ETHER));

            // block information
            DefaultBlockParameter blockParameter = DefaultBlockParameter.valueOf(transactionReceipt.get().getBlockNumber());
            EthBlock ethBlock = web3.ethGetBlockByNumber(blockParameter, true).sendAsync().get();
            EthBlock.Block block = ethBlock.getBlock();

            // block create Timestamp
            long timestamp = Long.parseLong(String.valueOf(block.getTimestamp()));
            Date date = new java.util.Date(timestamp * 1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
            String formattedDate = sdf.format(date);

            // block size
            BigInteger size = block.getSize();

            // hex -> UTF-8
            byte[] decoded = Base64.getDecoder().decode(encoded);

            System.out.println("size : " + size); // 블록 높이같음
            System.out.println("TimeStamp : " + formattedDate);
            System.out.println("hex : " + encoded);
            System.out.println("hexConversion : " + new String(decoded, StandardCharsets.UTF_8));

        } catch (IOException | InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}