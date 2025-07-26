package chapter06;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Summarizing {
    public static void main(String[] args) {
        System.out.println("Nr. of dishes: " + howManyDishes());
        System.out.println("The most caloric dish is: " + findMostCaloricDish());
        System.out.println("The most caloric dish is: " + findMostCaloricDishUsingComparator());
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Average calories in menu: " + calculateAverageCalories());
        System.out.println("Menu statistics: " + calculateMenuStatistics());
        System.out.println("Short menu: " + getShortMenu());
        System.out.println("Short menu comma separated: " + getShortMenuCommaSeparated());
    }

    private static String getShortMenuCommaSeparated() {
        return Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(", "));
    }

    private static String getShortMenu() {
        return Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining());
    }

    private static IntSummaryStatistics calculateMenuStatistics() {
        return Dish.menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
    }

    private static Double calculateAverageCalories() {
        return Dish.menu.stream()
                .collect(Collectors.averagingDouble(Dish::getCalories));
    }

    private static Integer calculateTotalCalories() {
        return Dish.menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
    }
    private static Dish findMostCaloricDishUsingComparator() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
        return Dish.menu.stream().collect(Collectors.reducing(moreCaloricOf)).get();
//        return Dish.menu.stream()
//                .max(Comparator.comparing(Dish::getCalories))
//                .orElse(null);
    }

    private static Dish findMostCaloricDish() {
        return Dish.menu.stream()
                .reduce(
                        (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2
                )
                .get();
    }

    private static long howManyDishes() {
//        return Dish.menu.stream().count();
        return Dish.menu.stream().collect(Collectors.counting());
    }

}
