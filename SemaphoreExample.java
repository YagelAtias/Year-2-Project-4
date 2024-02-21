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
            } catch(InterruptedException e){
                System.out.println("The thread was interrupted");
            }
        }
    }
}
