import java.util.concurrent.*;
public class SemaphoreExample {
    static class SharedResource{
        private final Semaphore semaphore;
        public SharedResource(int permits){
            semaphore = new Semaphore(permits);
        }
        public void accessResource(){
            try{
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "is accessing the resource.");
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "is releasing the resource.");
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
            sharedResource.accessResource();
        }
    }
}
