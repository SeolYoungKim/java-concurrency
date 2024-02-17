package thread_safety;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class InstanceLimit {
    record Person(String name) {
    }

    public static void main(String[] args) {
        Set<Person> synchronizedSet = Collections.synchronizedSet(
                Set.of(new Person("John"), new Person("Jane"), new Person("Adam"))
        );

        // 아래의 경우, 객체에 직접 접근이 가능해지므로 스레드 안전성이 깨질 수 있다.
        Iterator<Person> iterator = synchronizedSet.iterator();
    }
}
