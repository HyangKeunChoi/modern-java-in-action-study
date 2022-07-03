# chapter5 스트림 활용

+ 내부반복 -> 외부반복
```java
List<Dish> vegetarianDishes = 
        menu.stream()
        .filter(Dish::isVegetarian)
        .collect(toList());
```

+ 스트림 API는 내부반복 뿐 아니라 코드를 병렬로 실행할지 여부도 결정할 수 있다.
+ 이러한 일은 순차적인 반복을 단일 스레드로 구현하는 외부 반복으로는 달성할 수 없다.

### 필터링하는 방법은 총 2가지가 있다
+ 프레디케이트로 필터링
+ 고유 요소 필터링
    - distinct 메서드
        - 객체의 equals와 hashcode로 결정된다.

### 스트림 슬라이싱
+ TAKEWHILE
  - 이미 정렬되어 있다는 전제하게 조건을 만족하는 요소가 나왔을때 반복작업을 중단 할 수 있따.
+ DROPWHILE
  - dropWhile은 takeWhile과 정반대의 작업을 수행한다.

### 스트림 축소
+ Limit
  - 소스가 정렬되어 있지않으면 limit의 결과도 정렬되지 않은 상태로 반환된다.

### 스트림 요소 건너 뛰기
+ Skip

### 5.3 매핑
+ mapping : 기존 값을 고친다는 개념보다는 새로운 버전을 만든다는 개념에 가까우므로 변환에 가까운 매핑이라는 단어 사용
+ 각 단어 리스트가 주어졌을 때 각 단어가 포함하는 글자 수의 리스트를 반환해보자
```java
List<String> words = Arrays.asList("Modern", "java","in", "action");
List<Integer> wordLengths = words.stream().map(String::length).collect(toList());
```

### 5.3.2 스트림 평면화
+ flatMap 
    - 소스 참고 (Chapter5_example)

### 5.4 검색과 매칭

1. anyMatch : 스트림에서 적어도 한 요소와 일치하는지 확인할 때
2. allMatch : 스트림의 모든 요소가 주어진 프레디케이트와 일치하는지 검사
3. noneMatch : allMatch와 반대 연산을 수행한다.

> 쇼트서킷 기법 : 떄로 전체 스트림을 처리하지 않더라도 결과를 반환할 수 있다.
>
> 하나라도 거짓이라는 결과가 나오면 나머지 연산의 결과와 상관없이 거짓인 경우가 대표적인 예 이다. 이러한 상황을 쇼트서킷이라 부른다.

### 5.4.3 요소 탐색
+ findAny : 현재 스트림에서 임의의 요소를 반환한다.

### Optional이란
+ Optionial<T> 클래스는 값의 존재나 부재 여부를 표현하는 컨테이너 클래스이다.

### 5.4.4 첫번째 요소 찾기

#### findFirst와 findAny는 언제 사용할까?
+ 왜 findFirst와 findAny 메서드 모두 필요할까? 바로 병렬성 때문이다.
+ 병렬 실행에서는 첫 번째 요소를 찾기 어렵다. 따라서 요소의 반환 순서가 
+ 상관없다면 병렬 스트림에서는 제약이 적은 findAny를 사용한다.

### 리듀싱
+ 스트림 요소를 조합해서 더 복잡한 질의를 표현하는 방법
+ 함수형 프로그래밍 언어 용어로는 이 과정을 마치 종이를 작은 조각이 될 때까지 반복해서 접는 것과 비슷하다는 의미로 폴드라고 부른다.

### 실전 연습
+ 패스

### 5.7 숫자형 스트림
+ 스트림 API는 박싱 비용을 피할 수 있도록 int 요소에 특화된 IntStream,
+ double 요소에 특화된 DoubleStream
+ long요소에 특화된 LongStream을 제공한다.

### 숫자스트림으로 매핑 

```java
int calories = menu.stream()
        .mapToInt(Dish::getCalories)
        .sum();
```

### 객체 스트림으로 복원
```java
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
Stream<Integer> stream = intStream.boxed();
```

### 기본값 : OptionalInte
+ IntStream에서 최대값을 찾을 때는 0이라는 기본값 때문에 잘못된 결과가 도출될 수 있다.
+ 스트림에 요소가 없는 상황과 실제 최대값이 0인 상황을 어떻게 구별할 수 있을까?
    - OptionalInt로 해결 가능
```java
OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
int max = maxCaloreis.orElse(1);
```