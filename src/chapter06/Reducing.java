package chapter06;

import java.util.stream.Collectors;

public class Reducing {
    public static void main(String[] args) {
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
    }

    private static int calculateTotalCaloriesUsingSum() {
        return Dish.menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
    }

    private static int calculateTotalCaloriesWithoutCollectors() {
        return Dish.menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum)
                .get();
    }

    private static int calculateTotalCaloriesWithMethodReference() {
        return Dish.menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
    }

    private static int calculateTotalCalories() {
        return Dish.menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
    }


}
