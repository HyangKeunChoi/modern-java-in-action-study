# null 대신 Optional   

### 값이 없는 상황 처리   
 * nullPointException 을 줄이기 위한 null 확인 코드
 
```java 
	//깊은 의심이라고 표현하는 코드(너무 지저분한 들여쓰기)
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
	
	//너무 많은 출구 코드(유지보수 어려움)
	public String getCarInsuranceName(Person person){
		if(person == null){
			return "Unknown";
		}
		Car car = person.getCar();
		if(car == null){
			return "Unknown";
		}
		Insurance insurance = car.getInsurance();
		if(insurance == null){
			return "Unknown";
		}
		return insurance.getName();
	
	}
```

# null때문에 발생하는 문제
 - 에러의 근원이다: nullPointException 은 가장 흔히 발생하는 에러.   
 - 코드를 어지럽힌다: 중첩된 null 확인 코드를 추가함으로 가독성 저하   
 - 아무 의미가 없다: 말그대로   
 - 자바 철학에 위배된다: 자바는 포인터를 숨겼지만 null포인터만 예외   
 - 형식 시스템에 구멍을 만든다: null은 무형식이라 모든 참조 형식에 null 할당 가능, 다른 부분으로 null이 펴질 경우 어떤 의미인지 모름.   

# java.util.Optional<T>   
 * null참조와 Optional.empty() 차이
 
```java
	public class Person{
		private Optional<Car> car; //차를 소유 했을수도 안했을수도?
		public Optional<Car> getCar(){
			return car;
		}
	}
	public class Car {
		private Optional<Insurance> insurance; //Optional 을 참조하는 순간부터 값이 있는지 없는지 불확실 하다는 의미인듯
		public Optional<Insurance> getInsurance(){
			return insurance;
		}
	}
	public class Insurance{
		private String name; //보험회사는 반드시 이름을 가져야 한다
		public String getName() {
			return name;
		}
	}
```
# Optional 적용 패턴
 * Optional 객체 만들기
 	- 빈 Optional   
 		- Optional<Car> optCar = Optional.empty();   
 	- null이 아닌 값으로 Optional 만들기   
 		- Optional<Car> optCar = Optional.of(car);   
 	- null값으로 Optional 만들기   
 		- Optional<Car> optCar = Optional.ofNullable(car);
 		
# 맵으로 Optional값 추출 및 변환
 * 보통 객체의 정보 추출할 때 Optional 사용
 
```java
	String name = null;
	if(insurance != null){
		name = insurance.getName();
	}
	//위 유형의 패턴에 사용
	
	Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
	Optional<String> name = optInsurance.map(Insurance::getName);
	//1.Optional이 값을 포함하면 map의 인수로 제공된 함수가 값을 바꿈.
	//2.Optional이 비어있으면 아무일도 일어나지 않음.
	
	public String getCarInsuranceName(Person person){
		return person.getCar().getInsurance().getName();
	}
	//여러 메서드 안전하게 호출.
```

# flatMap으로 Optional 객체 연결

```java
	//컴파일 되지 않는 코드.
	Optional<Person> optPerson = Optional.of(person);
	Optional<String> name = 
		optPerson.map(person::getCar)
			.map(Car::getInsurance)
			.map(Insurance::getName);
```
 
