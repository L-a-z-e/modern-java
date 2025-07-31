package chapter08;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class CreatingCollectoins {

    public static void main(String[] args) {
        creatingLists();
        creatingSets();
        creatingMaps();
    }

    private static void creatingLists() {
        System.out.println("Creating lists...");

        System.out.println("---> Creating a list the old-fashioned way");
        List<String> friends = new ArrayList<>();
        friends.add("Bob");
        friends.add("Alice");
        friends.add("Jake");
        System.out.println(friends);

        System.out.println("---> Using Arrays.asList()");
        List<String> friends2 = Arrays.asList("Bob", "Alice", "Jake");
        friends2.set(0, "Richard");
        System.out.println(friends2);
        try {
            friends2.add("Laze");
        } catch (UnsupportedOperationException e) {
            System.out.println("As expected, can't add items to a list created with Arrays.asList()");
        }

        System.out.println("---> Creating a Set from a List");
        Set<String> friends3 = new HashSet<>(Arrays.asList("Bob", "Alice", "Jake"));
        System.out.println(friends3);

        System.out.println("---> Creating a Set from a Stream");
        Set<String> friends4 = Stream.of("Bob", "Alice", "Jake")
                .collect(Collectors.toSet());
        System.out.println(friends4);

        System.out.println("---> Creating a List with List.of()");
        List<String> friends5 = List.of("Bob", "Alice", "Jake");
        System.out.println(friends5);
        try {
            friends5.add("Laze");
        } catch (UnsupportedOperationException e) {
            System.out.println("As expected, can't add items to a list created with List.of()");
        }
        try {
            friends5.set(1, "Richard");
        } catch (UnsupportedOperationException e) {
            System.out.println("As expected, can't replace items in such a list");
        }
    }

    private static void creatingSets() {
        System.out.println("Creating Sets...");

        System.out.println("---> Creating a Set with Set.of()");
        Set<String> friends = Set.of("Bob", "Alice", "Jake");
        System.out.println(friends);

        System.out.println("---> Trying to pass duplicate items to Set.of()");
        try {
            Set<String> friends2 = Set.of("Bob", "Alice", "Alice");
            System.out.println("we shouldn't get here");
        } catch (IllegalArgumentException e) {
            System.out.println("As expected, duplicate items are not allowed with Set.of()");
        }
    }

    private static void creatingMaps() {
        System.out.println("---> Creating a Map with Map.of()");
        Map<String, Integer> ageOfFriends = Map.of("Bob", 30, "Alice", 25, "Jake", 35);
        System.out.println(ageOfFriends);

        System.out.println("---> Creating a Map with Map.ofEntries()");
        Map<String, Integer> ageOfFriends2 = Map.ofEntries(
                entry("Bob", 30),
                entry("Alice", 25),
                entry("Jake", 35)
        );
        System.out.println(ageOfFriends2);

    }
}
