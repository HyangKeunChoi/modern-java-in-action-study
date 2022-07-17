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

6.3.3 서브그룹으로 데이터 수집
groupingBy 컬렉터에 두 번째 인수로 counting 컬렉터를 전달해서 메뉴에서 요리의 수를 종류별로 계산할 수 있다.
```java
Map<Dish.Type, Long> typeCount = menu.stream().collect(
                                 gorupingBy(Dish::getType, counting()));                              
```
결과
> {MEAT=3, FISH=2, OTHER=4}

6.4.1 분할의 장점
- 분할 함수가 반환하는 참, 거짓 두 가지 요소의 스트림 리스트를 모두 유지한다는 것이 분할의 장점이다.
```java
Map<Bollean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu. stream().collect(
                                                                                    partitioningBy(Dish::isVegetarian, // 분할함수
                                                                                    groupBy(dish::getType)));          // 두 번째 컬렉터
```
결과
> {false={FISH=[prawns, salmon], MEAT=[pork, beef, chiocken]},
> true={OTHER=[french fries, rice, season fruit, pizza]}}

자바 8로 takeWhile 흉내내기
```java
public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
   int i = 0;
   for (A item : List) {
      if (!p. test(item)) { // 리스트의 현재 항목이 프레디케이트를 만족하는지 확인
         return list.subList(0, i); // 만족하지 않으면 현재 검사한 항목의 이전 항목 하위 리스트를 반환
      }
      i++;
   }
   return list; // 리스트의 모든 항목이 프레디케이트를 만족하므로 리스트 자체를 반환
}
```

6.7 마치며
- collect는 스트림의 요소를 요약 결과로 누적하는 다양한 방법(컬렉터라 불리는)을 인수로 갖는 최종 연산이다.
- - 스트림의 요소를 하나의 값으로 리듀스하고 요약하는 컬렉터뿐 아니라 최솟값 최댓값, 평균값을 계산하는 컬렉터 등이 미리 정의되어 있다.
- 미리 정의된 컬렉터인 gorupingBy로 스트림의 요소를 그룹화하거나, partitioningBy로 스트림의 요소를 분할할 수 있다.
- 컬렉터는 다수준의 그룹화, 분할, 리듀싱 연산에 적합하게 설계되어 있다.
- Collect 인터페이스에 정의된 메서드를 구현해서 커스텀 컬렉터를 개발할 수 있다.
