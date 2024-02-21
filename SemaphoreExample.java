import java.util.concurrent.*;
public class SemaphoreExample {
    static class SharedResource{
        private final Semaphore semaphore;
        public SharedResource(int permits){
            semaphore = new Semaphore(permits,true);
        }
        public void accessResource(){
            try{
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " is accessing the resource.");
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " is releasing the resource.");
                semaphore.release();
            }
        }
    }
    static class Worker extends Thread{
        private final SharedResource sharedResource;
        public Worker(String name, SharedResource sharedResource){
            super(name);
            this.sharedResource = sharedResource;
        }
        @Override
        public void run(){
            while (true){
                sharedResource.accessResource();
            }
        }
    }
    public static void main(String[] args){
        SharedResource sharedResource = new SharedResource(2);
        Thread thread1 = new Worker("Thread1",sharedResource);
        Thread thread2 = new Worker("Thread2",sharedResource);
        Thread thread3 = new Worker("Thread3",sharedResource);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
