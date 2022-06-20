### chapter1

-자바8 신 기술
	스트림API
	메서드에 코드를 전달하는 기법
	인터페이스의 디폴트 메서드

## Java8 프로그래밍 개념 3가지
#1.2.2 스트림 처리

-스트림이란?
	한번에 한 개씩 만들어지는 연속적인 데이터 항목들의 병렬처리	
	Stream<T> 병렬성 이득

#1.2.3 메서드에 코드 전달하는 기법

-동작파라미터화
	메서드를 다른 메서드의 인수로 넘겨주는 기능

#1.2.4 병렬성과 공유 가변 데이터

-병렬성,공유 가변 데이터
	각각의 코드를 동시에 실행 시킬 때, 안전하게 실행 할 수 있는 코드를 만들려면 
	공유된 가변 데이터에 접근x 



#1.3 자바함수
-프로그래밍 언어의 핵심은 값을 바꾸는것.
-메서드 참조
	isHidden이라는 함수는 준비되어 있을 때,
	File[] hiddenFiles = new File(".").listFiles(File :: isHidden);
	여기서 :: 이란? '이 메서드를 값으로 사용하라' 라는 뜻
	
-람다(익명함수)
	람다 예제 
	filterApples(inventory,(Apple a)-> a.getWeight()<80 || RED.equals(a.getColor()) );
	하지만 람다가 복잡한 동작을 수행 할 경우, 코드의 명확성이 우선되어야 함(새롭게 메서드 정의 후 참조하여 활용).
	
#1.4 스트림
-스트림API 예제
	Map<Currency, List<Transaction>> transactionsByCurrencies = 
		transactions.stream().filter(Transaction t) -> t.getprice() > 1000) //트랜잭션 필터링
					.collect(groupingBy(Transaction :: getCurrency));       //통화로 그룹화
	
-컬렉션 API 는 for-each 사용하는 외부반복
-스트림 API 는 루프신경 안쓰는 내부 반복


#1.5디폴트 메서드
-기존에 구현한 코드를 건들지 않고 원래의 인터페이스 설계를 확장할 수 있는 것.


### chapter2
 >DRY(Don't Repeat Yourself):같은 코드를 반복하지 말 것.
 
 
#동작 파라미터화

-변화하는 요구사항에 대응하는 일곱가지 시도
	첫번째, if조건문으로 녹색 사과만 필터링
	두번째, Color를 파라미터화 하여 유연한 대응
	==선택 조건을 결정하는 인터페이스를 생성== 
	세번째, 가능한 모든 속성을 파라미터로 추가하여 필터링
	네번째, 추상적 조건으로 필터링
	다섯번째, 익명 클래스 사용(객체 내부에서 사용되는 이름없는 클래스)
	여섯번째, 람다 표현식 사용	
	일곱번쨰, 리스트 형식으로 추상화
	
-동작 파라미터화 를 사용하면 바뀌는 요구사항에 효과적으로 대응 가능.
-동작 파라미터화의 강점: 컬렉션 탐색 로직과 각 항목에 적용할 동작을 분리할 수 있는것

#추상적 조건으로 필터링 예제 코드

public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
	List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory){
			if(P.test(apple)){
			result.add(apple);
			}
		}
	return result;
} 
public class AppleRedAndHeavyPredicate implements ApplePredicate{
	public boolean test(Apple apple){
		return RED.equals(apple.getColor()) && apple.getWeight() > 150;
	}
}

List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());


