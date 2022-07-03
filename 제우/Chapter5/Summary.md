# 5. 스트림 활용
*  이 장의 내용
  - 필터링, 슬라이싱, 매칭
  - 검색, 매칭, 리듀싱
  - 특정 범위의 숫자와 같은 숫자 스트림 사용하기
  - 다중 소스로부터 스트림 만들기
  - 무한 스트림
## 5.1 필터링
### 5.1.1 프레디케이트로 필터링
* filter메서드는 프레디케이트(불리언을 반환하는 함수)를 인수로 받아서 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.
* 예제
```java
List<Dish> vegetarianMenu = menu.stream()
                                .filter(Dish::isVegetarian) // 채식 요리인지 확인하는 메서드 참조
                                .collect(toList());
```
### 5.1.2 고유 요소 필터링
* distinct메서드는 고유 요소로 이루어진 스트림을 반환한다.
* 예제
```java
List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream()
       .filter(i -> i % 2 == 0)
       .distinct() // filter메서드의 결과인 2, 2, 4에서 고유요소만 반환
       .forEach(System.out.println); // 결과값: 2,4
```

## 5.2 스트림 슬라이싱
* 스트림의 요소를 선택하거나 스킵하는 다양한 방법이 있다.
* 프레디케이트를 이용하는 방법, 스트림의 처음 몇 개의 요소를 무시하는 방법, 특정 크기로 스트림을 줄이는 방법 등등을 이용하여 이런 작업을 수행할 수 있다.
### 5.2.1 프레디케이트를 이용한 슬라이싱
* 5.2.1에서는 요리리스트가 오름차순 정렬되어 있다는 전제로 한다.
* TAKEWHILE 활용
  - takeWhile을 활용하면 해당 프레디케이트가 참인 지점까지 슬라이스할 수 있다.
  ```java
  List<Dish> slicedMenu1
    = specialMenu.stream()
                 .takeWhile(dish -> dish.getcalories() < 320) // 320칼로리 미만의 요리 반환
                 .collect(toList());
  ```
* DROPWHILE 활용
  - dropWhile은 takeWhile과 정반대의 작업을 수행한다.
  - dropWhile은 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
  ``` java
  List<Dish> slicedMenu2
    = specialMenu.stream()
                 .dropWhile(dish -> dish.getcalories() < 320) // 320칼로리 이상의 요리 반환
                 .collect(toList());
  ```
### 5.2.2 스트림 축소
* limit메서드는 최대 요소 n개를 반환한다.
```java
List<Dish> dishes = specialMenu.stream()
                               .filter(dish -> dish.getCalories() > 300)
                               .limit(3)  // 프레디케이트와 일치하는 처음 세 요소를 선택한다.
                               .collect(toList());
```
### 5.2.3 요소 건너뛰기
* skip메서드는 처음 n개 요소를 제외한 스트림을 반환한다.
```java
List<Dish> dishes = menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .skip(2)
                        .collect(toList());
```
## 5.3 매핑
* map과 flatMap메서드는 특정 데이터를 선택하는 기능을 제공한다.
### 5.3.1 스트림의 각 요소에 함수 적용하기
```java
List<String> dishNames = menu.stream()
                             .map(Dish::getName) // getName은 문자열을 반환하므로 map메서드의 출력 스트림은 Stream<String>형식을 갖는다.
                             .collect(toList());
```
```java
List<String> words = Arrays.asList("Modern", "java", "In", "Action");
List<Integer> wordLengths = words.stream()
                            .map(String::length)
                            .collect(toList());
```

## 5.4 검색과 매칭
* 특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리
* allMatch, anyMatch, noneMatch, findFirst, findAny 등등..
### 5.4.1 프레디케이트가 적어도 한 요소와 일치하는지 확인
* anyMatch메서드는 프레디케이트가 주어진 스트림에서 적어도 한 요소와 일치하는지 확인할 때 사용한다.
```java
if(menu.stream().anyMatch(Dish::isVegetarian)) {
  System.out.println("The menu(somewhat) is vegetarian friendly");
}
// anyMatch는 불리언은 반환하므로 최종 연산이다.
```
### 5.4.2 프레디케이트가 모든 요소와 일치하는지 검사
* allMatch메서드는 스트림의 모든 요소가 주어진 프레디케이트와 일치하는지 검사한다.
```java
boolean isHealthy = menu.stream()
                        .allMatch(dish -> dish.getCalories() < 1000);
```
* noneMatch는 allMatch와 반대연산을 수행한다. 주어진 프레디케이트와 일치하는 요소가 없는지 확인한다.
```java
boolean isHealthy = menu.stream()
                        .noneMatch(dish -> dish.getCalories() >= 1000);
```
### 5.4.3 요소 검색
* findAny메서드는 현재 스트림에서 임의의 요소를 반환한다.
```java
Optional<Dish> dish =
  menu.stream()
      .filter(Dish::isVegetarian)
      .findAny()
```
### 5.4.4 첫 번째 요소 찾기
```java
List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> firstSquareDivisibleByThree = 
  someNumbers.stream()
             .map(n -> n * n)
             .filter(n -> n % 3 == 0)
             .findFirst(); // 결과값: 9
```

## 5.5 리듀싱
* 리듀스연산을 이용해서 '메뉴의 모든 칼로리 합계를 구하시오', '메뉴에서 칼로리가 가장 높은 요리는?'과 같이 스트림 요소를 조합해서 더 복잡한 질의를 표현할 수 있다.
* 이러한 질의를 수행하려면 결과가 나올 때까지 스트림의 모든 요소를 반복적으로 처리해야 한다.
* 이런 질의를 리듀싱 연산(모든 스트림 요소를 처리해서 값으로 도출하는)이라고 한다.
### 5.5.1 요소의 합

``` java
int sum = numbers.stream().reduce(0, (a,b) -> a+b);
```
* reduce는 두 개의 인수를 갖는다.
  - 초깃값 0
  - 두 요소를 조합해서 하나의 값을 만드는데 사용할 람다
* 자바8에서는 Integer클래스에 두 숫자를 더하는 정적 sum메서드를 제공한다. 따라서 직접 람다 코드를 구현할 필요가 없다.
``` java
  int sum = numbers.stream().reduce(0, Integer::sum);
```
### 5.5.2 최댓값과 최솟값
```java
Optional<Integer> max = numbers.stream().reduce(Integer::max);
Optional<Integer> min = numbers.stream().reduce(Integer::min);
```

## 5.7 숫자형 스트림
```java
int calories = menu.stream()
                   .map(Dish::getCalories)
                   .sum();  // 직접 호출 불가능
```
* 위 예제에서 sum메서드를 직접 호출 할 수 없다. map메서드가 ```Stream<T>```를 생성하기 때문이다.
* 내부적으로 합계를 계산하기 전에 Integer를 기본형으로 언박싱해야 한다.(박싱비용 발생)
* 스트림의 요소 형식은 Integer이지만 인터페이스에는 sum메서드가 없다.
### 5.7.1 기본형 특화 스트림
* 자바8에서는 세 가지 기본형 특화 스트림을 제공한다. 스트림API는 박싱 비용을 피할 수 있도록 아래3가지 인터페이스를 제공한다.
  - int요소에 특화된 IntStream
  - double요소에 특화된 DoubleStream
  - long요소에 특화된 LongStream
* 각각의 인터페이스는 숫자 스트림의 합계를 계산하는 sum, 최댓값 계산하는 max와 같이 자주 사용하는 숫자 관련 리듀싱 연산 수행 메서드를 제공한다.
* 특화 스트림은 오직 박싱 과정에서 일어나는 효율성과 관련이 있으며 추가 기능을 제공하지는 않는다.
```java
int calories = menu.stream()  // Stream<Dish> 반환
                   .mapToInt(Dish::getCalories) // IntStream 반환
                   .sum();

```
### 5.7.2 숫자 범위
* 자바8의 IntStream과 LongStream에서는 range와 rangeClosed라는 두 가지 정적 메서드를 제공한다.
* range메서드는 시작값과 종료값이 결과에 포함되지 않는 반면, rangeClosed는 시작값과 종료값이 결과에 포함된다.
```java
IntStream evenNumbers = IntStream.rangeClose(1, 100)
                                 .filter(n -> n % 2 == 0);
System.out.println(evenNumbers.count()); // 결과값: 50
```

## 5.8 스트림 만들기
* 일련의 값, 배열, 파일, 심지어 함수를 이용한 무한 스트림 만들기 등 다양한 방식으로 스트림을 만들 수 있다.
### 5.8.1 값으로 스트림 만들기
* Stream.of를 이용해서 스트림을 만들 수 있다.
```java
Stream<String> stream = Stream.of("Modern", "Java", "In", "Action");
stream.map(String::toUpperCase).forEach(System.out::println);
```
### 5.8.2 null이 될 수 있는 객체로 스트림 만들기
* 때로는 null이 될 수 있는 객체를 스트림(객체가 null이라면 빈 스트림)으로 만들어야할 수 있다.
* 예를 들어 System.getProperty는  제공된 키에 대응하는 속성이 없으면 null을 반환한다.
* 이런 메서드를 스트림에 활용하려면 아래와 같이 null을 명시적으로 확인해야 했다.
```java
// null을 확인해야하는 코드
String homeValue = System.getProperty("home");
Stream<String> homeValueStream
  = homeValue == null ? Stream.empty() : Stream.of(homeValue);
```
* Stream.ofNullable을 이용해 위 코드를 아래와 같이 구현할 수 있다.
```java
// Stream.ofNullable 이용
Stream<String> homeValueStream
  = Stream.ofNullable(System.getProperty("home"));
```
* null이 될 수 있는 객체를 포함하는 스트립값을 flatMap과 함게 사용하는 상황에서는 이 패턴을 더 유용하게 사용할 수 있다.
```java
Stream<string> values =
  Stream.of("config", "home", "user")
        .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
```
### 5.8.3 배열로 스트림 만들기
* 배열을 인수로 받는 정적 메서드 Arrays.stream을 이ㅣ용해서 스트림을 만들 수 있다.
* 예를 들어 다음처럼 기본형 int로 이루어진 배열을 IntStream으로 변환할 수 있다.
```java
int[] number = {2, 3, 5, 7, 11, 13};
int sum = Arrays.stream(numbers).sum();
```
### 5.8.5 함수로 무한 스트림 만들기
* 스트림 API는 함수에서 스트림을 만들 수 있는 두 정적 메서드 Stream.iterate와 Stream.generate를 제공한다.
* 두 연산을 이요해서 무한 스트림, 즉 고정된 컬렉션에서 고정된 크기로 스트림을 만드는 것과 달리 크기가 고정되지 않은 스트림을 만들 수 있다.
* iterate와 generate에서 만든 스트림은 요청할 때마다 주어진 함수를 이용해서 값을 만든다.(따라서 무제한으로 값을 계산할 수 있다.)
* 보통 무한한 값을 계산하지 않도록 limit메서드와 함께 사용한다.
```java
Stream.iterate(0, n -> n+2)
      .limit(10)
      .forEach(System.out::println);
```
* iterate는 요청할 대 마다 값을 생산할 수 있으며 끝이 없으므로 무한 스트림을 만든다. 이러한 스트림을 언바운드 스트림이라고 표현한다.

## 5.9 마치며
* 스트림API를 이용하면 복잡한 데이터 처리 질의를 표현할 수 있다.
* filter, distinct, takeWhile(자바9), dropWhile(자바9), skip, limit메서드로 스트림을 필터링하거나 자를 수 있다.
* 소스가 정렬되어 잇다는 사실을 알고 있을 때 takeWhile과 dropWhile메서드를 효과적으로 사용할 수 있다.
* map, flatMap메서드를 스트림의 요소를 추출하거나 변환할 수 있다.
* findFirst, findAny메서드로 스트림의 요소를 검색할 수 있다. allMatch, noneMatch, anyMatch메서드를 이용해서 주어진 프레디케이트와 일치하는 요소를 검색할 수 있다.
* 이들 메서드는 쇼트서킷(&&와 같은), 즉 결과를 찾는 즉시 반환하며, 전체 스트림을 처리하지는 않는다.
* reduce메서드로 스트림의 모든 요소를 반복 조합하며 값을 도출할 수 있다. 예를들어 reduce로 스트림의 최댓값이나 모든 요소의 합계를 계산할 수 있다.
* filter, map 등은 상태를 저장하지 않는 상태 없는 연산이다. reduce 같은 연산은 값을 계산하는 데 필요한 상태를 저장한다.
* IntStream, DoubleStream, LongStream은 기본형 특화 스트림이다. 이들 연산은 각각의 기본형에 맞게 특화되어 있다.
* 컬렉션 뿐만 아니라 값, 배열, 파일, iterate와 generate 같은 메서드로도 스트림을 만들 수 있다.
* 무한한 개수의 요소를 가진 스트림을 무한 스트림이라고 한다.
