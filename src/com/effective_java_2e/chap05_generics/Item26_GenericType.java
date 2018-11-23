package com.effective_java_2e.chap05_generics;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Created by sofia on 5/17/17.
 */

/**
 * Item 26: Favor generic types.
 *
 * The first step in generifying a class is to add one or more type parameters to its declaration.
 * The next step is to replace all the uses of the type Object with the appropriate type parameter.
 *
 * You can't create an array of a non-reifiable type, such as E.
 * This problem arises every time you write a generic type that is backed by an array.
 *
 * There are two ways to solve it:
 *
 * 1. The first solution directly circumvents the prohibition on generic array creation:
 *    create an array of Object and cast it to the generic array type.
 *    This usage is legal, but it's not typesafe and generates a warning about unchecked cast.
 *    Once you've proved that an unchecked cast is safe, suppress the warning in as narrow a scope as possible.
 *
 * 2. The second way to eliminate the generic array creation error is to change the type of the field from E[] to Object[].
 *    If you do this, you'll get an error about incompatible types.
 *    You can change this error into a warning by casting the element retrieved from the array from Object to E.
 *
 * All other things being equal, it is riskier to suppress an unchecked cast to an array type than to a scalar type,
 * which would suggest the second solution.
 * But in a generic class, you would probably be reading from the array as many points in the code,
 * so choosing the second solution would require many casts to E rather than a single cast to E[],
 * which is why the first solution is used more commonly.
 *
 * In summary:
 * Generic types are safer and easier to use than types that require casts in client code.
 * When you design new types, make sure that they can be used without such casts.
 * This will often mean making the types generic.
 * Generify your existing types as time permits.
 * This will make life easier for new users of these types without breaking existing clients.
 */
public class Item26_GenericType {

    /**
     * Object-based collection - a prime candidate for generification
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

        public Object pop() {
            if (size == 0)
                throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2*size+1);
        }
    }

    /**
     * Initial attempt to generify Stack
     */
    public static class Stack2<E> {
        private E[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack2() {
            // elements = new E[DEFAULT_INITIAL_CAPACITY];          // Error due to generic array creation
            elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];  // Warning about unchecked cast
        }

        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public E pop() {
            if (size == 0)
                throw new EmptyStackException();
            E result = elements[--size];        // Error due to incompatible types
            elements[size] = null;              // Eliminate obsolete reference
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2*size+1);
        }
    }

    /**
     * Generified Stack with warning suppression
     */
    public static class Stack3<E> {
        private E[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        // The elements array will contain only E instances from push(E).
        // This is sufficient to ensure type safety, but the runtime type of the array won't be E[];
        // it will always be Object[]
        @SuppressWarnings("unchecked")
        public Stack3() {
            elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }

        // Appropriate suppression of unchecked warning
        public E pop() {
            if (size == 0)
                throw new EmptyStackException();

            // push requires elements to be of type E, so cast is correct
            @SuppressWarnings("unchecked")
            E result = (E) elements[--size];    // Warning about unchecked cast

            elements[size] = null;              // Eliminate obsolete reference
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2*size+1);
        }
    }



    public static void main(String[] args) {
        Stack3<String> stack = new Stack3<>();
        for (String arg : args)
            stack.push(arg);
        while (!stack.isEmpty())
            System.out.println(stack.pop().toUpperCase());
    }

}
