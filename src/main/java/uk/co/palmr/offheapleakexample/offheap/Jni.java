package uk.co.palmr.offheapleakexample.offheap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.palmr.offheapleakexample.jni.QuestionableJniLib;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Jni
{
    private static final Logger logger = LogManager.getLogger(Jni.class);
    private final QuestionableJniLib questionableJniLib = QuestionableJniLib.getInstance();
    private ScheduledExecutorService scheduler;

    public Jni()
    {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void useMemory()
    {
        logger.info("Scheduling a random JNI call every 500ms");
        scheduler.scheduleAtFixedRate(this::randomJni, 0, 500, MILLISECONDS);
    }

    private void randomJni()
    {
        if (Math.random() > 0.5)
        {
            questionableJniLib.nativeMethodOne();
        }
        else
        {
            questionableJniLib.nativeMethodTwo();
        }
    }

    public void shutdownThreadPool()
    {
        scheduler.shutdown();
    }
}
