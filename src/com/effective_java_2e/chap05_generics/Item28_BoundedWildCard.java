package com.effective_java_2e.chap05_generics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 28: Use bounded wildcards to increase API flexibility.
 *
 * Parameterized types are "invariant".
 * Sometimes you need more flexibility than invariant typing can provide.
 * Java provides a special kind of parametrized type called a "bounded wildcard type" to deal with situations like this.
 *
 * For maximum flexibility, use wildcard types on input parameters that represent producers or consumers.
 * If an input parameters is both a producer and a consumer, then wildcard types will do you no good:
 * you need an exact type match, which is what you get without any wildcards.
 *
 * PECS stands for producer-extends, consumer-super
 *
 * If a parameterized type represents a T producer, use <? extends T>;
 * if it represents a T consumer, use <? super T>.
 *
 * Do not use wildcard types as return types.
 *
 * If the user of a class has to think about wildcard types, there is probably something wrong with the class's API.
 *
 * If the compiler doesn't infer the type that you wish it had,
 * you can tell it what type to use with an "explicit type parameter".
 *
 * Comparables are always consumers, so you should always use Comparable<? super T> in preference to Comparable<T>.
 * The same is true for comparators, so you should always use Comparator<? super T> in preference to Comparator<T>.
 *
 * There is a duality between type parameters and wildcards,
 * and many methods can be declared using one or the other.
 * In a public API, using wildcards is better because it's simpler.
 * As a rule, if a type parameter appears only once in a method declaration,
 * replace it with a wildcard.
 * If it's an unbounded type parameter, replace it with an unbounded wildcard;
 * if it's a bounded type parameter, replace it with a bounded wildcard.
 *
 * In summary:
 * Using wildcard types in your APIs, while tricky, makes the APIs far more flexible.
 * If you write a library that will be widely used, the proper use of wildcard types should be considered mandatory.
 * Remember the basic rule: producer-extends, consumer-super (PECS).
 * And remember that all comparables and comparators are consumers.
 */
public class Item28_BoundedWildCard {

    public abstract static class Stack<E> {
        public Stack() {}
        public abstract void push(E e);
        public abstract E pop();
        public abstract boolean isEmpty();

        /**
         * pushAll method without wildcard type - deficient
         */
//        public void pushAll(Iterable<E> src) {
//            for (E e : src)
//                push(e);
//        }

        /**
         * Wildcard type for parameter that serves as an E producer
         */
        public void pushAll(Iterable<? extends E> src) {
            for (E e : src)
                push(e);
        }

        /**
         * popAll method without wildcard type - deficient
         */
//        public void popAll(Collection<E> dst) {
//            while (!isEmpty())
//                dst.add(pop());
//        }

        /**
         * Wildcard type for parameter that servces as an E consumer
         */
        public void popAll(Collection<? super E> dst) {
            while (!isEmpty())
                dst.add(pop());
        }
    }

    /**
     * Wildcard type for parameter that serves as an E producer
     */
    static <E> E reduce(List<? extends E> list, Function<E> f, E initVal) {
        return null;
    }

    interface Function<E> {
    }

    public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
        return null;
    }

    // Comparables are always consumers, so
    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        Iterator<? extends T> i = list.iterator();
        T result = i.next();
        while (i.hasNext()) {
            T t = i.next();
            if (t.compareTo(result) > 0)
                result = t;
        }
        return result;
    }

    /**
     * Two possible declarations for the swap method
     */
    /**
     * Declaration using unbounded type parameter
     */
    public static <E> void swap1(List<E> list, int it, int j) {}
    /**
     * Declaration using unbounded wildcard
     */
    public static void swap(List<?> list, int i, int j) {
        swapHelper(list, i, j);
    }
    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }



    public static void main(String[] args) {
        /**
         * Explicit type parameter
         */
        Set<Integer> integers = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Double> doubles = new HashSet<>(Arrays.asList(4.0, 5.0, 6.0));
        Set<Number> numbers = Item28_BoundedWildCard.<Number>union(integers, doubles);
    }

}
