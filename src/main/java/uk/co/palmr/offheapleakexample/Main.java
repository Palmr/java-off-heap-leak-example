package uk.co.palmr.offheapleakexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.palmr.offheapleakexample.offheap.Buffers;
import uk.co.palmr.offheapleakexample.offheap.Threads;
import uk.co.palmr.offheapleakexample.offheap.UnclosedZipStream;
import uk.co.palmr.offheapleakexample.onheap.General;
import uk.co.palmr.offheapleakexample.onheap.Strings;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static General onHeapGeneral;
    private static Strings onHeapStrings;

    private static UnclosedZipStream offHeapUnclosedZipStream;
    private static Buffers offHeapBuffers;
    private static Threads offHeapThreads;

    public static void main(String[] args) throws Exception {
        logger.info("Starting application...");
        onHeapGeneral = new General();
        onHeapStrings = new Strings();

        offHeapBuffers = new Buffers();
        offHeapUnclosedZipStream = new UnclosedZipStream();
        offHeapThreads = new Threads();
        logger.info("...done");

        logger.info("Pausing for 20 seconds...");
        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
        logger.info("...done");

        logger.info("Consume memory...");
        useMemory();
        logger.info("...done");

        logger.info("Shutting down in 20 seconds...");
        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
        logger.info("...done");

        // On shutdown...
//        offHeapThreads.shutdownThreadPool();
    }

    private static void useMemory() throws IOException {
        onHeapGeneral.useMemory();
        onHeapStrings.useMemory();

        offHeapBuffers.useMemory();
        offHeapUnclosedZipStream.useMemory();
        offHeapThreads.useMemory();
    }
}
