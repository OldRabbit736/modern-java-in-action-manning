package modernjavainaction.chap17.libraryVersion;


import io.reactivex.rxjava3.core.Observable;
import modernjavainaction.chap17.implementVersion.TempInfo;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Observable<String> strings = Observable.just("first", "second");
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);

//        onePerSec.subscribe(i -> System.out.println(TempInfo.fetch("New York")));
        onePerSec.blockingSubscribe(
                i -> System.out.println(TempInfo.fetch("New York"))
        );

    }

    public static Observable<TempInfo> getTemperature(String town) {
        return Observable.create(emitter ->
                Observable.interval(1, TimeUnit.SECONDS)
                        .subscribe(i -> {

                        })
        );
    }
}
