package modernjavainaction.chap17;

import java.util.concurrent.Flow.*;

public class Main {

    public static void main(String[] args) {
//        new TempPublisher("New York").subscribe(new TempSubscriber());
//        TempPublisher.getFahrenheitPublisher("New York").subscribe(new TempSubscriber());
        TempPublisher.getCelsiusPublisher("New York").subscribe(new TempSubscriber());
    }
}
