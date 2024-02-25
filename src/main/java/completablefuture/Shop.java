package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {
    public static void main(String[] args) {
        Shop shop = new Shop();

        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        /* 제품 가격을 계산하는 동안 다른 작업을 수행할 수 있다. */

        try {
            double price = futurePrice.get();  // 가격 계산이 완료될 때 까지 blocking
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);  // 다른 스레드에서 비동기로 작업
                futurePrice.complete(price);  // 결과를 futurePrice에 설정
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);  // 도중에 예외 발생시 예외를 발생시킴
            }
        }).start();

        return futurePrice;  // 해당 메서드를 호출한 스레드는 계산 결과를 기다리지 않고 값을 반환 받음
    }

    private double calculatePrice(String product) {
        delay();
        throw new RuntimeException("product not available");
//        return ThreadLocalRandom.current().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
