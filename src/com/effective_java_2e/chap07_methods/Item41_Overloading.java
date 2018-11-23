package com.effective_java_2e.chap07_methods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 41: use overloading judiciously.
 *
 * Selection among overloaded methods is static, while selection among overridden methods is dynamic.
 * The choice of which overloading to invoke is made at compile time.
 *
 * You should avoid confusing uses of overloading.
 * A safe, conservative policy is never to export two overloaded methods with the same number of parameters.
 * If a method uses varargs, a conservative policy is not to overload it at all.
 * You can always give methods different names instead of overloading them.
 *
 * For constructors, you don't have the option of using different names:
 * multiple constructors for a class are always overloaded.
 * You do have the option of exporting static factories instead of constructors.
 *
 * Exporting multiple overloadings with the same number of parameters is unlikely to confuse programmers
 * if it is always clear which overloading will apply to any given set of actual parameters.
 * This is the case when at least one corresponding formal parameter in each pair of overloadings has a radically different type.
 * Two distinct classes are said to be unrelated if neither class is a descendant of the other.
 *
 * The programmer may not know which overloading will be invoked, but it is of no consequence if they behave identically.
 * The standard way to ensure this behavior is to have the more specific overloading forward to the more general.
 *
 * In summary:
 * you should generally refrain from overloading methods with multiple signatures that have the same number of parameters.
 * You should at least avoid situations where the same set of parameters can be passed to different overloadings by the addition of casts.
 * If such a situation cannot be avoided, you should ensure that all overloadings behave identically when passed the same parameters.
 */
public class Item41_Overloading {

    /**
     * Example of static selection of overloaded method
     * Broken! - What does this program print?
     */
    public static class CollectionClassifier {
        public static String classify(Set<?> s) {
            return "Set";
        }

        public static String classify(List<?> list) {
            return "List";
        }

        public static String classify(Collection<?> c) {
            return "Unknown Collection";
        }

        public static void main(String[] args) {
            Collection<?>[] collections = {
                    new HashSet<>(),
                    new ArrayList<>(),
                    new HashMap<>().values()
            };

            for (Collection<?> c : collections)
                System.out.println(classify(c)); // Invokes classify(Collection<?> c)
        }
    }

    /**
     * Fixed
     */
    public static class CollectionClassifier2 {
        public static String classify(Collection<?> c) {
            return (c instanceof Set) ? "Set"
                    : (c instanceof List ) ? "List"
                    : "Unknown Collection";
        }
    }

    /**
     * Example of dynamic selection of overridden method
     */
    static class Wine {
        String name() { return "wine"; }
    }

    static class SparklingWine extends Wine {
        @Override
        String name() { return "sparkling wine"; }
    }

    static class Champagne extends SparklingWine {
        @Override
        String name() { return "champagne"; }
    }

    public static class Overriding {
        public static void main(String[] args) {
            Wine[] wines = { new Wine(), new SparklingWine(), new Champagne() };
            for (Wine wine : wines)
                System.out.println(wine.name());    // Invokes overriding name() method
        }
    }

    /**
     * Forwarding of more specific overloading to more general
     */
//    public boolean contentEquals(StringBuffer sb) {
//        return contentEquals((CharSequence) sb);
//    }



    public static void main(String[] args) {

    }

}
