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

```java
LocalDateTime dt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21,13,45,25);
LocalDateTime dt2 = LocalDateTime.of(date,time);
LocalDateTime dt3 = date.atTime(13,45,25);
LocalDateTime dt4 = date.atTime(time);
LocalDateTime dt5 = time.atDate(date);
```

- LocalDate - atTime 메서드를 사용해 LocalDateTime을 생성할 수 있다.
- LocalTime - atDate 메서드롤 통해 LocalDateTime을 생성 가능

```java
LocalDate date1 = dt1.toLocalDate(); 
LocalTime time1 = dt1.toLocalTime();
```

- LocalDateTime ⇒ LocalDate or LocalTime

### Instant 클래스 : 기계의 날짜와 시간

- 사람은 주, 날짜, 시간, 분으로 계산
- 기계는 연속된 시간에서 특정 지점을 하나의 큰 수로 표현

  ⇒ Instant 클래스에서는 기계적인 관점에서 시간을 표현

  ⇒ 유닉스 에포크 시간 ( Unix epoch time ) 을 기준으로 특정지점까지의 시간을 초로 표현


```java
Instant.ofEpochSecond(3);
Instant.ofEpochSecond(3,0);
Instant.ofEpochSecond(2, 1_000_000_000); 
Instant.ofEpochSecond(4, -1_000_000_000); 

Instant.now();
```

- 기계전용 유틸리티

### Duration 과 Period 정의

- 시간의 간격을 표현하는 클래스

지금까지 살펴본 클래스들 Temporal 인터페이스를 구현하는 클래스들이다.

Temporal 인터페이스는 특정시간을 모델링하는 객체의 값을 어떻게 읽고 조작할 지 정의하는 인터페이스

- Duration 클래스의 정적 팩토리 메서드 between 으로 두시간 객체 사이의 지속시간을 만듦

```java
Duration d1 = Duration.between(time1 , time2);
Duration d2 = Duration.between(dateTime1 , dateTime2);
Duration d3 = Duration.between(instant1 , instant2);
```

- LocalDateTime 은 사람이 사용 , Instant 는 기계가 사용 ⇒ 두개를 혼합해서 Duration에 사용 불가

```java
Duration threeMinutes = Duration.ofMinutes(3);
Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);

Period tenDays = Period.ofDays(10);
Period threeWeeks = Period.ofWeeks(3);
Period twoYearsSixMonthsOneDay = Period.of(2,6,1);
```

### 날짜 조정 , 파싱 , 포매팅

- withAttribute 메서드로 기존의 LocalDate의 속성을 변경 ⇒ 새로운 객체로 반환 ( 기존 객체 변경 X)

```java
LocalDate date1 = LocalDate.of(2017,9,21); // 2017-09-21
LocalDate date2 = date1.withYear(2011); // 2011-09-21
LocalDate date3 = date2.withDayOfMonth(25) // 2011-09-25
LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2); // 2011-02-25
```

- with 메서드는 좀 더 범용적으로 메서드를 활용 할 수 있다. ( get 메서드와 쌍을 이룸 )
- with 와 get 메서드는 Temporal 객체의 필드값을 읽거나 고칠 수 있다.

```java
// 상대적인 방식으로 LocalDate 속성 변경 

LocalDate date1 = LocalDate.of(2017,9,21); // 2017-09-21
LocalDate date2 = date1.plusWeeks(1); // 2017-09-28
LocalDate date3 = date2.minusYears(6); // 2011-09-28
LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS); // 2012-03-28
```

- 여기서도 get , with 메서드와 비슷하게 plus 와 minus 메서드를 사용 ⇒ Temporal 인터페이스의 정의되어 있다.

```java
// 퀴즈 
다음 코드를 실행헀을 때 date의 변수값 

LocalDate date = LocalDate.of(2014,3,18); // 2014-03-18
date = date.with(ChronoField.MONTH_OF_YEAR, 9); // 2014-09-18
date = date.plusYears(2).minusDays(10); // 2016-09-08
date.withYear(2011); // 2011-09-28 -> 변수 할당되지 않아서 값에 적용X 

// 불편 클래스라고 하지 않았나?? 
```

### TemporalAdjusters 사용하기

- 복잡한 날짜 조정 기능
- with 메서드를 다양한 동작 기능을 제공하는 TemporalAdjuster를 사용

```java

// 미리 정의된 TemporalAdjusters 사용
LocalDate date1 = LocalDate.of(2014,3,18); // 2014-03-18
LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY)); // 2014-03-23
LocalDate date3 = date2.with(lastDayOfMonth()); // 2014-03-31 
```

- TemporalAdjuster는 인터페이스 TemporalAdjusters 는 TemporalAdjuster의 구현 클래스 ( 여러 TemporalAdjuster 를 반환하는 정적 팩토리 메서드를 갖고 있다.)