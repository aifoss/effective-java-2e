package com.effective_java_2e.chap11_serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 76: Write readObject methods defensively.
 *
 * Just as a constructor must check its arguments for validity and make defensive copies of parameters where appropriate,
 * so must a readObject method.
 * If a readObject method fails to do either of these things, it is a relatively simple matter for an attacker to violate the class's invariants.
 *
 * Loosely speaking, readObject is a constructor that takes a byte stream as its sole parameter.
 * The problem arises when readObject is presented with a byte stream that is artificially constructed to generate an object
 * that violates the invariants of its class.
 *
 * When an object is deserialized, it is critical to defensively copy any field containing an object reference that a client must not possess.
 *
 * Therefore, every serializable immutable class containing private mutable components
 * must defensively copy these components in its readObject method.
 *
 * Do not use the writeUnshared and readUnshared methods of ObjectOutputStream.
 *
 * They are typically faster than defensive copying, but they don't provide the necessary safety guarantee.
 *
 * Here is a simple litmus test for deciding whether the default readObject method is acceptable for a class:
 * would you feel comfortable adding a public constructor that took as parameters the values for each nontransient field in the object
 * and stored the values in the fields with no validation whatsoever?
 * If not, you must provide a readObject method,
 * and it must perform all the validity checking and defensive copying that would be required of a constructor.
 * Alternatively, you can use the "serialization proxy" pattern.
 *
 * There is one other similarity between readObject methods and constructors, concerning nonfinal serializable classes.
 * A readObject method must not invoke an overridable method, directly or indirectly.
 * If this rule is violated and the method is overridden, the overriding method will run before the subclass's state has been deserialized.
 *
 * To summarize:
 * Anytime you write a readObject method, adopt the mind-set that you are writing a public constructor
 * that must produce a valid instance regardless of what byte stream it is given.
 * All of the issues apply equally to classes with custom serialized forms.
 *
 * Here are the guidelines for writing a bulletproof readObject method:
 *
 * 1. For classes with object reference fields that must remain private, defensively copy each object in such a field.
 *
 *    Mutable components of immutable classes fall into this category.
 *
 * 2. Check any invariants and throw an InvalidObjectException if a check fails.
 *
 *    The checks should follow any defensive copying.
 *
 * 3. If an entire object graph must be validated after it is deserialized, use the ObjectInputValidation interface.
 *
 * 4. Do not invoke any overridable methods in the class, directly or indirectly.
 */
public class Item76_ReadObject {

    /**
     * Immutable class that uses defensive copying
     */
    public static final class Period implements Serializable {
        private Date start;
        private Date end;

        public Period(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
            if (this.start.compareTo(this.end) > 0)
                throw new IllegalArgumentException(start + " after " + end);
        }

        /**
         * readObject method with validity checking without defensive copying
         */
//        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
//            s.defaultReadObject();
//
//            // Check that our invariants are satisfied
//            if (start.compareTo(end) > 0)
//                throw new InvalidObjectException(start + " after " + end);
//        }

        /**
         * readObject method with defensive copying and validity checking
         */
        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();

            // Defensively copy our mutable components
            start = new Date(start.getTime());
            end = new Date(end.getTime());

            // Check that our invariants are satisfied
            if (start.compareTo(end) > 0)
                throw new InvalidObjectException(start + " after " + end);
        }

        @Override
        public String toString() {
            return start + " - " + end;
        }
    }

    /**
     * Mutable Period class vulnerable to attacker mutating the Period instance
     */
    public static class MutablePeriod {
        // A Period instance
        public final Period period;

        // period's start field, to which we shouldn't have access
        public final Date start;

        // period's end field, to which we shouldn't have access
        public final Date end;

        public MutablePeriod() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);

                // Serialize a valid Period instance
                out.writeObject(new Period(new Date(), new Date()));

                /**
                 * Append rogue "previous object refs" for internal
                 * Date fields in Period.
                 */
                byte[] ref = { 0x71, 0, 0x7e, 0, 5 };
                bos.write(ref); // The start field
                ref[4] = 4;
                bos.write(ref); // The end field

                // Deserialize Period and "stolen" Date references
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
                period = (Period) in.readObject();
                start = (Date) in.readObject();
                end = (Date) in.readObject();
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        }
    }



    public static void main(String[] args) {
        // Example attack to mutate instance fields in Period
        // Works only with readObject method without defensive copying
        MutablePeriod mp = new MutablePeriod();
        Period p = mp.period;
        Date pEnd = mp.end;

        // Let's turn back the clock
        pEnd.setYear(78);
        System.out.println(p);

        // Bring back the 60s
        pEnd.setYear(69);
        System.out.println(p);
    }

}
