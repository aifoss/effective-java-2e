package com.effective_java_2e.chap03_methods_common_to_all_objects;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 12: Consider implementing Comparable.
 *
 * By implementing Comparable, a class indicates that its instances have a natural ordering.
 *
 * The general contract of the compareTo method is similar to that of equals:
 * Compares this object with the specific object for order.
 * Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
 * Throws ClassCastException if the specific object's type prevents it from being compared to this object.
 *
 * The implementor must ensure sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y.
 * The implementor must also ensure that the relation is transitive: (x.compareTo(y) > 0 && y.compareTo(z) > 0) implies x.compareTo(z) > 0.
 * The implementor must ensure that x.compareTo(y) == 0 implies that sgn(x.compareTo(z)) == sgn(y.compareTo(z)) for all z.
 * It is strongly recommended, but not strictly required, that (x.compareTo(y) == 0) == (x.equals(y)).
 *
 * Within a class, any reasonable ordering will satisfy the compareTo contract.
 * Across classes, compareTo, unlike equals, doesn't have to work:
 * it is permitted to throw ClassCastException if two object references being compared refer to objects of different classes.
 *
 * The equality test imposed by a compareTo method must obey the same restrictions imposed by the equals contract:
 * reflexivity, symmetry, and transitivity.
 * Therefore, the same caveat applies:
 * there is no way to extend an instantiable class with a new value component while preserving the compareTo contract,
 * unless you are willing to forgo the benefits of object-oriented abstraction.
 * The same workaround applies, too.
 * If you want to add a value component to a class that implements Comparable, don't extend it;
 * write an unrelated class containing an instance of the first class.
 * Then provide a "view" method that returns this instance.
 * This frees you to implement whatever compareTo method you like on the second class,
 * while allowing its client to view an instance of the second class as an instance of the first class when needed.
 *
 * The provision that the equality test imposed by the compareTo method should generally return the same results as the equals method is obeyed,
 * the ordering imposed by the compareTo method is said to be consistent with equals.
 * If it's violated, the ordering is said to be inconsistent with equals.
 * A class whose compareTo method imposes an order that is inconsistent with equals will still work,
 * but sorted collections containing elements of this class may not obey the general contract of the appropriate collection interfaces.
 * This is because the general contracts for these interfaces are defined in terms of the equals method,
 * but sorted collections use the equality test imposed by compareTo in place of equals.
 *
 * If a class has multiple significant fields, the order in which you compare them is critical.
 * You must start with the most significant field and work your way down.
 */
public class Item12_Comparable {

    /**
     * Class using String class which implements Comparable
     */
    public static class WordList {
        public static void main(String[] args) {
            Set<String> s = new TreeSet<>();
            Collections.addAll(s, args);
            System.out.println(s);
        }
    }

    /**
     * Class that implements Comparable
     */
    public static final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
        private String s;

        @Override
        public int compareTo(CaseInsensitiveString cis) {
            return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
        }
    }

    /**
     * Class that has multiple significant fields
     */
    public static class PhoneNumber1 implements Comparable<PhoneNumber1> {
        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber1(int areaCode, int prefix, int lineNumber) {
            this.areaCode = (short) areaCode;
            this.prefix = (short) prefix;
            this.lineNumber = (short) lineNumber;
        }

        @Override
        public int compareTo(PhoneNumber1 pn) {
            // Compare area codes
            if (areaCode < pn.areaCode)
                return -1;
            if (areaCode > pn.areaCode)
                return 1;

            // Area codes are equal, compare prefixes
            if (prefix < pn.prefix)
                return -1;
            if (prefix > pn.prefix)
                return 1;

            // Area codes and prefixes are equal, compare line numbers
            if (lineNumber < pn.lineNumber)
                return -1;
            if (lineNumber > pn.lineNumber)
                return 1;

            // All fields are equal
            return 0;
        }
    }

    /**
     * PhoneNumber class with improved compareTo method
     */
    public static class PhoneNumber2 implements Comparable<PhoneNumber2> {
        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber2(int areaCode, int prefix, int lineNumber) {
            this.areaCode = (short) areaCode;
            this.prefix = (short) prefix;
            this.lineNumber = (short) lineNumber;
        }

        /**
         * Don't use this pattern unless you're certain that the difference between the lowest and highest possible field values
         * is less than or equal to Integer.MAX_VALUE.
         */
        @Override
        public int compareTo(PhoneNumber2 pn) {
            // Compare area codes
            int areaCodeDiff = areaCode - pn.areaCode;
            if (areaCodeDiff != 0)
                return areaCodeDiff;

            // Area codes are equal, compare prefixes
            int prefixDiff = prefix - pn.prefix;
            if (prefixDiff != 0)
                return prefixDiff;

            // Area codes and prefixes are equal, compare line numbers
            return lineNumber - pn.lineNumber;
        }
    }



    public static void main(String[] args) {

    }

}
