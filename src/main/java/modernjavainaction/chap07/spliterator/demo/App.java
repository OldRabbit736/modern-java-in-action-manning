package modernjavainaction.chap07.spliterator.demo;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class App {
    final static String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita " +
                    "mi  ritrovai in una  selva oscura" +
                    " ché la  dritta via era   smarrita ";

    public static void main(String[] args) {
        App.right();
        App.wrong();
    }

    public static void wrong() {
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);

        System.out.println("Found " + WordCounter.countWords(stream.parallel()) + " words");
    }

    public static void right() {
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        System.out.println("Found " + WordCounter.countWords(stream.parallel()) + " words");
    }
}
