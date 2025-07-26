package chapter06;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class Grouping {
    enum CaloricLevel { DIET, NORMAL, FAT };

    public static void main(String[] args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOptionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> caloricLevelsByType() {
        return Dish.menu.stream()
                .collect(
                        groupingBy(
                                Dish::getType,
                                groupingBy(
                                        dish -> {
                                            if (dish.getCalories() < 400)
                                                return CaloricLevel.DIET;
                                            else if (dish.getCalories() < 700)
                                                return  CaloricLevel.NORMAL;
                                            else
                                                return CaloricLevel.FAT;
                                        },
                                        toList()
                                )
                        )
                );
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return Dish.menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        summingInt(Dish::getCalories)
                        )
                );
    }

    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
        return Dish.menu.stream().collect(
                groupingBy(
                        Dish::getType,
                        collectingAndThen(
                                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                                Optional::get
                        )
                )
        );
    }

    private static Map<Dish.Type, Dish> mostCaloricDishesByType() {
        return Dish.menu.stream()
                .collect(toMap(Dish::getType, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Dish::getCalories)))
                );
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return Dish.menu.stream()
                .collect(groupingBy(Dish::getType, counting()));
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return Dish.menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() < 400)
                                return CaloricLevel.DIET;
                            else if (dish.getCalories() < 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        }, toList()
                        )
                ));
    }

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return Dish.menu.stream()
                .collect(groupingBy(
                        dish -> {
                            if (dish.getCalories() < 400)
                                return CaloricLevel.DIET;
                            else if (dish.getCalories() < 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        }
                ));
    }

    private static  Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        return Dish.menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return Dish.menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish -> Dish.dishTags.get(dish.getName()).stream(), toSet())));
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return Dish.menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        return Dish.menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
    }

}
