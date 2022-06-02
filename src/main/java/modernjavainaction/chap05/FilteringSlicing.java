package modernjavainaction.chap05;

import modernjavainaction.chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilteringSlicing {

    static List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));

    public static void main(String[] args) {
        FilteringSlicing.skipping();
    }

    public static void isVegetarian() {
        List<Dish> vegetarianMenu = Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        System.out.println(vegetarianMenu);
    }

    public static void uniqueElements() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        List<Integer> collect = numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect);
    }

    public static void takeWhile() {
        List<Dish> sliceMenu = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(sliceMenu);
    }

    public static void dropWhile() {
        List<Dish> sliceMenu = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(sliceMenu);
    }

    public static void skipping() {
        List<Dish> sliceMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() >= 300)
                .skip(2)
                .collect(Collectors.toList());

        System.out.println(sliceMenu);
    }


}
