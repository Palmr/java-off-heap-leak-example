package uk.co.palmr.offheapleakexample.offheap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Threads
{
    private static final Logger logger = LogManager.getLogger(Threads.class);

    private static final int THREAD_POOL_SIZE = 10;
    private ScheduledExecutorService scheduler;

    public Threads()
    {
        scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    }

    public void useMemory()
    {
        logger.info("Spawning {} threads", THREAD_POOL_SIZE);
        for (int i = 0; i < THREAD_POOL_SIZE; i++)
        {
            scheduler.scheduleAtFixedRate(this::scheduledTask, i, THREAD_POOL_SIZE, SECONDS);
        }
    }

    private void scheduledTask()
    {
        logger.info("Scheduled work is happening...");
    }

    public void shutdownThreadPool()
    {
        scheduler.shutdown();
    }
}
