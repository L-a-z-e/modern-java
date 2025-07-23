package chapter04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamBasic {
    public static void main(String[] args) {
        getLowCaloriesDishesNamesInJava7(Dish.menu).forEach(System.out::println);
        System.out.println("---");
        getLowCaloriesDishesNamesInJava8(Dish.menu).forEach(System.out::println);
    }

    private static List<String> getLowCaloriesDishesNamesInJava8(List<Dish> menu) {
        return menu.stream().filter(dish -> dish.getCalories() < 300).map(Dish::getName).collect(Collectors.toList());
    }

    private static List<String> getLowCaloriesDishesNamesInJava7(List<Dish> menu) {
        List<String> result = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 300) {
                result.add(dish.getName());
            }
        }
        return result;
    }

}
