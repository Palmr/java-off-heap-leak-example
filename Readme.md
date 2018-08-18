# Java Off-Heap Memory Leak Example

This is a little example project that consumes memory in Java in several ways to help demonstrate investigating memory 
leaks in Java for a presentation I gave on that topic.

Definitely do not look at this code for good practices. There are intentional leaks throughout.

## Presentation

// TODO - Link to presentation

## Building

Build using `gradle assemble` which should generate an archive containing the distributable code in build/distributions

 *Note*: I've got something wrong with my gradle sub-project build ordering setup that I haven't figured out how to fix.
 But if you want to build this project yourself, run assemble **twice** otherwise it will be missing the native library 
 in the distribution.

### Changes to QuestionableJniLib

  - Change to the JNI projects Java directory
    - `cd ./QuestionableJniLib/src/main/java`
  - Generate header file
    - `javah -jni -d ./../c uk.co.palmr.offheapleakexample.jni.QuestionableJniLib`

## Running

// TODO - Instructions for running this to get results as per the demo required in the presentation

  - Make sure you're running on Oracle JDK 1.8
    - `export JAVA_HOME=/path/to/oracle/jdk/on/your/machine`
  - Unzip distribution and go into it's folder
    - `unzip OffHeapLeakExample-1.0-SNAPSHOT.zip`
    - `cd OffHeapLeakExample-1.0-SNAPSHOT`
  - Run the application from the root folder
    - `./bin/OffHeapLeakExample`
