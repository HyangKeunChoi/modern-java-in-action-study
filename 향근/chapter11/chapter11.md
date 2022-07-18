# null 대신 Optional 클래스

+ 자바로 프로그램을 개발하면서 한 번이라도 NullPointerException을 겪은 사람은 손을 들자
+ NullPointerException은 초급자, 중급자, 남녀노소를 불문하고 모든 자바 개발자를 괴롭히는 예외긴 하지만 null이라는 표현을 사용하면서 치러야 할 당연한 대가가 아닐까?

### 11.1.1 보수적인 자세로 NullPointerException 줄이기
```java
public String getCarInsuranceName(Person person) {
  if(person != null) {
    Car car = person.getCar();
    if (car != null) {
      Insurance insurance = car.getInsurance();
      if (insurance != null) {
        return insurance.getName();
      }
    }
  }
  return "Unknown";
}
```

+ 반복 패턴(recurring pattern)코드를 깊은 의심이라고 부른다.
+ 즉, 변수가 null인지 의심되어 중첩 if블록을 추가하면 코드 들여쓰기 수준이 증가한다.
+ 이를 반복하다보면 코드의 구조가 엉망이 되고 가독성이 떨어진다. 따라서 뭔가 다른 해결 방법이 필요하다.


### 11.1.2 null 때문에 발생하는 문제
1. 에러의 근원이다. : NullpointerException은 자바에서 가장 흔히 발생하는 에러다.
2. 코드를 어지럽힌다. : 때로는 중첩된 null 확인 코드를 추가해야 하므로 null때문에 코드 가독성이 떨어진다.
3. 아무 의미가 없다. : null은 아무 의미도 표현하지 않는다. 특히 정적 형식 언어에서 값이 없음을 표현하는 방법으로는 적절하지 않다.
4. 자바 철학에 위배된다. : 자바는 개발자로부터 모든 포인터를 숨겼다. 하지만 예외가 있는데 그것이 바로 null 포인터다.
5. 형식 시스템에 구멍을 만든다. : null은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조 형식에 null을 할당할 수 있다. 이런식으로 null이 할당 되기 시작하면서
시스템의 다른 부분으로 null이 퍼졌을 때 애초에 null이 어떤 의미로 사용되었는지 알 수 없다.

## 11.2 Optional 클래스 소개
+ 자바 8은 하스켈과 스칼라의 영향을 받아서 java.util.Optional<T>라는 새로운 클래스를 제공한다.
+ Optional.empty는 Optional의 특별한 싱글턴 인스턴스를 반환하는 정적 팩토리 메서드다.
+ null참조와 Optional.empty는 서로 무엇이 다를까??
  - null을 참조하려 하면 NullPointerException이 발생하지만 Optional.empty()는 Optional객체이므로 이를 다양한 방식으로 활용할 수 있다.

> Optional을 이용하면 값이 없는 상황이 우리 데이터에 문제가 있는 것인지 아니면 알고리즘의 버그인지 명확하게 구분할 수 있다.
> 모든 null참조를 Optional로 대치하는 것은 바람직하지 않다.
> Optional의 역할은 더 이해하기 쉬운 API를 설계하도록 돕는것이다. 즉 메서드의 시그니처만 봐도 선택형값인지 여부를 구별할 수 있다.
 
### 11.3 Optional 적용 패턴
### 11.3.1 Optional 객체 만들기
#### 빈 Optional
```java
Optional<Car> optCar = Optional.empty();  
```
  
#### null이 아닌 값으로 Optional 만들기
```java
Optional<Car> optCar = Optional.of(car);  
```

#### null값으로 Optional 만들기
```java
Optional<Car> optCar = Optional.ofNullable(car);  
```  
  
#### Optional로 자동차 보험회사 이름 찾기
```java
  public String getCarInsuranceName(Optional<Person> person) {
    return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
  }
```

### 11.3.5 디폴트 액션과 Optional 언랩
  1. get()은 값을 읽는 가장 간단한 메서드이면서 동시에 가장 안전하지 않다. Optional에 값이 반드시 있다고 가정할 수 있는 상황이 아니면 get메서드를 사용하지 않는 것이 바람직 하다.
  2. orElse를 이용하면 Optional이 값을 포함하지 않을 때 기본값을 제공할 수 있다.
  3. orElseGet는 orElse메서드에 대응하는 게으른 버전의 메서드다. Optional에 값이 없을 때만 Supplier가 실핼되기 때문이다.
  4. orElseThrow는 Optional이 비어있을 때 예외를 발생시킨다는 점에서 get 메서드와 비슷하지만 이 메서드는 예외의 종료를 선택할 수 있다.
  5. ifPresent를 이용하면 값이 존재할 때 인수로 넘겨준 동작을 실행할 수 있다. 값이 없으면 아무 일도 일어나지 않는다.
  
  
### 11.4 Optional을 사용한 실용 에졔
#### 11.4.1 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기
```java
  Optional<Object> value = Optional.ofNullable(map.get("key"));
```
  
### 11.4.2 예외와 Optional 클래스
 + 자바 API는 어떤 이유에서 값을 제공할 수 없을 때 null을 반환하는 대신 예외를 발생시킬 때도 있다.
  - parseInt의 NumberFormatException이 그 예
    - 정수로 변환할 수 없는 문자열 문제를 빈 Optional로 해결 할 수 있다. 즉 parseInt가 Optional을 반환하도록 모델링한다.
  
```java
  public static Optional<Integer> stringToInt(String s) {
    try {
      return Optional.of(Integer.parseInt(s));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }
```
  
> 기본형 Optional을 사용하지 말자
