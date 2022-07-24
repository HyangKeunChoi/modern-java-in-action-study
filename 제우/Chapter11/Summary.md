# 11. null 대신 Optional 클래스
* 이 장의 내용
  - null 참조의 문제점과 null을 멀리해야 하는 이유
  - null 대신 Optional : null로부터 안전한 도메인 모델 재구현하기
  - Optional활용 : null 확인 코드 제거하기
  - Optional에 저장된 값을 확인하는 방법
  - 값이 없을 수도 있는 상황을 고려하는 프로그래밍
 
## 11.1 값이 없는 상황을 어떻게 처리할까?
* 예제: Person/Car/Insurance 데이터 모델
```java
public class Person {
  private Car car;
  public Car getCar() {return car;}
}

public class Car {
  private Insurance insurance;
  public Insurance getInsurance() { return insurance; }
}

public class Insurance {
  private String name;
  public String getName() { return name; }
}
```
```java
public String getCarInsuranceName(Person person) {
  return person.getCar().getInsurance().getName();  // 차를 소유하지 않은 사람의 getCar()를 호출할 경우 NPE발생.
}
```
### 11.1.1 보수적인 자세로 NullPointerException 줄이기
* 예기치 않은 NPE를 피하기 위해 필요한 곳에 null확인 코드를 추가한다.
```java
public String getCarInsuranceName(Person person) {
  if (person != null) {
    Car car = person.getCar();
    if (car != null ) {
      Insurance insurance = car.getInsurance();
      if (insurance != null) {
        return insurance.getName();
      }
    }
  }
  return "Unknown"
}
// null 확인 코드 때문에 나머지 호출 체인의 들여쓰기 수준이 증가한다.
// 이와 같은 반복 패턴코드를 '깊은 의심'이라고 부른다.
// 코드의 구조가 엉망이 되고 가독성이 떨어지는 문제가 발생한다.
```
```java
public String getCarInsuranceName(Person person) {
  if (person == null) {
    return "Unknown";
  }
  Car car = person.getCar();
  if (car == null) {
    return "Unknown";
  }
  Insurance insurance = car.getInsurance();
  if (insurance == null) {
    return "Unknown";
  }
  return insurance.getName();
}
// 너무 많은 출구가 존재하여 유지보수가 어려워진다.
```
### 11.1.2 null때문에 발생하는 문제
* 에러의 근원이다 : NPE은 자바에서 가장 흔히 발생하는 에러다.
* 코드를 어지럽힌다 : 때로는 중첩된 null확인 코드를 추가해야 하므로 코드 가독성이 떨어진다.
* 아무 의미가 없다 : null은 아무 의미도 표현하지 않는다. 특히 정적 형식 언어에서 값이 없을을 표현하는 방법으로는 적절하지 않다.
* 자바 철학에 위배된다 : 자바는 개발자로부터 모든 포인터를 숨겼다. 하지만 null포인터는 예외다.
* 형식 시스템에 구멍을 만든다 : null은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조 형식에 null을 할당할 수 있다.
### 11.1.3 다른 언어는 null대신 무얼 사용하나?
* 최근 그루비 같은 언어에서는 안전 네비게이션 연산자(?.)를 도입해서 null문제를 해결했다.
```java
def carInsuranceName = person?.car?.insurance?.name // 그루비에서 자동차 보험회사의 이름을 가져오는 코드
// 호출 체인에 null인 참조가 있으면 결과로 null이 반환된다.
```

## 11.2 Optional 클래스 소개
* Optioanl은 선택형값을 캡슐화하는 클래스다.
* 예를 들어 어떤 사람이 차를 소유하고있지 않다면 Person클래스의 car변수는 null을 가져야 할 것이다.
* 하지만 새로운 Optional을 이용할 수 있으므로 null을 할당하는 것이 아니라 변수형을 Optional\<Car\>로 설정할 수 있다.
* 값이 있으면 Optional클래스는 값을 감싼다. 반면 값이 없으면 Optional.empty메서드로 Optional을 반환한다.
* Optional.empty는 Optional의 특별한 싱글턴 인스턴스를 반환하는 정적 팩토리 메서드다.
* null과 Optional.empty() 차이점 : null을 참조하려 하면 NPE가 발생하지만 Optional.empty()는 Optional객체이므로 이를 다양한 방식으로 활용할 수 있다.
* Optional로 Person/Car/Insurance 데이터 모델 재정의
``` java
  public class Person {
    private Optional<Car> car;  // 사람이 차를 소유했을 수도 소유하지 않았을 수도 있으므로 Optional로 정의한다.
    public Optional<Car> getCar() { return car; }
  
  public class Car {
    private Optional<Insurance> insurance;  // 자동차가 보험에 가입되어 있을 수도 가입되어 있지 않을 수도 있으므로 Optional로 정의한다.
    public Optional<Insuracne> getInsurance() { return insurance; }
  }
  
  public class Insurance {
    private String name;  // 보험회사에는 반드시 이름이 있기 때문에 Optional로 정의X
    public String getName() { return name; }
  }
// Optional을 사용하면서 데이터 모델의 의미가 더욱 명확해졌다.
```
* 보험회사 이름은 Optional\<String\>이 아니라 String형식으로 선언되어 있는데, 이는 보험회사는 반드시 이름을 가져야 함을 보여준다.
* 따라서 보험회사 이름을 참조할 때 NPE가 발생할 수도 있다는 정보를 보여준다.
* 하지만 보험회사 이름이 null인지 확인하는 코드를 추가할 필요는 없다. 오히려 고쳐야 할 문제를 감추는 꼴이 되기 때문이다.
* 보험회사는 반드시 이름을 가져야 하며 이름이 없는 보험회사를 발견했다면 예외를 처리하는 코드를 추가하는 것이 아니라 
  보험회사 이름이 없는 이유가 무엇인지 밝혀서 문제를 해결해야 한다.(데이터를 확인해야 한다.)
* Optional을 이용하면 값이 없는 상황이 데이터에 문제가 있는 것인지 아니면 알고리즘의 버그인지 명확하게 구분할 수 있다.
* 모든 null참조를 Optional로 대치하는 것은 바람직하지 않다. Optional의 역할은 더 이해하기 쉬운 API를 설계하도록 돕는 것이다.
* 즉, 메서드의 시그니처만 보고도 선택형값인지 여부를 구별할 수 있다.
  
## 11.3 Optional 적용 패턴
* 지금까지 Optional 형식을 이용해서 도메인 모델의 의미를 더 명확하게 만들었으며 null참조 대신 값이 없는 상황을 표현할 수 있음을 확인했다.
* 지금부터 Optional을 어떻게 활용할 수 있을지 확인한다.
### 11.3.1 Optional 객체 만들기
* 빈 Optional
  - 정적 팩토리 메서드 Optional.empty()로 빈 Optional 객체를 얻을 수 있다.
  ```java
  Optional<car> optCar = Optional.empty();
  ```
* null이 아닌 값으로 Optional 만들기
  - 정적 팩토리 메서드 Optional.of()로 null이 아닌 값을 포함하는 Optional을 만들 수 있다.
  ```java
  Optional<Car> optCar = Optional.of(car);
  ```
  - 이제 car가 null이라면 즉시 NPE가 발생한다.(Optional을 사용하지 않았다면 car의 프로퍼티에 접근하려 할 때 에러가 발생했을 것이다.)
* null값으로 Optioanl 만들기
  - 정적 팩토리 메서드 Optional.ofNullable로 null값을 저장할 수 있는 Optional을 만들 수 있다.
  ```java
  Optional<car> optCar = Optional.ofNullable(car);
  ```
  - car가 null이면 빈 Optional 객체가 반환된다.
### 11.3.2 맵으로 Optional의 값을 추출하고 변환하기
* 보통 객체의 정보를 추출할 때는 Optional을 사용할 때가 많다.
``` java
String name = null;
if(insurance != null) {
  name = insurance.getName();
}
// 보험회사의 이름을 추출할 때 insurance가 null인지 확인해야 한다.
```
* 이런 유형의 패턴에 사용할 수 있도록 Optional은 map메서드를 지원한다.
```java
Optional<Insruance> optInsurance = Optional.ofNullable(insurance);
Optional<String> name = optInsurance.map(Insurance::getName);
```
### 11.3.3 flatMap으로 Optional 객체 연결
```java
Optional<Person> optPerson = Optional.of(person);
Optional<String> name =
  optPerson.map(Person::getCar)
           .map(Car::getInsurance)
           .map(Insurance::getName);
```
* 위 코드는 컴파일되지 않는다. optPerson의 형식은 Optional\<Person\>이므로 map메서드를 호출할 수 있다.
* 하지만 getCar는 Optional\<Car\>형식의 객체를 반환한다.
* 즉, map연산의 결과는 Optional\<Optional\<Car\>\> 형식의 객체다.
* getInsurance는 또 다른 Optional 객체를 반환하므로 getInsurance메서드를 지원하지 않는다.
* 이 문제를 flatMap으로 해결할 수 있다.
```java
// Optional로 자동차 보험회사의 이름 찾기
public String getCarInsuranceName(Optional<Person> person) {
  return person.flatMap(Person::getCar)
               .flatMap(Car::getInsurance)
               .map(Insurance::getName)
               .orElse("Unknown"); // 결과 Optional이 비어있으면 기본값 사용
}
```
* 위 코드와 같이 Optional을 사용하면 값이 없는 상황을 효과적으로 처리할 수 있다.
* 즉, null을 확인하느라 조건 분기문을 추가해서 코드를 복잡하게 만들지 않으면서도 쉽게 이해할 수 있는 코드를 완성할 수 있다.
### 11.3.4 Optional 스트림 조작
* 자바9에서는 Optional을 포함하는 스트림을 쉽게 처리할 수 있도록 Optional에 stream()메서드를 추가했다.
* Optional스트림을 값을 가진 스트림으로 변환할  때 이 기능을 유용하게 활용할 수 있다.
```java
public Set<String> getCarInsuranceNames(List<Person> persons) {
  return persons.stream()
                .map(Person::getCar)  // 사람 목록을 각 사람이 보유한 자동차의 Optional<Car> 스트림으로 변환
                .map(optCar -> optCar.flatMap(Car::getInsurance))   // flatMap연산을 이용해 Optional<Car>을 Optional<Insurance>로 변환
                .map(optIns -> optIns.flatMap(Insurance::getName))  // Optional<Insurance>를 Optional<String>으로 변환
                .flatMap(Optional::stream)  // Stream<Optional<String>>을 Stream<String>으로 변환
                .collect(toSet());  // 결과 문자열을 중복되지 않은 값을 갖도록 집합으로 수집
}
```
### 11.3.5 디폴트 액션과 Optional 언랩
* 11.3.3절에서는 빈 Optional인 상황에서 기본값을 반환하도록 orElse로 Optional을 읽었다.
* 이 외에도 Optional클래스는 Optional인스턴스에 포함된 값을 읽는 다양한 방법을 제공한다.
* get()은 값을 읽는 가장 간단한 메서드면서 동시에 가장 안전하지 않은 메서드이다.
  get은 랩핑된 값이 있으면 해당값을 반환하고 값이 없으면 NoSuchElementException을 발생시킨다.
  따라서 Optional에 값이 반드시 있다고 가정할 수 있는 상황이 아니면 get 메서드를 사용하지 않는 것이 바람직하다.
  결국 이 상황은 중첩된 null확인 코드를 넣는 상황과 크게 다르지 않다.
* orElse(T other)메서드를 이용하면 Optional이 값이 포함하지 않을 때 기본값을 제공할 수 있다.
* orElseGet(Supplier\<? extends T\> other)는 orElse메서드에 대응하는 게으른 버전의 메서드이다.
  Optional에 값이 없을 때만 Supplier가 실행되기 때문이다.
  디폴트 메서드를 만드는 데 시간이 걸리거나(효율성 때문에) Optional이 비어있을 때만 기본값을 생성하고 싶다면(기본값이 반드시 필요한 상황) orElseGet을 사용해야 한다.
* orElseThrow()(Supplier\<? extends T\> exceptionSupplier)는 Optional이 비어있을 때 예외를 발생시킨다는 점에서 get메서드와 비슷하다.
  이 메서드는 발생시킬 예외의 종류를 선택할 수 있다.
* ifPresent(Consumer\<? super T\> consumer)를 이용하면 값이 존재할 때 인수로 넘겨준 동작을 실행할 수 있다. 값이 없으면 아무 일도 일어나지 않는다.
### 11.3.6 두 Optional 합치기
```java
public Insurance findCheapestInsurance(Person person, Car car) {
  // 다양한 보험회사가 제공하는 서비스 조회
  // 모든 결과 데이터 비교
  return cheapestCompany;
}

public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
  if (person.isPresent() && car.isPresent()) {
    return Optional.of(findCheapestInsurance(person.get(), car.get()));
  } else {
    return Optional.empty();
  }
}


// nullSafeFindCheapestInsurance 개선버전
public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
  return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
}
```
### 11.3.7 필터로 특정값 거르기
```java
// 예제1
Optional<Insurance> optInsurance = ...;
optInsurance.filter(insurance -> "CambridgeInsurance".equals(insurance.getName()))
            .ifPresent(x -> System.out.println("ok"));
            
// 예제2
public String getCarInsuranceName(Optional<Person> person, int minAge) {
  return person.filter(p -> p.getAge() >= minAge)
               .flatMap(Person::getCar)
               .flatMap(Car::getInsurance)
               .map(Insurance::getName)
               .orElse("Unknown");
}
```

## 11.4 Optional을 사용한 실용 예제
### 11.4.1 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기
* 지금까지 살펴본 것처럼 null을 반환하는 것보다는 Optional을 반환하는 것이 더 바람직하다.
* get메서드의 시그니처는 우리가 고칠 수 없지만 get메서드의 반환값은 Optional로 감쌀 수 있다.
```java
Object value = map.get("key");
// 문자열 key에 해당하는 값이 없으면 null이 반환될 것이다.
// map에서 반환하는 값을 Optional로 감싸서 이를 개선할 수 있다.
Optional<Object> value = Optional.ofNullable(map.get("key"));
```
### 11.4.2 예외와 Optional 클래스
* 자바 API는 어떤 이유에서 값을 제공할 수 없을 때 null을 반환하는 대신 예외를 발생시킬 때도 있다.
* 대표적으로 Integer.parseInt(String)는 문자열을 정수로 바꾸지 못할 때 NumberFormatException을 발생시킨다.
* 아래 예제와 같은 OptionalUtility를 만들면 필요할 때 문자열을 Optional로 다룰 수 있다.
```java
public static Optional<Integer> stringToInt(String s) {
  try {
    return Optional.of(Integer.parseInt(s));
  } catch (NumberFormatException e) {
    return Optional.empty();
  }
}
```
### 11.4.4 응용
* 예를 들어 프로그램의 설정 인수로 Properties를 전달한다고 가정하자.
* 다음과 같은 Properties로 우리가 만든 코드를 테스트할 것이다.
```java
Properties props = new Properties();
props.setProperty("a", "5");
props.setProperty("b", "true");
props.setProperty("c", "-3");

// 프로그램에서는 Properties를 읽어서 값을 초 단위의 지속시간으로 해석한다. 다음과 같은 메서드 시그니처로 지속시간을 읽을 것이다.
public int readDuration(Properties props, String name)

// 지속시간은 양수여야 하므로 문자열이 양의 정수를 가리키면 해당 정수를 반환하지만 그 외에는 0을 반환한다. 이를 다음처럼 JUnit 어설션으로 구현할 수 있다.
assertEqulas(5, readDuration(param, "a"));
assertEqulas(0, readDuration(param, "b"));
assertEqulas(0, readDuration(param, "c"));
assertEqulas(0, readDuration(param, "d"));
// 이를 어설션은 다음과 같은 의미를 갖는다.
// 프로퍼티 'a'는 양수로 변환할 수 있는 문자열을 포함하므로 readDuration메서드는 5를 반환한다.
// 프로퍼티 'b'는 숫자로 변환할 수 없는 문자열을 포함하므로 readDuration메서드는 0을 반환한다.
// 프로퍼티 'c'는 음수 문자열을 포함하므로 0을 반환한다.
// 'd'라는 이름의 프로퍼티는 없으므로 0을 반환한다.
```
```java
// 프로퍼티에서 지속 시간을 읽는 명령형 코드
public int readDuration(Properties props, String name) {
  String value = props.getProperty(name);
  if(value != null) {
    try {
      int i = Integer.parseInt(value);
      if(i >0) return i;
    } catch (NumberFormatException nfe) { }
  }
  return 0;
}
// 위 코드를 아래와 같이 변형할 수 있다.
public int readDuration(Properties props, String name) {
  return Optional.ofNullable(props.getProperty(name))
                 .flatMap(OptionalUtility::stringToInt)
                 .filter(i -> i>0)
                 .orElse(0);
}
```

### 11.5 마치며
* 자바8에서는 값이 있거나 없음을 표현할 수 있는 클래스 java.util.Optional\<T\>를 제공한다.
* 팩토리 메서드 Optional.empty, Optional.of, Optional.ofNullable 등을 이용해서 Optional 객체를 만들 수 있다.
* Optional클래스는 스트림과 비슷한 연산을 수행하는 map, flatMap, filter 등의 메서드를 제공한다.
* Optional로 값이 없는 상황을 적절하게 처리하도록 강제할 수 있다. 즉, Optional로 예상치 못한 null예외를 방지할 수 있다.
* Optional을 활용하면 더 좋은 API를 설계할 수 있다. 즉, 사용자는 메서드의 시그니처만 보고도 Optional값이 사용되거나 반환되는 예측할 수 있다.
