import java.util.List;

public class LoadBalancer {
    private final List<RequestQueue> queues;
    private int currentQueueIndex = 0;
    private int RequestCount = 0;
    public LoadBalancer(List<RequestQueue> queues) {
        this.queues = queues;
    }

    // Round Robin algorithm to balance requests
    public void distributeRequest(Request request) {
        RequestQueue queue = queues.get(currentQueueIndex);
        queue.addRequest(request);
        RequestCount++;
        // Move to the next queue in a circular manner
        if (currentQueueIndex> queues.size())
            currentQueueIndex=0;
        else
            currentQueueIndex++;

    }
}
