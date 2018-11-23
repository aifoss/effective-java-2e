package com.effective_java_2e.chap03_methods_common_to_all_objects;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 11: Override clone judiciously.
 *
 * The Cloneable interface was intended as a "mixin interface" for objects to advertise that they permit cloning.
 * Its primary flaw is that it lacks a clone method, and Object's clone method is protected.
 *
 * Cloneable determines the behavior of Object's protected clone implementation:
 * if a class implements Cloneable, Object's clone method returns a field-by-field copy of the object;
 * otherwise, it throws CloneNotSupportedException.
 *
 * The general contract for the clone method is:
 * Creates and returns a copy of this object.
 *   x.clone() != x
 *   x.clone().getClass() == x.getClass() -- not absolute requirement
 *   x.clone().equals(x) -- not absolute requirement
 *
 * If you override the clone method in a nonfinal class, you should return an object obtained by invoking super.clone.
 * In practice, a class that implements Cloneable is expected to provide a properly functioning public clone method.
 *
 * If every field contains a primitive value or a reference to an immutable object,
 * the object returned by invoking super.clone() may be exactly what you need,
 * in which case no further processing is necessary.
 *
 * It is legal for an overriding method's return type to be a subclass of the overridden method's return type.
 * Because Object.clone returns Object, PhoneNumber.clone must cast the result of super.clone() before returning it,
 * but this is far preferable to requiring every caller of PhoneNumber.clone to cast the result.
 * The general principle at play here is: Never make the client do anything the library can do for the client.
 *
 * In effect, the clone method functions as another constructor;
 * you must ensure that it does no harm to the original object and that it properly establishes invariants on the clone.
 *
 * The clone architecture is incompatible with normal use of final fields referring to mutable objects,
 * except in cases where the mutable objects may be safely shared between an object and its clone.
 * In order to make a class cloneable, it may be necessary to remove final modifiers from some fields.
 *
 * It is not always sufficient to call clone recursively.
 * If a class has an array of object references, you'll have to copy each array bucket individually.
 *
 * A final approach to cloning complex objects is to call super.clone, set all of the fields in the resulting object to their virgin state,
 * and then call higher-level methods to regenerate the state of the object.
 *
 * Like a constructor, a clone method should not invoke any nonfinal methods on the clone under construction.
 * If clone invokes an overridden method, this method will execute before the subclass in which it is defined
 * has had a chance to fix its state in the clone, quite possibly leading to corruption in the clone and the original.
 *
 * Object's clone method is declared to throw CloneNotSupportedException,
 * but overriding clone methods can omit this declaration.
 * Public clone methods should omit it because methods that don't throw checked exceptions are easier to use.
 * If a class that is designed for inheritance overrides clone, the overriding method should mimic the behavior of Object.clone:
 * it should be declared protected, it should be declared to throw CloneNotSupportedException,
 * and the class should not implement Cloneable.
 *
 * If you decide to make a thread-safe class implement Cloneable,
 * remember that its clone method must be properly synchronized just like any other method.
 * Object's clone method is not synchronized, so you may have to write a synchronized clone method that invokes super.clone().
 *
 * To recap:
 * All classes that implement Cloneable should override clone with a public method whose return type is the class itself.
 * This method should first call super.clone and then fix any fields that need to be fixed.
 * Typically this means copying any mutable objects that comprise the internal "deep structure" of the object being cloned,
 * and replacing the clone's references to these objects with references to the copies.
 * If the class contains only primitive fields or references to immutable objects,
 * then it is probably the case that no fields need to be fixed.
 * There are exceptions to this rule.
 * For example, a field representing a serial number of other unique ID or a field representing the object's creation time will need to be fixed.
 *
 * If you extend a class that implements Cloneable, you have little choice but to implement a well-behaved clone method.
 * Otherwise, you are better off providing an alternative means of objects copying, or simply not providing the capability.
 *
 * A fine approach to object copying is to provide a copy constructor or copy factory.
 * A copy constructor is simply a constructor that takes a single argument whose type is the class containing the constructor:
 * e.g., public Yum(Yum yum);
 * A copy factory is the static factory analog of a copy constructor:
 * e.g., public static Yum newInstance(Yum yum);
 *
 * Advantages of the copy constructor or copy factory approach over Cloneable/clone:
 * 1. They don't rely on a risk-prone extralinguistic object creation mechanism.
 * 2. They don't demand unenforceable adherence to thinly documented conventions.
 * 3. They don't conflict with the proper use of final fields.
 * 4. They don't throw unnecessary checked exceptions.
 * 5. They don't require casts.
 *
 * A copy constructor or factory can take an argument whose type is an interface implemented by the class.
 * Interface-based copy constructors and factories, known as "conversion constructors" and "conversion factories",
 * allow the client to choose the implementation type of the copy rather than forcing the client to accept the implementation of the original.
 *
 * Given all of the problems associated with Cloneable,
 * it's safe to say that other interfaces should not extend it, and that classes designed for inheritance should not implement it.
 */
public class Item11_Clone {

    /**
     * Class that invokes super.clone()
     */
    public static class PhoneNumber implements Cloneable {
        @Override
        public PhoneNumber clone() {
            try {
                return (PhoneNumber) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(); // Can't happen
            }
        }
    }

    /**
     * Class that invokes clone() recursively on array elements
     */
    public static class Stack implements Cloneable {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public Object pop() {
            if (size == 0)
                throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }

        // Ensure space for at least one more element.
        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }

        @Override
        public Stack clone() {
            try {
                Stack result = (Stack) super.clone();
                result.elements = elements.clone();
                return result;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    /**
     * Class with flawed clone method that shares internal state
     */
    public static class HashTable1 implements Cloneable {
        private Entry[] buckets = new Entry[10];

        private static class Entry {
            final Object key;
            Object value;
            Entry next;

            Entry(Object key, Object value, Entry next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        // Broken - results in shared internal state
        @Override
        public HashTable1 clone() {
            try {
                HashTable1 result = (HashTable1) super.clone();
                result.buckets = buckets.clone(); // References the same linked lists as the original
                return result;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    /**
     * Class with clone method that calls recursive deep copy method for array elements
     */
    public static class HashTable2 implements Cloneable {
        private Entry[] buckets = new Entry[10];

        private static class Entry {
            final Object key;
            Object value;
            Entry next;

            Entry(Object key, Object value, Entry next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }

            // Recursively copy the linked list headed by this Entry
            // May result in stack overflow if the list is long
            Entry deepCopy() {
                return new Entry(key, value, next == null ? null : next.deepCopy());
            }
        }

        // clone method that uses deep copy for array elements
        @Override
        public HashTable2 clone() {
            try {
                HashTable2 result = (HashTable2) super.clone();
                result.buckets = new Entry[buckets.length];
                for (int i = 0; i < buckets.length; i++) {
                    if (buckets[i] != null) {
                        result.buckets[i] = buckets[i].deepCopy();
                    }
                }
                return result;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    /**
     * Class with clone method that calls iterative deep copy method for array elements
     */
    public static class HashTable3 implements Cloneable {
        private Entry[] buckets = new Entry[10];

        private static class Entry {
            final Object key;
            Object value;
            Entry next;

            Entry(Object key, Object value, Entry next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }

            // Iteratively copy the linked list headed by this Entry
            Entry deepCopy() {
                Entry result = new Entry(key, value, next);

                for (Entry p = result; p.next != null; p = p.next) {
                    p.next = new Entry(p.next.key, p.next.value, p.next.next);
                }

                return result;
            }
        }

        // clone method that uses deep copy for array elements
        @Override
        public HashTable3 clone() {
            try {
                HashTable3 result = (HashTable3) super.clone();
                result.buckets = new Entry[buckets.length];
                for (int i = 0; i < buckets.length; i++) {
                    if (buckets[i] != null) {
                        result.buckets[i] = buckets[i].deepCopy();
                    }
                }
                return result;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }



    public static void main(String[] args) {

    }

}
