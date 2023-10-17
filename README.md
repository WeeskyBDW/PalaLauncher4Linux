# PalaLauncher4Linux

A live patcher for Paladium Games launcher to allow launching games on Linux

## Compiling

Compiling should be easy and done automatically on any IDE that uses `pom.xml` files.
In my case, I use Pulsar (Atom's continuation) with its `ide-java` package and it works well.

Just don't forget to compile for Java 8 for better compatibility with the launcher.

## Packaging

This is pretty straightforward, just make a `.jar` file with the contents of the `target/classes` folder and add the contents of a [Javassist .jar](https://github.com/jboss-javassist/javassist/releases) into the .jar previously made.

## Using

Simple, just add `-javaagent:path/to/the/built.jar` before `.jar` when you start the launcher located in the `~/paladium-games` folder.
Also be sure to use Java 8 for better compatibility.
