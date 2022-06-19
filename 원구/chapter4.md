# #chapter4

## 4. 스트림
- JAVA 8 API에 새로 추가된 기능
- 멀티스레드 코드를 구현하지 않아도 데이터를 투명하게 병렬로 처리 가능
- 데이터 처리 연산을 지원하도록 소스에서 추출된 연속 요소로 정의


### 4.1 스트림이란 무엇인가?
- java7 코드와 java 8의 스트림을 이용한 코드 비교
```java
List<Dish> lowCalDishes = new ArrayList();
for(Dish d : menu){
 if(d.getCalories() < 400) {
	lowCalDishes.add(dish);
 }
}
Collections.sort(lowCalDishes, new Comparator<Dish>(){
	public int compare(Dish dish1, Dish dish2){
		return Integer.compare(dish1.getCalories(), dish2.getCalories());
	}
});
List<String> lowCalDishesName = new ArrayList<>();
for(Dish dish: lowCalDishes){
	lowCalDishesName.add(dish.getName());
}
```
- java 7 코드에서는 lowCalDishes라는 가비지 변수를 사용
- lowCalDishes는 컨테이너 역할만 하는 중간 변수

```java
import ...comparing;
import ...toList;

List<String> lowCalDishesName = 
		menu.stream()		
			.filter( d -> d.getCalories() < 400)		//400 칼로리 이하의 요리 선택
			.sorted(comparing(Dish::getCalories))		//칼로리로 요리 정렬
			.map(Dish::getName)		// 요리명 추출
			.collect(toList());		// 모든 요리명을 리스트에 저장
```
- java 8 에서는 라이브러리 내에서 모두 처리
- menu.stream() 부분을 menu.parallelStream()으로 바꾸면 멀티코어 아키텍처에서 병렬로 실행 가능
> filter, sorted, map, collect 같은 여러 빌딩 블록 연산을 연결해서 복잡한 데이터 처리 파이프라인을 만들 수 있다.

- 스트림 API 특징
  - 선언형 : 더 간결하고 가독성이 좋아진다.
  - 조립할 수 있음 : 유연성이 좋아진다.  
  - 병렬화 : 성능이 좋아진다.
  
### 4.2. 스트림 시작하기
- 연속된 요소 = 컬렉션과 마찬가지로 스트림은 특정 요소 형식으로 이루어진 연속된 값 집합의 인터페이스를 제공한다.
- 소스 = 스트림은 컬렉션, 배열, I/O 자원 등의 데이터 제공 소스로부터 데이터를 소비한다.
- 데이터 처리 연산 = 함수형 프로그래밍 언어에서 일반적으로 지원하는 연산과 데이터베이스와 비슷한 연산을 지원한다.
- 파이프라이닝 = 대부분의 스트림 연산은 스트림 연산끼리 연결해서 커다란 파이프라인을 구성할 수 있도록 스트림 자신을 반환한다.
- 내부 반복 = 반복자를 이용해서 명시적으로 반보하는 컬렉션과 달리 스트림은 내부 반복을 지원한다.

```java
import ...toList;

List<String> threeHighCalDisheNames = 
		menu.stream()		
			.filter( d -> d.getCalories() > 300)		
			.map(Dish::getName)
			.limit(3)
			.collect(toList());		
System.out.println(threeHighCalDisheNames); // [pork, beef, chicken]
```
- filter
	- 람다를 인수로 받아 스트림에서 특정 요소를 제외시킨다.
- map
	- 람다를 이용해서 한 요소를 다른 요소로 변환하거나 정보를 추출한다.
- limit
	- 정해진 개수 이상의 요소가 스트림에 저장되지 못하게 스트림 크기를 축소 truncate한다.
- collect
	- 스트림을 다른형식으로 반환한다.
	
### 4.3. 스트림과 컬렉션
- 스트림 
	- 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조이다.
- 컬렉션
	- 이론적으로 요청할 떄만 요소를 계산하는 고정된 자료구조다.
> 데이터를 언제 계산하느냐가 컬렉션과 스트림의 가장 큰 차이이다.
	
#### 4.3.1. 딱 한 번만 탐색할 수 있다.
- 탐색된 스트림의 요소는 소비된다.
- 한번 탐색한 요소를 다시 탐색하려면 초기 데이터 소스에서 새로운 스트림을 만들어야한다.
```java
List<String> title = Arrays.asList("Java8", "In", "Action");
Stream<String> s = title.stream();
s.forEach(System.out::println);		// title의 각 단어를 출력
s.forEach(System.out::println);		// 스트림이 이미 소비 되었거나 닫힘 (java.lang.IlegalStateException)
```

### 4.4 스트림 연산
- 스트림 인터페이스의 연산을 크게 두 가지로 구분할 수 있다.
```java
List<String> name = menu.stream()		
			.filter( d -> d.getCalories() > 300)		
			.map(Dish::getName)
			.limit(3)
			.collect(toList());
```
- 중간 연산 은 " filter, map, limit "이며, 서로 연결되어 파이프라인을 형성한다.
- 최종 연산 은 " collect " 이며, 파이프라인을 실행한 다음에 닫는다.
