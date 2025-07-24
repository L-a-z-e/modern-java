package chapter05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStream {
    public static void main(String[] args) {
        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty()
        Stream<String> emptyStream = Stream.empty();
        emptyStream.forEach(System.out::println);

        // Arrays.Stream
        int [] numbers = {1, 2, 3, 4, 5};
        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        Stream.iterate(0, n -> n + 3).limit(10).forEach(System.out::println);
        System.out.println("----------");

        // iterate (피보나치)
        Stream.iterate(new int[] {0, 1}, t -> new int[] {t[1], t[0] + t[1]})
                .limit(10)
                .forEach(i -> System.out.printf("{%d , %d}\n", i[0], i[1]));
        System.out.println("----------");

        // generate
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        System.out.println("----------");

        Stream.generate(() -> 1).limit(10).forEach(System.out::println);
        System.out.println("----------");

        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(System.out::println);
        System.out.println("----------");

        IntSupplier fibonacci = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int next = previous + current;
                previous = current;
                current = next;
                return previous;
            }
        };
        IntStream.generate(fibonacci).limit(10).forEach(System.out::println);
        System.out.println("----------");
        Path path = Paths.get("resources/chapter05/data.txt");

        try {
            long uniqueWords = Files.lines(path, Charset.defaultCharset())
                    .flatMap(string -> Arrays.stream(string.split("")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
