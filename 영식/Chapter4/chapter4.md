# Chapter 4 스트림 소개

### 스트림이란?

- 자바8에서 새로 추가된 기능
    - 선언형으로 컬렉션 데이터를 처리 가능
    - 멀티스레드 코드를 사용하지 않아도 투명하게 병렬로 처리 가능

```java
// Java 7 코드 
List<Dish> lowCaloricDishes = new ArrayList<>();
for ( Dish dish : menu ) {
	if(dish.getCalories() < 400 ) {
		lowCaloricDishes.add(dish);
	}
}
Collections.sort(lowCaloricDishes , new Comparator<Dish>() {
	public int compare(Dish dish1, Dish dish2) {
		return Integer.compare(dish1.getCalories() , dish2.getCalories());
	}
});

List<String> lowCaloricDishesName = new ArrayList<>();
for(Dish dish : lowCaloricDishes) {
	lowCaloricDishesName.add(dish.getName());
}

// Java 8 코드 

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

List<String> lowCaloricDishesName = 
	menu.stream()
		.filter(d -> d.getCalories() < 400) // 400 칼로리 이하 필터링
		.sorted(comparing(Dish::getCalories)) // 칼로리로 정렬
		.map(Dish::getName) // 요리명 추출
		.collect(toList()); // 요리명을 리스트에 저장

```

- 스트림은 생성 - 중간연산 - 최종연산을 이용해서 파이프라인을 형성한다.

- 스트림의 새로운 기능으로 받는 이익
    1. 선언형으로 코드 구현 가능 ⇒ 루프 혹은 if 조건문으로 제어 블록 사용해서 구현하는 것이 아닌 어떤 동작의 수행을 지정할 수 있다
    2. 가독성과 명확성

‼️ [파이프라인](https://ko.wikipedia.org/wiki/%ED%8C%8C%EC%9D%B4%ED%94%84%EB%9D%BC%EC%9D%B8_(%EC%BB%B4%ED%93%A8%ED%8C%85)) : 컴퓨터 과학에서 **파이프라인**은 한 데이터 처리 단계의 출력이 다음 단계의 입력으로 이어지는 형태로 연결된 구조를 가리킨다.

### 스트림 특징

- 파이프라이닝

  스트림 연산끼리 연결해서 파이프라인 구성할 수 있도록 스트림 자신을 반환

    - 파이프라인은 데이터베이스에서 사용하는 Query 문 같은 것
    - 게으름 (Laziness) , 쇼트서킷(short-circuiting ) 같은 최적화 가능
- 내부 반복

  반복자를 이용한 명시적 반복을 사용하는 컬렉션과 달리 스트림은 내부 반복을 지원


### 스트림과 컬렉션

- 컬렉션과 스트림의 차이
    1. DVD 와 인터넷 스트리밍

  ⇒ 데이터를 언제 계산하느냐의 차이

    - 컬렉션은 현재 자료구조를 포함한 모든 값을 메모리에 저장하는 구조, 컬렉션의 모든 요소는 컬렉션에 추가 되기 전에 계산되어야 한다.
    - 스트림 : 요청할 때만 계산

- 컬렉션은 `외부 반복` 스트림은 `내부 반복`

  for-each 부분이 눈에 보이는 것과 안보이는 것의 차이


### 스트림 연산

- 중간 연산
    - 다른 스트림을 변환
    - 최종 연산이 실행되기 전에는 연산 수행 X ⇒ 게으르다
- 최종 연산
    - 스트림 파이프라인의 결과를 도출
    - List , Integer , void 등 스트림 이외의 결과가 반환