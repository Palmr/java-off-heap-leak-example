package uk.co.palmr.offheapleakexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.palmr.offheapleakexample.offheap.Buffers;
import uk.co.palmr.offheapleakexample.offheap.Jni;
import uk.co.palmr.offheapleakexample.offheap.Threads;
import uk.co.palmr.offheapleakexample.offheap.UnclosedZipStream;
import uk.co.palmr.offheapleakexample.onheap.General;
import uk.co.palmr.offheapleakexample.onheap.Strings;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String EXPECTED_VM_NAME = "Java HotSpot(TM) 64-Bit Server VM";

    private static General onHeapGeneral;
    private static Strings onHeapStrings;

    private static UnclosedZipStream offHeapUnclosedZipStream;
    private static Buffers offHeapBuffers;
    private static Threads offHeapThreads;
    private static Jni offHeapJni;

    public static void main(String[] args) throws Exception
    {
        String vmName = System.getProperty("java.vm.name");
        if (!vmName.equals(EXPECTED_VM_NAME))
        {
            LOGGER.error("Potentially not running on the Oracle JVM, expected '{}' found '{}'",
                    EXPECTED_VM_NAME, vmName);
        }

        LOGGER.info("Starting application...");
        onHeapGeneral = new General();
        onHeapStrings = new Strings();

        offHeapBuffers = new Buffers();
        offHeapUnclosedZipStream = new UnclosedZipStream();
        offHeapThreads = new Threads();
        offHeapJni = new Jni();
        LOGGER.info("...done");

        LOGGER.info("Pausing for 20 seconds...");
        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
        LOGGER.info("...done");

        LOGGER.info("Consume memory...");
        useMemory();
        LOGGER.info("...done");

        // On shutdown...
//        offHeapThreads.shutdownThreadPool();
//        offHeapJni.shutdownThreadPool();
    }

    private static void useMemory() throws IOException
    {
        onHeapGeneral.useMemory();
        onHeapStrings.useMemory();

        offHeapBuffers.useMemory();
        offHeapUnclosedZipStream.useMemory();
        offHeapThreads.useMemory();
        offHeapJni.useMemory();
    }
}
