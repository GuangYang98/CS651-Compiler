// Copyright 2013- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

import jminusminus.CLEmitter;   //打包了个api 里面还有很多定义了的小函数 方便后面去使用
import static jminusminus.CLConstants.*;
import java.util.ArrayList;

/**
 * This class programatically generates the class file for 
 * the following Java application:
 * 
// IsPrime . java
public class IsPrime {
    // Returns true if n is prime , and false otherwise .
    private static boolean isPrime (int n) {
        if (n < 2) {
            return false ;
        }
        for (int i = 2; i <= n / i; i ++) {
            if (n % i == 0) {
                return false ;
            }
        }
        return true ;
    }
    // Entry point .
    public static void main ( String [] args ) {
        int n = Integer . parseInt ( args [0]);
        boolean result = isPrime (n);
        if ( result ) {
            System . out . println (n + " is a prime number ");
        }
        else {
            System . out . println (n + " is not a prime number ");
        }
    }
}
 */

public class GenIsPrime {   //公共的类 下面包含很多函数
    public static void main(String[] args) {  //主函数 下面调用了很多已经写好的方法函数
        CLEmitter e = new CLEmitter(true);//单个类
        ArrayList<String> accessFlags = new ArrayList<String>();//存了很多item的列表 缩小属性的范围，为了更少几率的出错

        // Add IsPrime class
        accessFlags.add("public");
        e.addClass(accessFlags, "IsPrime", "java/lang/Object", null, true);

        // Add the implicit no-arg constructor IsPrime()
        accessFlags.clear();
        accessFlags.add("public");
        e.addMethod(accessFlags, "<init>", "()V", null, true);
        e.addNoArgInstruction(ALOAD_0);
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/Object",
                                                    "<init>", "()V");
        e.addNoArgInstruction(RETURN);

        // Add isPrime() method to IsPrime
        accessFlags.clear();
        accessFlags.add("public");
        accessFlags.add("static");
        e.addMethod(accessFlags, "isPrime", "(I)Z", null, true);

        // Load n on the stack
        e.addNoArgInstruction(ILOAD_0);
        
        // Load the constant 2 on the stack
        e.addNoArgInstruction(ICONST_2);

        // Branch to "Label 1" if n >= 2
        e.addBranchInstruction(IF_ICMPGE, "Label 1");

        // Base case: load false on the stack and return it to the caller
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(IRETURN);

        // Emit label "Label 1"
        e.addLabel("Label 1");

        // Load the constant 2 on the stack
        e.addNoArgInstruction(ICONST_2);
        
        // Store constant 2 to i on the stack
        e.addNoArgInstruction(ISTORE_1);

        // Emit label "Label 4"
        e.addLabel("Label 4");
        
        // Load i on the stack
        e.addNoArgInstruction(ILOAD_1);

        // Load n and i on the stack and compute division
        e.addNoArgInstruction(ILOAD_0);
        e.addNoArgInstruction(ILOAD_1);
        e.addNoArgInstruction(IDIV);

        // Branch to "Label 2" if n / i > i
        e.addBranchInstruction(IF_ICMPGT, "Label 2");

        // Load n and i on the stack and perform MOD operation
        e.addNoArgInstruction(ILOAD_0);
        e.addNoArgInstruction(ILOAD_1);
        e.addNoArgInstruction(IREM);

        // Branch to "Label 3" if n % i != 0
        e.addBranchInstruction(IFNE, "Label 3");

        // Return false
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(IRETURN);

        // Emit label "Label 3"
        e.addLabel("Label 3");

        // Increase variable 1 by 1
        e.addIINCInstruction(1, 1);

        // Goto Label 4 for looping
        e.addBranchInstruction(GOTO, "Label 4");

        // Emit label "Label 2"
        e.addLabel("Label 2");

        // Return true
        e.addNoArgInstruction(ICONST_1);
        e.addNoArgInstruction(IRETURN);


        // Add main() method to Factorial
        accessFlags.clear();
        accessFlags.add("public");
        accessFlags.add("static");
        e.addMethod(accessFlags, "main", "([Ljava/lang/String;)V", null, true);

        // Get command-line argument n, convert it into an integer, and
        // store it away
        e.addNoArgInstruction(ALOAD_0);
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(AALOAD);
        //method 空格后到. 是路径，引号扩起来；下一段从.到：是名字，引号扩起来；再下一段是参数列表，引号再扩起来。
        e.addMemberAccessInstruction(INVOKESTATIC, "java/lang/Integer",
                                                   "parseInt", 
                                                   "(Ljava/lang/String;)I");
        e.addNoArgInstruction(ISTORE_1);
        e.addNoArgInstruction(ILOAD_1);
        e.addMemberAccessInstruction(INVOKESTATIC, "IsPrime",
                                                   "isPrime",
                                                   "(I)Z");
        e.addNoArgInstruction(ISTORE_2);
        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", "out",
                                                "Ljava/io/PrintStream;");

        e.addNoArgInstruction(ILOAD_1);
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream",
                                                    "print", 
                                                    "(I)V");

        e.addNoArgInstruction(ILOAD_2);
        e.addBranchInstruction(IFEQ, "Label 34");


        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", "out",
                                                "Ljava/io/PrintStream;");

        e.addLDCInstruction(" is a prime number");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream",
                                                   "println",
                                                   "(Ljava/lang/String;)V");

        e.addBranchInstruction(GOTO, "Label 42");

        e.addLabel("Label 34");
        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", "out",
                                                "Ljava/io/PrintStream;");

        e.addLDCInstruction(" is not a prime number");

        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream",
                                                    "println", 
                                "(Ljava/lang/String;)V");

        e.addLabel("Label 42");
        e.addNoArgInstruction(RETURN);

        // Write Factorial.class to file system
        e.write();
    }
}
