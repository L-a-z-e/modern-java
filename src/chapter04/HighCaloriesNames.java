package chapter04;

import java.util.List;
import java.util.stream.Collectors;

import static chapter04.Dish.menu;

public class HighCaloriesNames {
    public static void main(String[] args) {
        List<String> highCaloriesNames = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering: " + dish);
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("map: " + dish);
                    return dish.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
    }
}
