import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        MakeRequestQueues mq = new MakeRequestQueues(5);
        List<RequestQueue> queues = mq.getQueues();
        LoadBalancer loadBalancer = new LoadBalancer(queues);
        for (int i = 1; i <= 10; i++) {
            Request request = new Request("Req-" + i, "Payload-" + i);
            loadBalancer.distributeRequest(request);
        }
    }
}
