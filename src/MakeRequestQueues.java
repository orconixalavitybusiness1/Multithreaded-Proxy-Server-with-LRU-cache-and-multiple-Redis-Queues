import java.util.ArrayList;
import java.util.List;

public class MakeRequestQueues {
    int n;
    List<RequestQueue> queues;
    public  MakeRequestQueues(int n){
        this.n = n;
        queues = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            RequestQueue queue = new RequestQueue("queue_"+i);
            this.queues.add(queue);
        }
    }
    public List<RequestQueue> getQueues(){
        return queues;
    }
}
