package Test1_1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Test1_2Thread implements Runnable {
    private BlockingQueue<Integer> q;
    private final ExecutorService threadPool;

    public Test1_2Thread(BlockingQueue<Integer> q) {
        this.q = q;
        threadPool = Executors.newFixedThreadPool(50);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(!q.isEmpty()){
                    Integer value = q.take();
                    System.out.println("Successfully popped " + value);
                    threadPool.execute(new Test1_3Excetors(value));
                    Thread.sleep(1000);
                }
                else System.out.println("Queue is Empty");

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
