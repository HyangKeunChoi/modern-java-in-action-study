# [자바의 정석] 스트림 , 스트림의 특징

### 스트림이란

- 다양한 데이터 소스를 표준화된 방법으로 다루기 위한 것

다양한 데이터 소스 → 컬렉션 혹은 배열 ( 여러 데이터를 저장하고 있는 것들 )

표준화된 방법 → 지금까지는 컬렉션 프레임워크를 사용했다 → 실패함 why: 서로 사용방법이 달랐다.

- 컬렉션 프레임워크 : List , Set , Map

- Java 8부터는 스트림을 통해 다양한 데이터 소스들을 표준화된 방법으로 다룰 수 있게 되었다.

⇒ 컬렉션 프레임워크 , 배열들은 스트림을 만들 수 있다.

스트림을 만들고 똑같은 방식으로 작업을 처리할 수 있게 된다.

- 스트림 생성
- 중간연산 ( N번 할 수 있음 )
- 최종연산 ( 1번 할 수 있다 ) ⇒ 결과

과거에는 데이터 소스들마다 작업하는 방식이 달랐는데 스트림으로 통일된 방법으로 작업할 수 있게 되었다.

### 스트림 생성

```java
List<Integer> list = Arrays.asList(1,2,3,4,5);

// 스트림으로 변환 
Stream<Integer> intStream = list.stream(); // 컬렉션 
Stream<String> strStream = Stream.of(new String[]{"a","b","c"}); // 배열
Stream<Integer> evenStream = Stream.iterate(0, n-> n+2); // 0,2,4,6, ... 
Stream<Double> randomStream = Stream.generate(Math::random); // 람다식 
IntStream intStream = new Random().ints(5); // 난수 스트림 ( 크기가 5 ) 

```

컬렉션에는 스트림이라는 메서드가 있다 .

Stream<T> Collection.stream()

- 생성 방법
1. 컬렉션으로 생성
2. 배열로 생성
3. 람다식으로 생성
4. 난수 스트림

스트림 프로세스 ( 3단계 )

`스트림 만들기` → `중간 연산`  → `최종 연산` → 우리가 원하는 결과를 가져온다.

👨 스트림 단어의 뜻 : 하천을 뜻함

데이터 소스들이 연속적으로 하나씩 전달되어 중간 작업 , 최종 작업으로 흘러간다.

- 데이터의 연속적인 흐름 → 스트림

### 중간 연산과 최종연산

- 중간 연산 : 연산 결과가 스트림인 연산. 반복적으로 적용가능 ( 0 ~ n 번 )
- 최종 연산 : 연산 결과가 스트림이 아닌 연산. 단 한번만 적용가능( 스트림의 요소를 소모)

  ( 0~1번 )

  stream.**distinct().limit(5).sorted()**.**forEach(System.out::println)**

  **중간연산 3번** , **최종연산**

  distinct : 중복제거

  limit(5) : 다섯개 자르기

  sorted() : 정렬

  forEach(System.out::println) : 스트림의 요소를 하나씩 꺼내서 출력


```java
String[] strArr = { "dd","aaa","CC","cc","b" };
Stream<String> stream = Stream.of(strArr); // 문자열 배열이 소스인 스트림 생성 
Stream<String> filteredStream = stream.filter(); // 걸러내기 ( 중간 연산 ) 
Stream<String> distinctedStream = stream.distinct(); // 중복제거 ( 중간 연산 ) 
Stream<String> sortedStream = stream.sort(); // 정렬 ( 중간연산 ) 
Stream<String> limitedStream = stream.limit(5); // 스트림 자르기 ( 중간 연산 ) 
int total = stream.count(); // 요소 개수 세기 ( 최종연산 ) 
```

### 스트림의 특징 1

- 데이터 소스의 원본을 건드리지 않는다. 읽기만 한다 ( Read Only )

    ```java
    List<Integer> list = Arrays.asList(3,1,5,4,2);
    List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());
    // list를 정렬해서 새로운 List에 저장 
    
    System.out.println(list);  // [3,1,5,4,2]
    System.out.println(sortedList); // [1,2,3,4,5]
    
    // 기존 데이터인 list는 건드리지 않는다.
    ```

- 스트림은 Iterator처럼 `일회용`이다. (필요하면 다시 스트림을 생성해야 한다)
    - Iterator : 컬렉션의 모든 요소를 읽는 것

      ⇒ ❓Iterator


    ```java
    strStream.forEach(System.out::println); // 모든 요소를 화면에 출력 ( 최종연산 ) 
    int numOfStr = strStream.count(); // 에러. 스트림이 이미 닫혔음 - 중간연산도 마찬가지 
    ```
    
    - 이럴 경우 다시 stream를 만들어서 작업 → 원본은 그대로 있기 떄문에

- 최종 연산 전까지 중간연산이 수행되지 않는다. - 지연된 연산

    ```java
    IntStream intStream = new Random().ints(1,46); // 1~45 범위의 무한 스트림 
    intStream.distinct().limit(6).sorted().forEach(i->System.out.print(i+",")); 
    ```

  무한 스트림은 데이터를 계속 주는데 무한 스트림에서 중간연산 ( 중복제거 , 자르기 , 정렬 ) 후 출력 로직

  ⇒ 무한 스트림은 이론적으로 중간연산이 되지 않는다. 그런데 이러한 코드가 가능한 이유는 `지연된 연산` 때문이다.


### 스트림의 특징 2 : 내부 반복

- 스트림은 내부 반복으로 처리

    ```java
    for(String str : strList)
    	System.out.println(str);
    
    stream.forEach(System.out::println);
    
    void forEach(Consumer<? super T> action) {
    	Objects.requireNonNull(action); // 매개 변수의 널 체크 
    	for ( T t : src) // 내부 반복 ( for 문을 메서드 안으로 넣음 ) 
    		action.accept(T);
    }
    ```

    - 코드가 간결해지는 장점이 있다.

### 스트림의 특징 3 : 병렬스트림

- 멀티쓰레드로 병렬처리 하는 것을 `병렬스트림` 이라 한다.
- FP ( 함수형 프로그래밍 )은 빅데이터를 처리하기 위해 인기가 많아졌다.

  빅데이터 ⇒ 많은 작업량 → 빅데이터 처리를 빠르게 하려면 여러개의 쓰레드들이 작업해야 속도가 빨라진다 ( 병렬 처리 )


```java
Stream<String> strStream = Stream.of("dd","aaa","CC","cc","b");
int sum = strStream.parallel() // 병렬 스트림으로 전환 ( 속성만 변경) 
			.mapToInt(s -> s.length()).sum(); // 모든 문자열의 길이의 합 
```

parallel 반대 변환 → Sequential() ( 기본이 시퀀셜 )

### 기본형 스트림 - IntStream , LongStream , DoubleStream

- 오토박싱 & 언박싱의 비효율이 제거됨 ( Stream<Integer> 대신 IntStream 사용)
- 숫자와 관련된 유용한 메서드를 Stream<T> 보다 더 많이 제공
  - IntStream 경우 sum() , count() , average() 같은 메서드를 제공
- 빅데이터를 위한 작업이기 때문에 시간의 효율성을 위해 기본형 스트림을 제공
  - 기본형 → 참조형 , 참조형 → 기본형으로 변환되는 비효율적인 작업을 제거하기 위해