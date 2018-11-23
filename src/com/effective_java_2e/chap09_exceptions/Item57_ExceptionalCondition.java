package com.effective_java_2e.chap09_exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 57: Use exceptions only for exceptional conditions.
 *
 * Exceptions are to be used only for exceptional conditions;
 * they should never be used for ordinary control flow.
 *
 * A well-designed API must not force its clients to use exceptions for ordinary control flow.
 *
 * A class with a "state-dependent" method that can be invoked only under certain unpredictable conditions
 * should generally have a separate "state-testing" method indicating whether it is appropriate to invoke the state-dependent method.
 * An alternative to providing a separate state-testing method is to have the state-dependent method return a distinguished value
 * such as null if it is invoked with the object in an inappropriate state.
 *
 * Guidelines to help you choose between a state-testing method and a distinguished return value:
 *
 * If an object is to be accessed concurrently without external synchronization or is subject to externally induced state transitions,
 * you must use a distinguished return value,
 * as the object's state could change in the interval between the invocation of a state-testing method and its state-dependent method.
 *
 * Performance concerns may dictate that a distinguished return value be used
 * if a separate state-testing method would duplicate the work of the state-dependent method.
 *
 * All other things being equal, a state-testing method is mildly preferable to a distinguished return value.
 */
public class Item57_ExceptionalCondition {

    static class Mountain {
        public void climb() {}
    }

    static class Foo {

    }

    public static void main(String[] args) {
        /**
         * Exception-based loop
         * Horrible abuse of exceptions. Don't ever do this!
         */
        Mountain[] range = new Mountain[10];
        try {
            int i = 0;
            while (true)
                range[i++].climb();
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        /**
         * Do not use this hideous code for iteration over a collection!
         */
        try {
            Collection<Foo> collection = new ArrayList<>();
            Iterator<Foo> i = collection.iterator();
            while (true) {
                Foo foo = i.next();
            }
        } catch (NoSuchElementException e) {

        }
    }

}
