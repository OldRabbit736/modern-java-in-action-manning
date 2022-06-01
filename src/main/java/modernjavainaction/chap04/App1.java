package modernjavainaction.chap04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class App1 {
    public static void main(String[] args) {
        List<String> title = Arrays.asList("Modern", "Java", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        s.forEach(System.out::println); // stream has already been operated upon or closed
    }
}
