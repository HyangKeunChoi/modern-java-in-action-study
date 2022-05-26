1.1
- 자바8은 간결한 코드, 멀티코어 프로세서의 쉬운 활용이라는 두 가지 요구사항을 기반으로 한다.
- 병렬 연산을 지원하는 스트림API 제공
- 스트림을 이용하면 에러를 자주 일으킴, 멀티코어 CPU를 이용하는 것보다 비용이 훨씬 비싼 키워드 synchronized를 사용하지 않아도 된다.

1.2.2
- 스트림이란 한 번에 한 개씩 만들어지는 연속적인 데이터 항목들의 모임이다.

1.2.3
- 자바 8에서는 메서드를 다른 메서드의 인수로 넘겨주는 기능을 제공한다. 이를 동작 파라미터화 라고 부른다.

1.3.2
- 특정 항목을 선택해서 반환하는 동작을 필터라고 한다.

1.4
- for-each 루프를 이용하는 방식을 외부 반복이라고 한다.
- 스트림 API에서는 라이브러리 내부에서 모든 데이터가 처리된다. 이 것을 내부 반복이라고 한다.

1.4.1
- 스트림과 람다 표현식을 이용하면 '병렬성을 공짜로' 얻을 수 있다.
- 순차 처리 방식
import static java.util.stream.Collectors.toList;
List<Apple> heavyApples =
	inventory.stream().filter((Apple) -> a.getWeight() > 150)
		          .collect(toList());
- 병렬 처리 방식
import static java.util.stream.Collectors.toList;
List<Apple> heavyApples =
	inventory.parallelStream().filter((Apple) -> a.getWeight() > 150)
			        .collect(toList());

1.7
- 자바 8은 프로그램을 더 효과적이고 간결하게 구현할 수 있는 새로운 개념과 기능을 제공한다.
- 함수는 일급값이다. 메서드를 어떻게 함수형값으로 넘겨주는지, 익명 함수(람다)를 어떻게 구현하는지 기억하자.
