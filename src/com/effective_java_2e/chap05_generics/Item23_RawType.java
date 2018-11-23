package com.effective_java_2e.chap05_generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by sofia on 5/16/17.
 */

/**
 * Item 23: Don't use raw types in new code.
 *
 * A class or interface whose declaration has one or more type parameters is a generic class or interface.
 * Generic classes and interfaces are collectively known as "generic types".
 *
 * Each generic type defines a set of "parameterized types",
 * which consist of the class or interface name following by an angle-bracketed list of "actual type parameters"
 * corresponding to the generic type's formal type parameters.
 *
 * Each generic type defines a "raw type",
 * which is the name of the generic type used without any accompanying actual type parameters.
 *
 * If you use raw types, you lose all the safety and expressiveness benefits of generics.
 *
 * There are subtyping rules for generics,
 * and List<String> is a subtype of the raw type List, but not of the parameterized type List<Object>.
 * As a consequence, you lose type safety if you use a raw type like List,
 * but not if you use a parameterized type like List<Object>.
 *
 * Java provides a safe alternative to raw types known as "unbounded wildcard types".
 * If you want to use a generic type but you don't know or care what the actual type parameter is,
 * you can use a question mark instead.
 *
 * You can't put any element (other than null) into a Collection<?>.
 * And you can't assume anything about the type of the objects that you get out.
 * If these restrictions are unacceptable, you can use "generic methods" or "bounded wildcard types".
 *
 * There are two minor exceptions to the rule that you should not use raw types,
 * both of which stem from the fact that generic type information is erased at runtime:
 *
 * 1. You must use raw types in class literals.
 *    e.g., List.class, String[].class, int.class, not List<String>.class, List<?>.class
 *
 * 2. It is illegal to use the instanceof operator on parameterized types other than unbounded wildcard types.
 *
 * Parameterized type: List<String>
 * Actual type parameter: String
 * Generic type: List<E>
 * Formal type parameter: E
 * Unbounded wildcard type: List<?>
 * Raw type: List
 * Bounded type parameter: <E extends Number>
 * Recursive type bound: <T extends Comparable<T>>
 * Bounded wildcard type: List<? extends Number>
 * Generic method: static <E> List<E> asList(E[] a)
 * Type token: String.class
 */
public class Item23_RawType {

    public static void main(String[] args) {
        /**
         * Raw collection type - don't do this
         */
        Collection stamps = new ArrayList();

        // Erroneous insertion of coin into stamp collection
        stamps.add(new Coin());

        /**
         * Raw iterator type - don't do this
         */
        for (Iterator i = stamps.iterator(); i.hasNext(); ) {
            Stamp s = (Stamp) i.next(); // Throws ClassCastException
        }

        /**
         * Parameterized collection type - type-safe
         */
        Collection<Stamp> stamps2 = new ArrayList<>();

        /**
         * For-each loop over a parameterized collection - typesafe
         */
        for (Stamp s : stamps2) {
            // Do something
        }

        /**
         * For loop with parameterized iterator declaration - typesafe
         */
        for (Iterator<Stamp> i = stamps2.iterator(); i.hasNext(); ) {
            Stamp s = i.next(); // No cast necessary
            // Do something
        }

        /**
         * Uses raw type (List) - fails at runtime with ClassCastException
         */
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, new Integer(42));
        String s = strings.get(0); // Compiler-generated cast

        /**
         * Legitimate use of raw type - instanceof operator
         */
        Object o = null;
        if (o instanceof Set) {     // Raw type
            Set<?> m = (Set<?>) o;  // Wildcard type
        }
    }

    // Type-unsafe add method using raw type List
    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }

    /**
     * Use of raw type for unknown element type - don't do this
     */
    static int numElementsInCommon(Set s1, Set s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1))
                result++;
        }
        return result;
    }

    /**
     * Unbounded wildcard type - typesafe and flexible
     */
    static int numElementsInCommon2(Set<?> s1, Set<?> s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1))
                result++;
        }
        return result;
    }


    private static class Stamp {}

    private static class Coin {}

}
