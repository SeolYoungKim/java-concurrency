package thread_safety;

public class EventSource {
    public void registerListener(EventListener eventListener) {
        eventListener.onEvent(new Event());
    }
}
