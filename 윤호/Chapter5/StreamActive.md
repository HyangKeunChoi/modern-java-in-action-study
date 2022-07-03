# 스트림 활용

### 필터링
* predicate 필터링   
	- Predicate(boolean반환 함수)를 인수로 받아서 일치하는 요소 포함하는 스트림을 반환.
	
```java

	List<Dish> vegeterianMenu = menu.stream()
										   .filter(Dish::isVegiterian) //메서드 참조
										   .collect(toList());	
```
* 고유요소 필터링   
	- 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드 지원
	
```java

	//짝수선택,중복 필터링
	List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
	numbers.stream()
			.filter(i -> i%2 == 0)
			.distinct()
			.forEach(System.out::println); 
```

### 슬라이싱
* predicate 슬라이싱   
	- **takeWhile**,**dropWhile**     
	
```java
	
	List<Dish> specialMenu = Arrays.asList(
		new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
		...
		new Dish("french fries", true, 530, Dish.Type.OTHER)
	);		
	
	//기존 320칼로리 이하 요리 선택
	List<Dish> filteredMenu
		= specialMenu.stream()
						.filter(dish -> dish.getCalories() < 320)
						.collect(toList());
						
	//takeWhile 활용(무한 스트림을 포함한 모든 스트림에 predicate를 적용)
	List<Dish> slicedMenu1
		= specialMenu.stream()
						.takeWhile(dish -> dish.getCalories() < 320)
						.collect(toList());
	
	//dropWhile 활용(takeWhile과 반대 작업 수행)
	List<Dish> slicedMenu2
		= specialMenu.stream()
						.dropWhile(dish -> dish.getCalories() < 320)
						.collect(toList());											
```

* 스트림 축소  
 
```java
	List<Dish> dishes = specialMenu.stream()
				       .filter(dish -> dish.getCalories() > 300)
				       .limit(3)
				       .collect(toList()); //3개의 요소만 반환   
```
	
* 요소 건너뛰기 

```java
	List<Dish> dishes = specialMenu.stream()
				       .filter(dish -> dish.getCalories() > 300)
				       .skip(2)
				       .collect(toList());//2개의 요소 제외한 나머지 요소 반환   
```

### 매핑
	- 특정 객체에서 특정 데이터를 선택   

* 스트림 각 요소에 함수 적용
	- 함수를 인수로 받는 map메서드 지원

```java   

	// 단어를 인수로 받아서 길이를 반환하는 메서드
	List<String> words = Arrays.asList("Modern","Java");
	List<Integer> wordLengths = words.stream()
					 .map(String::length)
					 .collect(toList()); //6,4 반환?
	
	//각 요리명의 길이 알아내기
	List<Integer> dishNameLengths = menu.stream()
					    .map(Dish::getName)
					    .map(String::length)
					    .collect(toList());
```

* 스트림 평면화(flatMap)    
	- map,Arrays.stream(문자열 받아서 스트림 만들어줌)활용

```java
	String[] arrayOfWords = {"Goodbye","World"};
	Stream<String> streamOfwords = Arrays.stream(arrayOfWords);
	
	words.stream()
		  .map(word -> word.split("")) //각 단어를 개별 문자열 배열로 변환
		  .map(Arrays::stream)	//각 배열을 별도의 스트림으로 생성
		  .distinct()
		  .collect(toList());
	//List<Stream<String>> 이 만들어지므로 문제 발생	
	
	List<String> uniqueCharacters = 
		words.stream()
			  .map(word -> word.split(""))
			  .flatMap(Arrays::stream)  //생성된 스트림을 하나의 스트림으로 평면화
			  .distinct()
			  .collect(toList());
	
```

### 검색 및 매칭   
* 쇼트 서킷
	- anyMatch(Dish::isVegetarian): predicate가 주어진 스트림에서 적어도 한 요소와 일치하는지 확인,불리언 반환
	- allMatch : 모든 요소와 일치하는지 확인
	- noneMatch : 일치하는 요소가 없는지 확인, allMatch와 반대

* 요소 검색
	- findAny

```java
	Optional<Dish> dish = 
		menu.stream()
			 .filter(Dish::isVegetarian)
			 .findAny(); ///임의의 요소 반환
	
```

* Optional   
	- Optional<T> 클래스는 값의 존재나 부재 여부 표현   
	- isPresent() 는 Optional이 값을 포함하면 true 아니면 false 반환   
	- ifPresent(Consumer<T> block)은 값이 있으면 주어진 블록 실행   
	- T get()은 값이 존재하면 반환,없으면 Exception일으킴   
	- T orElse(T other)는 값이 존재하면 반환,없으면 기본값 반환

```java

	//null검사 필요 없는 예제
	menu.stream()
		.filter(Dish::isVegetarian)
		.findAny() //Optional<Dish> 반환
		.ifPresent(dish -> System.out.println(Dish.getName())); //값 있으면 출력 없으면 안일어남
```


### 리듀싱

* 리듀스 연산    
	- 모든 스트림 요소를 처리해서 값으로 도출(integer과 같은 결과가 나오게 하기 위함)
	- 폴드 라고도 표현

```java
	
	//요소의 합
	int sum = 0;
	for (int x : numbers) {
		sum += x;
	}
	//sum 메서드 적용
	int sum = numbers.stream()
						.reduce(0, Integer::sum);
	
	//reduce 사용
	int sum = numbers.stream()
						.reduce(0, (a,b) -> a+b);
						//곱셈 적용 
						.reduce(1, (a,b) -> a*b);
	
	//최댓값 최솟값
	Optional<Integer> max = numbers.stream()
										.reduce(Integer::max)
	Optional<Integer> min = numbers.stream()
										.reduce(Integer::min)
```

	

	
	

	
