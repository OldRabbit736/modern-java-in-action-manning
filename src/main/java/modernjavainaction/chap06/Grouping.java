package modernjavainaction.chap06;

import modernjavainaction.chap04.Dish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Grouping {


    public static void main(String[] args) {
        Grouping.groupingBy();
        Grouping.groupingBy2();
        Grouping.groupingByWithDownStreamPattern();
        Grouping.groupingByWithDownStreamPattern2();
        Grouping.groupingByWithDownStreamPattern3();
        Grouping.multilevelGrouping();
    }

    static void groupingBy() {
        Map<Dish.Type, List<Dish>> dishesByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));

        System.out.println(dishesByType);
    }

    static void groupingBy2() {
        Map<CaloricLevel, List<Dish>> collect = Dish.menu.stream()
                .collect(Collectors.groupingBy(dish -> {
                    int calories = dish.getCalories();
                    if (calories <= 400) return CaloricLevel.DIET;
                    if (calories <= 700) return CaloricLevel.NORMAL;
                    return CaloricLevel.FAT;
                }));

        System.out.println(collect);
    }

    static void groupingByWithDownStreamPattern() {
        Map<Dish.Type, List<Dish>> caloricDishesByType =
                Dish.menu.stream()
                        .collect(Collectors.groupingBy(Dish::getType,
                                Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())
                        ));

        System.out.println(caloricDishesByType);
    }

    static void groupingByWithDownStreamPattern2() {
        Map<Dish.Type, List<String>> dishNamesByType =
                Dish.menu.stream()
                        .collect(Collectors.groupingBy(Dish::getType,
                                Collectors.mapping(Dish::getName, Collectors.toList())
                        ));

        System.out.println(dishNamesByType);
    }

    static void groupingByWithDownStreamPattern3() {
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));

        Map<Dish.Type, Set<String>> tagsByDishType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream(), Collectors.toSet())
                ));

        System.out.println(tagsByDishType);
    }

    static void multilevelGrouping() {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = Dish.menu.stream()
                .collect(
                        Collectors.groupingBy(Dish::getType,
                                Collectors.groupingBy(dish -> {
                                    int calories = dish.getCalories();
                                    if (calories <= 400) return CaloricLevel.DIET;
                                    if (calories <= 700) return CaloricLevel.NORMAL;
                                    return CaloricLevel.FAT;
                                })
                        )
                );

        System.out.println(dishesByTypeCaloricLevel);
    }
}

enum CaloricLevel {DIET, NORMAL, FAT}
