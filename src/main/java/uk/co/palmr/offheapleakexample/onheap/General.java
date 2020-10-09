package uk.co.palmr.offheapleakexample.onheap;

import java.util.ArrayList;
import java.util.List;

public class General
{
    private static final int OBJECT_COUNT = 1000;

    private final Integer integer;
    private final String string;
    private final List<Object> objectList;

    public General()
    {
        integer = 123;
        string = "This should be on the heap";
        objectList = new ArrayList<>();
    }

    public void useMemory()
    {
        for (int i = 0; i < OBJECT_COUNT; i++)
        {
            objectList.add(new ObjectForHeap("Object on heap: " + i));
        }
    }

    private static class ObjectForHeap
    {
        private final String id;

        ObjectForHeap(final String id)
        {
            this.id = id;
        }

        @Override
        public String toString()
        {
            return "ObjectForHeap{" +
                   "id='" + id + '\'' +
                   '}';
        }
    }
}
