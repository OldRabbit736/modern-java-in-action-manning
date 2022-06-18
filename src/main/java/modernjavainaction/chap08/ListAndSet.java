package modernjavainaction.chap08;

import java.util.ArrayList;
import java.util.List;

public class ListAndSet {

    public static void main(String[] args) {
        ListAndSet.removeIf();
        ListAndSet.replaceAll();
    }

    static void removeIf() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        // mutate
        numbers.removeIf(i -> i % 2 == 0);
        System.out.println(numbers);
    }

    static void replaceAll() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        numbers.replaceAll(i -> i + 100);
        System.out.println(numbers);
    }
}
