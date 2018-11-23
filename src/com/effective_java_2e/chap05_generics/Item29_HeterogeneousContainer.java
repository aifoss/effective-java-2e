package com.effective_java_2e.chap05_generics;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 29: Consider typesafe heterogeneous containers.
 *
 * The most common use of generics is for collections, such as Set and Map,
 * and single-element containers, such as ThreadLocal and AtomicReference.
 * In all of these uses, it is the container that is parameterized.
 * This limits you to a fixed number of type parameters per container.
 *
 * Sometimes, however, you need more flexibility.
 * For example, a database row can have arbitrarily many columns,
 * and it would be nice to be able to access all of them in a typesafe manner.
 * The idea is to parameterize the key instead of the container.
 * Then present the parameterized key to the container to insert or retrieve a value.
 *
 * As a simple example of this approach, consider a Favorites class that allows its clients to store and retrieve a "favorite" instance
 * of arbitrarily many other classes.
 * The Class object will play the part of the parameterized key.
 * When a class literal is passed among methods to communicate both compile-time and runtime type information,
 * it is called a "type token".
 *
 * There are two limitations to the Favorites class:
 *
 * 1. A malicious client could easily corrupt the type safety of a Favorites instance,
 *    simply by using a Class object in its raw form.
 *
 *    The way to ensure that Favorites never violates its type invariant
 *    is to have the putFavorites method check that instance of the type represented by type,
 *    using a dynamic cast.
 *
 * 2. The second limitation is that it cannot be used on a non-reifiable type (e.g., List<String>).
 *
 *    There is no entirely satisfactory workaround for this limitation.
 *    There is a technique called "super type tokens" that goes a long way toward addressing the limitation,
 *    but this technique has limitations of its own.
 *
 * Sometimes you may need to limit the types that can be passed to a method.
 * This can be achieved with a "bounded type token",
 * which is simply a type token that places a bound on what type can be represented,
 * using a bounded type parameter or a bounded wildcard.
 *
 * In summary:
 * The normal use of generics, exemplified by the collections APIs, restricts you to a fixed number of type parameters per container.
 * You can get around this restriction by placing the type parameter on the key rather than the container.
 * You can use Class objects as keys for such typesafe heterogeneous containers.
 * A Class object used in this fashion is called a "type token".
 * You can also use a custom key type.
 * For example, you could have a DatabaseRow type representing a database row (the container), and a generic type Column<T> as its key.
 */
public class Item29_HeterogeneousContainer {

    /**
     * Typesafe heterogeneous container pattern - API
     */
    public static class Favorites {
        private Map<Class <?>, Object> favorites = new HashMap<>();

        public Favorites() {}

        public <T> void putFavorites(Class<T> type, T instance) {
            if (type == null)
                throw new NullPointerException("Type is null");
            // Achieving runtime type safety with a dynamic cast
            favorites.put(type, type.cast(instance));
        }

        public <T> T getFavorite(Class<T> type) {
            // Dynamic cast
            return type.cast(favorites.get(type));
        }
    }

    /**
     * Use of bounded type token in annotations API
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return null;
    }

    /**
     * Use of asSubclass to safely cast to a bounded type token
     */
    static Annotation getAnnotation(AnnotatedElement element, String annotationTypeName) {
        Class<?> annotationType = null; // Unbounded type token
        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }



    public static void main(String[] args) {
        /**
         * Typesafe heterogeneous container pattern - client
         */
        Favorites f = new Favorites();
        f.putFavorites(String.class, "Java");
        f.putFavorites(Integer.class, 0xcafebabe);
        f.putFavorites(Class.class, Favorites.class);

        String favoriteString = f.getFavorite(String.class);
        int favoriteInteger = f.getFavorite(Integer.class);
        Class<?> favoriteClass = f.getFavorite(Class.class);
        System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
    }

}
