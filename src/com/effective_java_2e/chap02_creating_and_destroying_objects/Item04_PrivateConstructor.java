package com.effective_java_2e.chap02_creating_and_destroying_objects;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 4: Enforce noninstantiability with a private constructor.
 *
 * A class can be made noninstantiable by including a private constructor.
 * This idiom also prevents the class from being subclassed.
 */
public class Item04_PrivateConstructor {

    /**
     * Noninstantiable utility class
     */
    public static class UtilityClass {
        // Suppress default constructor for noninstantiability
        private UtilityClass() {
            throw new AssertionError();
        }
    }



    public static void main(String[] args) {

    }

}
