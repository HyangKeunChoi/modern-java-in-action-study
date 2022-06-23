# 스트림 활용

### 필터링
* predicate 필터링   
	- Predicate(boolean반환 함수)를 인수로 받아서 일치하는 요소 포함하는 스트림을 반환.
'''java

	List<Dish> vegeterianMenu = menu.stream()
										   .filter(Dish::isVegiterian) //메서드 참조
										   .collect(toList());	
'''
* 고유요소 필터링   
	- 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드 지원
'''java

	//짝수선택,중복 필터링
	List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
	numbers.stream()
			.filter(i -> i%2 == 0)
			.distinct()
			.forEach(System.out::println); 
'''

### 슬라이싱
* predicate 슬라이싱   
	- **takeWhile**,**dropWhile**     
'''java
	
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
''' 

* 스트림 축소  
 
'''java
	List<Dish> dishes = specialMenu.stream()
				       .filter(dish -> dish.getCalories() > 300)
				       .limit(3)
				       .collect(toList()); //3개의 요소만 반환   
'''
	
* 요소 건너뛰기 

'''java
	List<Dish> dishes = specialMenu.stream()
				       .filter(dish -> dish.getCalories() > 300)
				       .skip(2)
				       .collect(toList());//2개의 요소 제외한 나머지 요소 반환   
'''

### 매핑
	- 특정 객체에서 특정 데이터를 선택   

* 스트림 각 요소에 함수 적용
	- 함수를 인수로 받는 map메서드 지원

'''java   
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
'''

	

	
	

	
