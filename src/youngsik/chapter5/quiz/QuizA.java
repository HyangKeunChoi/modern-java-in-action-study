package youngsik.chapter5.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.util.stream.Collectors.*;

public class QuizA {
    public static void main(String[] args) {
        /*
        퀴즈 5-2 매핑
        숫자리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환
         */
        Scanner scanner = new Scanner(System.in);
        List<Integer> intArray = new ArrayList<>();
        while(intArray.size() < 6) {
            intArray.add(scanner.nextInt());
        }
        List<Integer> integerList = intArray.stream().map(integer ->
                integer * integer).collect(toList());

        System.out.println(integerList.toString());
    }
}
