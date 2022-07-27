12.1.1 LocalDate와 LocalTime 사용
```java
LocalDate date = LocalDate.of(2017, 9, 21)
int year = date.getYear();            // 2017
Month month = date.getMonth();        // SEPTEMBER
int day = date.getDayOfMonth();       // 21
DayOfWeek now = date.getDayOfWeek();  // THURSDAY
int len = date.lengthOfMonth();       // 31(3월의 일 수)
boolean leap = date.isLeapYear();     // false(윤년이 아님)

LocalDate today = LocalDate.now();
int year = date.get(ChronoField.YEAR);
int month = date.get(ChronoField.MONTH_OF_YEAR);
int day = date.get(ChronoField.DAY_OF_MONTH);

// 가독성 높이기
int year = date.getYear();
int month = date.getMonthValue();
int day = date.getDayOfMonth();

LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
int hour = time.getHour();
int minute = time.getMinute();
int second = time.getSecond();
```

- 날짜와 시간 문자열로 LocalDate와 LocalTime의 인스턴스를 만드는 방법도 있다. 다음처럼 parse 정적 메서ㅏ드를 사용할 수 있다.

```java
 LocalDate date = LocalDate.parse("2017-09-21");
 LocalTime time = LocalTime.parse("13:45:20");
```

12.1.2 날짜와 시간 조합
```java
 LocalDateTime dt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21, 13, 45, 20);
 LocalDateTime dt2 = LocalDateTime.of(date, time);
 LocalDateTime dt3 = date.atTime(13, 45, 20);
 LocalDateTime dt4 = date.atTime(time);
 LocalDateTime dt5 = time.atDate(date);
```


12.1.4 DUration과 Period 정의
- LocalDateTime은 사람이 사용하도록, Instant는 기계가 사용하도록 만들어진 클래스로 두 인스턴스는 서로 혼합할 수 없다.
- 또한 Duration 클래스는 초와 나노초로 시간 단위를 표현하므로 between 메서드에 LocalDate를 전달할 수 없다.
- 년, 월, 일로 시간을 표현할 때는 Period 클래스를 사용한다.


```java
Duration threeMinutes = Duration.ofMinutes(3);
Duration threeMinutes = Duration.of(3, ChronoUnit,MINUTES);

Period tenDays = Period.ofDays(10);
Period threeWeeks = Period.ofWeeks(3);
Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
```

12.2 날짜조정, 파싱, 포매팅
- 절대적인 방식으로 LocalDate의 속성 바꾸기
```java
LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
LocalDate date2 = date1.withYeasr(2011);     // 2011-09-21
LocalDate date3 = date2.withDayOfMonth(25);  // 2011-09-25
LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2); // 2011-02-25
```

- 상대적인 방식으로 LocalDate 속성 바꾸기
```java
LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
LocalDate date2 = date1.plusWeeks(1);        // 2017-09-28
LocalDate date3 = date2.minusYear(6);        // 2011-09-28
LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS); // 2012-03-28
```

12.2.2 날짜와 시간 객체 출력과 피싱
- 포매팅과 파싱 전용 패키지인 java.time.format이 새로 추가되었다.
- DateTimeFormatter 클래스는 BASIC_ISO_DATE와 ISO_LOCAL_DATE 등의 상수를 미리 정의하고 있다.

```java
LocalDate date = LocalDate.of(2014, 3, 18);
String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20140318
String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2014-03-18
```

12.4 마치며
- 자바 8 이전 버전에서 제공하는 기존의 java.util.Date 클래스와 관련 클래스에서는 여러 불일치점들과 가변성, 어설픈 오프셋, 기본값, 잘못된 이름 결정 등의 설계 결함이 존재했다.
- 새로운 날짜와 시간API에서 날짜와 시간 객체는 모두 불변이다.
- 새로운 API는 각각 사람과 기계가 편리하게 날짜와 시간 정보를 관리할 수 있도록 두 가지 표현 방식을 제공한다.
