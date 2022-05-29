# 동작 파라미터화 코드 전달하기
- 동작 파라미터화란 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미
- 자주 바뀌는 요구사항에 대응하기 쉽다는 장점
## 2.2 동작 파라미터화
- 사과의 어떤 속성에 기초해서 불리언값을 반환(사과가 녹색인가? 150그램 이상인가?)하는 방법이 있다.
- 참, 거짓을 반환하는 함수를 프레디케이드라고 한다.
- 선택조건을 결정하는 인터페이스를 정의한다.
  - ![image](https://user-images.githubusercontent.com/39439576/170852971-a7261cab-4d4f-4bac-818c-71e5a95d41ee.png)

- 다음 코드처럼 다양한 선택조건을 대표하는 여러 버전의 ApplePredicate를 정의할 수 있다.
  - ![image](https://user-images.githubusercontent.com/39439576/170853118-9d0235d7-f34d-4c51-b932-6a839d0955f8.png)

  - ![image](https://user-images.githubusercontent.com/39439576/170853130-1ae17336-f032-440e-8cc3-cc0f65fab1ec.png)

- 위 조건에 따라 filter메소드가 다르게 동작하는데, 이를 전략 디자인패턴이라고 한다.
- 전략 디자인 패턴은 각 알고리즘(전략이라 부르는)을 캡슐화하는 알고리즘 패밀리를 정의해둔 다음에 런타임에 알고리즘을 선택하는 기법이다.
- 예제에서는 ApplePredicate가 알고리즘 패밀리이고, AppleHeavyWeightPredicate와 AppleGreenColorPredicate가 전략이다.
- ApplePredicate가 다양한 동작을 수행하는 방법
  FilterApples()에서 ApplePredicate객체를 받아 Apple의 조건을 검사하도록 메소드를 정의한다.
  이렇게 동작을 파라미터화, 즉 메서드가 다양한 동작(전략)을 인수로 받아 내부적으로 다양한 동작을 수행할 수 있다.(코드의 중복을 줄이고 유연하게 코드를 작성할 수 있다.)
  - ![image](https://user-images.githubusercontent.com/39439576/170853189-40ccf244-058e-4d5d-915d-ce437d50516a.png)
  - 위 코드에서 ApplePredicate를 메소드의 인자로 넘겨 여러 동작(무게, 색깔로 필터링하는 전략)을 하나의 코드에 간결하게 작성하였다.
  - 고객이 150그램이 넘는 빨간 사과를 검색해달라고 요청하면 개발자는 ApplePredicate를 적절히 구현하는 클래스만 만들면 된다.(유연성을 가질 수 있다.)
  - ![image](https://user-images.githubusercontent.com/39439576/170853291-5704c1bd-d87d-40f0-989e-ec3f63894bee.png)
  - ![image](https://user-images.githubusercontent.com/39439576/170853343-d7bb229f-41c7-4f90-8d62-1b19412de611.png)
  - 메소드에 전달한 ApplePredicate객체에 의해 filterApples메서드의 동작을 결정하였다. 즉, filterApples메서드의 동작을 파라미터화 하였다.

## 2.3 복잡한 과정 간소화
- 2.2의 예제에서 파라미터로 넘기는 전략들을 모두 구현하여 인스턴스화하는 과정이 조금은 번거롭게 느껴진다.
- 2.3장에서는 이 부분을 어떻게 개선할지 확인한다.
  # 2.3.2 익명 클래스 사용
  - 익명클래스를 이용하면 클래스 선언과 인스턴스화를 동시에 할 수 있다.
  - 다음 예제코드는 익명클래스를 이용해서 ApplePredicate를 구현하는 객체를 만드는 방법으로 필터링 예제를 다시 구현한 코드이다.
  - ![image](https://user-images.githubusercontent.com/39439576/170853813-edd5acb0-3127-4b9f-be59-42dbdcbd2adb.png)
  # 2.3.3 람다 표현식 사용
  - 익명클래스를 사용하여도 여전히 코드길이가 길다는 단점이 존재
  - ![image](https://user-images.githubusercontent.com/39439576/170853877-68e4f5b4-977c-449e-8b06-78b977fae965.png)
  - 위와 같이 람다표현식을 이용하면 코드를 간결화 할 수 있다.

## 마치며
- 동작 파라미터화는 메서드 내부적으로 다양한 동작을 수행할 수 있도록 코드를 메서드 인수로 전달한다.
- 동작 파라미터화를 이용하면 변화하는 요구사항에 잘 대응할 수 있는 유연한 코드를 구현할 수 있다.
- 자바8에서는 코드 전달을 익명클래스 뿐만 아니라 람다를 이용하여 간단하게 구현할 수 있다.
