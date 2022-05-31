package modernjavainaction.chap03;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Letter {

    @Contract(pure = true)
    public static @NotNull String addHeader(String text) {
        return "From Raoul, Mario and Alan: " + text;
    }

    @Contract(pure = true)
    public static @NotNull String addFooter(String text) {
        return text + " Kind regards";
    }

    @Contract(pure = true)
    public static @NotNull String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }

    public static void main(String[] args) {
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline = addHeader
                .andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);

        String text = "Hey, I like labda!!";
        String result = transformationPipeline.apply(text);
        System.out.println(result);
    }
}
