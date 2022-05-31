package modernjavainaction.chap03;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;

public class Lambda {
    public static void main(String[] args) {


        Lambda.functionAndThen();
        Lambda.functionCompose();






//        Predicate<List<String>> p = list -> list.isEmpty();
//
//        Supplier<Apple> s = () -> new Apple(200, Color.GREEN);
//
//        Consumer<Apple> c = apple -> System.out.println(apple.getWeight());
//
//        Comparator<Apple> c1 = (a1, a2) -> a1.getWeight() - a2.getWeight();
//        ToIntBiFunction<Apple, Apple> c2 = (a1, a2) -> a1.getWeight() - a2.getWeight();
    }

    public boolean startsWithNumber(String str) {
        return true;
    }

    public void testMethodReferences() {
        ToIntFunction<String> stringToInt = (String str) -> Integer.parseInt(str);
        stringToInt = Integer::parseInt;

        BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        contains = List::contains;

        Predicate<String> startWithNumber = (String s) -> this.startsWithNumber(s);
        startWithNumber = this::startsWithNumber;
    }

    public void testConstructorReferences() {
        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();

        Supplier<Apple> c2 = () -> new Apple();
        Apple a2 = c2.get();

        Function<Color, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(Color.RED);

        Function<Color, Apple> c4 = (color) -> new Apple(color);
        Apple a4 = c4.apply(Color.RED);
    }

    public void interesting() {
        Map<String, Function<Color, Apple>> map = new HashMap<>();
        map.put("apple", Apple::new);
//        map.put("apple", c -> new Apple(c));
//        map.put("orange", Orange::new);
    }

    public void comparatorComposition(List<Apple> inventory) {
        Comparator<Apple> comparator = Comparator.comparing(Apple::getWeight);
        Comparator<Apple> reversedComparator = comparator.reversed();

        inventory.sort(comparator);
        inventory.sort(reversedComparator);
    }

    public void predicateComposition() {
        Predicate<Apple> redApple = apple -> Color.RED.equals(apple.getColor());
        Predicate<Apple> notRedApple = redApple.negate();
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        Predicate<Apple> redAndHeavyAppleOrGreen = redAndHeavyApple.or(apple -> Color.GREEN.equals(apple.getColor()));
    }

    public static void functionAndThen() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        System.out.println(h.apply(1));
    }

    public static void functionCompose() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.compose(g);
        System.out.println(h.apply(1));
    }
}

interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
