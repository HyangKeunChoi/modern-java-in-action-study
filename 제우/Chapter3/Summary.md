# 람다표현식
- 람다 표현식은 익명 클래스처럼 이름이 없는 함수면서 메서드를 인수로 전달할 수 있다.

## 3.1 람다란 무엇인가?
* 람다 예제
``` java
Compartor<Apple> byWeight = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```
- 람다 표현식은 파라미터, 화살표, 바디로 이루어진다.
- 파라미터 리스트: Comparator의 compare 메서드 파라미터(a1, a2)
- 화살표: 화살표(->)는 람다의 파라미터 리스트와 바디를 구분한다.
- 람다 바디: 두 사과의 무게를 비교한다. 람다의 반환값에 해당하는 표현식이다.

## 3.2 어디에, 어떻게 람다를 사용할까?
### 3.2.1 함수형 인터페이스
* 함수형 인터페이스란 정확히 하나의 추상 메서드를 지정하는 인터페이스 이다.
![image](https://user-images.githubusercontent.com/39439576/171995508-504a4de0-0e89-41ff-bff8-e62de70cc78b.png)

* 예시
  ``` java
  public interface Predicate<T> {
    boolean test(T t)
  }
  ```
* 지금까지 살펴본 자바 API의 함수형 인터페이스로 Comparator, Runnable 등이 있다.

  ``` java
  public interface Comparator<T> {
    int compare(T o1, To2);
  }
  ```
  ``` java
  public interface Runnable {
    void run();
  }
  ```
### 3.2.2 함수 디스크립터
* 함수형 인터페이스의 추상 메서드 시그니처는 람다 표현식의 시그니처를 가리킨다.
* 람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 부른다.
* 예시
  ``` java
  () -> void
  ```
  위 표기는 파라미터 리스트가 없으며 void를 반환하는 함수를 의미한다.
  ``` java
  (Apple, Apple) -> int
  ```
  위 표기는 두 개의 Apple을 인수로 받아 int를 반환하는 함수를 가리킨다.
  
## 3.3 람다 활용 : 실행 어라운드 패턴
* 자원을 처리하는 코드를 설정과 정리 두 과정이 둘러싼 형태를 갖는 것을 실행 어라운드 패턴이라고 부른다.
  ``` java
  public String processFile() throws IOException {
    try (BufferedReader br =
        new BufferedReader(new FileReader("data.txt"))) {
      return br.readLine();
    }
  }
  ```
### 3.3.1 1단계: 동작파라미터화를 기억하라
* 위 코드는 파일에서 한 번에 한 줄만 읽을 수 있다.
* 동작파라미터화를 이용하면 한 번번에 두 줄을 읽을 수 있도록 변경할 수 있다.(processFile의 동작을 파라미터화 한다.)
* processFile메서드가 BufferedReader를 이용해서 다른 동작을 수행할 수 있도록 processFile 메서드로 동작을 전달해야 한다.
  ``` java
  String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());
  ```
### 3.3.2 2단계: 함수형 인터페이스를 이용해서 동작 전달
* 함수형 인터페이스 자리에 람다를 사용할 수 있다.
* BufferedReader -> String 과 IOException을 던질 수 있는 시그니처와 일치하는 함수형 인터페이스를 만들어야 한다.
  ``` java
  @FunctionalInterface
  public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
  }
  ```
  정의한 인터페이스를 processFile메서드의 인수로 전달할 수 있다.
  ``` java
  public String processFile(BufferedReaderProcessor p) throws IOException {
    ...
  }
  ```
### 3.3.3 3단계: 동작 실행
* BufferedReaderProcessor에 정의된 process메서드의 시그니처 (BufferedReader -> String)와 일치하는 람다를 전달할 수 있다.
  ``` java
  public String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedRedaer(new FileReader("data.txt"))) {
      return p.process(br);
    }
  }
  ```
### 3.3.4 4단계: 람다 전달
* 이제 람다를 이용해서 다양한 동작을 processFile메서드로 전달할 수 있다.
* 한 행을 처리하는 코드
  ``` java
  String oneLine = processFile((BufferedReader br) -> br.readLine());
  ```
* 두 행을 처리하는 코드
  ``` java
  String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
  ```

## 3.4 함수형 인터페이스 사용
* 함수형 인터페이스의 추상 메서드는 람다 표현식의 시그니처를 묘사한다.
* 함수형 인터페이스의 추상 메서드 시그니처를 함수 디스크립터라고 한다.
* 다양한 람다표현식을 사용하려면 공통의 함수 디스크립터를 기술하는 함수형 인터페이스 집합이 필요하다.
* 자바 API는 Comparable, Runnable, Callable 등 다양한 함수형 인터페이스를 포함하고 있다.
* java.util.function패키지로 여러 가지 새로운 함수형 인터페이스를 제공한다.(Predicate, Consumer, Function 인터페이스 등등)
### 3.4.1 Predicate
* java.util.function.Predicate<T> 인터페이스를 test라는 추상 메서드를 정의하며 test는 제네릭 형식의 T객체를 인수로 받아 불리언을 반환한다.
* 예시
  ``` java
  @FunctionalInterface
  public interface Predicate<T> {
    boolean test(T t);
  }
  
  public <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> results = new ArrayList<>();
    for(T t: list) {
      if(p.test(t)) {
        results.add(t);
      }
    }
    return results;
  }
  ```
  ``` java
  Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
  List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
  ```
### 3.4.2 Consumer
* java.util.function.Consumer<T> 인터페이스는 제네릭 형식 T객체를 받아서 void를 반환하는 accept라는 추상메서드를 정의한다.
* 예시
   ``` java
  @FunctionalInterface
  public interface Consumer<T> {
    void accept(T t);
  }
  
  public <T> void forEach(List<T> list, Consumer<T> c) {
    for(T t: list) {
      c.accept(t);
    }
  }
  ```
  ``` java
  forEach(
          Arrays.asList(1,2,3,4,5),
          (Integer i) -> System.out.println(i)
  );
  ```
### 3.4.3 Function
* java.util.function.Function<T, R> 인터페이스는 제네릭 형식 T객체를 받아서 제네릭 형식 R 객체를 반환하는 추상 메서드 apply를 정의한다.
* 입력을 출력으로 맵핑하는 람다를 정의할 때 Function 인터페이스를 활용할 수 있다.(ex. 사과의 무게정보 추출, 문자열을 길이와 맵핑)
* 예시
  ``` java
  @FunctionalInterface
  public interface Function<T, R> {
    R apply(T t);
  }
  
  public<T, R> List<R> map(List<T> list, Function<T, R> f) {
    List<R> result = new ArrayList<>();
    for(T t : list) {
      result.add(f.apply(t));
    }
    return result;
  }
  ```
  ``` java
  List<Integer> l = map(
        Arrays.asList("lambdas", "in", "action"),
        (String s) -> s.length()
  );
  ```
  
## 3.6 메서드 참조
* 메서드 참조를 이용하면 기존의 메서드 정의를 재활용해서 람다처럼 전달할 수 있다.
* 예시
  ``` java
  inventory.sort(comparing(Apple::getWeight));
  ```
### 3.6.1 요약
* 명시적으로 메서드명을 참조함으로써 가독성을 높일 수 있다.
* Apple::getWeight는 Apple클래스에 정의된 getWeight의 메서드 참조이다.
* 실제로 메서드를 호출하는 것은 아니므로 괄호는 필요없다.
* 결과적으로 메서드 참조는 람다 표현식 (Apple a) -> a.getWeight()를 축약한 것이다.
* 메서드 참조를 만드는 방법
  - 1. 정적 메서드 참조
      예를 들어 Integer의 parseInt 메서드는 Integer::parseInt로 표현할 수 있다.
  - 2. 다양한 형식의 인스턴스 메서드 참조
      예를 들어 String의 length 메서드는 String::length로 표현할 수 있다.
  - 3. 기존 객체의 인스턴스 메서드 참조
      Transaction 객체를 할당받은 expensiveTransaction 지역변수가 있고, Transaction 객체에는 getValue메서드가 있다면, expensiveTransaction::getValue로 표현할 수 있다.

## 3.7 람다, 메서드 참조 활용하기
### 3.7.1 1단계: 코드 전달
* List.sort메서드의 시그니처: void sort(Comparator<? super E> c)
* 예시
  ``` java
  public class AppleComparator implements comparator<Apple> {
    public int compare(Apple a1, Apple a2) {
      return a1.getWeight().compareTo(a2.getWeight());
    }
  }
  inventory.sort(new AppleComparator());
  ```
### 3.7.2 2단계: 익명 클래스 사용
* 한 번만 사용할 Comparator를 위 코드처럼 구현하기 보다 익명클래스를 이용하는 것이 좋다.
* 예시
  ``` java
  inventory.sort(new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2) {
      return a1.getWeight().compareTo(a2.getWeight());
    }
  }
  ```
### 3.7.3 3단계: 람다 표현식 사용
* 함수형 인터페이스를 기대하는 곳 어디에서나 람다 표현식을 사용할 수 있음을 기억하자.
* Comparator의 함수 디스크립터는 (T,T) -> int이다.
* 예시
  ``` java
  inventory.sort((Apple a1, Apple a2) -> a1.getWight().compareTo(a2.getWeight()));
  ```
### 3.7.4 4단계: 메서드 참조 사용
* 메서드 참조를 이용하면 람다 표현식의 인수를 더 깔끔하게 전달할 수 있다는 점을 기억하자.
* 예시
  ``` java
  inventory.sort(comparing(Apple::getWeight));
  ```

## 3.8 람다 표현식을 조합할 수 있는 유용한 메서드
* 자바 8 API의 몇몇 함수형 인터페이스는 다양한 유틸리티 메서드를 포함한다.
* 예를 들어 Comparator, Function, Predicate같은 함수형 인터페이스는 람다 표현식을 조합할 수 있도록 유틸리티 메서드를 제공한다.
* 간단한 여러 개의 람다 표현식을 조합해서 복잡한 람다 표현식을 만들 수 있다.
### 3.8.1 Comparator 조합
* 역정렬
  - 사과의 무게를 역정렬하고 싶다면 아래 코드처럼 작성할 수 있다.
  ``` java
  inventory.sort(comparing(Apple::getWeight).reversed());
  ```
* Comparator 연결
  - 무게가 같은 사과가 존재할 때 원산지 국가별로 정렬하고 싶다면 아래 코드처럼 작성할 수 있다.
  ``` java
  inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getCountry));
  ```
### 3.8.2 Predicate 조합
* Predicate 인터페이스는 negate, and, or 세 가지 유틸리티 메서드를 제공한다.
* negate: 특정 프레디케이트를 반전시킨다.
  - 예를 들어 '빨간색이 아닌 사과'를 얻으려면 아래 코드처럼 작성할 수 있다.
  ``` java
  Predicate<Apple> notRedApple = redApple.negate();
  ```
* and: 두 람다를 조합할 수 있다.
  - 예를 들어 '빨간색이면서 무거운 사과'를 선택하도록 하려면 아래 코드처럼 작성할 수 있다.
  ``` java
  Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight > 150);
  ```
* or: 두 람다를 조합할 수 있다.
  - 예를 들어 '빨간색이면서 무거운 사과 또는 그냥 녹색 사과'를 선택하도록 하려면 아래 코드처럼 작성할 수 있다.
  ``` java
  Predicate<Apple> redAndHeavyAppleOrGreen =
    redApple.and(apple -> apple.getWeight() > 150)
            .or(apple -> GREEN.equals(a.getColor()));
  ```
### 3.8.3 Function 조합
* Function 인스턴스를 반환하는 andThen, compose 두 가지 디폴트 메서드를 제공한다.
* andThen: 주어진 함수를 먼저 적용한 결과를 다른 함수의 입력으로 전달하는 함수를 반환
  ``` java
  Function<Integer, Integer> f = x -> x + 1;
  Function<Integer, Integer> g = x -> x * 2;
  Function<Integer, Integer> h = f.andThen(g);
  int result = h.apply(1);  // 결과값: 4
  ```
* compose: 인수로 주어진 함수를 먼저 실행한 다음에 그 결과를 외부 함수의 인수로 제공한다.
  ``` java
  Function<Integer, Integer> f = x -> x + 1;
  Function<Integer, Integer> g = x -> x * 2;
  Function<Integer, Integer> h = f.compose(g);
  int result = h.apply(1);  // 결과값: 3
  ```

### 마치며
* 람다 표현식은 익명 함수의 일종이다. 이름은 없지만 파라미터 리스트, 바디, 반환 형식을 가지며 예외를 던질 수 있다.
* 함수형 인터페이스는 하나의 추상 메서드만을 정의하는 인터페이스이다.
* 함수형 인터페이스를 기대하는 곳에서만 람다 표현식을 사용할 수 있다.
* 람다표현식을 이용해서 함수형 인터페이스의 추상 메서드를 즉석으로 제공할 수 있으며,
  람다 표현식 전체가 함수형 인터페이스의 인스턴스로 취급된다.
