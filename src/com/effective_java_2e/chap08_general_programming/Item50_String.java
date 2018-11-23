package com.effective_java_2e.chap08_general_programming;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 50: Avoid strings where other types are more appropriate.
 *
 * Strings are poor substitutes for other value types.
 * If there's an appropriate value type, whether primitive or object reference, you should use it.
 * If there isn't, you should write one.
 *
 * Strings are poor substitutes for enum types.
 *
 * Strings are poor substitutes for aggregate types.
 *
 * Strings are poor substitutes for capabilities.
 */
public class Item50_String {

    /**
     * Broken - inappropriate use of string as capability
     */
    public static class ThreadLocal {
        private ThreadLocal() {}

        // Sets the current thread's value for the named variable
        public static void set(String key, Object value) {}

        // Returns the current thread's value for the named variable
        public static Object get(String key) { return null; }
    }

    /**
     * Fixed - but can do better
     */
    public static class ThreadLocal2 {
        private ThreadLocal2() {}

        public static class Key {   // Capability
            Key() {}
        }

        // Generates a unique, unforgeable key
        public static Key getKey() {
            return new Key();
        }

        public static void set(Key key, Object value) {}
        public static Object get(Key key) { return null; }
    }

    /**
     * Fixed - but not typesafe
     */
    public static final class ThreadLocal3 {
        public ThreadLocal3() {}
        public void set(Object value) {}
        public Object get() { return null; }
    }

    /**
     * Fixed and typesafe
     */
    public static final class ThreadLocal4<T> {
        public ThreadLocal4() {}
        public void set(T value) {}
        public T get() { return null; }
    }



    public static void main(String[] args) {

    }

}
