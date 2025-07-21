package chapter02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

import static chapter02.Color.GREEN;
import static chapter02.Color.RED;

public class FilteringApples {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(120, Color.RED)
        );

        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println(greenApples);

        List<Apple> redApples = filterApplesByColor(inventory, RED);
        System.out.println(redApples);

        List<Apple> heavyApple = filterApplesByWeight(inventory, 100);
        System.out.println(heavyApple);

        List<Apple> colorApple = filterApples(inventory, GREEN, 0, true);
        System.out.println(colorApple);

        List<Apple> smallApple = filterApples(inventory, null, 50, false);
        System.out.println(smallApple);

        List<Apple> greenPredicateApples = filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println(greenPredicateApples);

        List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples);

        List<Apple> fancyFormattedApple = filterApples(inventory, new AppleHeavyWeightPredicate());
        prettyPrintApple(fancyFormattedApple, new AppleFancyFormatter());

        List<Apple> simpleFormattedApple = filterApples(inventory, new AppleGreenColorPredicate());
        prettyPrintApple(simpleFormattedApple, new AppleSimpleFormatter());

        List<Apple> annonymouseRedApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals(RED);
            }
        });
        prettyPrintApple(annonymouseRedApples, new AppleFancyFormatter());

        List<Apple> lambdaApple = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
        prettyPrintApple(lambdaApple, new AppleFancyFormatter());

        List<Integer> evenNumbers = filter(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), i -> i % 2 == 0);
        System.out.println(evenNumbers);

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello World!");
            }
        });

        t.start();

        Thread t2 = new Thread(() -> System.out.println("lambda Hello World!"));
        t2.start();

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> threadName = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName();
            }
        });

        Future<String> lambdaThreadName = executorService.submit(() -> Thread.currentThread().getName());

        System.out.println(threadName.get());
        System.out.println(lambdaThreadName.get());
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }

        return result;
    }

    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    public static class AppleHeavyWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    public static class AppleGreenColorPredicate implements  ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor().equals(Color.GREEN);
        }
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    public static class AppleRedAndHeavyPredicate implements  ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor().equals(Color.RED) && apple.getWeight() > 150;
        }
    }

    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
        for (Apple apple : inventory) {
            String output = formatter.format(apple);
            System.out.println(output);
        }
    }

    public static class AppleFancyFormatter implements AppleFormatter {
        @Override
        public String format(Apple apple) {
            String weight = apple.getWeight() > 150 ? "heavy" : "light";
            String color = apple.getColor().equals(RED) ? "red" : "green";
            return "A " + weight + " " + color + " apple";
        }
    }

    public static class AppleSimpleFormatter implements AppleFormatter {
        @Override
        public String format(Apple apple) {
            return "it's an apple : " + apple.getWeight();
        }
    }

    public interface AppleFormatter {
        String format(Apple apple);
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T element : list) {
            if(p.test(element)) {
                result.add(element);
            }
        }

        return result;
    }
}
