package com.effective_java_2e.chap02_creating_and_destroying_objects;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 6: Eliminate obsolete object references.
 *
 * An obsolete reference is a reference that will never be dereferenced again.
 *
 * Memory leaks in garbage-collected languages ("unintentional object retentions") are insidious.
 * If an object reference is unintentionally retained, not only is that object excluded from garbage collection,
 * but so too are any objects referenced by that object, and so on.
 *
 * The fix for this problem is simple: null out references once they become obsolete.
 * An added benefit of nulling out obsolete references is that, if they are subsequently dereferenced by mistake,
 * the program will immediately fail with a NullPointerException, rather than quietly doing the wrong thing.
 *
 * Nulling out object references should be the exception rather than the norm.
 * The best way to eliminate an obsolete reference is to let the variable that contained the reference fall out of scope.
 * This occurs naturally if you define each variable in the narrowest possible scope.
 *
 * Common source of memory leaks:
 * 1. Whenever a class manages its own memory, the programmer should be alert for memory leaks.
 *    Whenever an element is freed, any object reference contained in the element should be nulled out.
 * 2. Another common source of memory leaks is caches.
 *    If you implement a cache for which an entry is relevant exactly so long as there are references to its key outside of the cache,
 *    represent the cache as a WeakHashMap; entries will be removed automatically  after they become obsolete.
 *    If the useful lifetime of a cache entry is less well-defined, the cache should occasionally be cleansed of entries that have fallen into disuse.
 *    This can be done by a background thread (perhaps a Timer or ScheduledThreadPoolExecutor) or as a side effect of adding new entries to the cache.
 *    The LinkedHashMap class facilitates the latter approach with its removeEldestEntry method.
 * 3. A third common source of memory leaks is listeners and other callbacks.
 *    If you implement an API where clients register callbacks but don't deregister them explicitly, they will accumulate unless you take some action.
 *    The best way do ensure that callbacks are garbage-collected promptly is to store only weak references to them,
 *    for instance, by storing them only as keys in a WeakHashMap.
 */
public class Item06_ObsoleteObjectReference {

    /**
     * Stack implementation with memory leak
     *
     * If a stack grows then shrinks, the objects that were popped off the stack will not be garbage collected,
     * even if the program using the stack has no more references to them.
     * This is because the stack maintains obsolete references to these objects.
     * Any references outside of the "active portion" of the element array are obsolete.
     * The active portion consists of the elements whose index is less than size.
     */
    public static class Stack1 {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack1() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        // Memory leak due to obsolete references
        public Object pop() {
            if (size == 0) throw new EmptyStackException();
            return elements[--size];
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }
    }

    /**
     * Corrected version
     */
    public static class Stack2 {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack2() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        // Memory leak prevented
        public Object pop() {
            if (size == 0) throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }
    }



    public static void main(String[] args) {

    }

}
