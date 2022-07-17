6.2.2 요약 연산
 다음은 하나의 요약 연산으로 메뉴에 있는 요소 수, 요리의 칼로리 합계, 평균, 최댓값, 최솟값 등을 계산하는 코드다.
 
 ```java
 IntSummaryStatistics menuStatistics = 
    menu.stream().collect(summarizingInt(Dish::getCalories));
 ```
 
 위 코드를 실행하면 IntSummaryStatistics 클래스로 모든 정보가 수집된다. menuStatistics객체를 출력하면 다음과 같은 정보를 확인할 수 있다.
 IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}
 
 6.2.3 문자열 연결
 ```java
 String shortMenu = menu.stream().map(Dish::getName).collect(joining());
 ```
 - joining 메서드는 내부적으로 StringBuilder를 이용해서 문자열을 하나로 만든다.
 - Dish 클래스가 요리명을 반환하는 toString 메서드를 포함하고 있다면 다음 코드에서 보여주는 것처럼 map으로 각 요리의 이름을 추출하는 과정을 생략할 수 있다

```java
String shortMenu = menu.stream().collect(joining());
```

위 두 코드 모두 다음과 같은 결과를 도출한다.
porkbeefchickenfrench friesriceseason fruitpizzaprawnssalmon
하지만 결과 문자열을 해석할 수가 없다. 연결된 두 요소 사이에 구분 문자열을 넣을 수 있도록 오버로드된 joining 팩토리 메서드도 있다.

```java
String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon
