import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class RequestServer implements Runnable{
    private final Socket cSocket;

    RequestServer(Socket clientSocket){
        this.cSocket = clientSocket;
    }

    @Override
    public void run() {
        try{
            handleRequest(cSocket);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleRequest(Socket clientSocket) throws IOException{
        var in = clientSocket.getInputStream();
        var out = clientSocket.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead = in.read(buffer);

        if (bytesRead != -1) {
            String request = new String(buffer, 0, bytesRead);

            // Constructing a simple HTTP response
            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-Length: " + bytesRead + "\r\n" +
                    "\r\n" +
                    new String(buffer, 0, bytesRead);

            out.write(httpResponse.getBytes());
        }

        clientSocket.close();
    }
}




class RequestProcessor implements Runnable{
    private final BlockingQueue<Socket>[] requestQueues;
    private int QueueIndex;
    private final int NoOfQueues;
    private final int NoOfThreads;
    private final ExecutorService threadPool;

    RequestProcessor(BlockingQueue<Socket>[] rQueues,int NO_OF_QUEUES,int NO_OF_THREADS){
        this.QueueIndex = 0;
        this.NoOfQueues = NO_OF_QUEUES;
        this.requestQueues = rQueues;
        this.NoOfThreads = NO_OF_THREADS;
        this.threadPool = Executors.newFixedThreadPool(NoOfThreads);
    }

    @Override
    public void run(){


        while(true){
            if(!requestQueues[QueueIndex].isEmpty()) {
                Socket clientSocket = new Socket();
                try {
                    clientSocket = requestQueues[QueueIndex].take();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Handler interrupted.");
                }

                QueueIndex = (QueueIndex + 1) % NoOfQueues;

                threadPool.execute(new RequestServer(clientSocket));
            }
            else{
                continue;
            }
        }
    }
}

class Server{

    private final int PORT = 8090;
    private final int NO_OF_THREADS =50;
    private final int NO_OF_QUEUES = 10;
    private final int CAPACITY_OF_QUEUE = 5000;
    private final BlockingQueue<Socket>[] requestQueues;


    Server(){
        requestQueues = new LinkedBlockingQueue[NO_OF_QUEUES];

        for(int i=0;i<NO_OF_QUEUES;i++){
            requestQueues[i] = new LinkedBlockingQueue<>();
            System.out.println("Queue "+i+" Initialised");
            try {
                Thread.sleep(500);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void startServer() throws Exception{
        Thread.sleep(2000);

        System.out.println("Starting the server...");

        ServerSocket serverSocket = new ServerSocket(PORT);

        Thread.sleep(3000);

        System.out.println("Server started)");

        Thread.sleep(1000);

        System.out.println("Listening on port :"+PORT);

        int QueueIndex = 0;

        RequestProcessor requestProcessor = new RequestProcessor(requestQueues,NO_OF_QUEUES,NO_OF_THREADS);
        Thread rP = new Thread(requestProcessor);

        rP.start();

        while(true){
            Socket clientSocket = serverSocket.accept();

            requestQueues[QueueIndex].offer(clientSocket);


            QueueIndex = (QueueIndex + 1) % NO_OF_QUEUES;
        }
    }
    public static void main(String args[]){
        System.out.println("Initializing Request");

        try {
            Thread.sleep(5000);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Server server = new Server();

        try{
            server.startServer();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return;
    }
}