# 스트림 활용
5.1 필터링
* predicate
  * boolean을 반환하는 함수로 filter메서드는 이를 인수로 받아와서 predicate와 일치하는 모든 요소를 포함하는 스트림 반환
    ```java
    List<Dish> vegetaritanMenu = menu.Stream().filter(Dish:: isVegetarian).collect(toList());
    ```
* 고유요소 필터링(distinct 메서드 활용)
  * 고유요소는 hashCode, equals로 결정

5.2 스트림 슬라이싱
* 스트림 요소를 선택하거나 스킵하는 방법
* predicate(선택용)
  * takeWhile : 320보다 작은 요소만 반환 = 처음부터 거짓이 되는 지점까지의 요소만 선택
  ```java
  List<Dish> sliceMenu = specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).collect(toList());
  ```
  * dropWhile : 320보다 큰 요소 반환 = 처음부터 거짓이 되는 지점까지 요소를 버림
   ```java
  List<Dish> sliceMenu2 = specialMenu.stream().dropWhile(dish -> dish.getCalories() < 320).collect(toList());
  ```
  * 스트림 축소(limit) : limit(n) 사용하여 프레디케이트와 일치하는 n번째 요소까지만 반환
  * 요소 건너뛰기(skip) : filter의 조건을 만족하는 n개 요소를 건너뜀
   ```java
  List<Dish> dishes = menu.stream().filter(d-> d.getCalories() > 300).skip(2).collect(toList());
  ```
 
 5.3 매핑
 * 스트림의 각 요소에 함수 적용
 * flatMap 이용
  * Stream<String[]>를 Stream<String>으로 반환해야 하는 경우
  
  ```java
  List<Integer> uniqueCharacters = words.stream().map(word->word.split("")).flatMap(Arrays::stream).distinct().collect(toList());
  //flatMap을 이용해 생성된 스트림을 하나의 스트림으로 평면화(flat)
  ```
  5.4 검색과 매칭
  * 특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리
  * predicate가 적어도 한 요소와 일치하는지 확인
    * anyMatch메서드 사용 (boolean 반환)
  * predicate가 모든 요소와 일치하는지 검사
    * allMatch: 스트림의 모든 요소가 주어진 프레디케이트와 일치하는지 확인
    * noneMatch: 스트림의 모든 요소가 주어진 프레디케이트오 일치하지 않는지 확인
  * 스트림의 모드 요소를 처리하지 않고도 쇼트서킷을 이용하여 결과를 반환할 수 있음
  * 임의의 요소 검색:findAny
    * findAny를 사용하면 아무 값도 반환하지 않을 수 있으므로 이를 방지하기 위해 Optional 클래스 사용
    * 병렬 스트림에서 사용
  * 첫번째 요소 찾기:findFirst
  
  5.5 리듀싱
  * 리듀싱 연산: 모든 스트림 요소를 처리해서 값으로 도출하는 연산
    * reduce() 괄호 안에 람다 표현식을 넣음으로서 모든 요소를 연산할 수 있음
    * 초기값이 없는 reduce는 optional 객체 반환
  * 최댓값과 최솟값
    * 새로운 값을 이용해서 스트림의 모든 요소를 소비할 때 까지 람다를 반복하면서 수행하며 최댓값/최솟값을 생산
     ```java
    Optional<Integer> max = numbers.stream().reduce(Integer::max);
    Optional<Integer> min = numbers.stream().reduce(Integer::min);
    ```
