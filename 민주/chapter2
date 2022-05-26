동작 파라미터화 - 아직은 어떻게 실행할 것인지 결졍하지 않은 코드 블록을 의미한다.

2.3.1
* 익명클래스
  - 익명 클래스는 말 그대로 이름이 없는 클래스다.
  - 익명 클래스를 이용하면 클래스 선언과 인스턴스화를 동시에 할 수 있다.
  - 즉석에서 필요한 구현을 만들어서 사용할 수 있다.
  - 많은 프로그래머가 익명 클래스의 사용에 익숙하지 않다
  
  
2.4.1
람다를 사용하지 않은 일반 코드
inventory.sort(new Comparator<Apple>() {
  public int compare(Apple a1, Apple a2) {
    return a1.getWeight().compareTo(a2.getWeight());
  }
}

람다 표현식
inventory.sort(
  (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
    


Runnable을 이용한 스레드 실행
Thread t = new Thread(new Runnable() {
  public void run() {
    System.out.println("Hello World");
  }
});

람다 표현식
Thread t = new Thread(() -> System.out.println("Hello World"));



Button button = new Button("Send");
button.setOnAction(new EventHandler<ActionEvent>() {
  public void handle(ActionEvent event) {
    label.setText("Sent!!");
  }
)};

EventHandler는 setOnAction 메서드의 동작을 파라미터화한다. 람다 표현식으로 다음처럼 구현할 수 있다.
button.setOnAction((ActionEvent event) -> label.setText("Sent!!"));
new EventHandler를 쓸 필요 없음.



마치며
- 동작 파라미터화에서는 메서드 내부적으로 다양한 동작을 수행할 수 있도록 코드를 메서드 인수로 전달한다.
- 코드 전달 기법을 이용하면 동작을 메서드의 인수로 전달할 수 있다.
- 자바 API의 많은 메서드는 정렬, 스레드, GUI처리 등을 포함한 다양한 동작으로 파라미터화할 수 있다.
