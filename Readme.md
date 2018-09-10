# Java Off-Heap Memory Leak Example

This is a little example project that consumes memory in Java in several ways to help demonstrate investigating memory 
leaks in Java for a presentation I gave on that topic.

Definitely do not look at this code for good practices. There are intentional leaks throughout.

## Presentation

https://drive.google.com/open?id=1TsjfLCuIKoE_Q3kDFtwoCkuLZ3mr2KpyPO-t-qeYDyU

## Building

Build using `gradle assemble` which should generate an archive containing the distributable code in build/distributions

 *Note*: I've got something wrong with my gradle sub-project build ordering setup that I haven't figured out how to fix.
 But if you want to build this project yourself, run assemble **twice** otherwise it will be missing the native library 
 in the distribution.

### Changing QuestionableJniLib

  - Change to the JNI projects Java directory
    - `cd ./QuestionableJniLib/src/main/java`
  - Generate header file
    - `javah -jni -d ./../c uk.co.palmr.offheapleakexample.jni.QuestionableJniLib`

## Running

  - Make sure you're running on Oracle JDK 1.8
    - `export JAVA_HOME=/path/to/oracle/jdk/on/your/machine`
  - Unzip distribution and go into it's folder
    - `unzip OffHeapLeakExample-1.0-SNAPSHOT.zip`
    - `cd OffHeapLeakExample-1.0-SNAPSHOT`
  - Run the application from the root folder
    - `./bin/OffHeapLeakExample`
  - While it's paused for 20 seconds before using memory
    - Create a Native Memory Tracking baseline
    - Start logging memory to a file for plotting
    - `./bin/baseline_NMT_and_log_memory.sh`
  - Wait some time, got to let it gobble up memory...
  - Look at memory use
    - `./bin/plot_memory.gnuplot`
    - Looking higher than expected?
  - See what the JVM has to say about the heap
    - `jmap -heap <pid>`
  - Investigate the heap and maybe buffers with visualvm
    - `jvisualvm`
  - Check out diff between the Native Memory Tracking baseline taken and now: 
    - Summary diff (usually good enough): `jcmd <pid> VM.native_memory summary.diff`
    - Detail diff (usually too much detail): `jcmd <pid> VM.native_memory detail.diff`
  - Nothing particularly obvious there to explain endless memory use? Can kill the application now
  - Generate out the jemalloc profile diagrams
    - `./bin/jeprof_diagrams.sh`
  - Hopefully by now you've spotted where the memory is going!

### Jemalloc Runtime Error Messages

If you see lines in the stdout from the application like:

 > \<jemalloc>: Invalid conf pair: prof:true

Then the jemalloc on your machine was not compiled with profiling enabled.

The following is a bash script to do this if on a distro using dnf (i.e. Fedora, CentOS)

```bash
#!/bin/bash

# download source rpm to this folder
sudo dnf download --source jemalloc

# install source folder, will go to user home/rpmbuilds
rpm -ivh jemalloc*.src.rpm

# go to specs
cd ~/rpmbuild/SPECS

# get deps
sudo dnf builddep jemalloc.spec

# add --enable-prof to configure argument
sed -i '/^%configure/ s/$/ --enable-prof/' ~/rpmbuild/SPECS/jemalloc.spec

# build package
rpmbuild -bb jemalloc.spec

# install resultant rpm
sudo rpm -ivh `ls -1B ~/rpmbuild/RPMS/x86_64/jemalloc-*.rpm | grep -v 'dev\|debug'`
```
