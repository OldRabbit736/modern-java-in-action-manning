package modernjavainaction.chap08;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionFactories {

    public static void main(String[] args) {
//        CollectionFactories.arraysAsList();
//        CollectionFactories.listFactory();
        CollectionFactories.setFactory();
        CollectionFactories.mapFactory1();
        CollectionFactories.mapFactory2();
    }

    static void arraysAsList() {
        // fixed-sized list
        List<String> friends = Arrays.asList("Raphael", "Olivia");
        friends.set(0, "Richard");
        // friends.add("Thibaut"); // UnsupportedOperationException
        // friends.remove(0); // UnsupportedOperationException
    }

    static void notElegantSet() {
        Set<String> friends1 = new HashSet<>(Arrays.asList("Raphael", "Olivia"));
        Set<String> friends2 = Stream.of("Raphael", "Olivia").collect(Collectors.toSet());
    }

    static void listFactory() {
        // immutable list
        List<String> friends = List.of("Raphael", "Olivia");
        // friends.add("JS"); // UnsupportedOperationException
        System.out.println(friends);
    }

    static void setFactory() {
        // immutable set
        Set<String> friends = Set.of("Raphael", "Olivia");
        // friends.add("JS"); // UnsupportedOperationException
        System.out.println(friends);
    }

    static void mapFactory1() {
        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25);
        System.out.println(ageOfFriends);
    }

    static void mapFactory2() {
        Map<String, Integer> ageOfFriends = Map.ofEntries(
                Map.entry("Raphael", 30),
                Map.entry("Olivia", 25)
        );
        System.out.println(ageOfFriends);
    }
}
