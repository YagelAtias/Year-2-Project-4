/**
 * @author Yagel Atias 208905448
 * @author Slava Ignatiev 322015280
 * @Github https://github.com/YagelAtias/Year-2-Project-4
 */

import java.util.concurrent.*;

public class SemaphoreExample {

    /**
     * Shared resource that can be accessed by multiple threads.
     * The number of concurrent accesses is limited by a semaphore.
     */
    static class SharedResource {
        private final Semaphore semaphore;

        /**
         * Constructor of a shared resource.
         *
         * @param permits the maximum number of simultaneous accesses to the resource
         */
        public SharedResource(int permits) {
            semaphore = new Semaphore(permits, true);
        }

        /**
         * Represents accessing the resource.
         * This method tries to acquire a permit from the semaphore.
         * If a permit is available, the thread acquires it and accesses the resource.
         * After finished using the resource the thread releases the permit.
         */
        public void accessResource() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " is accessing the resource.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " is releasing the resource.");
                semaphore.release();
            }
        }
    }

    /**
     * Worker thread that repeatedly attempts to access the shared resource.
     */
    static class Worker extends Thread {
        private final SharedResource sharedResource;

        /**
         * Constructor of a worker thread.
         *
         * @param name           the name of the thread
         * @param sharedResource the resource to access
         */
        public Worker(String name, SharedResource sharedResource) {
            super(name);
            this.sharedResource = sharedResource;
        }

        /**
         * The method that is executed when the thread is started.
         * It runs in an infinite loop, where in each iteration it
         * attempts to access the shared resource.
         */
        @Override
        public void run() {
            while (true) {
                sharedResource.accessResource();
            }
        }
    }

    /**
     * The main method where the program starts.
     * It creates a shared resource and three worker threads that try to access it.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(2);

        Thread thread1 = new Worker("Thread1", sharedResource);
        Thread thread2 = new Worker("Thread2", sharedResource);
        Thread thread3 = new Worker("Thread3", sharedResource);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}