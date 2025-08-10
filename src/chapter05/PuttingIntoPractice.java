package chapter05;

import java.util.*;
import java.util.stream.Collectors;

public class PuttingIntoPractice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 질의 1: 2011년에 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값).
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted((t1, t2) -> t1.getValue() - t2.getValue())
                .toList()
                .forEach(System.out::println);
        System.out.println("------");
        // 질의 2: 거래자가 근무하는 모든 고유 도시는?
        transactions.stream()
                .map(transaction ->  transaction.getTrader().getCity())
                .distinct()
                .toList()
                .forEach(System.out::println);
        System.out.println("------");
        // 질의 3: Cambridge의 모든 거래자를 찾아 이름으로 정렬.
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getTrader().getName())
                .sorted(String::compareTo)
                .distinct()
                .toList()
                .forEach(System.out::println);
        System.out.println("-------");
        // 질의 4: 알파벳 순으로 정렬된 모든 거래자의 이름 문자열을 반환
        String allTraderNames = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted(String::compareTo)
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(allTraderNames);

        System.out.println("--------");
        // 질의 5: Milan에 거주하는 거래자가 있는가?
        boolean isMillanTraderExist = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(isMillanTraderExist);
        System.out.println("---------");
        // 질의 6: Cambridge에 사는 거래자의 모든 거래내역 출력.
        List<Transaction> cambridgeTransactions = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.toList());

        System.out.println(cambridgeTransactions);
        System.out.println("------------");

        // 질의 7: 모든 거래에서 최고값은 얼마인가?
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .max(Comparator.comparing(Integer::intValue));
        System.out.println(maxValue.get());
        System.out.println("--------");
        // 가장 작은 값을 가진 거래 탐색
        Optional<Integer> minValue = transactions.stream()
                .map(Transaction::getValue)
                .min(Comparator.comparing(Integer::intValue));
        System.out.println(minValue.get());

    }

}
