package com.effective_java_2e.chap08_general_programming;

import java.util.Comparator;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 49: Prefer primitive types to boxed primitives.
 *
 * Java has a two-part type system,
 * consisting of "primitives", such as int, double, and boolean,
 * and "reference types", such as String and List.
 * Every primitive type has a corresponding reference type, called a "boxed primitive".
 * The boxed primitives corresponding to int, double, and boolean are Integer, Double, and Boolean.
 *
 * The "autoboxing" and "auto-unboxing" features blur but do not erase the distinction between the primitive and boxed primitive types.
 *
 * There are 3 major differences between primitives and boxed primitives:
 *
 * 1. Primitives have only their values,
 *    whereas boxed primitives have identities distinct from their values.
 *
 *    Two boxed primitive instances can have the same value and different identities.
 *
 * 2. Primitive types have only fully functional values,
 *    whereas each boxed primitive type has one nonfunctional value, which is null,
 *    in addition to all of the functional values of its corresponding primitive type.
 *
 * 3. Primitives are generally more time- and space-efficient than boxed primitives.
 *
 * Applying the == operator to boxed primitives is almost always wrong.
 *
 * In nearly every case when you mix primitives and boxed primitives in a single operation,
 * the boxed primitive is auto-unboxed.
 * If a null object reference is auto-unboxed, you get a NullPointerException.
 *
 * When should you use boxed primitives?
 *
 * 1. You must use boxed primitives as elements, keys, and values in collections.
 *
 * 2. You must use boxed primitives as type parameters in parametrized types.
 *
 * 3. You must use boxed primitives when making reflective method invocations.
 *
 * In summary:
 * Use primitives in preference to boxed primitives whenever you have the choice.
 * Autoboxing reduces the verbosity, but not the danger, of using boxed primitives.
 * When your program compares two boxed primitives with the == operator, it does an identity operation,
 * which is almost certainly not what you want.
 * When your program does mixed-type computations involving boxed and unboxed primitives,
 * it does unboxing, and when your program does unboxing, it can throw a NullPointerException.
 * When your program boxes primitive values, it can result in costly and unnecessary object creations.
 */
public class Item49_PrimitiveType {

    /**
     * Broken comparator
     */
    Comparator<Integer> naturalOrder = new Comparator<Integer>() {
        public int compare(Integer first, Integer second) {
            return first < second ? -1 : (first == second ? 0 : 1);
        }
    };

    /**
     * Fixed comparator
     */
    Comparator<Integer> naturalOrder2 = new Comparator<Integer>() {
        public int compare(Integer first, Integer second) {
            int f = first;  // Auto-unboxing
            int s = second; // Auto-unboxing
            return f < s ? -1 : (f == s ? 0 : 1);   // No unboxing
        }
    };

    /**
     * Causes NullPointerException
     */
    public static class Unbelievable {
        static Integer i;

        public static void main(String[] args) {
            if (i == 42)
                System.out.println("Unbelievable");
        }
    }

    /**
     * Fixed
     */
    public static class Unbelievable2 {
        static int i;

        public static void main(String[] args) {
            if (i == 42)
                System.out.println("Unbelievable");
        }
    }



    public static void main(String[] args) {
        /**
         * Hideously slow program
         */
        Long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

}
