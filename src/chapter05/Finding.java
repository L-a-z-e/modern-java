package chapter05;

import chapter04.Dish;

import java.util.Optional;

public class Finding {
    public static void main(String[] args) {
        if (isVegetarianFriendlyMenu()) {
            System.out.println("Vegetarian friendly");
        }

        System.out.println(isHealthyMenu());
        System.out.println(isHealthyMenu2());

        Optional<Dish> dish = findVegetarianDish();
        dish.ifPresent(d -> System.out.println(d.getName()));
    }

    private static Optional<Dish> findVegetarianDish() {
        return Dish.menu.stream().filter(Dish::isVegetarian).findAny();
    }

    private static boolean isHealthyMenu2() {
        return Dish.menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);
    }

    private static boolean isHealthyMenu() {
        return Dish.menu.stream().allMatch(dish -> dish.getCalories() < 1000);
    }

    private static boolean isVegetarianFriendlyMenu() {
        return Dish.menu.stream().anyMatch(Dish::isVegetarian);
    }
}
