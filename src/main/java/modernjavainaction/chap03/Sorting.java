package modernjavainaction.chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class Sorting {

    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));

        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(30, Color.GREEN));

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight() - a2.getWeight();
            }
        });
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(20, Color.RED));

        inventory.sort((Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(10, Color.RED));

        inventory.sort(Comparator.comparing(Apple::getWeight));


        Predicate<Apple> p = (apple) -> apple.getWeight() > 150;

        Callable<String> c = () -> "Wow";

    }

    static class AppleComparator implements Comparator<Apple> {

        @Override
        public int compare(Apple o1, Apple o2) {
            return o1.getWeight() - o2.getWeight();
        }
    }
}
