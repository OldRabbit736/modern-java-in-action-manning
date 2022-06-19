package modernjavainaction.chap08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkingWithMap {

    static Map<String, String> favoriteMovies = Map.ofEntries(
            Map.entry("Raphael", "Star Wars"),
            Map.entry("Christina", "Matrix"),
            Map.entry("Olivia", "James Bond")
    );

    public static void main(String[] args) {
        WorkingWithMap.forEach();
        WorkingWithMap.sort();
        WorkingWithMap.getOrDefault();
        WorkingWithMap.computeIfAbsent();
        WorkingWithMap.remove();
        WorkingWithMap.replacement();
        WorkingWithMap.putAll();
        WorkingWithMap.merge1();
        WorkingWithMap.merge2();
        WorkingWithMap.quiz82();
    }

    static void forEach() {
        Map<String, Integer> friends = Map.of("Olivia", 24, "Patrick", 30);

        friends.forEach((name, age) -> System.out.println(name + " is " + age + " years old"));
    }

    static void sort() {
        favoriteMovies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);
    }

    static void getOrDefault() {
        String oliviaMovie = favoriteMovies.getOrDefault("Olivia", "Matrix");
        String jsMovie = favoriteMovies.getOrDefault("JS", "The Lord of The Rings");
        System.out.println(oliviaMovie);
        System.out.println(jsMovie);
    }

    static void computeIfAbsent() {
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        friendsToMovies.computeIfAbsent("Raphael", name -> new ArrayList<>()).add("Star Wars");
        System.out.println(friendsToMovies);
    }

    static void remove() {
        Map<String, String> favoriteMovie = new HashMap<>();
        favoriteMovie.put("Raphael", "Before sunset");
        favoriteMovie.put("Jane", null);

        System.out.println("before : " + favoriteMovie);

        favoriteMovie.remove("Raphael", "Before sunset");
        favoriteMovie.remove("Jane", null);

        System.out.println("after : " + favoriteMovie);
    }

    static void replacement() {
        Map<String, String> favoriteMovies = new HashMap<>();
        favoriteMovies.put("Raphael", "Star Wars");
        favoriteMovies.put("Olivia", "james bond");
        favoriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
        System.out.println(favoriteMovies);
    }

    static void putAll() {
        Map<String, String> family = Map.ofEntries(
                Map.entry("Teo", "Star Wars"), Map.entry("Christina", "James Bond")
        );
        Map<String, String> friends = Map.ofEntries(
                Map.entry("Raphael", "Star Wars")
        );

        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends);

        System.out.println(everyone);
    }

    static void merge1() {
        Map<String, String> family = Map.ofEntries(
                Map.entry("Teo", "Star Wars"), Map.entry("Cristina", "James Bond"));
        Map<String, String> friends = Map.ofEntries(
                Map.entry("Raphael", "Star Wars"), Map.entry("Cristina", "Matrix"));

        Map<String, String> everyone = new HashMap<>(family);
        friends.forEach((k, v) -> everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
//        friends.forEach((k, v) -> everyone.merge(k, v, (movie1, movie2) -> null));
        System.out.println(everyone);
    }

    static void merge2() {
        Map<String, Long> moviesToCount = new HashMap<>();
        moviesToCount.merge("JamesBond", 1L, (existingCount, incomingCount) -> existingCount + incomingCount);   // initialization to 1
        System.out.println(moviesToCount);
    }

    static void quiz82() {
        Map<String, Integer> movies = new HashMap<>();
        movies.put("JamesBond", 20);
        movies.put("Matrix", 15);
        movies.put("Harry Potter", 5);

        movies.entrySet().removeIf(entry -> entry.getValue() < 10);

        System.out.println(movies);
    }
}
