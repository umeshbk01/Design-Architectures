import java.util.LinkedList;
import java.util.Queue;

public class SharedResource {

    Queue<String> q;
    int capacity;
    public SharedResource(int capacity){
        this.q = new LinkedList<>();
        this.capacity = capacity;
    }
    public synchronized void producer(String item) throws InterruptedException {
        while (q.size() == capacity) {
            wait();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " produced: " + item);
        q.add(item);
        Thread.sleep(1000);
        notifyAll();
    }
    public synchronized String consumer() throws InterruptedException {
        while (q.isEmpty()) {
            wait();
        }
        String item = q.poll();
        Thread.sleep(1000);
        System.out.println("Thread " + Thread.currentThread().getName() + " consumed: " + item);
        notifyAll();
        return item;
    }
}