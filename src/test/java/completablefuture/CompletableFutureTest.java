package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureTest {
    @DisplayName("get()에서 대기하므로 블로킹이 발생하여 자원이 낭비된다.")
    @Test
    void completableFutureGet() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        threadPool.submit(() -> completableFuture.complete(f(x)));
        System.out.println("작업이 제출됨");

        int b = g(x);
        System.out.println("g() 호출");
        System.out.println(completableFuture.get() + b);

        threadPool.shutdown();
    }

    @DisplayName("스레드 풀에 제출하지 않고 직접 실행하면 동기식으로 수행된다. (CompletableFuture를 사용하는 이점이 없을듯)")
    @Test
    void completableFutureGet2() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        completableFuture.complete(f(x));  // 여기서 호출하면 끝날 때 까지 기다리는구나
        System.out.println("작업이 제출됨");

        int b = g(x);
        System.out.println("g() 호출");
        System.out.println(completableFuture.get() + b);

        threadPool.shutdown();
    }

    @DisplayName("thenCombine()을 이용할 수 있다")
    @Test
    void completableFutureThenCombine() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> y + z);

        threadPool.submit(() -> a.complete(f(x)));
        threadPool.submit(() -> b.complete(g(x)));

        System.out.println(c.get());
        threadPool.shutdown();
    }

    private Integer f(int x) {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "f() 호출됨");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[" + Thread.currentThread().getName() + "]" + "f() 처리됨");
        return x + 10;
    }

    private int g(int x) {
        return x - 10;
    }
}
