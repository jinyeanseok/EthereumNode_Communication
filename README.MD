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
  
