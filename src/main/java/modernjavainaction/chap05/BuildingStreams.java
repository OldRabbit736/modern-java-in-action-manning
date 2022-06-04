package modernjavainaction.chap05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {

    public static void main(String[] args) {
        BuildingStreams.fromValues();
        BuildingStreams.fromNullable();
        BuildingStreams.iterate();
        BuildingStreams.fibonacci();
        BuildingStreams.iterateWithPredicate();
        BuildingStreams.generate();
        BuildingStreams.generateWithSideEffect();
    }

    static void fromValues() {
        Stream<String> stream = Stream.of("Modern", "Java", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        Stream<String> emptyStream = Stream.empty();
        emptyStream.forEach(System.out::println);
    }

    static void fromNullable() {
        String homeValue = System.getProperty("random1");

        Stream<String> homeValueStream = homeValue != null ? Stream.of(homeValue) : Stream.empty();
        Stream<String> homeValueStreamBetter = Stream.ofNullable(System.getProperty("random1"));

        Stream<String> values = Stream.of("random1", "sun.desktop", "random2", "os.arch")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));

        System.out.println(Arrays.toString(values.toArray()));
    }

    static void fromArrays() {
        int[] numbers = {2, 3, 4, 5, 6, 7};
        int sum = Arrays.stream(numbers).sum();
    }

    static void fromFiles() {
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("./data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(uniqueWords);
    }

    static void iterate() {
        Stream<Integer> limit = Stream.iterate(0, n -> n + 2)
                .limit(10);

        System.out.println(Arrays.toString(limit.toArray()));
    }

    static void fibonacci() {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> t[0])
                .forEach(System.out::println);
    }

    static void iterateWithPredicate() {
        IntStream.iterate(0, n -> n < 100, n -> n + 4)
                .forEach(System.out::println);
    }

    static void generate() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        IntStream generate = IntStream.generate(() -> 1);
    }

    static void generateWithSideEffect() {

        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };

        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }
}
