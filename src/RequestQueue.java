import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {
    private final Queue<Request> queue;
    private final String queueName;

    public RequestQueue(String queueName) {
        this.queue = new LinkedList<>();
        this.queueName = queueName;
    }

    // Add request to the queue
    public void addRequest(Request request) {
        queue.add(request);
        System.out.println("Added " + request + " to " + queueName);
    }

    // Fetch the queue size
    public int getSize() {
        return queue.size();
    }

    public String getQueueName() {
        return queueName;
    }
}
