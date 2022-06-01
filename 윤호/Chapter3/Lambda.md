### chapter3

##람다란 무엇인가?
-메서드로 전달할 수 있는 익명 함수를 단순화한 것
	익명 : 보통의 메서드와 달리 이름이 없음.
	함수 : 특정 클래스에 종속되지 않음, 
		  메서드처럼 파라미터 리스트,바디,반환형식,가능한 예외 리스트를 포함
	전달 : 람다 표현식을 메서드 인수로 전달하거나 변수로 저장가능
	간결성 : 익명 클래스만큼 많은 코드 사용x

-람다 표현식 세부 내용
(Apple a1,Apple a2)     -> 	  a1.getWeight().compareTo(a2.getWeight());
 	람다 파라미터		   화살표					람다 바디

-람다 파라미터 : Comparator의 compare 메서드 파라미터
 화살표 : 람다의 파라미터 리스트와 바디 구분
 람다 바디 : 람다의 반환값에 해당하는 표현식
 
 -람다 기본 문법
 	(parameters) -> expression
 	(parameters) -> { statements; }
 
#3.2.1 함수형 인터페이스
-정확히 하나의 추상 메서드를 지정하는 인터페이스

ex) public interface Predicate<T>{ 만약 여기서 extends 로 상속받을 경우, 함수형 인터페이스 아님.
		boolean test (T t);	
	}
	
-전체 표현식을 함수형 인터페이스의 인스턴스로 취급
	
#3.2.1 함수 디스크립터
-시그니처(signature) 는 람다 표현식의 시그니처를 가리킴
-표현식의 시그니처를 서술하는 메서드를 <함수 디스크립터> 라고 부른다

public void process(Runnable r){
	r.run();
}

process(() -> System.out.println("awesome"));

void 반환하는 람다 표현식, Runnable인터페이스의 run 메서드 시그니처와 같다.
 	
@FunctionalInterface 
함수형 인터페이스임을 가리키는 어노테이션으로 추상메서드 가 한개 이상이면 컴파일러에서 에러 발생시킴

##람다 실행 어라운드 패턴
-실행 어라운드 패턴 : 실제 자원을 처리하는 코드를 준비코드와 마무리코드 두 과정이 둘러싸는 형태를 가짐.

#실행 어라운드 패턴을 적용하는 4단계 과정
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
#함수형 인터페이스, 형식 추론
#메서드 참조
#람다 만들기





