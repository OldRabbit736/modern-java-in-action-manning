package modernjavainaction.chap04;

import java.util.List;
import java.util.stream.Collectors;

public class App2 {
    public static void main(String[] args) {
        List<String> highCaloricDish = Dish.menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());

        System.out.println(highCaloricDish);
    }
}
