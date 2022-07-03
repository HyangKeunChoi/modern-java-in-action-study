package src.hyangkeun;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Chapter5_example {
    public static void main(String[] args) {

        List<String> words = Arrays.asList("Modern", "Java", "In", "action");

        // 해결되지 않음
        List<Stream<String>> collect = words.stream().map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());

        // flatmap 으로 해결
        List<String> collect1 = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        for (String s : collect1) {
            System.out.print(s);
        }


    }
}
