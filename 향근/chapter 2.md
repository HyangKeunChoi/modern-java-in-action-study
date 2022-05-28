# 동작 파라미터화 코드 전달하기

+ 동작 파라미터화란 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미한다. 이 코드 블록은 나중에 프로그램에서 호출한다. 즉, 코드 블록의 실행은 나중으로 미뤄진다.
  - 예를들어 나중에 실행될 메서드의 인수로 코드블록을 전달할 수 있다.

## 2.1 변화 하는 요구사항에 대응하기

#### 2.1.1 첫번째 : 녹색 사과 필터링
```java
public static List<Apple> filterGreenApples(List<Apple> inventory) {
  List<Apple> result = new ArrayList<>();
  for (Apple apple : inventory) {
    if(GREEN.equals(apple.getColor()) {
      result.add(apple);
    }
  }
  return result;
}  
```

#### 2.1.2 두번째 : 색을 파라미터 화
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
+ 색을 파라미터화 할 수 있도록 메서드에 파라미터를 추가하여 변화에 좀더 유연하게 대응

> Dry(dont't repeat yourself) : 같은 것을 반복하지 말자
> 
> KISS(Keep it Simple, Stupid) : 단순하게 하라
> 
> YAGNI (You Ain't Gonna Need It) : 정말 필요할 때까지 해당 기능을 만들지 마라.(실제로 필요할 때 무조건 구현하되, 그저 필요할 것이라고 예상할 때에는 절대 구현하지 말라.)

#### 2.1.3 세번째 : 가능한 모든 속성으로 필터링
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
+ 좀더 여러 속성을 파라미터로 받을 수 있긴 했지만 요구사항이 바뀌었을때 유연하게 대응할 수가 없을 정도로 지저분해 졌다.
  - 동작 파라미털화를 통해 해결해 보자

## 2.2 동작 파라미터화
#### 2.2.1 네번째 : 추상적 조건으로 필터링
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
+ 코드의 유연성도 얻으며, 가독성도 좋아 졌다, 필요에 따라 다양한 predicate를 만들어서 메서드로 전달할 수 있다.

## 2.3 복잡한 과정 간소화
#### 2.3.1 익명 클래스
익명 클래스는 자바의 지역 클래스와 비슷한 개념이다. 익명 클래스는 말 그대로 이름이 없는 클래스이다. 익명 클래스를 이용하면 클래스 선언과 인스턴스화를 동시에 
할 수 있다. 즉, 즉석에서 필요한 구현을 만들어서 사용할 수 있다.

#### 2.3.2 다섯 번째 시도 : 익명 클래스 사용
```java
List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
  public boolean test(Apple apple) {
    return RED.equals(apple.getColor());
  }
});
```
#### 단점
1. 익명 클래스는 여전히 많은 공간을 차지한다.
2. 많은 프로그래머가 익명 클래스의 사용에 익숙하지 않다.

#### 2.3.3 여섯 번째 시도 : 람다 표현식 사용
```java
  List<Apple> result = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```
#### 2.3.4 일곱 번째 시도 : 리스트 형식으로 추상화
```java
public static <T> filter(List<T> list, Predicate<T> p) {
  List<T> result = new ArrayList<>();
  for(T e : list) {
    if(p.test(e)) {
      result.add(e);
    }
  }
  return result;
}
```

## 2.4 실전 예제

#### 2.4.1 Comparator와 Comparable

#### 2.4.2 Runnable

#### 2.4.3 Callable 
