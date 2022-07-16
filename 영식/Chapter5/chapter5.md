# Chapter 5 스트림 활용

- 중간 연산들 정리

### 필터링

- 프레디케이트(boolean 반환 함수) 로 필터링
    - filter
- 고유 요소 필터링 - distinct() : 중복 제거

### 스트림 슬라이싱

- 스트림 요소 선택 or 스킵 하는 방법

- 프레디케이트를 이용한 슬라이싱
    - TAKEWHILE

    ```java
    List<Dish> filterMenu
    	= specialMenu.stream()
    							.filter(dish -> dish.getCalories() < 320 ) 
    							.collect(toList());
    
    // TAKEWHILE
    List<Dish> slicedMenu1
    	= specialMenu.stream()
    							.takeWhile(dish -> dish.getCalories() < 320)
    							.collect(toList());
    
    데이터 리스트가 이미 칼로리순으로 정렬이 되어있다면
    filter 연산 사용하기 보단 takeWhile 연산을 사용하자 
    320칼로리보다 크거나 같은 요리가 나올 때 반복 작업 중단 
    조건이 참일 때까지만 반복 
    
    filter 연산 경우엔 전체 스트림을 반복하면서 프레디케이트 적용한다. 
    
    // DROPWHILE 
    
    List<Dish> slicedMenu2
    	= specialMenu.stream()
    							.dropWhile(dish -> dish.getCalories() < 320 )
    							.collect(toList());
    
    dropWhile의 경우 takeWhile과 반대 작업 수행 프레디케이트가 처음으로 거짓되는 지점까지 
    발견된 요소를 버리고 , 
    프레디케이트가 거짓되는 그 지점에서 작업 중단 후 남은 요소들을 반환 
    
    ```


- 스트림 축소

  limit 활용

    ```java
    List<Dish> dishes = specialMenu.stream()
            .filter(dish -> dish.getCalories() > 300 )
            .limit(3)
    		.collect(toList());
    
    프레디케이트와 일치하는 처음 세 요소를 선택 후 즉시 반환 
    
    ```

- 요소 건너뛰기

  skip

    ```java
    List<Dish> dishes = menu.stream()
    												.filter(d -> d.getCalories() > 300)
    												**.skip(2)**
    												.collect(toList());
    // 300칼로리 이상의 처음 두 요리를 건너 뛴 다음 300칼로리가 넘는 나머지 요리를 반환 
    ```


### 매핑

- 특정 객체에 특정 데이터를 선택하는 작업

ex ) SQL 테이블에 특정열만 선택하는 것

- `map` , `flatMap` 메서드가 특정 데이터를 선택하는 기능을 제공

### 5.3.1 스트림의 각 요소에 함수 적용

```java
List<String> dishNames = menu.stream().map(Dish::getName).collect(toList());
/* 
메뉴 스트림의 요리명을 추출하는 코드
getName 메서드가 문자열을 반환하므로 map 메서드의 출력 스트림은 Stream<String> 형식 

Map 메서드의 인수로 받는 함수의 결과가 새로운 요소로 매핑되는 것을 의미한다. 
( 기존 값을 고치는 개념이 아닌 새로운 버전을 만든다는 개념 )
변환에 가까운 매핑 

*/
```

```java
List<String> word = Arrays.asList("Modern" , "Java" , "In" , "Action");
List<Integer> wordLengths = words.stream().map(String::length).collect(toList());

/* 
words 리스트의 스트림을 생성 후 String 인 리스트 데이터들의 length를 매핑해서 
List<Integer> 데이터로 반환 
*/
```

```java
/* 요리명의 길이 */

List<Integer> dishNameLengths = menu.stream().map(dish.getName).**map(String::length)**
.collect(toList());
```

### 5.3.2 스트림 평면화

❓ 평면화 뜻이 뭘까? → 데이터의 최소 단위를 나열하는 느낌?

map을 통해 String 리스트에서 고유 문자로 이루어진 리스트를 반환하려고 하면

List<String[]> 이 반환된다

```jsx
words : ["hello" , "World"]

words.stream()
.map(word -> word.split("")) // Stream<String[]>
.distinct()
.collect(toList()); // List<String[]> 

//한글자 씩 리스트를 반환하고 싶은 목적과 다르다

```

- map 과 Arrays.stream 활용

```jsx
words.stream()
.map(word -> word.split(""))
.map(Arrays::stream) // 각 배열을 별도의 스트림으로 생성 
.distinct()
.collect(toList());

// 결과값이 엄밀히 따지면 List<Stream<String>>이 만들어진다.
```

- flatMap 사용

flatMap은 스트림의 콘텐츠를 매핑

```java
List<String> uniqueCharacters =
	words.stream()
		.map(word -> word.split(""))
		.flatMap(Arrays::stream)
		.distinct()
		.collect(toList());
```

map으로 하기엔 작업이 많이 드는 과정 , flatMap 메서드를 사용해서 평면화 작업 진행

## 5.4 검색과 매칭

### anyMatch

- 프레디케이트가 적어도 한 요소와 일치

```java
if(menu.stream().anyMatch(Dish::isVegetarian)) {
	System.out.println("The menu is (someWhat) vegetarian friendly !!");
}
```

### allMatch

- 프레디케이트가 모든 요소와 일치하는지 검사

### noneMatch

- allMatch 와 반대 연산 실행 → 일치하는 요소가 없는지 확인

### 쇼트 서킷 연산

- 전체 스트림을 처리하지 않고 결과 반환
  - and 연산으로 연결된 불리언 표현식이 있으면 하나라도 틀리면 거짓 결과가 나옴 → 나머지는 상관없이 그것은 거짓이 되어버림
  - 쇼트서킷 연산은 무한한 스트림을 유한한 크기로 줄일 수 있다.
  - allMatch , noneMatch , findFirst , findAny , limit 등이 쇼트서킷 연산

## 5.5 리듀싱

- 합계 , 최댓값 , 최솟값등 스트림 요소들을 조합해서 값을 도출하는 연산 → `폴드`라고도 부른다.

### 요소의 합

int sum = numbers.stream().reduce( 0 , (a,b)→ a+b);

- 초깃값 = 0
- a , b 를 더한다.

int sum = numbers.stream().reduce(0 , Integer::sum);

- 정적 메서드 sum을 사용해서 간결한 코드 사용
- 초깃값 없이 사용 가능 ⇒ Optional로 반환

Optional<Integer> sum = numbers.stream().reduce((a,b) → (a + b) );

### 최댓값과 최솟값

Optional<Integer> max = numbers.stream().reduce(Integer::max);

Optional<Integer> min = numbers.stream().reduce(Integer::min);

