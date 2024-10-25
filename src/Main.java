import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        RequestQueue queue1 = new RequestQueue("Queue-1");
        RequestQueue queue2 = new RequestQueue("Queue-2");
        RequestQueue queue3 = new RequestQueue("Queue-3");
        List<RequestQueue> queues = new ArrayList<>();
        queues.add(queue1);
        queues.add(queue2);
        queues.add(queue3);
        LoadBalancer loadBalancer = new LoadBalancer(queues);
        for (int i = 1; i <= 10; i++) {
            Request request = new Request("Req-" + i, "Payload-" + i);
            loadBalancer.distributeRequest(request);
        }
    }
}
