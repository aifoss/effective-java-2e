package com.effective_java_2e.chap07_methods;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 42: Use varargs judiciously.
 *
 * Varargs methods, formally known as "variable arity methods", accept zero or more arguments of a specified type.
 *
 * Sometimes it's appropriate to write a method that requires one or more arguments of some type, rather than zero or more.
 * The right away to use varargs in this case is to declare the method to take two parameters, one normal parameter and one varargs parameter.
 *
 * Don't retrofit every method that has a final array parameter;
 * use varargs only when a call really operates on a variable-length sequence of values.
 *
 * Exercise care when using the varargs facility in performance-critical situations.
 * Every invocation of a varargs method causes an array allocation and initialization.
 *
 * In summary:
 * Varargs methods are a convenient way to define methods that require a variable number of arguments, but they should not be overused.
 */
public class Item42_Varargs {

    /**
     * Simple use of varargs
     */
    static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }

    /**
     * The WRONG way to use varargs to pass one or more arguments
     * If the client invokes this method with no arguments, it fails at runtime rather than compile time.
     */
    static int min(int... args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Too few arguments");
        int min = args[0];
        for (int i = 1; i < args.length; i++)
            if (args[i] < min)
                min = args[i];
        return min;
    }

    /**
     * The right awy to use varargs to pass one or more arguments
     */
    static int min2(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs)
            if (arg < min)
                min = arg;
        return min;
    }

    /**
     * Using method overloading to restrict cost associated with varargs
     */
    public void foo() {}
    public void foo(int a1) {}
    public void foo(int a1, int a2) {}
    public void foo(int a1, int a2, int a3) {}
    public void foo(int a1, int a2, int a3, int... rest) {}



    public static void main(String[] args) {

    }

}
