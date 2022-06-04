### 람다 표현식
3.1 람다란 무엇인가?
* 람다표현식: 메서드로 전달할 수 있는 익명함수를 단순화한 것
  * 이름은 없지만 파라미터 리스트, 바디, 반환형식, 예외 리스트 등을 가질 수 있음.
* 특징
    * 익명: 이름이 없음
    * 함수: 특정 클래스에 종속되지 않음
    * 전달: 람다 표현식을 메섣 인수 전달하거나 변수로 저장
    * 간결성: 익명 클래스에 비해 훨씬 간결한 코드를 구현할 수 있음
    * 동작 파라미터 이용 시 익명 클래스처럼 판에 박힌 코드를 구현할 필요가 없음
* 람다의 구성
  * 파라미터 리스트
  * 함수
  * 람다 바디

3.2 람다 사용 방법 및 상황
* 어디서? 함수형 인터페이스에서 사용
* 함수형 인터페이스?
  * 하나의 추상 메서드르 지정하는 인터페이스 (추상 메서드: 선언부만 작성하고 구현부는 작성하지 않은 메서드)
  * 함수혀 인터페이스를 이용하여 전체 표현식을 함수혀 인터페이스의 인스턴스로 취급이 가능
  * @FunctionallInterface 어노테이션 사용
* 함수 디스크립터
  * 표현식의 시그니처를 서술하는 메서드
  * () -> void: 파라미터 리스트가 없으며 void를 반환하는 함수 ( ()-> {} 랑 동일)

3.3 람다 활용
* 실행 어라운드 패턴
  * 중복되는 준비코드와 정리코드가 둘러싸는 형태
  * 동작 파라미터화 활용
    String result = processFile((BufferedReader br) -> br.realLine() + br.readLine());
  * 함수형 인터페이스를 이용해서 동작 전달
    @FunctionallInterface
    public interface BufferedReaderProcessor {
      String process(BufferedReader b) throws IOException; }
  * 동작 실행
    public String processFile (BufferedReaderProcessor p) throws IOException {
      try( BufferedReader br = new BufferReader ( new FileReader("data.txt"))) {
        return p.process(br); //BufferedReader 객체처리
        }
       }
  * 람다 전달
    String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
 
 3.4 함수형 인터페이스 사용
 * Predicate<T>
    * 제너릭 형식의 T를 인수로 받아 boolean 반환, 따로 정의할 필요 없이 바로 사용할 수 있음
 * Consumer<T>
    *  T
 
