package modernjavainaction.chap07.spliterator;

public class WordCounterIterative {

    final static String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita " +
                    "mi  ritrovai in una  selva oscura" +
                    " ché la  dritta via era   smarrita ";

    public static void main(String[] args) {
        int wordsCount = WordCounterIterative.countWordsIteratively(SENTENCE);
        System.out.println(wordsCount);
    }

    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }
}
