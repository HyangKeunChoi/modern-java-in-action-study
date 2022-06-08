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


## 어디에 어떻게 람다를 사용할까?

어디에 ⇒ 함수형 인터페이스에

- 함수형 인터페이스 인스턴스를 생성할 때
- 함수형 인터페이스를 인수로 하는 메서드에

어떻게 사용할까?

- 함수형 인터페이스 안에 있는 추상메서드의 시그니처에 맞게 람다 표현식을 사용해야한다.

‼️ @FunctionalInterface : 함수형 인터페이스를 가리키는 어노테이션 , 실제 함수형 인터페이스가 아니면 오류가 발생 → 추상메서드가 두개일 경우 등

## 실행 어라운드 패턴

- 책 내용이 이해가 안되어서 구글링

![책내용](../image/img1.png)

- `준비`와 `정리` 부분은 틀처럼 정해져 있고 실행부분만 바뀌는 코드의 형태

### 3.3.1 ~ 3.3.4

기존의 코드에서 함수형 인터페이스로 인수를 변경하고 , 해당 동작을 람다표현식으로 전달해서 실행 어라운드 패턴으로 변경

```java
// first 
public String processFile() throws IOException {
	try ( BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
		return br.readLine();
	}
}

// 함수형 인터페이스 
public interface BufferedReaderProcessor {
	String process(BufferedReader p) throws IOException; // 추상 메서드 
}

// 인수를 함수형 인터페이스인 BuffredReaderProccessor로 변경 
// return 값에 함수형 인터페이스의 추상 메서드 process값으로 선언
public String processFile(BufferedReadderProccessor p) thorw IOException {
	try (BufferedReader br = new BufferedReader (new FileReader("data.txt"))) {
		return p.process(br);
	}
}

// 람다 표현식 사용 
// (BufferedReader br) -> br.readLine(); 
// 인수 BufferedReader 형태 객체 , 반환 값 String 값 
// 해당 람다 표현식은 추상메서드인 process 의 시그니처(함수 디스크립터)와 같기 때문에 알맞은 람다 표현식 
String oneLine = processFile((BufferedReader br) -> br.readLine());

String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());

```