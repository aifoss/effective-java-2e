package com.effective_java_2e.chap05_generics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 27: Favor generic methods.
 *
 * Static utility methods are particularly good candidates for generification.
 *
 * The type parameter list, which declares the type parameter, goes between the method's modifiers and its return type.
 *
 * One noteworthy feature of generic methods is that you needn't specify the value of the type parameter explicitly
 * as you must when invoking generic constructors.
 * The compiler figures out the value of the type parameters by examining the types of the method arguments.
 * This process is known as "type inference".
 *
 * With the "generic static factory method" pattern, you can create parameterized type instance creation without repetitious declaration.
 *
 * A related pattern is the "generic singleton factory".
 * On occasion, you will need to create an object that is immutable but applicable to many different types.
 * Because generics are implemented by erasure, you can use a single object for all required type parameterizations,
 * but you need to write a static factory method to repeatedly dole out the object for each requested type parameterization.
 * This pattern is most frequently used for function objects (e.g., Collections.reverseOrder),
 * but it is also used for collections (Collections.emptySet).
 *
 * It is permissible, though relatively rare, for a type parameter to be bounded by some expression involving that type parameter itself.
 * This is what's known as a "recursive type bound".
 * The most common use of recursive type bound is in connection with the Comparable interface.
 * The type parameter T in Comparable<T> defines the type to which elements of the type implementing Comparable<T> can be compared.
 * The elements must be "mutually comparable".
 *
 * In summary:
 * Generic methods, like generic types, are safer and easier to use than methods that require their clients to cast input parameters and return values.
 * Like types, you should make sure that your new methods can be used without casts,
 * which will often mean making them generic.
 * And like types, you should generify your existing methods to make life easier for new users without breaking existing clients.
 */
public class Item27_GenericMethod {

    /**
     * Uses raw types - unacceptable
     */
    public static Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);   // Warning about unchecked call
        result.addAll(s2);              // Warning about unchecked call
        return result;
    }

    /**
     * Generic method
     */
    public static <E> Set<E> union2(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    /**
     * Parameterized type instance creation with constructor
     */
    Map<String, List<String>> anagrams = new HashMap<String, List<String>>();

    /**
     * Generic static factory method
     */
    public static <K,V> HashMap<K,V> newHashMap() {
        return new HashMap<K,V>();
    }

    /**
     * Parameterized type instance creation with static factory
     */
    Map<String, List<String>> anagrams2 = newHashMap();

    /**
     * Interface that describes a function that accepts and returns a value of some type T
     */
    public interface UnaryFunction<T> {
        T apply(T arg);
    }

    /**
     * Generic singleton factory pattern
     */
    private static UnaryFunction<Object> IDENTITY_FUNCTION =
            new UnaryFunction<Object>() {
                public Object apply(Object arg) {
                    return arg;
                }
            };

    /**
     * IDENTITY_FUNCTION is stateless and its type parameter is unbounded
     * so it's safe to share one instance across all types
     */
    @SuppressWarnings("unchecked")
    public static <T> UnaryFunction<T> identityFunction() {
        return (UnaryFunction<T>) IDENTITY_FUNCTION;
    }

    /**
     * Using a recursive type bound to express mutual comparability
     * Returns the maximum in a list
     */
    public static <T extends Comparable<T>> T max(List<T> list) {
        Iterator<T> i = list.iterator();
        T result = i.next();
        while (i.hasNext()) {
            T t = i.next();
            if (t.compareTo(result) > 0)
                result = t;
        }
        return result;
    }



    public static void main(String[] args) {
        /**
         * Use of generic method
         */
        Set<String> guys = new HashSet<>(Arrays.asList("Tom", "Dick", "Harry"));
        Set<String> stooges = new HashSet<>(Arrays.asList("Larry", "Moe", "Curly"));
        Set<String> aflCio = union2(guys, stooges);
        System.out.println(aflCio);

        /**
         * Use of generic singleton
         */
        String[] strings = { "jute", "hemp", "nylon" };
        UnaryFunction<String> sameString = identityFunction();
        for (String s : strings)
            System.out.println(sameString.apply(s));

        Number[] numbers = { 1, 2.0, 3L };
        UnaryFunction<Number> sameNumber = identityFunction();
        for (Number n : numbers)
            System.out.println(sameNumber.apply(n));

    }

}
