# Chapter 6 스트림으로 데이터 수집

- Collect 방식도 다양한 방식으로 리듀싱 연산을 수행 할 수 있다.

```java
// 통화별로 트랜잭션을 그룹화한 코드 ( 명령형 버전 ) 

Map<Currency , List<Transaction>> transactionsByCurrencies = 
	new HashMap<>();

for ( Transaction transaction : transactions ) {
	Currency currency = transaction.getCurrency();
	List<Transaction> transactionsForCurrency = 
		transactionsByCurrencies.get(currency);
	if ( transactionsForCurrency == null ) {
		transactionsForCurrency = new ArrayList<>();
		transactionsByCurrencies.put(currency, transactionsForCurrency);
	}
	transactionsForCurrency.add(transaction);
}

// toList 대신 다른 collect 메서드를 전달함으로써 간결하게 구현 
Map<Currency , List<Transaction>> transactionsByCurrencies = 
	transactions.strem()
		.collect(groupingBy(Transaction::getCurrency));

```

## 6.1 컬렉터란 무엇인가?

- Collector 인터페이스 구현은 스트림의 요소를 어떤 식으로 도출할지 지정한다.
- 5장에선 toList ( ‘ 각 요소를 리스트로 만듦 ‘ ) 를 Collector의 구현으로 사용
- 위 예제 코드에선 groupingBy 를 통해 ‘ 각 키 ( 통화) 버킷 그리고 각 키 버킷에 대응하는 요소 리스트를 값으로 포함하는 맵을 만들라’ 는 동작을 수행

### 6.1.1 고급 리듀싱 기능을 수행하는 컬렉터