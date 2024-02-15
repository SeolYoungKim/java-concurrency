package thread_safety;

public class ThisEscape {
    private int thisCanBeEscape = 0;

    public ThisEscape(EventSource source) throws InterruptedException {
        source.registerListener(
                new EventListener() {
                    public void onEvent(Event e) {
                        doSomething(e);
                    }
                }
        );

        Thread.sleep(10000);
        thisCanBeEscape = 1;
    }

    public static void main(String[] args) throws InterruptedException {
        ThisEscape thisEscape = new ThisEscape(new EventSource());
    }

    void doSomething(Event e) {
        System.out.println(this.thisCanBeEscape);
        System.out.println(e);
    }
}
