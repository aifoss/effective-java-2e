package com.effective_java_2e.chap04_classes_and_interfaces;

import java.util.*;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 16: Favor composition over inheritance.
 *
 * Unlike method invocation, inheritance violates encapsulation.
 * A subclass depends on the implementation details of its superclass for its proper function.
 * A related cause of fragility in subclasses is that their superclass can acquire new methods in subsequent releases.
 *
 * Instead of extending an existing class, give your new class a private field that references an instance of the existing class.
 * This design is called "composition" because the existing class becomes a component of the new one.
 * Each instance method in the new class invokes the corresponding method on the contained instance of the existing class and returns the results.
 * This is known as "forwarding", and the methods in the new class are known as "forwarding methods".
 * The resulting class will be rock solid, with no dependencies on the implementation details of the existing class.
 * The composition-and-forwarding approach implementation is broken into two pieces: the class itself and a reusable "forwarding class".
 *
 * The InstrumentedSet class below is known as a "wrapper" class because each InstrumentedSet instance contains ("wraps") another Set instance.
 * This is also known as the "Decorator" pattern, because the InstrumentedSet class "decorates" a set by adding instrumentation.
 *
 * Sometimes the combination of composition and forwarding is loosely referred to as "delegation".
 * Technically, it's not delegation unless the wrapper object passes itself to the wrapped object.
 *
 * The disadvantages of wrapper classes are few.
 * One caveat is that wrapper classes are not suited for use in "callback frameworks",
 * wherein objects pass self-references to other objects for subsequent invocations ("callbacks").
 * Because a wrapped object doesn't know of its wrapper, it passes a reference to itself (this) and callbacks elude the wrapper.
 * This is known as the SELF problem.
 *
 * Inheritance is appropriate only in circumstances where the subclass really is a subtype of the superclass.
 *
 * Inheritance propagates any flaws in the superclass's API, while composition lets you design a new API that hides these flaws.
 *
 * To summarize:
 * Inheritance is powerful, but it is problematic because it violates encapsulation.
 * It is appropriate only when a genuine subtype relationship exists between the subclass and the superclass.
 * Even then, inheritance may lead to fragility if the subclass is in a different package from the superclass
 * and the superclass is not designed for inheritance.
 * To avoid this fragility, use composition and forwarding instead of inheritance,
 * especially if an appropriate interface to implement a wrapper class exists.
 */
public class Item16_Composition {

    /**
     * Broken - Inappropriate use of inheritance
     */
    public static class InstrumentedHashSet<E> extends HashSet<E> {
        // The number of attempted element insertions
        private int addCount = 0;

        public InstrumentedHashSet() {

        }

        public InstrumentedHashSet(int initCap, float loadFactor) {
            super(initCap, loadFactor);
        }

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

    /**
     * Wrapper class - Uses composition in place of inheritance
     */
    public static class InstrumentedSet<E> extends ForwardingSet<E> {
        private int addCount = 0;

        public InstrumentedSet(Set<E> s) {
            super(s);
        }

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

    /**
     * Reusable forwarding class
     */
    public static class ForwardingSet<E> implements Set<E> {
        private final Set<E> s;

        public ForwardingSet(Set<E> s) {
            this.s = s;
        }

        public void clear() {
            s.clear();
        }

        public boolean contains(Object o) {
            return s.contains(o);
        }

        public boolean isEmpty() {
            return s.isEmpty();
        }

        public int size() {
            return s.size();
        }

        public Iterator<E> iterator() {
            return s.iterator();
        }

        public boolean add(E e) {
            return s.add(e);
        }

        public boolean remove(Object o) {
            return s.remove(o);
        }

        public boolean containsAll(Collection<?> c) {
            return s.containsAll(c);
        }

        public boolean addAll(Collection<? extends E> c) {
            return s.addAll(c);
        }

        public boolean removeAll(Collection<?> c) {
            return s.removeAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return s.retainAll(c);
        }

        public Object[] toArray() {
            return s.toArray();
        }

        public <T> T[] toArray(T[] a) {
            return s.toArray(a);
        }

        @Override
        public boolean equals(Object o) {
            return s.equals(o);
        }

        @Override
        public int hashCode() {
            return s.hashCode();
        }

        @Override
        public String toString() {
            return s.toString();
        }
    }

    static void walk(Set<Dog> dogs) {
        InstrumentedSet<Dog> iDogs = new InstrumentedSet<>(dogs);
    }

    static class Dog {

    }


    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));
        // Returns 6 instead of 3 because each element added with the addAll method is double-counted
        // HashSet's addAll implementation invokes add
        System.out.println(s.getAddCount());
    }

}
