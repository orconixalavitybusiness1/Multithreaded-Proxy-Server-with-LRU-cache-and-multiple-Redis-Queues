package Test1_1;

public class Test1_3Excetors implements Runnable{
    private int value;

    Test1_3Excetors(int v){
        this.value = v;
    }

    @Override
    public void run() {
        System.out.println(this.value+" Request Served Successfully by "+Thread.currentThread().getName());
        try{
            Thread.sleep(1000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
