3. 람다 표현식
> 람다 표현식은 익명 클래스처럼 이름이 없는 함수면서 메서드를 인수로 전달할 수 있으므로 일단은 람다표현식이 익명 클래스와 비슷하다고 생각하자

3.1 람다란 무엇인가?
> 람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화한 것
> 람다 표현식에는 이름은 없지만, 파라미터 리스트, 바디, 반환 형식, 발생할 수 있는 예외 리스트는 가질 수 있다.

* 람다의 특징
1. 익명
 > 보통의 메서드와 달리 이름이 없으므로 익명이라 표현한다. 구현해야 할 코드에 대한 걱정거리가 줄어든다.
2. 함수
 > 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다. 하지만 메서드처럼 파라미터 리스트, 바디, 반환 형식, 가능한 예외 리스트를 포함한다.
3. 전달
 > 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
4. 간결성
 > 익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.
 
 
람다 표현식은 파라미터, 화살표, 바디로 이루어진다.
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
   람다 파라미터     화살표              람다바디
   

3.2.2 함수 디스크립터
> 함수형 인터페이스의 추상 메서드 시그니처는 람다 표현식의 시그니처를 가리킨다.
  람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 부른다.


3.4.3 Funtion
> 자바의 모든 형식은 참조형(Byte, Integer, Object, List) 아니면 기본형(int, double, byte, char)에 해당한다.
  하지만 제네릭 파라미터(예를 들면 Consumer<T>의 T)에는 참조형만 사용할 수 있다.
> 자바에서는 기본형을 참조형으로 변환하는 기능을 제공한다. 이 기능을 박싱이라고 한다.
  참조형을 기본형으로 변환하는 반대 동작을 언박싱이라고 한다.
  프로그래머가 편리하게 코드를 구현할 수 있도록 박싱과 언박싱이 자동으로 이루어지는 오토박싱이라는 기능도 제공한다.
  
3.5.2 같은 람다, 다른 함수형 인터페이스
> Predicate는 불리언 반환값을 갖는다.
  Predicate<String> p = s -> list.add(s);
  Consumer는 void 반환값을 갖는다.
  Consumer<String> b = s -> list.add(s);
  
다음 코드를 컴파일할 수 없는 이유
Object o = () > { Systema.out.println("Trickey example"); };
> 람다 표현식의 콘텍스트는 Object(대상 형식)다. 하지만 Object는 함수형 인터페이스가 아니다.

따라서 () -> void 형식의 함수 디스크립터를 갖는 Runnable로 대상 형식을 바꿔서 문제를 해결할 수 있다.
Runnable r = () -> { Systema.out.println("Trickey example"); };

람다 표현식을 명시적으로 대상 형식을 제공하는 Runnable로 캐스팅해서 문제를 해결할 수도 있다.
Object o = (Runnable) () -> { Systema.out.println("Trickey example"); };

3.5.3 형식 추론
(형식을 추론하지 않음)
 > Comparator<Apple> c = 
      (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
(형식을 추론함)
 > Comparator<Apple> c = 
      (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());

상황에 따라 명시적으로 형식을 포함하는 것이 좋을 때도 있고 형식을 배제하는 것이 가독성을 향상시킬 때도 있다.
어떤 방법이 좋은지 정해진 규칙은 없다.

3.5.4 지역 변수 사용
 > 람다 표현식에서는 익명 함수가 하는 것처럼 자유 변수(파라미터로 넘겨진 변수가 아닌 외부에서 정의된 변수)를 활용할 수 있다.
   이와 같은 동작을 람다 캡처링이라고 부른다.
   
3.6.1 요약
 > 명시적으로 메서드명을 참조함으로써 가독성을 높일 수 있다.
   메서드 참조는 어떻게 활용할까?
 > Apple::getWeight는 Apple 클래스에 정의된 getWeight 메서드 참조.
   실제로 메서드를 호출하는 것은 아니므로 괄호는 필요 없음!!
   (Apple a) -> a.getWeight()를 축약한 것.
   
3.7.2 2단계 : 익명 클래스 사용
 > inventory.sort(new Comparator<Apple>() {
       public int compare(Apple a1, Apple a2) {
           return a1.getWeight().compareTo(a2.getWeight()));
       }
   });
   
Comparator의 함수 디스크립터는 (T, T) -> int다.
(Apple, Apple) -> int로 표현할 수 있다.
inventory.sort((Apple a1, Apple a2) ->
                a1.getWeight().compareTo(a2.getWeight()));

람다의 파라미터 형식을 추론
inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));

가독성 향상
Comparator<Apple> c = Comparator.comparing((Apple a) -> a.getWeight());

간소화
import static java.util.Compartor.comparing;
inventory.sort(comparing(apple -> apple. getWeight()));

3.8.1 역정렬
무게를 내림차순으로 정렬
 > inventory.sort((comparing(Apple::getWeight).reversed());
 
무게가 같다면 두 번째 Comparator를 만들 수 있다.
 > inventory.sort((comparing(Apple::getWeight)
            .reversed() -----무게를 내림차순으로 정렬
            .thenComparing(Apple::getCountry)); -----무게가 같으면 국가별로 정렬
          
3.8.2 Predicate 조합
Predicate 인터페이스는 복잡한 프레디케이트를 만들 수 있도록 negate, and, or 세 가지 메서드를 제공한다
> Predicate(Apple) notRedApple = redApple.negate(); -----빨간색이 아닌 사과
> Predicate(Apple) redAndHeavyApple =
  redApple.and(apple -> apple.getWeight() > 150); ----- 빨간색이면서 무거운 사과
> Predicate(Apple) redAndHeavyAppleOrGreen = 
      redApple.and(apple -> apple.getWeight() > 150)
              .or(apple -> GREEN.equals(a.getColor())); -----빨간색이면서 무거운(150그램 이상) 사과 또는 그냥 녹색 사과
단순한 람다 표현식을 조합해서 더 복잡한 람다 표현식을 만들 수 있다.
람다 표현식을 조합해도 코드 자체가 문제를 잘 설명한다.

3.8.3 Function 조합
andThen 메서드는 주어진 함수를 먼저 적용한 결과를 다른 함수의 입력으로 전달하는 함수를 반환.
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.andThen(g);
int result = h.apply(1); -----4를 반환
1+1 = 2 후 2 * 2 = 4

compse 메서드는 인수로 주어진 함수를 먼저 실행한 다음에 그 결과를 외부 함수의 인수로 제공
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.compose(g);
int result = h.apply(1); -----3을 반환
1 * 2 = 2 후에 2 + 1 = 3

3.10 마치며
 > 람다 표현식은 익명 함수의 일종. 이름은 없지만, 파라미터 리스트, 바디, 반환 형식을 가지며 예외를 던질 수 있다.
 > 함수형 인터페이스는 하나의 추상 메서드만을 정의하는 인터페이스다.
 > 함수형 인터페이스를 기대하는 곳에서만 람다 표현식을 사용할 수 있다.
 > 람다 표현식을 이용해서 함수형 인터페이스의 추상 메서드를 즉석으로 제공할 수 있으며
   람다 표현식 전체가 함수형 인터페이스의 인스턴스로 취급된다.
 > 람다 표현식의 기대 형식을 대상 형식이라고 한다.
