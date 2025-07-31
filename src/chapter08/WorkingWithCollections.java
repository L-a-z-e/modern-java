package chapter08;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class WorkingWithCollections {
    public static void main(String[] args) {
        workingWithLists();
        workingWithMaps();
        computingOnMaps();
        removingFromMaps();
        replacingInMaps();
        mergingMaps();
    }

    private static void workingWithLists() {
        System.out.println("WorkingWithLists()");

        System.out.println("Transforming list items with a Stream");
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");
        referenceCodes.stream()
                .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
                .toList()
                .forEach(System.out::println);
        System.out.println("Original list remains unchanged: " + referenceCodes);

        System.out.println("---> Mutating a list with a ListIterator");
        for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext();) {
            String code = iterator.next();
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }
        System.out.println("Original list changed: " + referenceCodes);

        System.out.println("---> Mutating a list with replaceAll()");
        referenceCodes = Arrays.asList("a12", "C14", "b13");
        referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
        System.out.println("Changed by replaceAll(): " + referenceCodes);
    }

    private static void workingWithMaps() {
        System.out.println("workingWithMaps()");

        System.out.println("---> Iterating a map with a for loop");
        Map<String, Integer> ageOfFriends = Map.of("Bob", 30, "Alice", 25, "Jake", 35);
        for (Map.Entry<String, Integer> entry : ageOfFriends.entrySet()) {
            String friend = entry.getKey();
            Integer age = entry.getValue();
            System.out.println(friend + " is " + age + "years old");
        }
        System.out.println("---> Iterating a map with forEach()");
        ageOfFriends.forEach((k, v) -> System.out.println(k + " is " + v + "years old"));

        System.out.println("---> Iterating a map sorted by keys through a Stream");
        Map<String, String> favoriteMovies = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix"),
                entry("Olivia", "James Bond")
        );
        favoriteMovies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);

        System.out.println("---> Using getOrDefault()");
        System.out.println(favoriteMovies.getOrDefault("Raphael", "Matrix"));
        System.out.println(favoriteMovies.getOrDefault("Alice", "Matrix"));
    }

    private static void computingOnMaps() {
        Map<String, List<String>> friendsToMovies = new HashMap<>();

        System.out.println("---> Adding a friend and movie in a verbose way");
        String friend = "Bob";
        List<String> movies = friendsToMovies.get(friend);
        if (movies == null) {
            movies = new ArrayList<>();
            friendsToMovies.put(friend, movies);
        }
        movies.add("Star Wars");
        System.out.println(friendsToMovies);

        System.out.println("---> Adding a friend and movies using computeIfAbsent()");
        friendsToMovies.clear();
        friendsToMovies.computeIfAbsent("Bob", name -> new  ArrayList<>())
                .add("Star Wars");
        System.out.println(friendsToMovies);
        friendsToMovies.computeIfAbsent("Bob", name -> new  ArrayList<>())
                .add("Matrix");
        System.out.println(friendsToMovies);
    }

    private static void removingFromMaps() {
        Map<String, String> favoriteMovies = new HashMap<>();
        favoriteMovies.put("Raphael", "Jack Reacher 2");
        favoriteMovies.put("Cristina", "Matrix");
        favoriteMovies.put("Olivia", "James Bond");
        String key = "Raphael";
        String value = "Jack Reacher 2";

        System.out.println("---> Removing an unwanted entry the cumbersome way");
        boolean result = remove(favoriteMovies, key, value);
        System.out.printf("%s [%b]%n", favoriteMovies, result);
    }



    private static void replacingInMaps() {
        Map<String, String> favoriteMovies = new HashMap<>();
        favoriteMovies.put("Raphael", "Star Wars");
        favoriteMovies.put("Olivia", "James Bond");

        System.out.println("---> replacing values in a map with replaceAll()");
        favoriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
        System.out.println(favoriteMovies);
    }

    private static void mergingMaps() {
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond")
        );

        Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"));

        System.out.println("---> Merging old way");
        Map<String, String> everyone = new HashMap<>();
        everyone.putAll(family);
        everyone.putAll(friends);
        System.out.println(everyone);

        Map<String, String> friends2 = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix")
        );

        System.out.println("---> Merging maps using merge()");
        Map<String, String> everyone2 = new HashMap<>(family);
        friends2.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println(everyone2);
    }

    private static <K,V> boolean remove(Map<K, V> favoriteMovies, K key, V value) {
        if (favoriteMovies.containsKey(key) && Objects.equals(favoriteMovies.get(key), value)) {
            favoriteMovies.remove(key);
            return true;
        }
        return false;
    }
}
