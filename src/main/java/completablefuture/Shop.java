package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;

@Getter
public class Shop {
    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Shop shop = new Shop("my shop");

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

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[ThreadLocalRandom.current().nextInt(Discount.Code.values().length)];
        return "%s:%.2f:%s"
                .formatted(name, price, code);
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delay();
        return ThreadLocalRandom.current().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
