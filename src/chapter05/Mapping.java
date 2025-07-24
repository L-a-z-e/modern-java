package chapter05;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapping {
    public static void main(String[] args) {
        List<String> dishList = Dish.menu
                .stream()
                .map(Dish::getName)
                .collect(Collectors.toList());

        System.out.println("dishList");
        dishList.forEach(System.out::println);
        System.out.println("----");

        List<String> words = Arrays.asList("Hello", "World");

        List<Integer> wordsLength = words.stream()
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println("wordsLength");
        System.out.println(wordsLength);
        System.out.println("-----");

        System.out.println("flatMap & distinct");
        words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct()
                .forEach(System.out::println);
        System.out.println("-----");

        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);

        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                .collect(Collectors.toList());

        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));


    }
}
