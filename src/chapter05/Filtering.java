package chapter05;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering {
    public static void main(String[] args) {

        System.out.println("Filtering with Predicate");
        List<Dish> vegetarianMenus = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        vegetarianMenus.forEach(System.out::println);

        System.out.println("Filtering unique elements");
        List<Integer> numList = List.of(1, 2, 3, 4, 4, 5, 5, 10);
        numList.stream().filter(i -> i < 7).distinct().forEach(System.out::println);

        List<Dish> specialMenu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));

        System.out.println("filtering calories < 320");
        specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // takeWhile 처음부터 조건이 처음으로 false가 되는 부분 전까지 유지
        System.out.println("takeWhile calories < 320");
        specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // dropWhile: 처음부터 조건이 true인 항목은 모두 버리고, 조건이 처음 false가 되는 그 지점부터 끝까지 유지
        System.out.println("dropWhile calories < 320");
        specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("limit 3");
        specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .forEach(System.out::println);

        System.out.println("skip 3");
        specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .forEach(System.out::println);
    }
}
