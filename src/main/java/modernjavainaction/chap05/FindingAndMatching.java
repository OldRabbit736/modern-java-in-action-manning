package modernjavainaction.chap05;

import modernjavainaction.chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FindingAndMatching {

    public static void main(String[] args) {
        FindingAndMatching.anyMatch();
        FindingAndMatching.allMatch();
        FindingAndMatching.noneMatch();
        FindingAndMatching.findAny();
        FindingAndMatching.findFirst();
    }


    public static void anyMatch() {
        boolean b = Dish.menu.stream().anyMatch(Dish::isVegetarian);
        String result = b ? "They have one or more vegetarian dishes" : "No vegetarian dish";
        System.out.println(result);
    }

    public static void allMatch() {
        boolean isHealthy = Dish.menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        String result = isHealthy ? "The menu is healthy!" : "The menu have some unhealthy dishes";
        System.out.println(result);
    }

    public static void noneMatch() {
        boolean isHealthy = Dish.menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);
        String result = isHealthy ? "The menu is healthy!" : "The menu have some unhealthy dishes";
        System.out.println(result);
    }

    public static void findAny() {
        Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(dish -> System.out.println(dish.getName()));

//        System.out.println(any.isPresent()? "There is at least one vegetarian dish": "No vegetarian dish");
    }

    public static void findFirst() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
                .map(i -> i * i)
                .filter(i -> i % 3 == 0)
                .findFirst();

        System.out.println(firstSquareDivisibleByThree);
    }
}
