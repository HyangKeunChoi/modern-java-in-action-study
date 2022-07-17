## 스트림으로 데이터 수집

+ 중간 연산은 한 스트림을 다른 스트림으로 변환하는 연산으로서, 여러 연산을 연결할 수 있다.
+ 중간 연산은 스트림 파이프라인을 구성하며, 스트림의 요소를 소비하지 않는다.
+ 반면 최종연산은 스트림의 요소를 소비해서 최종 결과를 도출한다.
+ 최종 연산은 스트림 파이프라인을 최적화하면서 계산을 짧게 생략하기도 한다.

### 6.1 컬렉터란 무엇인가.
+ 함수형 프로그래밍에서는 '무엇'을 원하는지 직접 명시할 수 있어서 어떤 방법으로 이를 얻을지는 신경 쓸 필요가 없다.
+ groupingBy를 이용해서 각 키(통화) 버킷 그리고 각 키 버킷에 대응하는 요소 리스트를 값으로 포함하는 맵(map)을 만들라는 동작을 수행한다.

## 6.1.1 고급 리듀싱 기능을 수행하는 컬렉터
+ collect로 결과를 수집하는 과정을 간단하면서도 유연한 방식으로 정의할 수 있다는 점이 컬렉터의 최대 강점이다.

## 6.1.2 미리 정의된 컬렉터
+ Collectors에서 제공하는 메서드의 기능은 크게 세 가지로 구분할 수 있다.
  - 스트림 요소를 하나의 값으로 리듀스하고 요약
  - 요소 그룹화
  - 요소 분할

## 6.2 리듀싱 요약
### 6.2.1 스트림 값에서 최댓값과 최솟값 검색
```java
Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));
```

### 6.2.2 요약 연산

+ summingInt : 객체를 int로 매핑하는 함수를 인수로 받는다. summingInt의 인수로 전달된 함수는 객체를 int로 매핑한 컬렉터를 반환한다.
+ 그리고 summingInt가 collect 메서드로 전달되면 요약 작업을 수행한다.

### 6.2.3 문자열 연결
+ joining 메서드

```java
String shortMenu = menu.stream().map(Dish::getName).collect(joining(","));
```

### 6.2.4 범용 리듀싱 요약 연산
```java
int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
```

### 6.3 그룹화
```java
public enum CaloricLevel { DIET, NORMAL, FAT }

Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
    groupingBy(dish -> {
      if (dish.getCalories() < 400) return CaloricLevel.DIET;
      else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
      else return CaloricLevel.FAT;
      }));
```

### 6.3.1 그룹화된 요소 조작

+ 틀린 그룹화
```java
Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream().filter(dish -> dish.getCalories() > 500)
                                                              .collect(groupingBy(Dish::getType));
```

+ 올바른 그룹화
```java
Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream().collect(groupingBy(Dish::getType, 
                                                                        filtering(dish -> dish.getCalories() > 500, toList())));
``

> Collector 안으로 필터 프레디케이트를 이동함으로 이 문제를 해결 할 수 있다.

### 6.4 분할
+ 분할은 분할 함수(partitioning function)라 불리는 프레디케이트를 분류 함수로 사용하는 특수한 그룹화 기능이다.

### 6.4.1 분할의 장점
+ 분할 함수가 반환하는 참, 거짓 두가지 요소의 스트림 리스트를 모두 유지한다는것이 분할의 장점이다.
```java
Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(
                                                                partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
```



