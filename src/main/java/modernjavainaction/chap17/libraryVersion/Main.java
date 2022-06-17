package modernjavainaction.chap17.libraryVersion;


import io.reactivex.rxjava3.core.Observable;

public class Main {
    public static void main(String[] args) {
        Observable<String> strings = Observable.just("first", "second");
    }
}
