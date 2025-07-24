package chapter05;

import java.util.Arrays;
import java.util.List;

public class Laziness {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        numbers.stream()
                .filter(i -> {
                            System.out.println("filtering : " + i);
                            return i % 2 == 0;
                        }
                )
                .map(i -> {
                        System.out.println("square : " + i);
                        return i * i;
                    }
                )
                .limit(2)
                .forEach(System.out::println);
    }
}
