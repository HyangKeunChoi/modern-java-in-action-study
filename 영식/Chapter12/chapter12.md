# Chapter 12 새로운 날짜와 시간 API

## 서론

`자바 API` ⇒ 유용한 컴포넌트를 제공 but 항상 완벽하진 않다.

대부분 개발자들이 날짜와 시간 관련 기능에 만족하지 못했다.

따라서 이러한 부분을 개선한 새로운 날짜와 시간 API가 자바8에서 등장

- 자바 1.0 의 Date 클래스
    - Date 클래스 하나로 날짜와 시간 기능 제공
    - Date 클래스는 특정 시점을 날짜가 아닌 밀리초 단위로 표현
    - 1900년을 기준으로 하는 오프셋 과 0에서 시작하는 달 인덱스 등의 모호한 설계로 유용성이 떨어진다.
- 자바 1.1 의 Calendar 클래스
    - Date 클래스의 여러 메서드들을 사장시키고 대안으로 제공
    - 1900년 기준 오프셋은 없지만 0 시작하는 달 인덱스는 그대로
    - 오히려 Date 와 Calendar 두 가지 클래스는 개발자들에게 혼란 가중
        - DateFormat 같은 기능은 Date 클래스에서만 동작

- Date 와 Calendar 모두 가변 클래스라는 설계 떄문에 유지보수 어렵다.

  ( 18장 함수형 프로그래밍 )


⇒ 이런 상황 속에서 개발자들은 Joda-Time 같은 서드파티를 이용

### 12.1 LocalDate , LocalTime , Instant , Duration , Period 클래스

java.time 패키지에 있는 클래스들이다.

### LocalDate 와 LocalTime 사용

- LocalDate : 시간을 제외한 날짜를 표현한 불변 객체 ( 어떤 시간대 정보도 포함하지 않는다 )

```java
LocalDate date = LocalDate.of(2017,9,21); // 2017-09-21
int year = date.getYear(); // 2017 
Month month = date.getMonth(); // SEPTEMBER
int date = date.getDayOfMonth(); // 21
DayOfWeek dow = date.getDayOfWeek(); // THURSDAY
int len = date.lenghOfMonth(); // 31 ( 9월의 일수 )
boolean leap = date.isLeapYear(); // false ( 윤년이 아님 ) 
```

- 현재 날짜 정보

LocalDate today = LocalDate.now();

- get 메서드에 TemporalField 를 전달해서 정보를 얻는 방법도 존재

  ❓ TemporalField  : 시간 관련 객체에서 어떤 필드의 값에 접근할지 정의하는 인터페이스

- TemporalField 를 정의한 열거자 ChronoField 를 이용해서 원하는 정보를 얻을 수 있다.

```java
int year = date.get(ChronoField.YEAR);
int month = date.get(ChronoField.MONTH_OF_YEAR);
int day = date.get(ChronoField.DAY_OF_MONTH);
```

- LocalTime : 13:45:20 같은 시간을 표현
    - 두 가지 버전의 정적 메서드 of 로 LocalTime 인스턴스를 만들 수 있다.
        - 시간 , 분 인수로 받는 메서드
        - 시간 , 분 , 초를 인수로 받는 메서드

    ```java
    LocalTime time = LocalTime.of(13,45,20); // 13:45:20
    int hour = time.getHour(); // 13
    int minute = time.getMinute() // 45
    int second = time.getSecond() // 20 
    ```

- 날짜와 시간 문자열로 LocalDate , LocalTime 인스턴스 생성

    ```java
    LocalDate date = LocalDate.parse("2017-09-21");
    LocalTime time = LocalTime.parse("13:45:25");
    ```

    - parse 메서드에 DateTimeFormatter도 전달 가능

      ❓ DateTimeFormatter의 인스턴스는 날짜 , 시간 , 객체의 형식을 지정

      ⚠️ 문자열을 LocalDate 나 LocalTime 으로 파싱할 수 없을 때 `DateTimeParseException` 발생


### 날짜와 시간 조합

LocalDateTime = LocalDate + LocalTime 을 쌍으로 갖는 복합 클래스

- 날짜와 시간 모두 표현