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
