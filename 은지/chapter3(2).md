3.5 형식 검사, 형식 추론, 제약
  * 형식 검사
    * context를 이용해서 형식을 추론
    * 대상 형식:  어떤 컨텍스트에서 기대되는 람다 표현식의 형식
    * 형식 확인 과정을 통해 형식 검사 수행
    *  대상형식이라는 특징 때문에 같은 람다 표현식이더라도 호환되는 추상 메서드를 가진 다르 함수형 인터페이스로 활용이 가능

  *  형식 추론
    * 자바 컴파일러는 람다 표현식이 사용된 콘텍스트를 이용해서 람다 표현식과 관련된 함수형 인터페이스를 추론
    * 대상 형식을 이용해서 함수 디스크립터를 알 수 있으므로 컴파일러는 람다의 시그니처 추론이 가능
    List<Apple> greenApples = filter(inventory, apple -> GREEN.equals(apple.getColor()));
   * 형식을 추론하면(명시성을 배제하면) 더 가독성 좋은 코드를 만들 수 있음

  * 지역변수 사용
    * 람다 표현식에서는 익명함수가 하는 것처럼 자유 변수를 사용할 수 있음
    * 자유변수: 파라미터로 넘겨지 변수가 아닌 외부에서 정의된 변수 , 이를 활용하는 방법이 람다 캡쳐링
    * 지역변수는 final의 형태로 명시되거나 final과 같은 역할을 해야함
    * 왜? 지역변수와 인스턴스 변수는 저장되느 위치가 다른데 람다에서는 지역변수의 복사본을 사용함에 따라 복사본의 값이 바뀌지 않아야함

3.6 메서드 참조
  * 기존의 메서드 정의를 재활용해 람다처럼 전달이 가능
    inventoru.sort(comparing(Apple::getWeight));
    * 왜 중요한가? 특정 메서드만을 호출하는 람다의 축약형, 명시적을 메서드명을 참조함으로서 코드 가독성을 높임 (:: 구분자를 붙이는 방식 등으로 참조)
    Apple::getWeight는 (Appple a) -> a.getWeight()를 축약
    * 정적 메서드 참조
    * 다양한 형식의 인스턴스 메서드 참조
    * 기존 객체의 인스턴스 메서드 참조: 람다표현식엣 현존하는 외부 객체의 메서드를 호출할 때 사용
    * 메서드 참조는 콘텍스트의 형식과 일치해야 함
  * 생성자 참조
    * Classname::new와 같이 생성자 참조 만들 수 있음
 
 
 3.7 람다, 메서드 참조 활용하기 
  * 1단계: 코드전달
  * 2단계: 익명 클래스 사용
  * 3단계: 람다 표현식 사용
    * 함수형 인터페이스를 사용할 수 있는 구간 어디에서나 람다 표현식을 사용 - 여기서 형식 추론을 통해 코드를 더 간결화할 수 있음
  * 4단계: 메서드 참조 사용
  
 3.8 람다표현식을 조합할 수 있는 유용한 메서드
  * 함수형 인터페이스는 람다표현식을 조합할 수 있어 여러 람다식을 복잡한 람다식 구현이 가능
  * 디폴트메서드
  
 3.10 요약
  * 람다표현식으 익명 함수의 일종, 이름은 없지만 파라미터 리스트, 바디, 반환식을 가지며 exception 던질 수 있음
  * 람다식을 이용해 간결한 코드 구현이 가능
  * 함수형 인터페이스는 하나의 추상 메서드만 정의
  * 람다식 전체를 함수형 인터페이스의 인스턴스로 취급
  * 실행 어라운드 패턴을 람다와 활용하면 유연성과 재사용성을 추가할 수 있음
  * 메서드 참조를 이용하며 기존의 메서드 구현을 재사용하고 직접 전달 가능
 
