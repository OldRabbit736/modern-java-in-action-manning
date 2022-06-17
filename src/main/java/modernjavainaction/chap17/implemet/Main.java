package modernjavainaction.chap17.implemet;

public class Main {

    public static void main(String[] args) {
//        new TempPublisher("New York").subscribe(new TempSubscriber());
//        TempPublisher.getFahrenheitPublisher("New York").subscribe(new TempSubscriber());
        TempPublisher.getCelsiusPublisher("New York").subscribe(new TempSubscriber());
    }
}
