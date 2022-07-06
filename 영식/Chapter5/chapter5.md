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