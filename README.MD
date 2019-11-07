# Ethereum node Communication

## Geth
#### Geth connect
- geth --networkid "10" --nodiscover --datadir C:\Users\jys\Geth\private_net --rpc --rpcaddr "localhost" --rpcport "8545" --rpccorsdomain "*" --rpcapi "eth, net, web3, personal" --targetgaslimit "20000000" console 2>> C:\Users\jys\Geth\private_neterror.log  --allow-insecure-unlock 0x811f6a5f5e13b294e35e58bf7b9dd02ad36c9490, 0x9c49326525ad14fcf3307000bc940992e4614306, 0xd8d2c181e4c89516dd51e5d6dfa8dee34c9b7e55 --password C:\Users\jys\Geth\private_net\geth\password.txt

#### Create account



-  personal.newAccount("1")
  "0x811f6a5f5e13b294e35e58bf7b9dd02ad36c9490"
  
   personal.newAccount("2")
  "0x9c49326525ad14fcf3307000bc940992e4614306"
  
   personal.newAccount("3")
  "0xd8d2c181e4c89516dd51e5d6dfa8dee34c9b7e55"
  
#### Coinbase change
- miner.setEtherbase(eth.accounts[1])

#### Mining
- miner.start(1)
- eth.mining 
- miner.stop()

#### error
 > String.valueOf("ec9588eb8595ed9598ec84b8ec9a94ebb894eba19decb2b4ec9db8eab3b5ebb680eca491ec9e85eb8b88eb8ba42e").trim()); 

      ex) 안녕하세요 블록체인 테스트입니다.      
- 위의 값을 HEX값으로 변환

  `"EC 95 88 EB 85 95 ED 95 98 EC 84 B8 EC 9A 94 20 EB B8 94 EB A1 9D EC B2 B4 EC 9D B8 20 ED 85 8C EC 8A A4 ED 8A B8 EC 9E 85 EB 8B 88 EB 8B A4 2E" `              

- HEX값으로 변환하여 Transaction을 보내면 Input Date 값에 깨진 글자가 출력

#### error solution
> String.valueOf("EC 95 88 EB 85 95 ED 95 98 EC 84 B8 EC 9A 94 20 EB B8 94 EB A1 9D EC B2 B4 EC 9D B8 20 ED 85 8C EC 8A A4 ED 8A B8 EC 9E 85 EB 8B 88 EB 8B A4 2E").**replaceAll("\\p{Z}", "")**);
  
```   
      Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io"));
         
      System.out.println("Successfuly connected to Ethereum");
```

> ropsten Testnet 연동







- 검색하기 컨텐츠

  블록에 담긴 텍스트나 Txid를 검색하고 싶을 때 `KMP알고리즘 참조`
  
  https://bowbowbow.tistory.com/6
  
  
- 컨텐츠사이트에서 TEXT, Txid검색

  ```SELECT CUST_ID, CUST_NAME, EMAIL
    FROM ENC_CUSTOMER
    WHERE EMAIL LIKE '%kr';```
 
    LIKE로 검색  

 - transaction.java
 
    ```위 코드에서는 send, riceved 가 정해져 있어서 a에서 b로 transaction을 전송하는데 해당 프로젝트가 요구하는것은 가격을 지불하는 것이 아니라 수수료만 지불하기 위해서이고, 해당 홈페이지에 회원가입을 하면 해당 홈페이지에서 임의의 계정을 지급하여 transaction을 할 때 ether를 0ether로 설정하고 self로 보내어 수수료만 지급할 수 있도록 설계```
    
    ```사용자가 계정생성 하면 사이트에서 지갑을 만들어주고
       그 사용자가 글을 쓰면  그 글에대한 사용자 주소를 불러와서 그 주소를 통해서 다른 트랜잭션을 발생시켜 노드에 쌓아두고
       그렇게되면 이주소를 통해 여러개의 글을 봤을 때 내 어드렛주소만 치면 다른 메시지를 볼 수 있게끔
       
       DB랑 연동해서 배치파일로 최신화 ```