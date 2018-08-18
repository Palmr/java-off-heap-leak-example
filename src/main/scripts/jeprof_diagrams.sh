#!/usr/bin/env bash

JAVACMD="$JAVA_HOME/bin/java"
HEAP_LATEST=$(ls -t jeprof.*.heap | head -n1)

jeprof --add_lib ./lib/libQuestionableJniLib.so --show_bytes --dot $JAVACMD jeprof.*.heap > jemalloc_profile_all.dot
jeprof --add_lib ./lib/libQuestionableJniLib.so --show_bytes --dot $JAVACMD $HEAP_LATEST > jemalloc_profile_latest.dot

dot -Tpng jemalloc_profile_latest.dot > jemalloc_profile_latest.png
dot -Tpng jemalloc_profile_all.dot > jemalloc_profile_all.png
