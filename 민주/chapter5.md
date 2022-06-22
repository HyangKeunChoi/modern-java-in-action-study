5.1.2 고유 요소 필터링
- 스트림은 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다.(고유 여부는 스트림에서 만든 객체의 hashCode, equals로 결졍된다.)

```java  
 List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
  numbers.stream()
         .filter(i -> i % 2 == 0 )
         .distinct()
         .forEach(System.out::println);
 ```
5.2.1 프레디케이트를 이용한 슬라이싱
- 자바 9은 스트림의 요소를 효과적으로 선택할 수 있도록 takeWhile, dropWhile 두 가지 새로운 메서드를 지원한다.

* takeWhile 활용
  - 리스트가 이미 정렬되어 있다는 사실을 이용해 특정 조건에 일치하지 않는 값이 나왔을 때 반복 작업을 중단할 수 있다.
  - 무한스트림을 포함한 모든 스트림에 프레디케이트를 적용해 스트림을 슬라이스할 수 있다.

* dropWhile 활용
  - takeWhile과 정반대의 작업을 수행한다. 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
    프레디케이트가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환한다.
  
 5.2.2 스트림 축소
  - 스트림은 주어진 값 이하의 크기를 갖는 새로운 스트림을 반환하는 limit(n) 메서드를 지원한다.
    스트림이 정렬되어 있으면 최대 요소 n개를 반환할 수 있다.
```java   
public static final List<Dish> menu = Arrays.asList(
      new Dish("pork", false, 800, Dish.Type.MEAT),
      new Dish("beef", false, 700, Dish.Type.MEAT),
      new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("pizza", true, 550, Dish.Type.OTHER),
      new Dish("prawns", false, 400, Dish.Type.FISH),
      new Dish("salmon", false, 450, Dish.Type.FISH)
  );
  
  List<Dish> dishes = specialMenu.stream()
                                 .filter(dish -> dish.getCalories() > 300)
                                 .limit(3)
                                 .collect(toList());
```  
 -> pork
    beef
    chicken
    
 5.2.3 요소 건너뛰기
 - 스트림은 처음 n개 요소를 제외한 스트림을 반환하는 skip(n) 메서드를 지원한다.
   n개 이하의 요소를 포함하는 스트림에 skip(n)을 호출하면 빈 스트림이 반환된다.
   limit(n)과 skip(n)은 상호 보완적인 연산을 수행한다.

5.3.2 스트림 평면화
* flatMap 사용
  > flatMap 메서드는 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결하는 기능을 수행한다.

'''java
List<String> uniqueCharacters = 
 words.stream()
  .map(word -> word.split(""))
  .flatMap(Arrays::stream)
  .distinct()
  . collect(toList());
'''
 -> HeloWrd
 flatMap을 사용하지 않으면 Helo World
 
 
