package modernjavainaction.chap06;

import modernjavainaction.chap04.Dish;

import static java.util.Comparator.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class Subgroups {
    public static void main(String[] args) {
        Subgroups.groupingBy1();
        Subgroups.groupingBy2();
        Subgroups.groupingBy3();
        Subgroups.groupingBy4();
    }

    static void groupingBy1() {
        Map<Dish.Type, Long> typesCount = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, counting()));

        System.out.println(typesCount);
    }

    static void groupingBy2() {
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(comparingInt(Dish::getCalories))));
        // if there’s no Dish in the menu for a given type,
        // that type won’t have an Optional.empty() as value;
        // it won’t be present at all as a key in the Map.

        System.out.println(mostCaloricByType);
    }

    static void adapt() {
        Map<Dish.Type, Dish> mostCaloricByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)),
                                Optional::get
                        )
                ));

        System.out.println(mostCaloricByType);
    }

    static void toMap1() {
        Map<Dish.Type, Dish> mostCaloricByType = Dish.menu.stream()
                .collect(toMap(Dish::getType, Function.identity(), BinaryOperator.maxBy(comparingInt(Dish::getCalories))));

        System.out.println(mostCaloricByType);
    }

    static void groupingBy3() {
        Map<Dish.Type, Integer> totalCaloriesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));

        System.out.println(totalCaloriesByType);
    }

    static void groupingBy4() {
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, mapping(dish -> {
                                    int calories = dish.getCalories();
                                    if (calories <= 400) return CaloricLevel.DIET;
                                    if (calories <= 700) return CaloricLevel.NORMAL;
                                    return CaloricLevel.FAT;
                                },
//                              toSet()
                                toCollection(HashSet::new)  // to have more control
                        )
                ));

        System.out.println(caloricLevelsByType);
    }

}
