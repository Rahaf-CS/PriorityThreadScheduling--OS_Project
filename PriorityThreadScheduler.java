package osproject;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
/**
 *
 * @author nada
 */
public class PriorityThreadScheduler {
    //schedules train entry into common line
    private ExecutorService priorityJobPoolExecutor;
    private final ExecutorService priorityJobScheduler=Executors.newSingleThreadExecutor();
    private PriorityBlockingQueue<Thread> priorityQueue;
 
    public PriorityThreadScheduler(Integer poolSize, Integer queueSize) {
        priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
        priorityQueue = new PriorityBlockingQueue< >(
          queueSize, 
          Comparator.comparing(Thread ::getPriority).thenComparing(Thread :: getName));
          
        priorityJobScheduler.execute(() -> {

            while (true) {
                try {
                    priorityJobPoolExecutor.execute(priorityQueue.take());//execute
                } catch (InterruptedException e) {
                    //exception needs special handling
                    break;
                }
            }
        });
    }
 
    public void scheduleJob(Thread job){
        priorityQueue.add(job);
    }
    
}
