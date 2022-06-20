# 함수형 인터페이스 ( 자바의 정석 )

[[자바의 정석 - 기초편] ch14-5,6 함수형인터페이스](https://youtu.be/0Sp9eFRV8gE)

- 함수형 인터페이스 ⇒ 단 하나의 추상 메서드만 선언된 인터페이스

```java
interface MyFunction { 
	public abstract int max (int a , int b );
}

// 익명클래스로 작성 
MyFunction f =  new MyFunction() {
	public int max (int a , int b ) {
	return a > b ? a : b ;
	}
};

int value = f.max(3,5); // OK , MyFunction에 max() 가 있다.
// obj.max(3,5) => 에러 
// f 함수형 인터페이스 안에는 함수형 인터페이스가 존재 

```

- 함수형 인터페이스 타입의 참조변수로 람다식을 참조할 수 있다.

  ⇒ 단 , 함수형 인터페이스의 메서드와 람다식의 **매개변수 개수와 반환타입**이 일치 해야한다.


```java
// 위에 있는 익명클래스 대신에 람다식을 사용할 수 있다.
MyFunction f = (a , b ) -> a > b ? a : b ;

int value = f.max(3,5); // 실제로는 람다식(익명 함수)이 호출된다.

```

1. 함수형 인터페이스 선언
2. 함수형 인터페이스의 참조식에 람다식을 사용
3. 함수형 인터페이스는 람다식을 다루기 위해서 사용한다.

- 실습

```java
@FucntionalInterface // 안붙여도 되지만 붙이면 만약 추상메서드가 두개면 에러가 발생 
interface MyFunction {
	public abstract int max(int a , int b) ;
}

MyFunction f = (a, b) -> a > b ? a: b; // 람다식 , 익명 객체 
// 람다식 ( 익명 객체 )을 다루기 위한 참조 변수의 타입은 함수형 인터페이스로 한다.

int value = f.max(3,5); // 함수형 인터페이스 

```

- 람다식의 형태와 함수형 인터페이스의 추상메서드의 형태가 같아야 한다. ( 매개변수와 반환 타입 )

  ⇒ 같지 않으면 에러가 난다.


람다식은 메서드 , 람다식을 만들었으면 사용해야한다. 사용해야하려면 이름이 있어야한다. 람다식은 익명객체로 이름이 없다. ⇒ 람다식의 이름이 필요하기 때문에 함수형 인터페이스를 사용해서 이름을 붙여서 호출해서 사용

- 함수형 인터페이스의 추상메서드하고 람다식하고 연결해주는 것
- 추상메서드를 통해서 람다식을 호출하는 것으로 생각하자

### Example

```java
List<String> list = Arrays.asList("abc", "aaa", "bbb", "ddd", "aaa");

Collections.sort(list, new Comparator<String>() {
	public int compare(String s1 , String s2) {
		return s2.compareTo(s1);
		}
	});
	
// 익명 클래스를 람다식으로 대체 
Collections.sort(list,(s1 , s2 ) -> s2.compareTo(s1));

// 함수형 인터페이스 Comparator 에 람다식을 주입
Comparator c = (s1 , s2) -> s2.compareTo(s1); 
```

- 함수형 인터페이스 타입의 매개변수에 람다식을 받을 수 있다.

  어떤 메서드의 매개변수가 함수형 인터페이스의 경우 람다식을 매개변수로 넣어서 호출해서 사용