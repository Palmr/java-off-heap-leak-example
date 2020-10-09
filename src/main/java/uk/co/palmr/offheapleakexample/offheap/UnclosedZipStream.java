package uk.co.palmr.offheapleakexample.offheap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UnclosedZipStream
{
    private static final Logger logger = LogManager.getLogger(UnclosedZipStream.class);
    private static final int BUFFER_SIZE = 1024 * 1024; // 1MB

    private ZipOutputStream zipOutputStream;
    private final byte[] buffer = new byte[BUFFER_SIZE];

    public UnclosedZipStream()
    {
        final Random random = new Random();
        random.nextBytes(buffer);
    }

    public void useMemory() throws IOException
    {
        logger.info("Creating handle to unclosed zip stream with {} bytes written to it", BUFFER_SIZE);
        FileOutputStream fos = new FileOutputStream(File.createTempFile("test", ".zip"));
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        zipOutputStream = new ZipOutputStream(bos);
        zipOutputStream.putNextEntry(new ZipEntry("hello-world.txt"));
        zipOutputStream.write("Hello World!".getBytes());
        zipOutputStream.putNextEntry(new ZipEntry("random-5MB.txt"));
        zipOutputStream.write(buffer);
    }
}
