# 스트림 활용(2)

5.7 숫자형 스트림
* 기본형 특화 스트림을 이용해 sum,max등의 리듀싱 연산 수행이 가능
  * IntStream: int 요소에 특화
  * DoubleStream : double 요소에 특화
  * LongStream : long 요소에 특화
  * 숫자 스트림으로 맵핑
    * mapToInt, mapToDouble, mapToLong
    * map과 같은 기능을 수행하지만 Stream<T>르 반환하는게 아닌 특화된 스트림을 반환
    ```java
    int Calories = menu.stream().mapToInt(Dish::getCalories).sum();
    //Stream<Integer>가 아닌 IntStream을 반환해서 sum을 이용한 칼로리 계산이 가능
    ```

  * 객체 스트림으로 복원하기
    * 특화된 스트림이 아닌 다른 값을 반환하고 싶을 때? 스트림 인터페이스에 정의된 일반적인 연산 사용
    ```java
    IntStream intStream = menu.stream().mapToInt(Dish::getCalories); //스트림을 숫자스트림으로 변환
    Stream<Integer> stream = intStream.boxed(); //숫자스트림을 스트림으로 변환
    ```
  * 기본값: OptionalInt
    * 특화 스트림의 기본값은 OptionalInt, OptionalDouble, OptionalLong사용

* 숫자 범위
  * range, rangeClosed : 첫번째 인수로 시작값, 두번째 인수로 종료값 가짐
  * range는 시작,종료값이 결과값에 포함이 안되지만 rangeClosed는 결과값이 포함됨

* 피타고라스 수로의 활용
  ```java
    Stream<int[]> pythagoreanTriples = 
      IntStream.rangeClosed(1, 100).boxed() // 세 수의 스트림 생성, flatMap에서 각가의 스트림을 하나의 평준화된 스트림으로 만듦
               .flatMap( a -> IntStream.rangeClosed(a, 100) //중복된 세 수가 생성되는걸 방지하기 위해 시작값 a로 설정
                                       .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                       .mapToObj( b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)})
              );
    ```
* 피타고라스 수로의 활용2: 조건에 맞는 결과만 필터링
  ```java
    Stream<double[]> pythagoreanTriples2 = 
      IntStream.rangeClosed(1, 100).boxed() 
               .flatMap( a -> IntStream.rangeClosed(a, 100) //중복된 세 수가 생성되는걸 방지하기 위해 시작값 a로 설정
                                       .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                       .mapToObj( b -> new double[]{a, b, Math.sqrt(a*a + b*b})
                                       .filter( t -> t[2] % 1 == 0)); //세 수의 세번째 요소가 정수인 결과만 반환하도록 filter 처리
              );
    ```
5.8 스트림 만들기
* 값으로 스트림 만들기
  * Stream.of를 이용: 임의의 수르 인수로 받는 정적 메서드
* null 이 될 수 있는 객체로 스트림 만들기
  * Stream.ofNullable을 이용
  * null을 명시적으로 확인할 필요가 없음
* 배열로 스트림 만들기
  * Arrays.stream 활용
   ```java
   int[] numbers = {2,3,5,7,11,13};
   int sum = Arrays.stream(numbers).sum();
   ```
  * 파일로 스트림 만들기
    * File.lines(): 파일의 각 행 요소를 반환하는 스트림
  
  * 무한스트림: 크기가 고정되지 않은 스트림
    * Stream.iterate와 Stream.generate 이용
    * 요청할 때마다 주어진 함수를 이용하여 값 생성
     ```java
     Stream.iterate(0, n -> n + 2)
           .limit(10)
           .forEach(System.out::println);
    ```
    * iterate: 연속된 일련의 값 만들 때 사용
    * generate: 요구할 때 값을 계산, 생산된 값을 연속적으로 계산하지는 않음
  
  
  
