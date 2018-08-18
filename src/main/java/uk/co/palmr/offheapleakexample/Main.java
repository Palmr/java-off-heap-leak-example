package uk.co.palmr.offheapleakexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.palmr.offheapleakexample.onheap.General;
import uk.co.palmr.offheapleakexample.onheap.Strings;

import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static General onHeapGeneral;
    private static Strings onHeapStrings;

    public static void main(String[] args) throws InterruptedException {
        logger.info("Starting application...");
        onHeapGeneral = new General();
        onHeapStrings = new Strings();
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
    }

    private static void useMemory() {
        onHeapGeneral.useMemory();
        onHeapStrings.useMemory();
    }
}
