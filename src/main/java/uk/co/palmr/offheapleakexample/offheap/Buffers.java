package uk.co.palmr.offheapleakexample.offheap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Buffers
{
    private static final Logger LOGGER = LogManager.getLogger(Buffers.class);

    private static final int BUFFER_SIZE = 5 * 1024 * 1024; // 5MB

    private ByteBuffer directBuffer;
    private MappedByteBuffer mappedByteBuffer;

    // Runtime jar should be there, given it's Oracle/Open JDK
    private final String fileToMap = System.getProperty("java.home") + "/lib/rt.jar";

    public void useMemory() throws IOException
    {
        allocateDirectBuffer();
        allocateMappedBuffer();
    }

    private void allocateDirectBuffer()
    {
        LOGGER.info("Allocating direct buffer {} bytes", BUFFER_SIZE);
        directBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        directBuffer.put("Using some off-heap memory in a direct buffer".getBytes());
    }

    private void allocateMappedBuffer() throws IOException
    {
        LOGGER.info("Allocating mapped buffer {} bytes", BUFFER_SIZE);
        final FileInputStream fileInputStream = new FileInputStream(fileToMap);
        mappedByteBuffer = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, BUFFER_SIZE);
        mappedByteBuffer.load();
    }
}
