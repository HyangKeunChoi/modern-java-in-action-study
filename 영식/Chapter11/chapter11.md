# Optional

### NPE 피하기

- if ( ≠ null 사용 )  ⇒ null 확인 코드가 추가된다. ⇒ 코드의 들여쓰기 수준이 증가 , 가독성이 줄어들고 코드 구조가 엉망이 됨
- if 절 여러개 사용해서 return 출구를 여러개 만들기 ⇒ 수많은 출구 패턴이 생기므로써 복잡성이 증가된다.

### Null 로 발생하는 문제

1. NullPointerException 발생
2. 코드 가독성 저하
3. Null 자체의 의미는 없음

- **자바8에서 다른 언어의 선택형 값 개념의 영향을 받아서 Optional<T>라는 클래스가 제공되기 시작**

### Optional

⇒ 선택형 값을 캡슐화하는 클래스

값이 있으면 값을 감싸고 , 값이 없으면 Option.empty() 메서드로 빈 Optional 반환

- null 과 Optional.empty() 차이
    1. null ⇒ NPE
    2. Optional.empty() 는 Optional 객체를 반환해서 NPE 발생하지 않는다.

- 자바 빈 클래스에 Optional<T> 로 property를 생성하면 해당 객체가 필수값이 아닌 있을수도 있고 없을 수도 있는 선택값의 의미를 갖는다.

- 객체 property가 Optional로 감싸져 있지 않으면 그 값은 필수값이므로 null일 경우 NPE로 알려줘야한다.

### Optional 적용 패턴

- Optional이 감싼 값을 어떻게 사용하는가

- Optional 객체 만들기
    1. 빈 Optional 객체

       ⇒ Optional<Car> optCar = Optional.empty();

    2. null 아닌 값으로 만들기

       ⇒ Optional<Car> optCar = Optional.of(car);

    3. null 값으로 만들기

       ⇒ Optional<Car> optCar = Optional.ofNullable(car);


### Optional 맵으로 추출

⇒ 평준화 방법으로 flatMap을 사용함 → 다시 읽어서 이해해야하는 부분

### Optional의 디폴트 액션

1. get

   ⇒ 값 반환 ( 없으면 NoSuchElementException 발생 ) ⇒ Optional 안에 값이 100% 있다고 가정했을 때 사용

2. orElse

   ⇒ 값이 없을 때 기본 값 제공

3. orElseGet

   ⇒ orElse 메서드에 대응하는 게으른 버전 메서드 Optional이 비어 있을 때만 기본값 생성하고 싶으면 사용

4. orElseThrow

   ⇒ 값이 비어있을 때 발생시킬 Exception 설정

5. ifPresent
6. ifPresentOrElse