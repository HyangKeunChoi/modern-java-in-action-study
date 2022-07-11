package youngsik.chapter5.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class QuizB {
     /*
    퀴즈 5-2 매핑
    두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환하시오.

     */
    public static void main(String[] args) {

        Scanner scannerA = new Scanner(System.in);
        List<Integer> intListA = new ArrayList<>();

        while(intListA.size() <3) {
            intListA.add(scannerA.nextInt());
        }
        List<Integer> intListB = new ArrayList<>();
        while(intListB.size() < 2) {
            intListB.add(scannerA.nextInt());
        }


        List<int[]> streamCollect = intListA.stream().flatMap(i -> intListB.stream()
                .map(j -> new int[]{i, j})
        ).collect(toList());

        List<int[]> collect = new ArrayList<>();
        for (Integer i : intListA) {
            for (Integer j : intListB) {
                int[] ints = new int[]{i, j};
                collect.add(ints);
            }
        }

    }
}
