package modernjavainaction.chap08;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ImprovedConcurrentHashMap {
    public static void main(String[] args) {
        threshold();
        counting();
        keySet();
    }

    static void newOperations() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("a", 3L);
        map.put("b", 1L);
        map.put("c", 5L);

        //map.forEach();
        //map.forEachEntry();
        //map.forEachKey();
        //map.forEachValue();
    }

    static void threshold() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("a", 3L);
        map.put("b", 1L);
        map.put("c", 5L);

        long parallelismThreshold = 1;
        Optional<Long> maxValue = Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));

        System.out.println(maxValue);
    }

    static void counting() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("a", 3L);
        map.put("b", 1L);
        map.put("c", 5L);

        long l = map.mappingCount();

        System.out.println(l);
    }

    static void keySet() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        map.put("a", 3L);
        map.put("b", 1L);
        map.put("c", 5L);

        ConcurrentHashMap.KeySetView<String, Long> strings = map.keySet();
        ConcurrentHashMap.KeySetView<Object, Boolean> objects = ConcurrentHashMap.newKeySet();

        System.out.println(strings);
        System.out.println(objects);
    }


}
