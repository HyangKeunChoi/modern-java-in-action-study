# 스트림 활용

* 제목
	- 소제목

**강조** 
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

	
