package modernjavainaction.chap16;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    static final List<Shop> shops = List.of(
            new Shop("BuyItAll1"),
            new Shop("BuyItAll2"),
            new Shop("BuyItAll3"),
            new Shop("BuyItAll4"),
            new Shop("BuyItAll5"),
            new Shop("BuyItAll6"),
            new Shop("BuyItAll7"),
            new Shop("BuyItAll8"),
            new Shop("BuyItAll9"),
            new Shop("BuyItAll10"),
            new Shop("BuyItAll11"),
            new Shop("BuyItAll12")
    );

    static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            (Runnable r) -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
    );

    public static void main(String[] args) {
//        measureFindPrices(App::findPrices);
//        measureFindPrices(App::findPricesParallel);
//        measureFindPrices(App::findPricesAsyncGood);
//        measureFindPrices(App::findPricesAsyncBad);
//        measureFindPrices(App::findPricesAsyncUsingCustomExecutor);
//        measureFindPrices(App::findPricesWithDiscount);
//        measureFindPrices(App::findPricesWithDiscountAsyncMyTry);
//        measureFindPrices(App::findPricesWithDiscountAsync);

        printPrices(shops, "myPhone");
    }

    static void getPrice() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        // Do some more tasks, like querying other shops
        // doSomethingElse();
        // while the price of the product is being calculated

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    static void measureFindPrices(BiFunction<List<Shop>, String, List<String>> findPrices) {
        long start = System.nanoTime();
        System.out.println(findPrices.apply(shops, "myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("findPrices: Done in " + duration + " msecs");
    }

    static List<String> findPrices(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> String.format("[%s] %s price is %.2f\n",
                        Thread.currentThread().getName(), shop.getName(), shop.getPrice(product))
                )
                .collect(Collectors.toList());
    }

    static List<String> findPricesParallel(List<Shop> shops, String product) {
        return shops.parallelStream()
                .map(shop -> String.format("[%s] %s price is %.2f\n",
                        Thread.currentThread().getName(), shop.getName(), shop.getPrice(product))
                )
                .collect(Collectors.toList());
    }

    static List<String> findPricesAsyncGood(List<Shop> shops, String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("[%s] %s price is %.2f\n",
                        Thread.currentThread().getName(), shop.getName(), shop.getPrice(product)))
                )
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    static List<String> findPricesAsyncBad(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("[%s] %s price is %.2f\n",
                        Thread.currentThread().getName(), shop.getName(), shop.getPrice(product)))
                )
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    static List<String> findPricesAsyncUsingCustomExecutor(List<Shop> shops, String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("[%s] %s price is %.2f\n",
                                Thread.currentThread().getName(), shop.getName(), shop.getPrice(product)),
                        executor
                ))
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    static List<String> findPricesWithDiscount(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> shop.getPriceWithCouponCode(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    static List<String> findPricesWithDiscountAsyncMyTry(List<Shop> shops, String product) {
        List<CompletableFuture<String>> shopPricesFuture = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithCouponCode(product), executor))
                .collect(Collectors.toList());


        List<CompletableFuture<String>> shopDiscountedPricesFuture = shopPricesFuture.stream()
                .map(CompletableFuture::join)
                .map(Quote::parse)
                .map(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor))
                .collect(Collectors.toList());

        return shopDiscountedPricesFuture.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    static List<String> findPricesWithDiscountAsync(List<Shop> shops, String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithCouponCode(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                        () -> Discount.applyDiscount(quote), executor
                ))).collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // Java8 부터
    static Future<Double> getFuturePriceInUSDWithCompletableFuture(Shop shop, String product, Executor executor) {
        return CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor)
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> ExchangeService.getRate(Money.EUR, Money.USD)),
                        (price, rate) -> price * rate
                );
    }

    // Java8 이전
    static Future<Double> getFuturePriceInUSDWithFuture(Shop shop, String product, ExecutorService executor) {
        Future<Double> futureRate = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return ExchangeService.getRate(Money.EUR, Money.USD);
            }
        });

        return executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double priceInEUR = shop.getPrice(product);
                return priceInEUR * futureRate.get();
            }
        });
    }

    // Java9부터 timeout 관련 메소드 지원
    static Future<Double> getFuturePriceInUSDWithCompletableFutureWithTimeout(Shop shop, String product, Executor executor) {
        return CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor)
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> ExchangeService.getRate(Money.EUR, Money.USD))
                                .completeOnTimeout(1.0, 1, TimeUnit.SECONDS)
                        ,
                        (price, rate) -> price * rate
                )
                .orTimeout(3, TimeUnit.SECONDS);
    }

    public static Stream<CompletableFuture<String>> findPricesStream(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPriceWithCouponCodeAndRandomDelay(product), executor
                ))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor
                        )
                ));
    }

    public static void printPrices(List<Shop> shops, String product) {
        long start = System.nanoTime();

        CompletableFuture[] futures = findPricesStream(shops, product)
                .map(future -> future.thenAccept(
                        s -> System.out.println(
                                s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")
                ))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();

        System.out.println(
                "All shops have now responded in "
                + ((System.nanoTime() - start) / 1_000_000)
                + " msecs"
        );
    }
}
