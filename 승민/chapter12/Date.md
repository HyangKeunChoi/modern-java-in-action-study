### 12
# 새로운 날짜와 시간 API
- 기존의 DATE 생성 코드
    - Date date = new Date(117,8,21);
- 1900년을 기준으로 하는 오프셋, 0에서 시작하는 달 인덱스 등 모호한 설계로 유용성이 떨어진다.
- 직관적이지 않다.
- CET (중앙 유럽 시간 기준)
- 자바 1.1에서는 Date 클래스의 여러 메서드를 deprecated 시키고 캘린더를 제공했으나 이마저도 설계문제가 있었다.
- DateFormat의 경우 쓰레드 unsafe 하므로 위험한 소스로 분류되어있다.

- 자바8에서는 Joda-Time 의 많은 기능을 java.time 패키지로 추가했다.

# LocalDate와 LocalTime 사용
- LocalDate 인스턴스는 시간을 제외한 날짜를 표현하는 불변 객체다.
- LocalDate 객체는 어떤 시간대 정보도 포함하지 않는다.
- 연도 , 달 , 요일 등을 반환함

- 정적 팩토리 메서드 of로 LocalDate 인스턴스를 만들 수 있다.
```java
LocalDate date = LocalDate.of(2022, 1, 02);
int year = date.getYear(); //2022
Month month = date.getMonth(); //JUNE
int day = date.getDayOfMonth(); //28
DayOfWeek dow = date.getDayOfWeek(); //31 (3월 일 수);
boolean leap = date.isLeapYear(); // false (윤년이 아님)
```
- 팩토리 메서드 now는 시스템 시계 정보를 이용하여 현재시간을 얻는다.
- LocalDate today = LocalDate.now();
- TemporalField를 이용하여 정보를 얻을 수도 있다.
- int year = date.get(ChronField.YEAR);
- int year = date.getYear();

- DateTimeFormatter는 DateFormat를 대체하는 클래스이다.
- 문자열을 LocalDate나 LocalTime으로 파싱할 수 없을때 parse 메서드는 DateTimeParse예외를 일으킨다.

# LocalDateTime
- LocalDateTime은 날짜와 시간을 모두 표현할 수 있다.

```java
LocalDateTime dt1 = LocalDateTime.of(2022,Month.~, 21,13,45,20);
LocalDateTime dt1 = LocalDateTime.of(date,time);
LocalDateTime dt1 = date.atTime(13,45,20);
LocalDateTime dt1 = date.atTime(time);
LocalDateTime dt1 = time.atDate(date);
```

- Instant클래스도 사람이 확인 할 수 있도록 시간을 표시해주는 정적 팩토리 메서드 now를 제공한다.
- 기계 전용으로 사람이 읽을 수 있는 시간을 제공해주지 않는다.

# 날짜 조정, 파싱, 포매팅
- withAttribute 메서드로 기존의 LocalDate를 바꾼 버전을 만들 수 있다.
```java
LocalDate date1 = LocalDate.of(2022,1,01);  // 2022-01-01
LocalDate date2 = LocalDate.withYear(2011); // 2011-01-01
LocalDate date3 = LocalDate.withDayOfMonth(21); // 2011-01-21
LocalDate date4 = LocalDate.with(ChronoField.MONTH_OF_YEAR, 2); // 2011-02-21
```

- get과 with메서드로 temporal객체의 필드값을 읽거나 고칠 수 있다.
- 어떤 temporal 객체가 지정된 필드를 지원하지 않으면 UnsupportedTemporal 예외가 발생한다.
## 상대적인 방식으로 LocalDate속성 바꾸기
```java
LocalDate date1 = LocalDate.of(2022,1,01);  // 2022-02-01
LocalDate date2 = date1.plusWeeks(1) // 2022-02-08
LocalDate date3 = date2.minusYears(10); // 2012-02-08
LocalDate date4 = date3.plus(1, ChronoUnit.MONTHS); // 2012-01-08
```

# TemporalAdjusters 사용
- 다음주 일요일, 돌아오는 평일, 어떤 달의 마지막 날 등 좀 더 복잡한 날짜 조정 기능이 필요할 때가 있다.
- 오버로드된 with 메서드에 좀 더 다양한 동작을 수행 할 수 있다.
- 팩토리 메서드 표 :  399P

- 필요 기능이 없을 경우 TemporalAdjuster 구현을 만들 수 있다.
- 함수형 인터페이스로 하나의 메서드만 정의 가능
- https://docs.oracle.com/javase/8/docs/api/java/time/temporal/TemporalAdjusters.html

- 이동된 날짜가 평일이 아니라면, 즉 토요일이나 일요일이면 월요일로 이동한다.

```java
NextWorkingDay implements TemporalAdjuster{
    @Override
    public Temporal adjustInto(Temporal temp){
        DayOfWeek dow = DayofWeek.of(temp.get(ChronoField.DAY_OF_WEEK)); //현재 날짜 읽기
        int dayToAdd = 1; //보통 하루 추가
        int (dow == DayOfWeek.FRIDAY) dayToAdd = 3; //그러나 오늘이 금요일이면 3일 추가
        else if(dow == DayOfWeek.SATURDAY) dayToAdd = 2 // 토요일이면 2일추가
        return temp.plus(dayToAdd,ChronoUnit.DAYS) //적정한 날 수 만큼 추가된 날짜 반환)

    }

}

//현재 요일이 금요일이나 토요일이라면 2,3일을 이동시킨다.


```

## 문자열로 표현된 날짜를 날짜 객체로 다시 변경할때
- java.time.format이 새로 추가되었다.
- 가장 중요한 클래스는 DateTimeFormatter이다.
- 정적 팩토리 메서드와 상수를 이용해서 손쉽게 포매터를 만들 수 있다.
- 두개의 서로 다른 포매터로 문자열을 만드는 예제
```java
LocalDate date = LocalDate.of(2022, 03, 18);
String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); //20220318
String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); //2022-03-18
//가장 많이 쓰이는 포매터로 변경 할 수 있다.

//반대의 상황
LocalDate date = LocalDate.parse("20220318", DateTimeFormatter.BASIC_ISO_DATE);
LocalDate date2 = LocalDate.parse("20220318", DateTimeFormatter.ISO_LOCAL_DATE);
```
- 기존의 DateFormat 클래스와 달리 모든 쓰레드에서 안전하게 사용 할 수 있다.

## 패턴으로 생성
```java
DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
//지역화의 경우 Local설정
LocalDate date1 = LocalDate.of(2014, 3, 18);
String formatDate = date1.format(format);
LocalDate date2 = LocalDate.parse(formatDate, format);
```

# 다양한 시간대 java.time.ZoneId
- 지금까지는 시간대 정보가 없었다.
- java.util.TimeZone을 대체하는 java.time.ZoneId 클래스가 등장했다.
- 서머타임등(DST)의 복잡한 처리가 자동으로 처리된다.
- 표준 시간이 같은 지역을 묶어서 시간대 규칙 집합을 정의한다.
- getRules()를 이용해서 해당 시간대의 규정을 획득한다.
- ZoneId romeZone = ZoneId.of("Europe/Rome");
- 지역 ID는 "지역/도시"형식이다.
- toZoneId로 기존의 TimeZone객체를 ZoneId로 변환할 수 있다.

```java
[LocalDate / LocalTime / ZoneId]
[    LocalDateTime    ]
[    ZonedDateTime             ]
```
- 각 범위를 표현
- 모든것을 포함하는 것이 ZonedDateTime이다.



