# #chapter3

## 3. 람다 표현식
- 메서드로 전달할 수 있는 익명 함수를 단순화한 것이라고 할 수 있다.

### 3.1 람다란 무엇인가?
- 익명
  - 보통의 메서드와 달리 이름이 없으므로 익명이라 표현
- 함수
  - 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다. 
- 전달
  - 메서드 인수로 전달하거나 변수로 저장할 수 있다.
- 간결성
  - 익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.
  
> 람다를 사용하는 이유
- 람다 표현식을 이용하면 동작 파라미터 형식의 코드를 더 쉽게 구현할 수 있다.

```java
//기존 코드
Comparator<Apple> byWeight = new Comparator<Apple>(){
  public int compare(Apple a1, Apple a2){
    return a1.getWeight().compareTo(a2.getWeight());  
  }
}
// 람다 이용
Comparator<Apple> byWeight = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```
- 람다 표현식을 사용하면 compare 메서드의 바디를 직접 전달하는 것처럼 코드를 전달할 수 있다.
- 람다 표현식은 파라미터, 화살표, 바디로 이루어진다.

### 3.2.1 함수형 인터페이스
- 함수를 1급 객체처럼 다룰 수 있게 해주는 어노테이션으로, 인터페이스에 선언하여 단 하나의 추상 메소드만을 갖도록 제한하는 역할을 한다.
- 자바 API의 함수형 인터페이스로는 Comparator, Runnable 등이 있다.

### 3.2.2 함수 디스크립터
- 람다 표현식의 시그니처를 서술하는 메서드를 함수

### @FunctionalInterface는 무엇인가?
- 함수형 인터페이스임을 가르키는 어노테이션
- 인터페이스 선언 했지만 실제로 함수형 이너페이스가 아니면 컴파일 오류 발생 

## 3.4 함수형 인터페이스 사용
### 3.4.1 Predicate
- Predicate<T> 인터페이스는 test라는 추상 메서드를 정의하며 test는 제네릭 형식 T의
  객체를 인수로 받아 불리언을 반환한다.
```java
@FunctionalInterface
public interface Predicate<T> {
  boolean test(T t);
}
public <T> List<T> filter(List<T> list, predicate<T> p){

  List<T> results = new ArrayList<>();
  for(T t: list){
    if(p.test(t)){
	  results.add(t);
	}
  }
  return results;
}
Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
```

### 3.4.2 Consumer
- Consumer<T> 인터페이스는 제네릭 형식 T 객체를 받아서 void를 반환하는 accept라는 추상 메서드를 정의
```java
@FunctionalInterface
public interface Consumer<T> {
  void accept(T t);
}

public <T> void forEach(List<T> list, Consumer<T> c){
  for(T t: test){
    c.accept(t);
  }
}
forEach(
      Arrays.asList(1,2,3,4,5),
	  (Integer i) -> System.out.println(i) // consumer의 accept 메서드를 구현하는 람다
```

### 3.4.3 Function
- Function<T,R> 인터페이스는 제네릭 형식 T를 인수로 받아서 제네릭 형식 R 객체를
  반환하는 추상 메서드 apply를 정의
```java
@FunctionalInterface
public interface function<T, R>{
  R apply(T t);
}

public <T, R> List<R> map(List<T> list, Function<T, R> f) {
  List<R> result = new ArrayList<>();
  for(T t: list){
    result.add(f.apply(t));
  }
  return result;
}

// [7, 2, 6]
List<Integer> l = map(
    Arrays.asList("lambdas", "in", "action"),
	(String s) -> s.length() 	//Function의 apply 메서드를 구현하는 람다
});
```
