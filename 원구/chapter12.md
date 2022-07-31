# #chapter12

## 12. 새로운 날짜와 시간 API
> 날짜와 시간을 생성하는 기본적인 방법을 살펴보고, 날짜 시간 객체를 조작, 파싱, 출력, 
  다양한 시간대와 대안 캘린더 등 새로운 날짜와 시간 API를 사용하는 방법을 살펴본다.
  
### 12.1 LocalDate, LocalTime, Instant, Duration, Period 클래스
- java.time 패키지는 LocalDate, LocalTime, LocalDateTime, Instant, Duration, Period 등 새로운 클래스를 제공한다.

### 12.1.1 LocalDate와 LocalTime 사용
- LocalDate 인스턴스는 시간을 제외한 날짜를 표현하는 불변 객체다.
- 특히 LocalDate 객체는 어떤 시간대 정보도 포함하지 않는다.
```java
LocalDate date = LocalDate.of(2022, 7, 31);	// 2022-07-31
int year = date.getYear();		// 2022
Month month = date.getMonth();		// JULY
int day = date.getDayOfMonth();		// 31
DayOfWeek dow = date.getDayOfWeek();	// SUNDAY
int len = date.lengthOfMonth();		// 31 
boolean leap = date.isLeapYear();		// false
```

- 팩토리 메서드 now는 시스템 시계의 정보를 이용해서 현재 날짜 정보를 얻는다.
```java
LocalDate today = LocalDate.now();
```

- TemporalField는 시간 관련 객체에서 어떤 필드의 값에 접근할지 정의하는 인터페이스다.
```java
// TemporalField를 이용해서 LocalDate 값 얻기
int year = date.get(ChronoField.YEAR);
int month = date.get(ChronoField.MONTH_OF_YEAR);
int day = date.get(ChronoField.DAY_OF_MONTH);
```
- 다음처럼 내장 메서드 getYear(), getMonthValue(), getDayOfMonth() 등을 이용해 가독성을 높을 수 있다.

### 12.1.2 날짜와 시간 조합
- LocalDateTime은 LocalDate, LocalTime을 쌍으로 갖는 복합 클래스이다.
- 날짜와 시간을 모두 표현할 수 있으며 직접 LocalDateTime을 만드는 방법도 있고 날짜와 시간을 조합하는 방법도 있다.
```java
// 2022-07-31T20:10:30
LocalDateTime dt1 = LocalDateTime.of(2022, Month.JULY, 31, 20, 10, 30);
LocalDateTime dt2 = LocalDateTime.of(date, time);
LocalDateTime dt3 = date.atTime(20, 10, 30);
LocalDateTime dt4 = date.atTime(time);
LocalDateTime dt5 = time.atDate(date);
```

### 12.1.4 Duration과 Period 정의
- Duration 클래스의 정적 팩토리 메서드 between으로 두시간 객체 사이의 지속시간을 만들 수 있다.
```java
Duration d1 = Duration.between(time1, time2);
Duration d1 = Duration.between(dateTime1, dateTime2);
Duration d2 = Duration.between(instant1, instant2);
```
- Instan는 기계가 사용하도록 만들어진 클래스로 두 인스턴스는 혼합할 수 없다.
- Duration과 Period 만들기
```java
Duration threeMinutses = Duration.ofMinutes(3);
Duration threeMinutses = Duration.of(3, ChronoUnit.MINUTES);

Period tenDays = Period.ofDays(10);
Period threeWeeks = Period.ofWeeks(3);
Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
```

### 12.2 날짜 조정, 파싱, 포매팅
- withAttribute 메서드로 기존 LocalDate를 바꾼 버전을 직접 간단하게 만들 수 있다.
```java
LocalDate date1 = LocalDate.of(2022, 07, 31);	// 2022-07-31
LocalDate date2 = date1.withYear(2021);		// 2021-07-31
LocalDate date3 = date2.withDayOfMonth(25);		// 2021-07-25
LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2);		// 2021-02-25
```
- 상대적인 방식으로 LocalDate속성 바꾸기
```java
LocalDate date1 = LocalDate.of(2022, 07, 31);		// 2022-07-31
LocalDate date2 = date1.plusWeeks(1);		// 2022-08-07
LocalDate date3 = date2.minusYears(6);		// 2016-08-07
LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS);		// 2017-02-07
```

### 12.2.1 TemporalAdjusters 사용하기
- 미리 정의된 TemporalAdjusters 사용하기
```java
import static java.time.temporal.TemporalAdjusters.*;
LocalDate date1 = LocalDate.of(2022, 07, 24);		// 2022-07-23
LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));		//2022-07-30
LocalDate date3 = date2.with(lastDayOfMonth());		// 2022-07-31
```
- TemporalAdjusters를 이용하면 좀 더 복잡한 날짜 조정 기능을 직관적으로 해결할 수 있다.
- 실제로 TemporalAdjusters 인터페이스는 다음처럼 하나의 메서드만 정의한다. 
- 하나의 메서드만 정의하므로 함수형 인터페이스다.
```java
@FunctionalInterface
public interface TemporalAdjusters {
	Temporal adjustInto(Temporal temporal);
}
```

### 12.3 다양한 시간대와 캘린더 활용 방버
- 새로운 날짜와 시간 API의 큰 편리함 중 하나는 시간대를 간단하게 처리할 수 있다는 장점이다.
- 기존의 java.util.TimeZone을 대체할 수 있는 java.time.ZoneId 클래스가 새롭게 등장했다.
- 날짜와 시간 API에서 제공하는 다른 클래스와 마찬가지로 ZoneId는 불변 클래스다.

### 12.3.1 시간대 사용하기
- 표준 시간이 같은 지역을 묶어서 시간대 규칙 집합을 정의한다.
```java
ZoneId romeZone = ZoneId.of("Europe/Rome");
```
- 지역 ID는 '{지역}/{도시}' 형식으로 이루어지며 IANA Time Zone Database에서 제공하는 지역 집합 정보를 사용
```java
ZoneId zoneId = TimeZone.getDefault().toZoneId(0;
```
