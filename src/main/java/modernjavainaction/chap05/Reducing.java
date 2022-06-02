package modernjavainaction.chap05;

import modernjavainaction.chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Reducing {
    static List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    public static void main(String[] args) {
        Reducing.reduce();
        Reducing.reduceWithoutInitialValue();
        Reducing.minMax();
        Reducing.quiz53();
    }

    public static void reduce() {
        Integer total = numbers.stream()
                .reduce(0, Integer::sum);

        System.out.println(total);
    }

    public static void reduceWithoutInitialValue() {
        List<Integer> numbers = Arrays.asList();
        Optional<Integer> total = numbers.stream()
                .reduce(Integer::sum);

        System.out.println(total);
    }

    public static void minMax() {
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        System.out.println("min : " + min);
        System.out.println("max : " + max);
    }

    public static void quiz53() {
        Integer sum = Dish.menu.stream()
                .map(d -> 1)
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }
}
