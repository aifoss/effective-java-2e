package com.effective_java_2e.chap04_classes_and_interfaces;

import java.util.Date;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 17: Design and document for inheritance or else prohibit it.
 *
 * To document a class so that it can be safely subclassed, you must describe implementation details that should otherwise be left unspecified.
 *
 * What does it mean for a class to be designed and documented for inheritance?
 *
 * 1. The class must document its self-use of overridable methods.
 *    More generally, a class must document any circumstances under which it might invoke an overridable method.
 *
 * 2. A class may have to provide hooks into its internal workings in the form of judiciously chosen protected methods or protected fields.
 *
 * The only way to test a class designed for inheritance is to write subclasses.
 * You must test your class by writing subclasses before you release it.
 *
 * Restrictions that a class must obey to allow inheritance:
 *
 * 1. Constructors must not invoke overridable methods, directly or indirectly.
 *
 *    The superclass constructor runs before the subclass constructor,
 *    so the overriding method in the subclass will get invoked before the subclass constructor has run.
 *    If the overriding method depends on any initialization performed by the subclass constructor,
 *    the method will not behave as expected.
 *
 * 2. If you implement Cloneable or Serializable in a class designed for inheritance,
 *    neither clone nor readObject may invoke an overridable method, directly or indirectly.
 *
 * 3. If you implement Serializable in a class designed for inheritance and the class has a readResolve or writeReplace method,
 *    you must make the readResolve or writeReplace method protected rather than private.
 *
 *    If these methods are private, they will be silently ignored by subclasses.
 *
 * Designing a class for inheritance places substantial limitations on the class.
 * There are some situations where it is clearly the right thing to do,
 * such as abstract classes, including skeletal implementations of interfaces.
 * There are other situations where it is clearly the wrong thing to do,
 * such as immutable classes.
 *
 * The best solution to this problem is to prohibit subclassing in classes that are not designed and documented to be safely subclassed.
 *
 * Two ways to prohibit subclassing:
 * 1. Declare the class final.
 * 2. Make all the constructors private or package-private and add public static factories in place of constructors.
 *
 * You can eliminate a class's self-use of overridable methods mechanically, without changing its behavior.
 * Move the body of each overridable method to a private helper method and have each overridable method invoke its private helper method.
 * Then replace each self-use of an overridable method with a direct invocation of the overridable method's private helper method.
 */
public class Item17_Inheritance {

    /**
     * Documentation of remove method in java.util.AbstractCollection
     */
    // public boolean remove(Object o)
    /**
     * Removes a single instance of the specified element from this collection, if it
     * is present (optional operation). More formally, removes an element e such
     * that (o==null ? e==null : o.equals(e)), if the collection contains one or
     * more such elements. Returns true if the collection contained the specified
     * element (or equivalently, if the collection changed as a result of the call).
     *
     * This implementation iterates over the collection looking for the specified element.
     * If it finds the element, it removes the element from the collection using
     * the iterator’s remove method. Note that this implementation throws an
     * UnsupportedOperationException if the iterator returned by this collection’s
     * iterator method does not implement the remove method.
     */

    /**
     * Dcoumentation exposing protected method from java.util.AbstractList
     */
    // protected void removeRange(int fromIndex, int toIndex)
    /**
     * Removes from this list all of the elements whose index is between
     * fromIndex, inclusive, and toIndex, exclusive. Shifts any succeeding
     * elements to the left (reduces their index). This call shortens the ArrayList
     * by (toIndex - fromIndex) elements. (If toIndex == fromIndex, this
     * operation has no effect.)
     *
     * This method is called by the clear operation on this list and its sublists.
     * Overriding this method to take advantage of the internals of the list implementation
     * can substantially improve the performance of the clear operation
     * on this list and its sublists.
     *
     * This implementation gets a list iterator positioned before fromIndex and repeatedly
     * calls ListIterator.next followed by ListIterator.remove, until
     * the entire range has been removed. Note: If ListIterator.remove
     * requires linear time, this implementation requires quadratic time.
     *
     * Parameters:
     *  fromIndex index of first element to be removed.
     *  toIndex index after last element to be removed.
     */

    /**
     * Class where superclass constructor invokes an overridable method
     */
    public static class Super {
        // Broken - constructor invokes an overridable method
        public Super() {
            overrideMe();
        }

        public void overrideMe() {

        }
    }

    /**
     * Subclass that overrides the overrideMe method
     */
    public static final class Sub extends Super {
        private final Date date;

        Sub() {
            date = new Date();
        }

        // Overriding method invoked by superclass constructor
        @Override
        public void overrideMe() {
            System.out.println(date);
        }

        public static void main(String[] args) {
            Sub sub = new Sub();
            sub.overrideMe();
        }
    }



    public static void main(String[] args) {

    }

}
