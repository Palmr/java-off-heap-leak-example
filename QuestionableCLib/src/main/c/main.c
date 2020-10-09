#include "uk_co_palmr_offheapleakexample_jni_QuestionableJniLib.h"
#include "stuff.h"
#include <stdio.h>


JNIEXPORT void JNICALL Java_uk_co_palmr_offheapleakexample_jni_QuestionableJniLib_nativeMethodOne
  (JNIEnv *env, jobject obj)
{
    do_stuff_non_leaky("Native method ONE called");
    return;
}

JNIEXPORT void JNICALL Java_uk_co_palmr_offheapleakexample_jni_QuestionableJniLib_nativeMethodTwo
  (JNIEnv *env, jobject obj)
{
    do_stuff_leaky("Native method TWO called");

    return;
}
