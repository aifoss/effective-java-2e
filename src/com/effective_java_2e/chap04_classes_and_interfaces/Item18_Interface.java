package com.effective_java_2e.chap04_classes_and_interfaces;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 18: Prefer interfaces to abstract classes.
 *
 * Reasons for preferring interfaces over abstract classes:
 *
 * 1. Existing classes can be easily retrofitted to implement a new interface.
 *
 * 2. Interfaces are ideal for defining mixins.
 *    A "mixin" is a type that a class can implement in addition to its primary type to declare that it provides some optional behavior.
 *
 * 3. Interfaces allow the construction of nonhierarchical type frameworks.
 *    If there are n attributes in the type system, there are 2^n possible combinations that you might have to support in a class hierarchy.
 *    This is what's known as a "combinatorial explosion".
 *    Bloated class hierarchies can lead to bloated classes containing many methods that differ only in the type of their arguments,
 *    as there are no types in the class hierarchy to capture common behaviors.
 *
 * 4. Interfaces enable safe, powerful functionality enhancements via the "wrapper class" idiom.
 *
 * You can combine the virtues of interfaces and abstract classes by providing an abstract skeletal implementation class
 * to go with each nontrivial interface that you export.
 * By convention, skeletal implementations are called Abstract[Interface], where [Interface] is the name of the interface they implement.
 * e.g., AbstractCollection, AbstractSet, AbstractList, AbstractMap.
 *
 * The beauty of skeletal implementations is that they provide the implementation assistance of abstract classes
 * without improving the severe constraints that abstract classes impose when they serve as type definitions.
 * For most implementors of an interface, extending the skeletal implementation is the obvious choice, but it is strictly optional.
 * If a preexisting class cannot be made to extend the skeletal implementation,
 * the class can always implement the interface manually.
 * The class implementing the interface can forward invocations of interface methods
 * to a contained instance of a private inner class that extends the skeletal implementation.
 * This technique, known as "simulated multiple inheritance", is closely related to the wrapper class idiom.
 * It provides most of the benefits of multiple inheritance, while avoiding the pitfalls.
 *
 * To write a skeletal implementation:
 * First you must study the interface and decide which methods are the primitives
 * in terms of which the others can be implemented.
 * These primitives will be the abstract methods in your skeletal implementation.
 * Then you must provide concrete implementations of all the other methods in the interface.
 *
 * Good documentation is absolutely essential for skeletal implementations.
 *
 * A minor variant on the skeletal implementation is the simple implementation (e.g., AbstractMap.SimpleEntry).
 * A simple implementation is like a skeletal implementation in that it implements an interface and is designed for inheritance,
 * but it differs in that it isn't abstract: it is the simplest possible working implementation.
 *
 * Using abstract classes to define types that permit multiple implementations has one great advantage over using interfaces:
 * It is far easier to evolve an abstract class than an interface.
 * It is, generally speaking, impossible to add a method to a public interface without breaking all existing classes that implement the interface.
 *
 * Public interfaces must be designed carefully.
 * Once an interface is released and widely implemented, it is almost impossible to change.
 */
public class Item18_Interface {

    /**
     * Interface extending other interfaces
     */
    public interface SignerSongwriter extends Singer, Songwriter {
        AudioClip strum();
        void actSensitive();
    }

    public interface Singer {
        AudioClip sing(Song s);
    }

    public interface Songwriter {
        Song compose(boolean hit);
    }

    class AudioClip {}

    class Song {}

    /**
     * Concrete implementation built atop skeletal implementation
     */
    static List<Integer> intArraysAsList(final int[] a) {
        if (a == null)
            throw new NullPointerException();

        return new AbstractList<Integer>() {
            public Integer get(int i) {
                return a[i]; // Autoboxing
            }

            @Override
            public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // Auto-unboxing
                return oldVal;  // Autoboxing
            }

            public int size() {
                return a.length;
            }
        };
    }

    /**
     * Skeletal implementation of Map.Entry interface
     */
    public abstract static class AbstractMapEntry<K,V> implements Map.Entry<K,V> {
        // Primitive operations
        public abstract K getKey();
        public abstract V getValue();

        // Entries in modifiable maps must override this method
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        // Implements the general contract of Map.Entry equals
        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (! (o instanceof Map.Entry))
                return false;

            Map.Entry<?,?> arg = (Map.Entry) o;
            return equals(getKey(), arg.getKey()) &&
                    equals(getValue(), arg.getValue());
        }

        private static boolean equals(Object o1, Object o2) {
            return o1 == null ? o2 == null : o1.equals(o2);
        }

        // Implements the general contract of Map.Entry hashCode
        @Override
        public int hashCode() {
            return hashCode(getKey()) ^ hashCode(getValue());
        }

        private static int hashCode(Object o) {
            return o == null ? 0 : o.hashCode();
        }
    }



    public static void main(String[] args) {

    }

}
