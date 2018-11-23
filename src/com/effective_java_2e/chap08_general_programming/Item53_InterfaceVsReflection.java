package com.effective_java_2e.chap08_general_programming;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 53: Prefer interfaces to reflection.
 *
 * The core reflection facility, java.lang.reflect, offers programmatic access to information about loaded classes.
 * Furthermore, Constructor, Method, and Field instances let you manipulate their underlying counterparts reflectively.
 * Reflection allows one class to use another, even if the latter class did not exist when the former was compiled.
 *
 * The power of reflection, however, comes at a price:
 *
 * 1. You lose all the benefits of compile-time type checking, including exception checking.
 * 2. The code required to perform reflective access is clumsy and verbose.
 * 3. Reflective method invocation is much slower than normal method invocation.
 *
 * Reflection is used only at design time.
 * As a rule, objects should not be accessed reflectively in normal applications at runtime.
 *
 * You can obtain many of the benefits of reflection while incurring few of its costs by using it only in a very limited form.
 * For many programs that must use a class that is unavailable at compile time,
 * there exists at compile time an appropriate interface or superclass by which to refer to the class.
 * If this is the case, you can create instances reflectively and access them normally via their interface or superclass.
 * This technique is sufficiently powerful to implement a full-blown "service provider framework".
 *
 * Two disadvantages of reflection:
 *
 * 1. It can generate runtime errors, which would have been compile-time errors if reflective instantiation were not used.
 * 2. It takes many lines of tedious code to generate an instance of the class from its name,
 *    whereas a constructor invocation would fit nearly on a single line.
 *
 * A legitimate, if rare, use of reflection is to manage a class's dependencies on other classes, methods, or fields
 * that may be absent at runtime.
 *
 * In summary:
 * Reflection is a powerful facility that is required for certain sophisticated programming tasks, but it has many disadvantages.
 * If you are writing a program that has to work with classes unknown at compile time,
 * you should, if at all possible, use reflection only to instantiate objects,
 * and access the objects using some interface or superclass that is known at compile time.
 */
public class Item53_InterfaceVsReflection {

    public static void main(String[] args) {
        /**
         * Reflective instantiation with interface access
         */
        // Translate the class name into a Class object
        Class<?> cl = null;
        try {
            cl = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
            System.exit(1);
        }

        // Instantiate the class
        Set<String> s = null;
        try {
            s = (Set<String>) cl.newInstance();
        } catch (IllegalAccessException e) {
            System.err.println("Class not accessible");
            System.exit(1);
        } catch (InstantiationException e) {
            System.err.println("Class not instantiable");
            System.exit(1);
        }

        // Exercise the set
        s.addAll(Arrays.asList(args).subList(1, args.length));
        System.out.println(s);
    }

}
