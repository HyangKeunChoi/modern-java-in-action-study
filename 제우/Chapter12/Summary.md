### Chapter12. 새로운 날짜와 시간 API
* 이 장의 내용
- 자바8에서 새로운 날짜와 시간 라이브러리를 제공하는 이유
- 사람이나 기계가 이해할 수 있는 날짜와 시간 표현 방법
- 시간의 양 정의하기
- 날짜 조작, 포매팅, 파싱
- 시간대와 캘린더 다루기

## 12.1 LocalDate, LocalTime, Instant, Duration, Period 클래스
### 12.1.1 LocalDate와 LocalTime 사용
* LocalDate 인스턴스는 시간을 제외한 날짜를 표현하는 불변 객체다.
```java
LocalDate date = LocalDate.of(2017, 9, 21) // of메서드로 LocalDate인스턴스를 만들 수 있다. 2017-09-21
int year = date.getYear() // 2017
Month month = date.getMonth(); // SEPTEMBER
int day = date.getDayOfMonth(); // 21
DayOfWeek dow = date.getDayOfWeek();  // THURSDAY
int len = date.lengthOfMonth(); // 31(3월의 수)
boolean leap = date.inLeapYear(); // false(윤년이 아님)

LocalDate today = LocalDate.now();  // now메서드로 현재 시스템의 날짜 정보를 얻을 수 있다.
```

* LocalTime 인스턴스는 시간정보를 표현할 수 있다.
```java
LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
int hour = time.getHour();  // 13
int minute = time.getMinute();  // 45
int second = time.getSecond();  // 20
```

* parse메서드를 이용해서 날짜와 시간 문자열로 LocalDate와 LocalTime의 인스턴스를 만들 수 있다.
```java
LocalDate date = LocalDate.parse("2017-09-21");
LocalTime time = LocalTime.parse("13:45:20");
```

### 12.1.2 날짜와 시간 조합
* LocalDateTime은 LocalDate와 LocalTime을 쌍으로 갖는 복합 클래스다.
* 즉, LocalDateTime은 날짜와 시간을 모두 표현할 수잇다.
```java
LocalDateTime dt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21, 13, 45, 20);
LocalDateTime dt2 = LocalDateTime.of(date, time);
LocalDateTime dt3 = date.atTime(13, 45, 20);
LocalDateTime dt4 = date.atTime(time);
LocalDateTime dt5 = time.atDate(date);
```

### 12.1.3 Instant 클래스 : 기계의 날짜와 시간
* Instant는 초와 나노초 정보를 포함하며, 사람이 읽을 수 있는 시간 정보를 제공하지 않는다.

### 12.1.4 Duration과 Period 정의
* Duration 클래스의 정적 팩토리 메서드 between으로 두 시간 객체 사이의 지속시간을 만들 수 있다.
```java
Duration d1 = Duration.between(time1, time2);
Duration d2 = Duration.between(dateTime1, dateTime2);
Duration d3 = Duration.between(instant1, instant2);
```
* Duration 클래스는 초와 나노초로 시간 단위를 표현하므로 between메서드에 LocalDate(날짜)를 전달할 수없다.
* 년, 월, 일로 날짜를 표현할 때는 Period 클래스를 사용한다.
```java
Period toDays = Period.between(LocalDate.of(2017, 9, 11), LocalDate.of(2017, 9, 21));
```

## 12.2 날짜 조정, 파싱, 포매팅
* withAttribute 메서드로 기존의 LocalDate를 바꾼 버전을 간단하게 만들 수 있다.
* 다음 코드에서는 바뀐 속성을 포함하는 새로운 객체를 반환하는 메서드를 보여준다. 모든 메서드는 기존 객체를 바꾸지 않는다.
```java
LocalDate date1 = LocalDate.of(2017, 9, 21);  // 2017-09-21
LocalDate date2 = date1.withYear(2011);  // 2011-09-21
LocalDate date3 = date2.withDayOfMonth(25); // 2011-09-25
LocalDate date4 = date3.with(ChronField.MONTH_OF_YEAR, 2);  //2011-02-25
```
* withAttribute 메서드 이외에도 많은 날짜 조정 메서드가 존재하니 필요할 때 찾아쓸 것.
### 12.2.1 TemporalAdjusters 사용하기
* 때로는 다음주 일요일, 돌아오는 평일, 어떤 날의 마지막 날 등 조금 더 복잡한 날짜 조정 기능이 필요하다.
* 날짜와 시간 API는 다양한 상황에서 사용할 수 잇도록 다양한 TemporalAdjuster를 제공한다.
```java
import static java.time.temporal.TemporalAdjusters.*;
LocalDate date1 = LocalDate.of(2014, 3, 18);  // 2014-03-18
LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY)); // 2014-03-23
LocalDate date3 = date2.with(lastDayOfMonth()); // 2014-03-31
// 위 코드에서 확인할 수 있는 것처럼 TemporalAdjuster를 이용하면 좀 더 복잡한 날짜 조정 기능을 직관적으로 해결할 수 있다.
```
### 12.2.2 날짜와 시간 객체 출력과 파싱
* 날짜와 시간 관련 작업에서 포매팅과 파싱은 서로 떨어질 수 없는 관계다.
* DateTimeFormatter를 이용해서 날짜나 시간을 특정 형식의 문자열로 만들 수 있다.
```java
LocalDate date = LocalDate.of(2014, 3, 18);
String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);  // 20140318
String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);  // 2014-03-18
```
* 반대로 날자나 시간을 표현하는 문자열을 파싱해서 날짜 객체를 다시 만들 수 있다.
* parse메서드를 이용해서 문자열을 날짜 객체로 만들 수 있다.
```java
LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
```
* DateTimeFormatter클래스는 특정 패턴으로 포매터를 만들 수 있는 정적 팩토리 메서드 ofPattern을 제공한다.
```java
 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 LocalDate date1 = LocalDate.of(2014, 3, 18);
 String formattedDate = date1.format(formatter);
 LocalDate date2 = LocalDate.parse(formattedDate, formatter);
```

## 12.4 마치며
* 자바 8이전 버전에서 제공하는 기존의 java.util.Date 클래스와 관련 클래스에서는 여러 불일치점들과 가변성, 어설픈 오프셋, 기본값, 잘못된 이름 결정 등의 설계 결함이 존재한다.
* 새로운 날짜와 시간 API에서 날짜와 시간 객체는 모두 불변이다.
* 새로운 API는 각각 사람과 기계가 편리하게 날짜와 시간 정보를 관리할 수 있도록 두 가지 표현 방식을 제공한다.
* 날짜와 시간 객체를 절대적인 방법과 상대적인 방법으로 처리할 수 있으며 기존 인스턴스를 변환하지 않도록 처리 결과로 새로운 인스턴스가 생성된다.
* TemporalAdjuster를 이용하면 단순히 값을 바꾸는 것 이상의 복잡한 동작을 수행할 수 있으며 자신만의 커스텀 날짜 변환 기능을 정의할 수 있다.
* 날짜와 시간 객체를 특정 포맷으로 출력하고 파싱하는 포매터를 정의할 수 있다. 패턴을 이용하거나 프로그램으로 포매터를 만들 수 있으며 포매터는 스레드 안정성을 보장한다.
