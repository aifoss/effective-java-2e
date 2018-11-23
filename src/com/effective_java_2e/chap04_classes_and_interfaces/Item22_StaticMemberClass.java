package com.effective_java_2e.chap04_classes_and_interfaces;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 22: Favor static member classes over nonstatic.
 *
 * A "nested class" is a class defined within another class.
 * A nested class should exist only to serve its enclosing class.
 *
 * There are four kinds of nested classes:
 * (a) static member classes
 * (b) nonstatic member classes
 * (c) anonymous classes
 * (d) local classes
 *
 * All but the first kind are known as "inner classes".
 *
 * A static member class has access to all of the enclosing class's members, even those declared private.
 * It is a static member of its enclosing class and obeys the same accessibility rules as other static members.
 *
 * One common use of a static member class is as a public helper class, useful only in conjunction with its outer class.
 *
 * Each instance of a nonstatic member class is implicitly associated with an "enclosing instance" of its containing class.
 * Within instance methods of a nonstatic member class,
 * you can invoke methods on the enclosing instance or obtain a reference to the enclosing instance using the "qualified this" construct.
 *
 * If an instance of a nested class can exist in isolation from an instance of its enclosing class,
 * then the nested class must be a static member class.
 *
 * The association between a nonstatic member class instance and its enclosing instance is established when the former is created.
 * Normally, the association is established automatically by invoking a nonstatic member class constructor
 * from within an instance method of the enclosing class.
 * It is possible, although rare, to establish the association manually using the expression enclosingInstance.newMemberClass(args).
 *
 * One common use of a nonstatic member class is to define an Adapter that allows an instance of the outer class to be viewed
 * as an instance of some unrelated class.
 *
 * If you declare a member class that does not require access to an enclosing instance,
 * always put the "static" modifier in its declaration.
 *
 * A common use of private static member classes is to represent components of the object represented by their enclosing class.
 * For example, many Map implementations have an internal Entry object for each key-value pair in the map.
 *
 * It is doubly important to choose correctly between a static and a nonstatic member class
 * if the class in question is public or protected member of an exported class.
 *
 * Anonymous classes are simultaneously declared and instantiated at the point of use.
 * They are permitted at any point in the code where an expression is legal.
 * Anonymous classes have enclosing instances if and only if they occur in a nonstatic context.
 * But even if they occur in a static context, they cannot have any static members.
 * Clients of an anonymous class can't invoke any members except those it inherits from its supertype.
 *
 * One common use of anonymous classes is to create "function objects" on the fly.
 * Another common use of anonymous classes is to create "process objects", such as Runnable, Thread, or TimerTask instances.
 * A third common use is within static factory methods.
 *
 * A local class can be declared anywhere a local variable can be declared and obeys the same scoping rules.
 * Like member classes, they have names and can be used repeatedly.
 * Like anonymous classes, they have enclosing instances only if they are defined in a nonstatic context,
 * and they cannot contain static members.
 *
 * To recap:
 * If a nested class needs to be visible outside of a single method or is too long to fit comfortably inside a method,
 * use a member class.
 * If each instance of the member class needs a reference to its enclosing instance, make it nonstatic;
 * otherwise, make it static.
 * Assuming the class belongs inside a method,
 * if you need to create instances from only one location and there is a preexisting type that characterizes the class,
 * make it an anonymous class;
 * otehrwise, make it a local class.
 */
public class Item22_StaticMemberClass {

    /**
     * Typical use of a nonstatic member class
     */
    public class MySet<E> extends AbstractSet<E> {
        public Iterator<E> iterator() {
            return new MyIterator();
        }

        public int size() {
            return 0;
        }

        // Nonstatic member class
        private class MyIterator implements Iterator<E> {
            public E next() {
                return null;
            }

            public boolean hasNext() {
                return false;
            }
        }
    }



    public static void main(String[] args) {

    }

}
