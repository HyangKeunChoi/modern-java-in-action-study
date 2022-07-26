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
```java
LocalDate date = LocalDate.of(2017, 9, 21);
int year = date.getYear();
Month month = date.getMonth();
```
