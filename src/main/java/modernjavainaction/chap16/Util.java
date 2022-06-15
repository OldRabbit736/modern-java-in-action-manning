package modernjavainaction.chap16;

public class Util {
    public static void delay() {
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
