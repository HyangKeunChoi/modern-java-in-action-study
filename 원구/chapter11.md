# #chapter11

## 11. null 대신 Optional 클래스
- 자바를 포함해서 최근 수십 년간 탄생한 대부분의 언어 설계에는  null 참조 개념을 포함한다.
> 가장 형식적인 함수형 언어인 하스켈, ML 등은 예외적으로 null 참조 개념을 사용하지 않는 언어다.
   이들 언어는 대수적 데이터 형식을 포함한다. 대수적 데이터 형식은 간결하게 표현할 뿐 아니라 null 같은 
   특별한 값을 포함할 것인지 등을 명시하는 규격 명세를 포함한다.
   
### 11.1 값이 없는 상황을 어떻게 처리할까?

```java
public class Person {
	private Car car;
	public Car getCar() { return car; }
}

public class Car {
	private Insurance insurance;
	pulbic Insurance getInsurance() { return insurance; }
}

public class Insurance {
	private String name;
	public String getName() { return name; }
}
```

- 대부분의 프로그래머는 null 참조를 반환하는 방식으로 자동차를 소유하고 있지 않음을 표현할 것이다.
- getInsurance는 null 참조의 보험 정보를 반환하려 할 것이므로 런타임에 NullPointerException이 발생하면서 프로그램이 중단된다.

#### 11.1.1 보수적인 자세로 NullPointerException 줄이기

- 아래의 코드는 변수를 참조할 때 마다 null을 확인하며 중간 과정에 하나라도 null 참조가 있으면 Unknown을 반환한다.
- 모든 변수가 null인지 의심하므로 변수를 접근할 때마다 중첩된 if가 추가되면서 코드 들여쓰기 수준이 증가한다.
- 따라서 이와 같은 반복 패턴 코드를 깊은 의심이라고 부른다.

```java
public String getCarInsuranceName(Person person){
	if(person != null){
		Car car = person.getCar();
		if(car != null){
			Insurance insurance = car.getInsurance();
			if(insurance != null){
				return insurance.getName();			
			}
		}
	}
	return "Unknown";
}
```

### 11.1.2 null 때문에 발생하는 문제

> 자바에서 null 참조를 사용하면서 발생할 수 있는 이론적, 실용적 문제를 확인하자.
- 에러의 근원이다 : NullPointerException은 자바에서 가장 흔히 발생하는 에러다.
- 코드를 어지럽힌다 : 떄로는 중첩된 null 확인 코드를 추가해야 하므로 null 떄문에 코드 가독성이 떨어진다.
- 아무 의미가 없다 : null은 아무 의미도 표현하지 않는다. 특히 정적 형식 언어에서 값이 없음을 표현하는 방법으로는 적절하지 않다.
- 자바 철학에 위배된다 : 자바는 개발자로부터 모든 포인터를 숨겼다. 하지만 예외가 있는데 그것이 바로 null 포인터다.
- 형식 시스셑ㅁ에 구멍을 만든다 : null은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조 형식에 null을 할당할 수 있다.

### 11.2 Optional 클래스 소개
- 자바 8은 하스켈과 스칼라의 영향을 받아서 java.util.Optional<T> 라는 새로운 클래스를 제공한다.
- Optional.empty()는 Optional의 특별한 싱글턴 인스턴스를 반환하는 정적 팩토리 메서드다.

### 11.3 Optional 적용 패턴
#### 11.3.1 Optional객체 만들기
- 빈 Optional
```java
Optional<Car> optCar = Optional.empty();
```
- null이 아닌 값으로 Optional 만들기
```java
Optional<Car> optCar = Optional.of(car);
```
- null값으로 Optional 만들기
```java
Optional<Car> optCar = Optional.ofNullable(car);
```

### 11.4 Optional을 사용한 실용 예제
#### 11.4.1 잠재저긍로 null이 될 수 있는 대상을 Optional로 감싸기
- 자바 API에서는 null을 반환하면서 요청한 값이 없거나 어떤 문제로 계산에 실패했음을 알린다.
- Map의 get 메서드는 요청한 키에 대응하는 값을 찾지 못했을 때, null을 반환하는
```java
// 문자열 "key"에 해당하는 값이 없으면 null이 반환될 것이다.
Object value = map.get("key");

// 이와 같은 코드를 이용해서 null일 수 있는 값을 Optional로 안전하게 반환할 수 있다.
Optional<Object> value = Optional.ofNullable(map.get("key"));
```

### 11.4.2 예외와 Optional 클래스

```java
public static Optional<Integer> stringToInt(String s){
	try{
		// 문자열을 정수로 변환할 수 있으면 정수로 변환된 값을 포함하는 Optional을 반환
		return Optional.of(Integer.parseInt(s));	
	}catch (NumberFormatException e) {
		return Optional.empty();	// 그렇지 않으면 빈 Optional을 반환
	}
}
```
