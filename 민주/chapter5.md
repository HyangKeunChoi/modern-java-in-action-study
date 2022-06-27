5.1.2 고유 요소 필터링
- 스트림은 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다.(고유 여부는 스트림에서 만든 객체의 hashCode, equals로 결졍된다.)

```java  
 List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
  numbers.stream()
         .filter(i -> i % 2 == 0 )
         .distinct()
         .forEach(System.out::println);
 ```
5.2.1 프레디케이트를 이용한 슬라이싱
- 자바 9은 스트림의 요소를 효과적으로 선택할 수 있도록 takeWhile, dropWhile 두 가지 새로운 메서드를 지원한다.

* takeWhile 활용
  - 리스트가 이미 정렬되어 있다는 사실을 이용해 특정 조건에 일치하지 않는 값이 나왔을 때 반복 작업을 중단할 수 있다.
  - 무한스트림을 포함한 모든 스트림에 프레디케이트를 적용해 스트림을 슬라이스할 수 있다.

* dropWhile 활용
  - takeWhile과 정반대의 작업을 수행한다. 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
    프레디케이트가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환한다.
  
 5.2.2 스트림 축소
  - 스트림은 주어진 값 이하의 크기를 갖는 새로운 스트림을 반환하는 limit(n) 메서드를 지원한다.
    스트림이 정렬되어 있으면 최대 요소 n개를 반환할 수 있다.
```java   
public static final List<Dish> menu = Arrays.asList(
      new Dish("pork", false, 800, Dish.Type.MEAT),
      new Dish("beef", false, 700, Dish.Type.MEAT),
      new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("pizza", true, 550, Dish.Type.OTHER),
      new Dish("prawns", false, 400, Dish.Type.FISH),
      new Dish("salmon", false, 450, Dish.Type.FISH)
  );
  
  List<Dish> dishes = specialMenu.stream()
                                 .filter(dish -> dish.getCalories() > 300)
                                 .limit(3)
                                 .collect(toList());
```  
 -> pork
    beef
    chicken
    
 5.2.3 요소 건너뛰기
 - 스트림은 처음 n개 요소를 제외한 스트림을 반환하는 skip(n) 메서드를 지원한다.
   n개 이하의 요소를 포함하는 스트림에 skip(n)을 호출하면 빈 스트림이 반환된다.
   limit(n)과 skip(n)은 상호 보완적인 연산을 수행한다.

5.3.2 스트림 평면화
* flatMap 사용
  > flatMap 메서드는 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결하는 기능을 수행한다.

```java
List<String> uniqueCharacters = 
 words.stream()
  .map(word -> word.split(""))
  .flatMap(Arrays::stream)
  .distinct()
  . collect(toList());
```
 -> HeloWrd
 flatMap을 사용하지 않으면 Helo World
 
 
 5.4 검색과 매칭
  - 스트림 API는 allMatch, anyMatch, noneMatch, findFirst, findAny 등 다양한 유틸리티 메서드를 제공한다.
 menu에 채식요리가 있는지 확인하는 예제.
 ```java
 if(menu.stream().anyMatch(Dish::isVegetarian)) {
     System.out.println("채식요리");
 }
 ```
 
 - allMatch 메서드는 anyMatch와 달리 스트림의 모든 요소가 주어진 프레디케이트와 일치하는지 검사한다.
모든 요리가 1000칼로리 이하면 건강식으로 간주.
```java
boolean isHealthy = menu.stream()
                        .allMatch(dish -> dish.getCalories() < 1000);
```
 - noneMatch는 allMatch와 반대 연산을 수행.

Optional이란?
 - Optional<T> 클래스(java.util.Optional)는 값의 존재나 부재 여부를 표현하는 컨테이너 클래스다.
   null은 쉽게 에러를 일으킬 수 있으므로 자바 8 라이브러리 설계자는 Optional<T>를 만들었다.
   자세한 내용은 10장에서 자세히 설명.
   일단 Optional은 값이 존재하는지 확인하고 값이 없을 때 어떻게 처리할지 강제하는 기능을 제공한다는 사실만 알아두자.
 - isPresent()는 Optional이 값을 포함하면 참(true)을 반환하고, 값을 포함하지 않으면 거짓(false)을 반환한다.
 - ifPresent<Consumer<T> block)은 값이 있으면 주어진 블록을 실행한다.
 
 ```java
 Optional<Dish> dish = menu.stream()
                           .filter(Dish::isVegetarian)
                           .findAny()
                           .ifPresent(d -> System.out.println(d.getName())); // 값이 있으면 출력되고 값이 없으면 아무 일도 일어나지 않는다.
 ```
 
 5.5 리듀싱
  - 모든 스트림 요소를 처리해서 값으로 도출하는 것을 리듀싱이라고 한다.
  - 함수형 프로그래밍 언어 용어로는 이 과정이 마치 종이(우리의 스트림)를 작은 조각이 될 때까지 반복해서 접는 것과 비슷하다는 의미로 폴드라고 부른다.
 
 5.7.1 기본형 특화 스트림
  - 자바 8에서는 세 가지 기본형 특화 스트림을 제공한다.
    1. int 요소에 특화된 IntStream
    2. double 요소에 특화된 DoubleStream
    3. long 요소에 특화된 LongStream
  - 각각의 인터페이스는 숫자 스트림의 합계를 계산하는 sum, 최댓값 요소를 검색하는 max 같이 자주 사용하는 리듀싱 연산 수행 메서드를 제공한다.
 ```java
 int calories = menu.stream()
                    .mapToInt(Dish::getCalories)
                    .sum();
 ```
  - 스트림이 비어있으면 sum은 기본값 0을 반환한다.
  - OptionalInt를 이용해서 최댓값이 없는 상황에 사용할 기본값을 명시적으로 정의할 수 있다.
```java
 int max = maxCalories.orElse(1);
```
 
 5.7.2 숫자 범위
  - range 메서드는 시작값과 종료값이 결과에 포함되지 않음.
  - rangeClosed 메서드는 시작값과 종료값이 결과에 포함됨.
  - (1, 100)일 경우 range 메서드는 1과 100을 포함하지 않음. rangClosed 메서드는 1과 100을 포함.
 
 5.8.1 스트림 만들기
  - 임의의 수를 인수로 받는 정적 메서드 Stream.of를 이용해서 스트림을 만들 수 있다.
  - 다음 코드는 스트림의 모든 문자열을 대문자로 변환한 후 문자열을 하나씩 출력한다.
 ```java
 Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
 stream.map(String::toUpperCase).forEach(System.out::println);
 
 Stream<String> emptyStream = Stream.empty(); // empty 메서드를 이용하여 스트림을 비울 수 있다.
 ```
 
 
 5.8.5 함수로 무한 스트림 만들기
 ```java
 Stream.iterate(0, n -> n + 2)
       .limit(10)
       .forEach(System.out::println);
 ```
  - iterate는 요청할 때마다 값을 생산할 수 있으며 끝이 없으므로 무한 스트림을 만든다. 이러한 스트림을 언바운드 스트림이라고 표현한다.
 
 5.9 마치며
  - filter, distinct, takeWhile(자바9), dropWhile(자바9), skip, limit 메서드로 스트림을 필터링하거나 자를 수 있다.
  - 소스가 정렬되어 있다는 사실을 알고 있을 때 takeWhile과 dropWhile 메서드를 효과적으로 사용할 수 있다.
  - map, flatmap 메서드로 스트림의 요소를 추출하거나 변환할 수 있다.
  - findFirst, findAny 메서드로 스트림의 요소를 검색할 수 있다.
    allMatch, noneMatch, anyMatch 메서드를 이용해서 주어진 프레디케이트와 일차하는 요소를 스트림에서 검색할 수 있다.
