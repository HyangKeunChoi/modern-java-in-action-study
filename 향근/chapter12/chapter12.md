# 새로운 날짜와 시간 API

+ 기존의 자바의 날짜 시간 API는 완벽하지 않았다.

### 기존 날짜 시간 API의 문제점
1. 날짜를 의미하는 Date라는 클래스의 이름과 달리 Date 클래스는 특정 시점을 날짜가 아닌 밀리초 단위로 표현한다.
2. 1900년을 기준으로 하는 오프셋, 0에서 시작하는 달 인덱스 등 모호한 설계로 유용성을 떨어뜨렸다.
3. Date 클래스의 toString으로는 반환되는 문자열을 추가로 활용하기가 어렵ㄴ다.
4. 가변 클래스이다.

### Calendar의 문제점
1. Calendar에서는 1900년도에서 시작하는 오프셋은 없앴지만 여전히 달의 인덱스는 0부터 시작했다.
2. Date와 Calendar 두 가지 클래스가 등장하면서 혼란이 가중
3. 가변 클래스이다.

### DateFormat의 문제점
1. 일부 기능은 Date클래스에서만 작동했다.
2. 스레드에 안전하지 않다.

### 12.1 LocalDate, LocalTime, Instant, Duration, Period 클래스

### 12.1.1 LocalDate와 LocalTime 사용
+ LocalDate 인스턴스는 시간을 제외한 날짜를 표현하는 불변 객체다.
+ 특히 LocalDate 객체는 어떤 시간 정보도 포함하지 않는다.
+ 마찬가지로 12:34:56 같은 시간은 LocalTime 클래스로 표현할 수 있다.
```java
LocalDate date = LocalDate.of(2017, 9, 21);
int year = date.getYear();
Month month = date.getMonth();

LocalTime time = LocalTime.of(12, 34, 56);
int hour = time.getHour();
int minute = time.getMinute();
int second = time.getSecond();
```

### 12.1.2 날짜와 시간 조합
+ LocalDateTime은 LocalDate와 LocalTime 쌍으로 갖는 복합 클래스다.
+ LocalDateTime은 날짜와 시간을 모두 표현할 수 있다.

```java
LocalDateTime dt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21, 13, 45, 20);
```

### 12.1.3 Instant 클래스 : 기계의 날짜와 시간
+ 사람은 보통 주,날짜 시간, 분으로 날짜와 시간을 계산한다.
+ 하지만 기계에서는 이와 같은 단위로 시간을 표현하기가 어렵다.
+ 기계의 관점에서는 연속된 시간에서 특정 지점을 하나의 큰 수로 표현하는 것이 가장 자연스러운 시간 표현 방법이다.
+ 새로운 java.time.Instant 클래스에서는 이와 같은 기계적인 관점에서 시간을 표현한다.

### 12.1.4 Duration과 Period 정의
+ Duration 클래스의 정적 팩토리 메서드 between으로 두 시간 객체 사이의 지속시간을 만들 수 있다.
+ 다음 코드에서 보여주는 것처럼 두 개의 LocalTime, 두개의 LocalDateTime, 또는 두 개의 Instant로 Duration을 만들 수 있다.

```java
Duration d1 = Duration.between(time1, time2); 
Duration d1 = Duration.between(dateTime1, dateTime2);
Duration d2 = Duration.between(instant1, instant2);
```

+ Period 클래스의 팩토리 메서드 between을 이용하면 두 LocalDate의 차이를 확인 할 수 있다.

```java
Period tenDays = Period.between(LocalDate.of(2017,9,11), LocalDate.of(2017, 9, 21));
```

+ Duration과 Period 클래스는 자신의 인스턴스를 만들 수 있도록 다양한 팩토리 메서드를 제공한다. 즉, 다음 예제에서 보여주는 것처럼 두 시간 객체를 사용하지 않고도 Duration과 Period클래스를 만들 수 있다.

> 지금까지 살펴본 모든 클래스는 불변이다.
> 
> 불변 클래스는 함수형 프로그래밍 그리고 스레드 안전성과 도메인 모델의 일관성을 유지하는 데 좋은 특징이다. 하지만 새로운 날짜와 시간 API에서는 변경된 객체 버전을 만들 수 있는 메서드를 제공한다.

### 12.2 날짜 조정, 파싱, 포매팅
+ withAttribute 메서드로 기존의 LocalDate를 바꾼 버전을 직접 간단하게 만들 수 있다.
```java
LocalDate date1 = LocalDate.of(2021,9,21);
LocalDate date2 = date1.withYear(2011);
LocalDate date3 = date2.withDayOfMonth(25);
```

#### 상대적인 방식으로 LocalDate 속성 바꾸기
```java
LocalDate date1 = LocalDate.of(2017, 9, 21);
LocalDate date2 = date1.plusWeeks(1); // 2017-09-28
LocalDate date3 = date2.minusYear(6); // 2011-09-28
LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS);
```

### 12.2.2 날짜와 시간 객체 출력과 파싱
+ DateTimeFormatter
```java
LocalDate date = LocalDate.of(2014, 3, 18);
String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
```
### 12.3 다양한 시간대와 캘린더 활용 방법

### 12.4
+ 자바 8 이전 버전에서 제공하는 기존의 java.util.Date 클래스와 관련 클래스에서는 여러 불일치점들과 가변성, 어설픈 오프셋, 기본값, 잘못된 이름 결정등의 설계 결함이 존재 했다.
+ 새로운 날짜와 시간 API에서 날짜와 시간 객체는 모두 불편이다.
+ 새로운 API는 각각 사람과 기계가 편리하게 날짜와 시간 정보를 관리할 수 있도록 두 가지 표현방식을 제공한다.
+ 날짜와 시간 객체를 절대적인 방법과 상대적인 방법으로 처리할 수 있으며 기존 인스턴스를 변환하지 않도록 처리 결과로 새로운 인스턴스가 생성된다.
+ TemporalAdjuster를 이용하면 단순히 값을 바꾸는 것 이상의 복잡한 동작을 수행할 수 있으며 자신만의 커스텀 날짜를 변환 할 수 있다.
+ 날짜와 시간 객체를 특정 포맷으로 출력하고 파싱하는 포매터를 정의할 수 있다. 패턴을 이용하건 프로그램으로 포매터를 만들 수 있으며 포매터는 스레드 안정성을 보장한다.
+ 이하 생략...
