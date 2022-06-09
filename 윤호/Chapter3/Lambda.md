### chapter3

## 람다란 무엇인가?
-메서드로 전달할 수 있는 익명 함수를 단순화한 것
	익명 : 보통의 메서드와 달리 이름이 없음.
	함수 : 특정 클래스에 종속되지 않음, 
		  메서드처럼 파라미터 리스트,바디,반환형식,가능한 예외 리스트를 포함
	전달 : 람다 표현식을 메서드 인수로 전달하거나 변수로 저장가능
	간결성 : 익명 클래스만큼 많은 코드 사용x

-람다 표현식 세부 내용

'''java

	(Apple a1,Apple a2)     -> 	  a1.getWeight().compareTo(a2.getWeight());
		람다 파라미터		   화살표					람다 바디



-람다 파라미터 : Comparator의 compare 메서드 파라미터
 화살표 : 람다의 파라미터 리스트와 바디 구분
 람다 바디 : 람다의 반환값에 해당하는 표현식
 
 -람다 기본 문법

 	(parameters) -> expression
 	(parameters) -> { statements; }
 	

 
# 3.2.1 함수형 인터페이스
-정확히 하나의 추상 메서드를 지정하는 인터페이스



	 public interface Predicate<T>{ 만약 여기서 extends 로 상속받을 경우, 함수형 인터페이스 아님.
		boolean test (T t);	
	}
	

	
-전체 표현식을 함수형 인터페이스의 인스턴스로 취급
	
# 3.2.1 함수 디스크립터
-시그니처(signature) 는 람다 표현식의 시그니처를 가리킴
-표현식의 시그니처를 서술하는 메서드를 <함수 디스크립터> 라고 부른다



	public void process(Runnable r){
		r.run();
	}



	process(() -> System.out.println("awesome"));


void 반환하는 람다 표현식, Runnable인터페이스의 run 메서드 시그니처와 같다.
 	
@FunctionalInterface 
함수형 인터페이스임을 가리키는 어노테이션으로 추상메서드 가 한개 이상이면 컴파일러에서 에러 발생시킴

## 람다 실행 어라운드 패턴
-실행 어라운드 패턴 : 실제 자원을 처리하는 코드를 준비코드와 마무리코드 두 과정이 둘러싸는 형태를 가짐.

# 실행 어라운드 패턴을 적용하는 4단계 과정
1.동작 파라미터화를 기억



	public String processFile() throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
			return br.readLine();
		}
	}
	


2.함수형 인터페이스를 이용해서 동작 전달



	public interface BufferedReaderProcess{
		String process(BufferedReader b) throws IOException;
	}
	
	public String processFile(BufferedReaderProcess p)throws IOException{
		...
	}
	


3.동작 실행



	public String processFile(BufferedReaderProcess p) throws IOException{
		try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
			return p.process(br);
		}
	}
	

	
4.람다 전달 



	String oneLine = processFile((BufferedReader br) -> br.readLine());
	String twoLines = processFile((BufferedReader br) -> br.readLine + br.readLine());
	

# 함수형 인터페이스
함수 디스크립터:함수형 인터페이스의 추상 메서드 시그니처
-다양한 람다 표현식을 사용하려면 공통의 [함수 디스크립터]를 기술하는 [함수형 인터페이스] 집합 필요.

>Predicate
-test라는 추상 메서드를 정의하며
-test는 제네릭 형식 T 의 객체를 인수로 받아 불리언 반환


	
	@FunctionalInterface
	public interface Predicate<T>{
		boolean test(T t);
	}
	public <T> List<T> filter(List<T> list, Predicate<T> p){
		List<T> results = new ArrayList<>();
		for(T t: list){
			if(p.test(t)){
				results.add(t);
			}
		}
		return results;
	}
		
	Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
	List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
	


>Consumer
-accept라는 추상메서드를 정의하며
-제네릭 형식 T 객체를 받아서 void를 반환


	
	@FunctionalInterface
	public interface Consumer<T> {
		void accept(T t);
	}
	public <T> void forEach(List<T> list, Consumer<T> c){
		for(T t: list){
			c.accept(t);
		}
	}
	forEach(
		Arrays.asList(1,2,3,4,5),
		(Integer i) -> System.out.println(i) --Consumer 의 accept 메서드를 구현하는 람다
	);


>Function
-apply라는 추상메서드를 정의하며
-제네릭 형식 T 객체를 받아서 제네릭 형식 R 객체를 반환


	
	@FunctionalInterface
	public interface Function<T, R> {
		R apply(T t);
	}
	public <T, R> List<R> map(List<T> list, Function<T, R> f){
		List<R> result = new ArrayList<>();
		for(T t: list){
			result.add(f.apply(t));
		}
		return result;
	}
	List<Integer> l = map(
		Arrays.asList("lambdas", "in", "action"),
		(String s) -> s.length() --Function 의 apply 메서드를 구현하는 람다
	);


# 메서드 참조
	
	람다
	(Apple apple) -> apple.getWeight()
	메서드 참조	
	(Apple :: getWeight)
 

-메서드 참조는 가독성을 높일 수 있다.
-메서드 참조의 세가지 유형
	1.정적 메서드 참조
		Integer 의 parseInt 메서드 --> Integer::parseInt
	2.다양한 형식의 인스턴스 메서드 참조
		String 의 length 메서드 --> String::length
	3.기존 객체의 인스턴스 메서드 참조
		getValue 메서드 있는 Transaction 의 객체를 할당받은 expensiveTransaction 지역변수 --> expensiveTransaction::getValue

	List에 포함된 문자열 정렬 프로그램
	List<String> str = Arrays.asList("a","b","A","B");
	람다		str.sort((s1,s2) -> s1.compareToIgnoreCase(s2));
	메서드참조	str.sort(String::compareToIgnoreCase);
	
	
# 생성자 참조 와 메서드 참조 예제 코드

**생성자 참조(인스턴스를 생성하는 경우)**
 
	public static void main(){
		List<String> memberNameList = List.of("A","B","C");
		List<Member> memberList = memberNameList.stream()
										.map(name -> new Member(name)).collect(Collectors.toList());
		memberList.forEach(member -> Sysout(member.getName()));								
	}
	
	public static class Member{
		private String name;
		
		public Member(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
-상위 코드에서 memberList를 초기화 할 때 map메서드에서 람다식을 사용.
	이 때, map에 포함되는 요소들은 String 객체지만, Member 클래스의 인스턴스를 만들 때 인자로 들어감
	
	//일반 람다식
	List<Member> memberList = memberNameList.stream().map(name -> new Member(name)).collect(Collectors.toList());
	//메서드 참조 표현식
	List<Member> memberList = memberNameList.stream().map(Member::new).collect(Collectors.toList());
	
	//일반 람다식
	(인자로 들어갈 객체 -> new 클래스명(인자로 들어갈 객체))
	//메서드 참조 표현식
	(클래스명::new)
	
	
	
**메서드 참조(인스턴스의 메서드를 참조하는 경우)**

	public static void main(){
		List<Member> memberList = Arrays.asList(new Member("A"),new Member("B"),new Member("C"));
		String collect = memberList.stream().map(element -> element.getName()).collect(Collectors.joining());
		Sysout(collect);	
									
	}
	
	public static class Member{
		private String name;
		
		public Member(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
-상위 코드에서 map에 포함되는 요소들은 Member인스턴스 그리고 map내부에서 인스턴스의 메서드인 getName()호출.
	람다 표현식에서 element가 두번씩 표현되는걸 방지
	
	//일반 람다식
	String collect = memberList.stream().map(element -> element.getName()).collect(Collectors.joining());
	//메서드 참조 표현식
	String collect = memberList.stream().map(Member::getName).collect(Collectors.joining());
	
	//일반 람다식
	(인스턴스 -> 인스턴스.메서드명)
	//메서드 참조 표현식
	(인스턴스의 클래스명::메서드명)
	
'''



