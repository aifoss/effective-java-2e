package com.effective_java_2e.chap03_methods_common_to_all_objects;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 9: Always override hashCode when you override equals.
 *
 * You must override hashCode in every class that overrides equals.
 *
 * The key provision that is violated when you fail to override hashCode is: equal objects must have equal hash codes.
 *
 * A recipe for a hash function that distributes any reasonable collection of unequal instances uniformly across all possible hash values:
 *
 * 1. Store some constant nonzero value, say, 17, in an int variable called result.
 *
 * 2. For each significant field f in your object, do the following:
 *
 *    a. Compute an int hash code c for the field:
 *       i.   If the field is a boolean, compute (f ? 1 : 0).
 *       ii.  If the field is a byte, char, short, or int, compute (int) f.
 *       iii. If the field is a long, compute (int) (f ^ (f >>> 32)).
 *       iv.  If the field is a float, compute Float.floatToIntBits(f).
 *       v.   If the field is a double, compute Double.doubleToLongBits(f),
 *            and then hash the resulting long as in Step 2.a.iii.
 *       vi.  If the field is an object reference and this class's equals method compares the field by recursively invoking equals,
 *            recursively invoke hashCode on the field.
 *            If a more complex comparison is required, compute a "canonical representation" for this field and invoke hashCode on the canonical representation.
 *            If the value of the field is null, return 0.
 *       vii. If the field is an array, treat it as if each element were a separate field.
 *            That is, compute a hash code for each significant element by applying these rules recursively,
 *            and combine these values per Step 2.b.
 *            If every element in an array field is significant, you can use one of the Arrays.hashCode methods.
 *
 * b. Combine the hash code c computed in Step 2.a into result as follows:
 *       result = 31 * result + c;
 *
 *       A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction for better performance:
 *       31 * i = (i << 5) - i
 *
 * 3. Return result.
 *
 * 4. When you are finished writing the hashCode method, ask yourself whether equal instances have equal hash codes.
 *
 * You may exclude redundant fields from the hash code computation.
 * You must exclude any fields that are not used in equals comparisons.
 *
 * If a class is immutable and the cost of computing the hash code is significant,
 * you might consider caching the hash code in the object rather than recalculating it each time it is requested.
 * If you believe that most objects of this type will be used as hash keys,
 * then you should calculate the hash code when the instance is created.
 * Otherwise, you might choose to lazily initialize it the first time hashCode is invoked.
 *
 * Do not be tempted to exclude significant parts of an object from the hash code computation to improve performance.
 */
public class Item09_HashCode {

    /**
     * Class that overrides equals w/o overriding hashCode
     */
    public static final class PhoneNumber1 {
        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber1(int areaCode, int prefix, int lineNumber) {
            rangeCheck(areaCode, 999, "area code");
            rangeCheck(prefix, 999, "prefix");
            rangeCheck(lineNumber, 9999, "line number");

            this.areaCode = (short) areaCode;
            this.prefix = (short) prefix;
            this.lineNumber = (short) lineNumber;
        }

        private static void rangeCheck(int arg, int max, String name) {
            if (arg < 0 || arg > max)
                throw new IllegalArgumentException(name+": "+arg);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber1))
                return false;
            PhoneNumber1 pn = (PhoneNumber1)o;
            return pn.lineNumber == lineNumber
                    && pn.prefix == prefix
                    && pn.areaCode == areaCode;
        }

        // Broken - no hashCode method
    }

    /**
     * Class with hashCode method that uses the recipe
     */
    public static final class PhoneNumber2 {
        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber2(int areaCode, int prefix, int lineNumber) {
            rangeCheck(areaCode, 999, "area code");
            rangeCheck(prefix, 999, "prefix");
            rangeCheck(lineNumber, 9999, "line number");

            this.areaCode = (short) areaCode;
            this.prefix = (short) prefix;
            this.lineNumber = (short) lineNumber;
        }

        private static void rangeCheck(int arg, int max, String name) {
            if (arg < 0 || arg > max)
                throw new IllegalArgumentException(name+": "+arg);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber1))
                return false;
            PhoneNumber1 pn = (PhoneNumber1)o;
            return pn.lineNumber == lineNumber
                    && pn.prefix == prefix
                    && pn.areaCode == areaCode;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + areaCode;
            result = 31 * result + prefix;
            result = 31 * result + lineNumber;
            return result;
        }
    }

    /**
     * Class with lazily-initialized, cached hashCode method
     */
    public static final class PhoneNumber3 {
        private volatile int hashCode;

        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber3(int areaCode, int prefix, int lineNumber) {
            rangeCheck(areaCode, 999, "area code");
            rangeCheck(prefix, 999, "prefix");
            rangeCheck(lineNumber, 9999, "line number");

            this.areaCode = (short) areaCode;
            this.prefix = (short) prefix;
            this.lineNumber = (short) lineNumber;
        }

        private static void rangeCheck(int arg, int max, String name) {
            if (arg < 0 || arg > max)
                throw new IllegalArgumentException(name+": "+arg);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber1))
                return false;
            PhoneNumber1 pn = (PhoneNumber1)o;
            return pn.lineNumber == lineNumber
                    && pn.prefix == prefix
                    && pn.areaCode == areaCode;
        }

        @Override
        public int hashCode() {
            int result = hashCode;

            if (result == 0) {
                result = 31 * result + areaCode;
                result = 31 * result + prefix;
                result = 31 * result + lineNumber;
                hashCode = result;
            }

            return result;
        }
    }



    public static void main(String[] args) {

    }

}
