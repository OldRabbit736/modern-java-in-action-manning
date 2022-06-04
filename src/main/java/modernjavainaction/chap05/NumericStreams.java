package modernjavainaction.chap05;

import modernjavainaction.chap04.Dish;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStreams {
    public static void main(String[] args) {
        NumericStreams.calories();
        NumericStreams.optional();
        NumericStreams.range();
        NumericStreams.printPythagoreanTriples();
        NumericStreams.printBetterPythagoreanTriples();
    }

    static void calories() {
        int sum = Dish.menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        System.out.println(sum);
    }

    static void boxed() {
        IntStream intStream = Dish.menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();
    }

    static void optional() {
        OptionalInt max = Dish.menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        max.ifPresent(value -> System.out.println("maximum calories is " + value));
    }

    static void range() {
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)   // inclusive
                .filter(n -> n % 2 == 0);   // no computation (no terminal operation)

        IntStream even = IntStream.range(1, 100)    // exclusive
                .filter(n -> n % 2 == 0);

        System.out.println(Arrays.toString(evenNumbers.toArray())); // computation
        System.out.println(Arrays.toString(even.toArray()));
    }

    static void printPythagoreanTriples() {
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );

        pythagoreanTriples.limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    static void printBetterPythagoreanTriples() {
        Stream<double[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0)
                );

        pythagoreanTriples.limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }
}
