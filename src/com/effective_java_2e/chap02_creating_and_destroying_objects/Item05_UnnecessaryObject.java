package com.effective_java_2e.chap02_creating_and_destroying_objects;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 5: Avoid creating unnecessary objects.
 *
 * An object can always be reused if it is immutable.
 * You can often avoid creating unnecessary objects by using static factory methods in preference to constructors.
 * Prefer primitives to boxed primitives, and watch out for unintentional autoboxing.
 *
 * Creating additional objects to enhance the clarity, simplicity, or power of a program is generally a good thing.
 * Avoiding object creation by maintaining your own object pool is a bad idea unless the objects in the pool are extremely heavyweight.
 */
public class Item05_UnnecessaryObject {

    /**
     * Reusing mutable objects that are not modified - DON'T DO THIS
     */
    public static class Person1 {
        private final Date birthDate;

        public Person1(Date birthDate) {
            this.birthDate = birthDate;
        }

        // DON'T DO THIS
        public boolean isBabyBoomer() {
            // Unnecessary allocation of expensive object
            Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
            Date boomStart = gmtCal.getTime();
            gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
            Date boomEnd = gmtCal.getTime();
            return birthDate.compareTo(boomStart) >= 0 &&
                    birthDate.compareTo(boomEnd) < 0;
        }
    }

    /**
     * Version that avoids inefficiency with a static initializer
     */
    public static class Person2 {
        private static final Date BOOM_START;
        private static final Date BOOM_END;

        static {
            Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
            BOOM_START = gmtCal.getTime();
            gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
            BOOM_END = gmtCal.getTime();
        }

        private final Date birthDate;

        public Person2(Date birthDate) {
            this.birthDate = birthDate;
        }

        public boolean isBabyBoomer() {
            return birthDate.compareTo(BOOM_START) >= 0 &&
                    birthDate.compareTo(BOOM_END) < 0;
        }
    }



    public static void main(String[] args) {
        // Reuse string instances
        String s1 = new String("stringette"); // DON'T DO THIS
        String s2 = "stringette"; // use a single String instance

        // Prefer primitives
        Long sum = 0L; // DON'T DO THIS
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i; // creates unnecessary instances
        }
        System.out.println(sum);
    }

}
