# chapter4

'''java

### 스트림이란?
데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소   
	1.데이터 처리 연산:데이터베이스와 비슷한 연산 지원 EX)filter,map,find,match   
	2.소스:리스트로 스트림을 만들면 리스트의 요소와 같은 순서 유지   
	3.연속된 요소:특정 요소 형식으로 이루어진 연속된 값집합의 인터페이스 제공   
	
*스트림을 이용하면 선언형으로 컬렉션 데이터를 처리할 수 있음   
*멀티스레드 코드를 구현 안해도 데이터를 투명하게 병렬 처리 가능


스트림 예제 코드

	import static java.util.Comparator.comparing;
	import static java.util.stream.collectors.toList;
	List<String> lowCaloricDishesName = 
		menu.stream() ---> menu.parallelStream()으로 바꾸면 멀티코어 아키텍처에서 병렬로 실행 가능
			 .filter(d -> d.getCalories() < 400) -400칼로리 이하의 요리 선택
			 .sorted(comparing(Dish::getCalories)) -칼로리로 요리 정렬
			 .map(Dish::getName) - 요리명 추출
			 .collect(toList()); - 모든 요리명 리스트에 저장
상단의 예제 코드에서 쓰인 filter,sorted,map,collect 가 스트림 파이프라인 이다.

**스트림API의 특징 3가지**   
1.선언형: 더 간결하고 가독성이 좋아짐   
2.조립할 수 있음 : 유연성이 좋아짐   
3.병렬화 : 성능이 좋아짐   

### 스트림 중요 특징 2가지
1.파이프라이닝   
	-대부분의 스트림 연산은 스트림 연산끼리 연결해서 파이프 라인을 구성할 수 있도록 스트림 자신을 반환함   
	 그 덕분에 게으름,쇼트서킷 같은 최적화도 얻을 수 있음.   
	-연산 파이프라인은 데이터 소스에 적용하는 데이터베이스 질의와 비슷함.   
2.내부 반복   
	-반복자를 이용해서 명시적으로 반복하는 컬렉션과 달리,스트림은 내부 반복을 지원함.   

### 스트림과 컬렉션 차이
-데이터를 언제 계산하느냐가 가장 큰 차이.   
	컬렉션:모든 요소는 컬렉션에 추가하기 전에 계산되어야 함.   
	스트림:요청할 떄만 요소를 계산함.

-스트림은 단 한번만 소비할 수 있음.(한번 소비하면 재 소비 불가능)   

	컬렉션:외부반복
	List<String> names = new ArrayList<>();
	for(Dish dish: menu){
		names.add(dish.getName());
	}
	
	스트림:내부반복(반복자 필요 없음)
	List<String> names = menu.stream()
								  .map(Dish::getName)
								  .collect(toList());


**스트림 연산**   
	   중간연산,최종연산 구분   
		   중간연산:다른 스트림을 반환, 단말 연산을 스트림 파이프라인에 실행하기 전까지 아무 연산도 수행 안함.   
		   최종연산:결과를 도출.
		
**스트림 이용 과정 세가지**   
   1.질의를 수행할(컬렉션) 데이터 소스   
   2.스트림 파이프라인을 구성할 중간 연산 연결   
   3.스트림 파이프라인을 실행하고 결과를 만들 최종 연산
	
'''