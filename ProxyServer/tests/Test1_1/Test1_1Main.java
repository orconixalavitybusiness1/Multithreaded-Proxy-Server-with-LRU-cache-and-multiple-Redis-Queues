package Test1_1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test1_1Main {
    public static void main(String args[]) {
        BlockingQueue<Integer> q = new LinkedBlockingQueue<>(1000);
        Test1_2Thread r1 = new Test1_2Thread(q);
        Thread thread = new Thread(r1);
        int r = 0;

        thread.start();
        while (true) {
            try {
                q.put(r++);
                System.out.println("Inserted " + r);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
