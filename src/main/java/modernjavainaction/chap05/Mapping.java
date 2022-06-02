package modernjavainaction.chap05;

import modernjavainaction.chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapping {
    public static void main(String[] args) {
        Mapping.getDishNames();
        Mapping.getNameLengths();
        Mapping.flat();
        Mapping.quiz521();
        Mapping.quiz5223();
    }

    public static void getDishNames() {
        List<String> dishNames = Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());

        System.out.println(dishNames);
    }

    public static void getNameLengths() {
        List<Integer> nameLengths = Dish.menu.stream()
                .map(dish -> dish.getName().length())
//                .map(Dish::getName)
//                .map(String::length)
                .collect(Collectors.toList());

        System.out.println(nameLengths);
    }

    public static void flat() {
        List<String> words = Arrays.asList("Goodbye", "Words");
        List<String> collect = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect);
    }

    public static void quiz521() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(i -> i * i)
                .collect(Collectors.toList());

        System.out.println(squares);
    }

    public static void quiz5223() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);

        List<List<Integer>> pairs = list1.stream()
                .flatMap(i1 -> list2.stream().map(i2 -> Arrays.asList(i1, i2)))
                .collect(Collectors.toList());

        List<List<Integer>> pairsDivisibleBy3 = pairs.stream()
                .filter(arr -> (arr.get(0) + arr.get(1)) % 3 == 0)
                .collect(Collectors.toList());

        System.out.println(pairs);
        System.out.println(pairsDivisibleBy3);
    }

}
