package src.hyangkeun;

import java.util.function.*;

public class LambdaExpressionSample {
    public static void main(String[] args) {
        // Type T 인자를 받고 boolean을 리턴
        Predicate<Integer> predicate = (num) -> num % 2 == 0;
        System.out.println(predicate.test(100));

        // BiPredicate - 2개의 인자를 받고 boolean을 리턴하는 함수형 인터페이스
        BiPredicate<Integer, Integer> biPredicate = (n1, n2) -> n1 > n2;
        System.out.println(biPredicate.test(100, 200));

        // 1개의 Type T 인자를 받고 void를 리턴
        Consumer<String> consumer = s -> System.out.println(s.toUpperCase());
        Consumer<String> consumer2 = LambdaExpressionSample::accept; // 메소드 레퍼런스 - 정적 메소드 참조
        consumer.accept("Hi");
        consumer2.accept("Hi");

        // 인자를 받지 않고 T 객체를 리턴
        Supplier<String> supplier1 = () -> "123";
        Supplier<Integer> supplier2 = () -> 123;
        System.out.println(supplier1.get());
        System.out.println(supplier2.get());

        // Function은 1개의 인자 T를 받고 1개의 객체 R를 리턴
        Function<Integer, Integer> func = x -> x * 2;
        System.out.println(func.apply(10));

        // UnaryOperator는 T의 인자 하나를 받고, 동일한 타입의 T 객체를 리턴
        UnaryOperator<Integer> unaryOperator = n -> n * 10;
        System.out.println(unaryOperator.apply(20));
    }
    public static void accept(String s) {
        System.out.println(s.toUpperCase());
    }
}
