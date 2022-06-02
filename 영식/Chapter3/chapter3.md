# Chapter 3 람다 표현식

## 3.1 람다란 무엇인가?

- 메서드로 전달할 수 있는 익명 함수를 단순화 한 것
    - 익명 : 보통 메서드와 달리 이름이 없음
    - 함수 : 메서드와 달리 특정 클래스에 종속되지 않는 것 , 메서드처럼 파라미터 , 바디 , 반환 형식 등을 포함
    - 람다 표현식은 메서드 인수로 전달하거나 변수로 저장할 수 있다.
    - 간결성 : 익명 클래스처럼 많은 코드를 구현할 필요 X

```java

Comparator<Apple> byWeight = new Comparator<Apple>() {
	public int compare(Apple a1 , Apple a2) {
		return a1.getWeight().compareTo(a2.getWeight());
	}
};

Comparator<Apple> byWeight = 
	(Apple a1 , Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```

람다로 간단해진 코드

(Apple a1 , Apple a2 ) `람다 파라미터` → a1.getWeight().compareTo(a2.getWeight()); `람다 바디`

- 화살표는 파라미터 리스트와 바디를 구분

- 람다 표현식 예시
    1. ( String s ) → s.length() ⇒ String 파라미터 하나와 int 값 반환 `return 생략 가능`
    2. ( Apple a ) → a.getWeight() > 150 ⇒ Apple 파라미터 하나 , boolean 리턴
    3.

     ```java
     ( int x , int y ) → { 
         System.out.println(”Result”); 
         System.out.println( x + y );
     }
     int 파라미터 두개와 리턴값 X
     
     ```

    4. () → 42 ⇒ 파라미터 값 없이 int 값 42 반환
    5. (Apple a1 , Apple a2 ) → a1.getWeight().compareTo(a2.getWeight())

       ⇒ Apple 두개 파라미터 , 무게 비교 결과값 int 반환


    - (parameters) → expression ⇒ `표현식 스타일`
    - (parameters) → { statements; } ⇒ `블록 스타일`