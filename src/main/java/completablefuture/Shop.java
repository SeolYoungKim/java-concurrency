package completablefuture;

import java.util.concurrent.ThreadLocalRandom;

public class Shop {
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

    private double calculatePrice(String product) {
        delay();
        return ThreadLocalRandom.current().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
