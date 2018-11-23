package com.effective_java_2e.chap05_generics;

import java.util.Arrays;

/**
 * Created by sofia on 5/17/17.
 */

/**
 * Item 24: Eliminate unchecked warnings.
 *
 * Eliminate every unchecked warning that you can.
 * If you can't eliminate a warning, and you can prove that the code that provoked the warning is typesafe,
 * then (and only then) suppress the warning with an @SuppressWarnings("unchecked") annotation.
 * Always use the SuppressWarnings annotation on the smallest scope possible.
 *
 * Every time you use a @SuppressWarnings("unchecked") annotation, add a comment saying why it's safe to do so.
 */
public class Item24_UncheckedWarning<U> {

    private int size;
    private U[] elements;

    /**
     * Unchecked cast
     */
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /**
     * Adding local variable to reduce scope of @SuppressWarnings
     */
    public <T> T[] toArray2(T[] a) {
        if (a.length < size) {
            // This cast is correct because the array we're creating
            // is of the same type as the one passed in, which is T[]
            @SuppressWarnings("unchecked")
            T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }



    public static void main(String[] args) {

    }

}
