String.valueOf 부분에 공백을 제거하고 싶은데 trim()함수로는 제거가 되지 않고 
replaceAll("\\p{Z}", "") 함수를 사용해야 제거가 되서 etherscan부분에 hex값에서 utf-8이 제대로 표기됨