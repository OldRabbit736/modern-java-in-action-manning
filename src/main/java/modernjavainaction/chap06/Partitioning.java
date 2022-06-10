package modernjavainaction.chap06;

import modernjavainaction.chap04.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Partitioning {
    public static void main(String[] args) {
        Partitioning.partition();
        Partitioning.partitionDownstream();
        Partitioning.partitionDownstream2();
        Partitioning.quiz621();
        Partitioning.quiz623();
        Partitioning.prime();
    }

    static void partition() {
        Map<Boolean, List<Dish>> partitionedMenu = Dish.menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));

        System.out.println(partitionedMenu.get(true));
    }

    static void partitionDownstream() {
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = Dish.menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));

        System.out.println(vegetarianDishesByType);
    }

    static void partitionDownstream2() {
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = Dish.menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)
                ));

        System.out.println(mostCaloricPartitionedByVegetarian);
    }

    static void quiz621() {
        Map<Boolean, Map<Boolean, List<Dish>>> caloricDishesByVegetarian = Dish.menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        partitioningBy(dish -> dish.getCalories() > 500)
                ));

        System.out.println(caloricDishesByVegetarian);
    }

    static void quiz623() {
        Map<Boolean, Long> dishCountByVegetarian = Dish.menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        counting()
                ));

        System.out.println(dishCountByVegetarian);
    }

    static void prime() {
        Map<Boolean, List<Integer>> numberByPrime = IntStream.rangeClosed(2, 50).boxed().collect(
                partitioningBy(Partitioning::isPrime)
        );

        System.out.println(numberByPrime);
    }

    static boolean isPrime(int candidate) {
        return IntStream.range(2, candidate)
                .noneMatch(i -> candidate % i == 0);
    }
}
