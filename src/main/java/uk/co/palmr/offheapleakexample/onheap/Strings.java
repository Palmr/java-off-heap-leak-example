package uk.co.palmr.offheapleakexample.onheap;

import org.apache.commons.lang3.RandomStringUtils;

public class Strings
{
    private static final int BUFFER_SIZE = 1024 * 1024; // 1MB

    private String generatedString;
    private String internedString;

    public void useMemory()
    {
        generatedString = RandomStringUtils.randomAlphabetic(BUFFER_SIZE);
        internedString = ("INTERNED_STRING:" + generatedString).intern();
    }
}
