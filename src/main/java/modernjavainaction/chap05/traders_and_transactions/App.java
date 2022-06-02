package modernjavainaction.chap05.traders_and_transactions;

import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.query01();
        app.query02();
        app.query03();
        app.query04();
        app.query05();
        app.query06();
        app.query07();
        app.query08();
    }

    final List<Trader> traders;
    final List<Transaction> transactions;

    public App() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        this.traders = Arrays.asList(raoul, mario, alan, brian);
        this.transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    // Find all transactions in the year 2011 and sort them by value (small to high).
    public void query01() {
        List<Transaction> collect = this.transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        System.out.println(collect);
    }

    // What are all the unique cities where the traders work?
    public void query02() {
        List<String> distinctCities = traders.stream()
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(distinctCities);
    }

    // Find all traders from Cambridge and sort them by name.
    public void query03() {
        List<Trader> tradersFromCambridge = this.traders.stream()
                .filter(trader -> Objects.equals(trader.getCity(), "Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println(tradersFromCambridge);
    }

    // Return a string of all traders’ names sorted alphabetically.
    public void query04() {
        String names = traders.stream()
                .map(Trader::getName)
                .sorted()
//                .reduce("", (n1, n2) -> n1 + n2);     // inefficient
        .collect(Collectors.joining()); // efficient

        System.out.println(names);
    }

    // Are any traders based in Milan?
    public void query05() {
        boolean anyTradersInMilan = traders.stream()
                .anyMatch(trader -> trader.getCity().equals("Milan"));

        String result = anyTradersInMilan ? "There are traders in Milan" : "There are no traders in Milan";
        System.out.println(result);
    }

    // Print the values of all transactions from the traders living in Cambridge.
    public void query06() {
        List<Integer> values = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());

        System.out.println(values);
    }

    // What’s the highest value of all the transactions?
    public void query07() {
        Optional<Transaction> max = transactions.stream()
                .max(Comparator.comparingInt(Transaction::getValue));
        // max is special case of reduction

        max.ifPresent(transaction -> System.out.println(transaction.getValue()));
    }

    // Find the transaction with the smallest value.
    public void query08() {
        Optional<Integer> min = transactions.stream()
                .map(Transaction::getValue)
                .min(Integer::compareTo);

        min.ifPresent(System.out::println);
    }

}
