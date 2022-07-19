11.1.2 null 때문에 발생하는 문제
- 에러의 근원이다 : NullPointerException은 자바에서 가장 흔히 발생하는 에러이다.
- 코드를 어지럽힌다 : 때로는 중첩된 null 확인 코드를 추가해야 하므로 null 때문에 코드 가독성이 떨어진다.
- 아무 의미가 없다 : null은 아무 의미도 표현하지 않는다. 특히 정적 형식 언어에서 값이 없음을 표현하는 방법으로는 적절하지 않다.
- 자바 철학에 위배된다 : 자바는 개발자로부터 모든 포인터를 숨겼다. 하지만 예외가 있는데 그것이 바로 null 포인터다.
- 형식 시스템에 구멍을 만든다 : null은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조 형식에 null을 할당할 수 있다.
  이런 식으로 null이 할당되기 시작하면서 시스템의 다른 부분으로 null이 퍼졌을 때 애초에 null이 어떤 의미로 사용되었는지 알 수 없다.
  
  
11.2 Optional 클래스 소개
- Optional은 선택형값을 캡슐화하는 클래스다.

11.3.1 Optional 객체 만들기
- Optional을 사용하려면 Optional 객체를 만들어야 한다. 다양한 방법으로 Optional 객체를 만들 수 있다.

빈 Optional
- 정적 팩토리 메서드 Optional.empty로 빈 Optional 객체를 얻을 수 있다.
  Optional<Car> optCar = Optional.empty();

null이 아닌 값으로 Optional 만들기
- 또는 정적 팩토리 메서드 Optional.of로 null이 아닌 값을 포함하는 Optional을 만들 수 있다.
  Optional<Car> optCar = Optional.of(car);
  이제 car가 null이라면 즉시 NullPointerException이 발생한다(Optional을 사용하지 않았다면 car의 프로퍼티에 접근하려 할 때 에러가 발생했을 것이다).

null 값으로 Optional 만들기
- 마지막으로 정적 팩토리 메서드 Optional.ofNullable로 null값을 저장할 수 있는 Optional을 만들 수 있다.
  Optional<Car> optCar = Optional.opfNullable(car);
  car가 null이면 빈 Optional 객체가 반환된다.
  
Optional은 get메서드를 이용해서 값을 가져올 수 있는데 Optional이 비어있으면 get을 호출했을 때 예외가 발생한다.
즉, Optional을 잘못 사용하면 결국 null을 사용했을 때와 같은 문제를 겪을 수 있다.

11.3.2 맵으로 Optional의 값을 추출하고 변환하기
- 보통 객체의 정보를 추출할 때는 Optional을 사용할 때가 많다.
  다음 코드처럼 이름 정보에 접근하기 전에 insurance가 null인지 확인해야 한다.
  
  ```java
   String name = null;
   if(insurance != null) {
       name = insurance.getName();
   }
  ```
  
이런 유형의 패턴에 사용할 수 있도록 Optional은 map 메서드를 지원한다.

Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
Optional<String> name = optInsurance.map(Insurance::getName);

11.3.5 디폴트 액션과 Optional 언랩
- get()은 값을 읽는 가장 간단한 메서드면서 동시에 가장 안전하지 않은 메서드다.
  메서드 get은 래핑된 값이 있으면 해당 값을 반환하고 값이 없으면 NoSuchElementException을 발생시킨다.
  따라서 Optional에 값이 반드시 있다고 가정할 수 있는 상황이 아니면 get 메서드를 사용하지 않는 것이 바람직하다.
  결국 이 상황은 중첩된 null 확인 코드를 넣는 상황과 크게 다르지 않다.
  
- orElse 메서드를 이용하면 Optional이 값을 포함하지 않을 때 기본값을 제공할 수 있다.
  
- orElseGet는 orElse 메서드에 대응하는 게으른 버전의 메서드다.
  Optional에 값이 없을 때만 Supplier가 실행되기 때문이다.
  디폴트 메서드를 만드는 데 시간이 걸리거나(효율성 때문에) Optional이 비어있을 때만 기본값을 생성하고 싶다면(기본값이 반드시 필요한 상황) orElseGet을 사용해야 한다.
  
- orElseThrow는 Optional이 비어있을 때 예외를 발생시킨다는 점에서 get 메서드와 비슷하다.
  하지만 이 메서드는 발생시킬 예외의 종류를 선택할 수 있다.
 
- ifPresent를 이용하면 값이 존재할 때 인수로 넘겨준 동작을 실행할 수 있다. 값이 없으면 아무 일도 일어나지 않는다.
  
- ifPresentOrElse 메서드는 Optional이 비었을 때 실행할 수 있는 Runnable을 인수로 받는다는 점만 ifPresent와 다르다.
  
11.4.1 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기
  ```java
  Object value = map.get("key");
  ```
  - 문자열 'key'에 해당하는 값이 없으면 null이 반환될 것이다. map에서 반환하는 값을 Optional로 감싸서 이를 개선할 수 있다.
    코드가 복잡하기는 하지만 기존처럼 if-then-else를 추가하거나, 아니면 아래와 같이 깔끔하게 Optional.ofNullable을 이용하는 두 가지 방법이 있다.
  ```java
  Optional<Object> value = Optional.ofNullable(map.get("key"));
  ```
  이와 같은 코드를 이용해서 null일 수 있는 값을 Optional로 안전하게 변환할 수 있다.
  
  11.4.2 예외와 Optional 클래스
  - 자바 API는 어떤 이유에서 값을 제공할 수 없을 때 null을 반환하는 대신 예외를 발생시킬 때도 있다.
    전형적인 예가 문자열을 정수로 변환하는 정적 메서드 Integer.pasrseInt(String)다.
    이 메서드는 문자열을 정수로 바꾸지 못할 때 NumberFormatException을 발생시킨다.
    즉, 문자열이 숫자가 아니라는 사실을 예외로 알리는 것이다.
    정수로 변환할 수 없는 문자열 문제를 빈 Optional로 해결할 수 있다.
  
  ```java
  public static Optional<Integer> stringToInt(String s) {
      try {
          return Optional.of(Integer.parseInt(s)); // 문자열을 정수로 반환할 수 있으면 정수로 변환된 값을 포함하는 Optional을 반환한다.
      }catch (NumberFormatException e) {
          return Optional.empty(); // 그렇지 않으면 빈 Optional을 반환한다.
      }
  }
  ```
  
  위와 같은 메서드를 포함하는 유틸리티 클래스 OptionalUtility를 만들어야 함.
  그러면 필요할 때 OptionalUtility.stringToInt를 이용해서 문자열을 Optional<Integer>로 변환할 수 있다.
  기존처럼 거추장스러운 try/catch 로직을 사용할 필요가 없다.
  
  11.4.3 기본형 Optional을 사용하지 말아야 하는 이유
  - Optional클래스의 유용한 메서드 map, flatmap, filter 등을 지원하지 않으므로 기본형 특화 Optional을 사용할 것을 권장하지 않는다.
  
  11.5 마치며
  - 역사적으로 프로그래밍 언어에서는 null 참조로 값이 없는 상황을 표현해왔다.
  - 자바 8에서는 값이 있거나 없음을 표현할 수 있는 클래스 java.util.Optional<T>를 제공한다.
  - 팩토리 메서드 Optional.empty, Optional.of, Optional.ofNullable 등을 이용해서 Optional 객체를 만들 수 있다.
  - Optional 클래스는 스트림과 비슷한 연산을 수행하는 map, flatmap, filter 등의 메서드를 제공한다.
  - Optional로 값이 없는 상황을 적절하게 처리하도록 강제할 수 있다. 즉, Optional로 예상치 못한 null 예외를 방지할 수 있다.
