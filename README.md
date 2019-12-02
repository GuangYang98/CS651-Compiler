# Compiler Course Design: Building a Java compiler totally from scratch. 
**Term:** Fall 2019 
**Course:** CS651 - Compiler I 
**Date:** Fall 2019 Semester (Sep - Dec 2019) 
**Language:** Java  

Built a Java compiler from scratch -- developed the compiler pipeline 
of FSM file scanning, LL(1) token parsing, semantic type-checking analysis,
and code generation to compile a source file into JVM bytecode and runnable.

[J--](https://github.com/GuangYang98/CS651-Compiler/blob/master/j--) folder contains all the source code and test case of the Jminusminus compiler.

[Projects](https://github.com/GuangYang98/CS651-Compiler/blob/master/projects) folder contains all the project assignments in course CS651.

Up to now, the latest project is **Project 5**. I uploaded the project assignment in this repository. For Academic Integrity, I will update the repository after the grading of Project 5.

## Getting Started with J-- Compiler

1. Download the entire [J--](https://github.com/GuangYang98/CS651-Compiler/blob/master/j--) folder and put all the file into some folder. We will refer to this
   folder as `$j`. 

2. Configure the environment variable PATH to include `$j/j--/bin`.
   Verify that you have configured the PATH variable correctly by typing in the
   command prompt `j--` and this should work in any directory. If you see
   "command not found" then make sure the `PATH` is correct and that the
   permissions within the bin folder are executable.

3. Install [J2SE 8](https://www.java.com/en/download/manual.jsp) or later
   and configure the environment variable `PATH` to include the path to the
   Java binaries. For example, on Windows, `PATH` might include
   `C:\Program Files\Java\jdk1.8.0\bin`.

4. Install [Ant 1.8.2](http://ant.apache.org/bindownload.cgi) or later, set the
   environment variable `ANT_HOME` to point to the folder where it is installed,
   and configure environment variable `PATH` to include `$ANT_HOME/bin`.

5. Test that your installation is correct by running:

```
$j/j--> ant
```

You are now ready to work with the _j--_ compiler. 

## Compile and Test the Compiler

Using Apache Ant, execute the appropriate Ant target as follows:

```
$j/j--> ant <target>
```

**To simply compile this compiler, please run:**

```
$j/j--> ant clean compile jar
```

If no target is specified, the default (`runCompilerTests`) target is executed.
To obtain a list of available Ant targets, execute the following command:

```
$j/j--> ant help
```

## Print the Abstract Syntax Tree (AST)

To print the abstract syntax tree:

```
$j/j--> bin/j-- -p P.java
```

where `P.java` is the path to any java file you want to analyze and print the abstract syntax tree.

## Make Use of the Compiler

The _j--_ compiler can be executed directly on the command line using the script
`$j/j--/bin/j--`. For example, `$j/j--/tests/pass/HelloWorld.java` can be compiled
using _j--_ as follows:

```
$j/j--> j-- tests/pass/HelloWorld.java
```

The class file `HelloWorld.class` produced under `$j/j--/pass/` can be run as follows:

```
$j/j--> java pass.HelloWorld
```

The full command-line syntax for the `j--` script is as follows:

```
Usage: j-- <options> <source file>
Where possible options include:
  -t  Only tokenize input and print tokens to STDOUT
  -p  Only parse input and print AST to STDOUT
  -pa Only parse and pre-analyze input and print AST to STDOUT
  -a  Only parse, pre-analyze, and analyze input and print AST to STDOUT
  -s  <naive|linear|graph> Generate SPIM code
  -r  <num> Max. physical registers (1-18) available for allocation; default = 8
  -d  <dir> Specify where to place output files; default = .
```

# 