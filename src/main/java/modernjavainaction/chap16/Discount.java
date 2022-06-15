package modernjavainaction.chap16;

import static modernjavainaction.chap16.Util.delay;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), COLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode()) + "\n";
    }

    private static double apply(double price, Code code) {
        delay();
        return price * (100 - code.percentage) / 100;
    }
}
