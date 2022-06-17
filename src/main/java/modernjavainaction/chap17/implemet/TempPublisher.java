package modernjavainaction.chap17.implemet;

import java.util.concurrent.Flow.*;

public class TempPublisher implements Publisher<TempInfo> {

    private final String town;

    public TempPublisher(String town) {
        this.town = town;
    }

    @Override
    public void subscribe(Subscriber<? super TempInfo> subscriber) {
        subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    public static Publisher<TempInfo> getFahrenheitPublisher(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    public static Publisher<TempInfo> getCelsiusPublisher(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }
}
