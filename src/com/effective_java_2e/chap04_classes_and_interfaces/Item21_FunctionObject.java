package com.effective_java_2e.chap04_classes_and_interfaces;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 21: Use function objects to represent strategies.
 *
 * Some languages support function pointers, delegates, lambda expressions, or similar facilities
 * that allow programs to store and transmit the ability to invoke a particular function.
 * Such facilities are typically used to allow the caller of a function to specialize its behavior
 * by passing in a second function.
 * This is an example of the Strategy pattern.
 *
 * Java does not provide function pointers, but object references can be used to achieve a similar effect.
 * Invoking a method on an object typically performs some operation on that object.
 * However, it is possible to define an object whose methods perform operations on other objects, passed explicitly to the methods.
 * An instance of a class that exports exactly one such method is effectively a pointer to that method.
 * Such instances are known as "function objects".
 *
 * A "concrete strategy" class is typically stateless: it has no fields, hence all instances of the class are functionally equivalent.
 * Thus it should be a singleton to save on unnecessary object creation costs.
 *
 * To pass a concrete strategy instance to a method, we need an appropriate type for the parameter.
 * We need to define a "strategy interface" to go with the concrete strategy class.
 *
 * Concrete strategy classes are often declared using anonymous classes.
 * But using an anonymous class in this way will create a new instance each time the call is executed.
 * If it is to be executed repeatedly, consider storing the function object in a private static final field and reusing it.
 * Another advantage of doing this is that you can give the field a descriptive name for the function object.
 *
 * Because the strategy interface serves as a type for all of its concrete strategy classes,
 * a concrete strategy class needn't be made public to export a concrete strategy.
 * Instead, a "host class" can export a public static field (or static factory method) whose type is the strategy interface,
 * and the concrete strategy class can be a private nested class of the host.
 *
 * To summarize:
 * A primary use of function pointers is to implement the Strategy pattern.
 * To implement this pattern in Java, declare an interface to represent the strategy,
 * and a class that implements this interface for each concrete strategy.
 * When a concrete strategy is used only once, it is typically declared and instantiated as an anonymous class.
 * When a concrete strategy is designed for repeated use, it is generally implemented as a private static member class
 * and exported in a public static final field whose type is the strategy interface.
 */
public class Item21_FunctionObject {

    /**
     * Strategy interface
     */
    public interface StringComparator<T> {
        public int compare(T t1, T t2);
    }

    /**
     * Concrete strategy class for string comparison
     */
    static class StringLengthComparator implements StringComparator<String> {
        public static final StringLengthComparator INSTANCE = new StringLengthComparator();

        private StringLengthComparator() {}

        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

    /**
     * Concrete strategy class declared as an anonymous class
     */
    static void sortStrings(String[] stringArray) {
        Arrays.sort(stringArray, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
    }

    /**
     * Exporting a concrete strategy
     */
    static class Host {
        // Concrete strategy class
        private static class StrLenCmp implements Comparator<String>, Serializable {
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        }

        // Returned comparator is serializable
        public static final Comparator<String> STRING_LENGTH_COMPARATOR = new StrLenCmp();
    }



    public static void main(String[] args) {

    }

}
