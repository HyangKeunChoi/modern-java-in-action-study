# #chapter5

## 5.1 필터링
- 프레디케이트 필터링 방법과 고유 요소만 필터링하는 방법을 배운다.

### 5.1.1 프레디케이트로 필터링
- filter 메서드는 프레디케이트를 인수로 받아서 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.

```java
List<Dish> vegetarianMenu = menu.stream()
				.filter(Dish::isVegetarian)		//채식 요리인지 확인하는 메서드 참조
				.collec(toList());
```

### 5.1.2 고유 요소 필터링
- 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다.

## 5.2 스트림 슬라이싱
- 스트림의 처음 몇 개의 요소를 무시하는 방법, 특정 크기로 스트림을 줄이는 방법 등 다양한 방법

### 5.2.1 프레디케이트를 이용한 슬라이싱
#### TAKEWHILE 활용
```java
List<Dish> slicedMenu1 = specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).collect(toList());	
```
- 모든 스트림에 프레디케이트를 적용해 스트림을 슬라이스할 수 있다.

#### DROPWHILE 활용
```java
List<Dish> slicedMenu2 = specialMenu.steam().dropWhile(dish -> dish.getCalories() < 320).collect(toList());
```
- 거짓이 되는 지점까지 발견된 요소를 버린다.
- 무한한 남은 요소를 가진 무한 스트림에서도 동작한다.

## 5.2.2 스트림 축소
- 스트림이 정렬되어 있으면 최대 요소 n개를 반환할 수 있다.
```java
List<Dish> dishes = specialMenu.stream().filter(dish -> dish.getCalories() < 320).limit(3).collect(toList());
```

## 5.3 매핑
- 특정 객체에서 특정 데이터를 선택하는 작업은 데이터 처리 과정에서 자주 수행되는 연산이다.
### 5.3.1 스트림의 각 요소에 함수 적용하기
- stream은 함수를 인수로 받는 map 메서드를 지원한다.
- 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다.
```java
List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
List<Integer> wordLengths = words.stream()
				.map(String::length)
				.collect(toList());

List<Integer> dishNameLengths = menu.stream()
				.mao(Dish::getName)
				.map(String::length)
				.collect(toList());
```

### 5.3.2 스트림 평면화
- map과 Arrays.stream 활용
```java
String[] arrayOfWords = {"Goodbye", "World"};
Stream<String> streamOfwords = Arrays.stream(arrayOfwords);

words.stream().
	 .map(word -> word.split(""))
	 .map(Array::stream)
	 .distinct()
	 .collect(toList());
```

- flatMap 사용
```java
List<String> uniqueCharaters =
	words.stream()
		 .map(word -> word.split(""))
		 .flatMap(Arrays::stream)
		 .distinct()
		 .collect(toList());
```

## 5.4 검색과 매칭
- 특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리도 자주 사용
- 스트림 API는 allMath, anyMatch, noneMatch, findFirst, findAny 등 다양한 유틸리티 메서드를 제공한다.

## 5.5 리듀싱
- 스트림의 모든 요소를 반복적으로 처리 (모든 스트림 요소를 처리해서 값으로 도출)
