package modernjavainaction.chap07.parallel;

import java.util.stream.Stream;

public class ParallelStreams {
    public static void main(String[] args) {
        long sum = ParallelStreams.parallelSum(10);
        System.out.println(sum);

    }

    static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }
}
