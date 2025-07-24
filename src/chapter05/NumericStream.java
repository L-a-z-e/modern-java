package chapter05;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStream {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        Arrays.stream(numbers.toArray()).forEach(System.out::println);

        int calories = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("calories :" + calories);

        OptionalInt maxCalorie = Dish.menu.stream().mapToInt(Dish::getCalories).max();
        System.out.println("maxCalorie :" + maxCalorie);

        int max;
        if (maxCalorie.isPresent()) {
            max = maxCalorie.getAsInt();
        } else {
            max = 1;
        }

        System.out.println(max);

        IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0).forEach(System.out::println);

        IntStream intStream = IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0);
        System.out.println(intStream.count());

        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        System.out.println(" ---- ");
        Stream<int[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0))
                .map(array -> Arrays.stream(array).mapToInt(a -> (int) a).toArray());

        pythagoreanTriples2.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

    }
}
