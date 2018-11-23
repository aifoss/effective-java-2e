package com.effective_java_2e.chap03_methods_common_to_all_objects;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 10: Always override toString.
 *
 * Providing a good toString implementation makes your class much more pleasant to use.
 *
 * The toString method is automatically invoked when an object is passed to println, printf, the string concatenation operator,
 * or assert, or printed by a debugger.
 *
 * When practical, the toString method should return all of the interesting information contained in the object.
 *
 * One important decision you'll have to make when implementing a toString method is whether to specify the format of the return value in the documentation.
 * Whether or not you decide to specify the format, you should clearly document your intentions.
 *
 * Whether or not you specify the format, provide programmatic access to all of the information contained in the value returned by toString.
 * By failing to provide accessors, you turn the string format into a de factor API, even if you've specified that it's subject to change.
 */
public class item10_ToString {

    /**
     * Class with toString method documentation with format specification
     */
    public static final class PhoneNumber {
        private volatile int hashCode;

        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber(int areaCode, int prefix, int lineNumber) {
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
            if (!(o instanceof PhoneNumber))
                return false;
            PhoneNumber pn = (PhoneNumber)o;
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

        /**
         * Returns the string representation of this phone number.
         * The string consists of fourteen characters whose format
         * is "(XXX) YYY-ZZZZ", where XXX is the area code, YYY is
         * the prefix, and ZZZZ is the line number. (Each of the
         * capital letters represents a single decimal digit.)
         *
         * If any of the three parts of this phone number is too small
         * to fill up its field, the field is padded with leading zeros.
         * For example, if the value of the line number is 123, the last
         * four characters of the string representation will be "0123".
         *
         * Note that there is a single space separating the closing
         * parenthesis after the area code from the first digit of the
         * prefix.
         */
        @Override
        public String toString() {
            return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNumber);
        }
    }

    /**
     * Class with toString method documentation without format specification
     */
    public static class Potion {

        /**
         * Returns a brief description of this potion. The exact details
         * of the representation are unspecified and subject to change,
         * but the following may be regarded as typical:
         *
         * "[Potion #9: type=love, smell=turpentine, look=india ink]"
         */
        @Override
        public String toString() {
            // ...
            return "";
        }
    }



    public static void main(String[] args) {

    }

}
