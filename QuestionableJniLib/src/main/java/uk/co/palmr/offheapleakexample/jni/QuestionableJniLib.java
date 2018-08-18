package uk.co.palmr.offheapleakexample.jni;

import java.io.File;

public class QuestionableJniLib {
    private static QuestionableJniLib instance;

    QuestionableJniLib()
    {
    }

    public static QuestionableJniLib getInstance()
    {
        if (instance == null)
        {
            final String nativeLibraryPath = getNativeLibraryPath();
            loadInstance(new File(nativeLibraryPath));
        }
        return instance;
    }

    public static synchronized QuestionableJniLib loadInstance(final File nativeLibraryFile)
    {
        if (instance == null)
        {
            System.load(nativeLibraryFile.getAbsolutePath());
            instance = new QuestionableJniLib();
        }
        return instance;
    }

    private static String getNativeLibraryPath()
    {
        final String envVar = System.getenv("QUESTIONABLE_JNI_LIB_PATH");
        if (envVar != null)
        {
            return envVar;
        }

        return "lib/libQuestionableJniLib.so";
    }

    public native void nativeMethodOne();
    public native void nativeMethodTwo();
}
