package modernjavainaction.chap06;

import modernjavainaction.chap04.Dish;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReducingAndSummarizing {
    public static void main(String[] args) {
        ReducingAndSummarizing.counting();
        ReducingAndSummarizing.minMax();
        ReducingAndSummarizing.summarization();
        ReducingAndSummarizing.summarizingInt();
        ReducingAndSummarizing.joiningStrings();
        ReducingAndSummarizing.generalSummarizationWithReduction();
    }

    static void counting() {
        long howManyDishes = Dish.menu.stream()
                .collect(Collectors.counting());

        System.out.println(howManyDishes);
    }

    static void minMax() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

        Optional<Dish> mostCalorieDish = Dish.menu.stream()
                .collect(Collectors.maxBy(dishCaloriesComparator));

        Optional<Dish> leastCalorieDish = Dish.menu.stream()
                .collect(Collectors.minBy(dishCaloriesComparator));

        mostCalorieDish.ifPresent(dish -> System.out.println(dish.getCalories()));
        leastCalorieDish.ifPresent(dish -> System.out.println(dish.getCalories()));
    }

    static void summarization() {
        int totalCalories = Dish.menu.stream()
                .collect(Collectors.summingInt(Dish::getCalories));

        double avgCalories = Dish.menu.stream()
                .collect(Collectors.averagingInt(Dish::getCalories));

        System.out.println("total calories: " + totalCalories);
        System.out.println("avg calories: " + avgCalories);
    }

    static void summarizingInt() {
        IntSummaryStatistics collect = Dish.menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));

        System.out.println(collect);
    }

    static void joiningStrings() {
        String menu = Dish.menu.stream()
                .map(Dish::getName).collect(Collectors.joining(", "));

        System.out.println(menu);
    }

    static void generalSummarizationWithReduction() {
        Integer totalCalories = Dish.menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));

        Optional<Dish> maxCaloriesDish = Dish.menu.stream()
                .collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        System.out.println("totalCalories: " + totalCalories);
        maxCaloriesDish.ifPresent(System.out::println);
    }
}
