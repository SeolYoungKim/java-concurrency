package thread_safety;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Delegate {
    public static void main(String[] args) {
        ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("a", 1);
        concurrentMap.put("b", 2);
        concurrentMap.put("c", 3);

        Map<String, Integer> unmodifiableMap = Collections.unmodifiableMap(concurrentMap);
        System.out.println("Before: " + unmodifiableMap);

        concurrentMap.replace("a", 1, 100);
        System.out.println("After: " + unmodifiableMap);
    }
}
