package youngsik.chapter5.quiz;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Practice {

    public static void main(String[] args) {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brain", "Cambridge");

        List<Transaction> transactions =  Arrays.asList(
            new Transaction(brian , 2011 , 300),
            new Transaction(raoul , 2012 , 1000),
            new Transaction(raoul , 2011 , 400),
            new Transaction(mario , 2012 , 710),
            new Transaction(mario , 2012 , 700),
            new Transaction(alan , 2012 , 950)
        );
        // 1 번 : 2011년 일어난 모든 트랜잭션을 찾아 값을 오름 차순으로 정리하시오.

        List<Transaction> collect = transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

        // 2 번 : 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> LocationList = transactions.stream()
                .map(transaction -> transaction.getTrader())
                .map(trader -> trader.getLocation())
                .distinct()
                .collect(toList());

        List<String> LocationList2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getLocation())
                .distinct()
                .collect(toList());

        Set<String> setLocation = transactions.stream()
                .map(transaction -> transaction.getTrader().getLocation())
                .collect(toSet());


        // 3번 : 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> traderNames =
                transactions.stream()
                        .map(transaction -> transaction.getTrader())
                        .filter(trader -> "Cambridge" == trader.getLocation())
                        .sorted(comparing(Trader::getName))
                        .collect(toList());

        List<Trader> traderNames2 =
                transactions.stream()
                        .map(transaction -> transaction.getTrader())
                        .filter(trader -> "Cambridge".equals(trader.getLocation()))
                        .sorted(comparing(Trader::getName))
                        .collect(toList());


        // 4번 모든 거래자의 이름을 알파벳순으로 정렬해서 반환
        List<Trader> Names =
                transactions.stream()
                        .map(transaction -> transaction.getTrader())
                        .sorted(comparing(Trader::getName))
                        .collect(toList());

        String traderStr =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                        .distinct()
                        .sorted()
                        .reduce("" , (n1 , n2) -> n1 + n2);

        // 5번 밀라노에 거래자가 있는가?
        boolean hasMilanTrader =
                transactions.stream()
                        .map(transaction -> transaction.getTrader())
                        .anyMatch(trader -> "Milan".equals(trader.getLocation()));

        boolean hasMilanTrader2 =
                transactions.stream()
                        .anyMatch(transaction -> transaction.getTrader().getLocation().equals("Milan"));

        // 6번 케임브리지에 거주하는 거래자의 모든 트랜젹션 값을 출력
                transactions.stream()
                        .filter(transaction -> "Cambridge".equals(transaction.getTrader().getLocation()))
                        .map(Transaction::getValue)
                        .forEach(System.out::println);

        // 7번 전체 트랜잭션 중 최댓값은 얼마인가?

        Optional<Integer> maxValue =
                transactions.stream()
                    .map(Transaction::getValue)
                    .reduce(Integer::max);

        // 8번 전체 트랜젹선 중 최솟값은 얼마인가?

        Integer minValue =
                transactions.stream()
                    .map(Transaction::getValue)
                    .reduce(0, Integer::min);
    }
}
