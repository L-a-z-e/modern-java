package chapter06;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class Partitioning {
    public static void main(String[] args) {
        System.out.println("Dishes partitioned by vegetarian: " + partitionByVegeterian());
        System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
        System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
    }

    private static Map<Boolean, Dish> mostCaloricPartitionedByVegetarian() {
        return Dish.menu.stream()
                .collect(
                    partitioningBy(
                            Dish::isVegetarian,
                            collectingAndThen(
                                maxBy(Comparator.comparing(Dish::getCalories)),
                                Optional::get
                            )
                    )
                );
    }

    private static Map<Dish.Type, Map<Boolean,List<Dish>>> vegetarianDishesByType() {
        return Dish.menu.stream()
                .collect(groupingBy(
                        Dish::getType,
                        partitioningBy(
                                Dish::isVegetarian
                        )
                ));
    }

    private static Map<Boolean, List<Dish>> partitionByVegeterian() {
        return Dish.menu.stream()
                .collect(
                        partitioningBy(Dish::isVegetarian)
                );
    }
}
