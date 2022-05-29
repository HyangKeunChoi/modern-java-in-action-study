# #chapter2

## 2. 동적 파라미터화 코드 전달하기
- 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미한다.

## 2.1 변화하는 요구사항에 대응하기
### 2.1.1 첫번째 시도: 녹색 사과 필터링
```java
enum color{RED, GREEN}

public static List<Apple> fillterGreenApple(List<Apple> invent){
    List<Apple> result = new ArrayList<>();
    for(Apple apple : invent){
        if(GREEN.equals(apple.getColor())){  // 녹색 사과만 선택
            result.add(apple);
        }
    }
    return result;
}
```
- 녹색 사과만 필터링 
- 다양한 색으로 필터링하는 등의 변화에는 적절하게 대응할 수 없다.

### 2.1.2 두번째 시도: 색을 파라미터화
```java
public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
  List<Apple> result = new ArrayList<>();
  for (Apple apple : inventory) {
    if( apple.getColor().equals(color)) {
      result.add(apple);
    }
  }
  return result;
}  
```
- 색을 파라미터화할 수 있도록 메서드에 파라미터를 추가하면 변화하는 요구사항에 좀 더 유연하게 대응이 가능하다.

### 2.1.3 세 번째 시도: 가능한 모든 속성으로 필터링
```java
public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
  List<Apple> result = new ArrayList<>();
  for (Apple apple : inventory) {
    if((flag && apple.getColor().equals(color) ||
        (!flag && apple.getWeight > weight)) {
      result.add(apple);
    }
  }
  return result;
}  
```
- 색이나 무게 중 어떤 것을 기준으로 필터링할지 가리키는 플래그를 추가한 소스
- 만약 요구사항이 바뀌었을 때 유연하게 대응할 수 없기 때문에 사용하지 말아야 한다.

## 2.2 동적 파라미터화
- 사과의 어떤 속성에 기초해서 불리언값을 반환 하는 방법
- 참 또는 거짓을 반환하는 함수를 프레디케이트라고 한다. ( 선택 조건을 결정하는 인터페이스 정의 )
```java
public interface ApplePredicate {
    boolean test(Apple apple);
}
```
- ApplePredicate는 사과 선택 전략을 캡슐화함.
### 2.2.1 네 번째 시도: 추상적 조건으로 필터링
```java
public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
  
  List<Apple> result = new ArrayList<>();
  for (Apple apple : inventory) {
    if(p.test(apple)) {
      result.add(apple);
    }
  }
  return result;
}  
```
- Apple의 속성과 관련한 모든 변화에 대응할 수 있는 유연한 코드가 된 것이다.

## 2.3 복잡한 과정 간소화
### 2.3.1 익명 클래스
- 자바의 지역 클래스와 비슷한 개념이다.
- 말 그대로 이름이 없는 클래스다.
- 클래스 선언과 인스턴스화를 동시에 할 수 있다.
### 2.3.2 다섯 번째 시도: 익명 클래스 사용
```java
List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
  public boolean test(Apple apple) {
    return RED.equals(apple.getColor());
  }
});
```
- filterApples 메서드의 동작을 직접 파라미터화

### 2.3.3 여섯 번째 시도: 람다 표현식 사용
```java
List<Apple> result = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```
- 이전 코드보다 훨씬 간단해진걸 확인할 수 있다.
### 2.3.4 일곱 번째 시도: 리스트 형식으로 추상화
```java
public interface Predicate<T> {
  boolean test(T t);
}

public static <T> filter(List<T> list, Predicate<T> p) {
  List<T> result = new ArrayList<>();
  for(T e : list) {
    if(p.test(e)) {
      result.add(e);
    }
  }
  return result;
}
// 람다표현식을 사용한 예제

List<Apple> redApples = filter(inventory, (Apple apple) -> RED.equals(apple.getColor()));
List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0 );
```
- 람다표현식을 사용했을 때 유연성과 간결함이라는 두마리 토끼를 모두 잡을 수 있다.
