package thread_safety;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class CounterTest {
    static class Counter {
        public int count;

        public synchronized void increment() throws InterruptedException {
            Thread.sleep(5000);
            count++;
        }
    }

    @Test
    void name() {
        CountDownLatch countDownLatch = new CountDownLatch(101);
        Counter counter = new Counter();
        try (ExecutorService threadPool = Executors.newFixedThreadPool(100)) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.printf("[%s] getLock\n", Thread.currentThread().getName());
                    counter.increment();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();

            for (int i = 0; i < 100; i++) {
                threadPool.submit(() -> {
                    counter.count++;  // 위 스레드가 counter의 락을 획득하고 있음에도 불구하고 필드 접근이 된다.😱
                    System.out.printf("[%s] count: %d\n", Thread.currentThread().getName(), counter.count);
                    countDownLatch.countDown();
                });
            }

            countDownLatch.await();
            System.out.println("result: " + counter.count);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}