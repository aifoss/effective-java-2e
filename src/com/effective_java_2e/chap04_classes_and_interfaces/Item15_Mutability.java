package com.effective_java_2e.chap04_classes_and_interfaces;

import java.math.BigInteger;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 15: Minimize mutability.
 *
 * An immutable class is simply a class whose instances cannot be modified.
 *
 * The Java platform libraries contain many immutable classes, including String, boxed primitive classes, and BigInteger and BigDecimal.
 *
 * Immutable classes are easier to design, implement, and use than mutable classes.
 * They are less prone to error and are more secure.
 *
 * To make a class immutable, follow these five rules:
 *
 * 1. Don't provide any methods that modify the object's state ("mutators").
 *
 * 2. Ensure that the class can't be extended.
 *    Preventing subclassing is generally accomplished by making the class final.
 *
 * 3. Make all fields final.
 *    This is necessary to ensure correct behavior if a reference to a newly created instance is passed from one thread to another without synchronization.
 *
 * 4. Make all fields private.
 *    This prevents clients from obtaining access to mutable objects referred to by fields and modifying these objects directly.
 *
 * 5. Ensure exclusive access to any mutable components.
 *    Make defensive copies in constructors, accessors, and readObject methods.
 *
 * Immutable objects are simple.
 * An immutable object can be in exactly one state, the state in which it was created.
 *
 * Immutable objects are inherently thread-safe; they require no synchronization.
 *
 * Immutable objects can be shared freely.
 *
 * Immutable classes should take advantage of this by encouraging clients to reuse existing instances wherever possible.
 * One easy way to do this is to provide public static final constants for frequently used values.
 * An immutable class can provide static factories that cache frequently requested instances to avoid creating new instances.
 *
 * A consequence of the fact that immutable objects can be shared freely is that you never have to make defensive copies.
 * In fact, you never have to make any copies at all because the copies would be forever equivalent to the originals.
 * Therefore, you need not and should not provide a clone method or copy constructor on an immutable class.
 *
 * Not only can you share immutable objects, but you can share their internals.
 *
 * Immutable objects make great building blocks for other objects, whether mutable or immutable.
 * A special case of this principle is that immutable objects make great map keys and set elements.
 *
 * The only real disadvantage of immutable classes is that they require a separate object for each distinct value.
 * There are two approaches to coping with this problem:
 * 1. Guess which multistep operations will be commonly required and provide them as primitives.
 *    e.g., BigInteger
 * 2. If not, then your best bet is to provide a public mutable companion class.
 *    e.g., String with StringBuilder
 *
 * An alternative to making an immutable class final is to make all of its constructors private or package-private
 * and to add public static factories in place of the public constructors.
 *
 * If you choose to have your immutable class implement Serializable and it contains one or more fields that refer to mutable objects,
 * you must provide an explicit readObject or readResolve method,
 * or use the ObjectOutputStream.writeUnshared and ObjectInputStream.readUnshared methods,
 * even if the default serialized form is acceptable.
 * Otherwise, an attacker could create a mutable instance of your not-quite-immutable class.
 *
 * Classes should be immutable unless there's a very good reason to make them mutable.
 *
 * If a class cannot be made immutable, limit its mutability as much as possible.
 * Therefore, make every field final unless there is a compelling reason to make it nonfinal.
 *
 * Constructors should create fully initialized objects with all of their invariants established.
 * Don't provide a public initialization method separate from the constructor or static factory unless there is a compelling reason to do so.
 * Don't provide a "reinitialize" method that enables an object to be reused as if it had been constructed with a different initial state.
 */
public class Item15_Mutability {

    /**
     * Immutable class
     */
    public static final class Complex1 {
        // Public static final constants
        public static final Complex1 ZERO = new Complex1(0, 0);
        public static final Complex1 ONE = new Complex1(1, 0);
        public static final Complex1 I = new Complex1(0, 1);

        private final double re;
        private final double im;

        public Complex1(double re, double im) {
            this.re = re;
            this.im = im;
        }

        // Accessors with no corresponding mutators
        public double realPart() {
            return re;
        }
        public double imaginaryPart() {
            return im;
        }

        // Functional approach - create and return a new Complex instance rather than modifying this instance
        public Complex1 add(Complex1 c) {
            return new Complex1(re + c.re, im + c.im);
        }

        public Complex1 subtract(Complex1 c) {
            return new Complex1(re - c.re, im - c.im);
        }

        public Complex1 multiply(Complex1 c) {
            return new Complex1(
                    re * c.re - im * c.im,
                    re * c.im + im * c.re);
        }

        public Complex1 divide(Complex1 c) {
            double tmp = c.re * c.re + c.im * c.im;
            return new Complex1(
                    (re * c.re + im * c.im) / tmp,
                    (im * c.re - re * c.im) / tmp);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Complex1))
                return false;

            Complex1 c = (Complex1) o;
            return Double.compare(re, c.re) == 0 &&
                    Double.compare(im, c.im) == 0;
        }

        @Override
        public int hashCode() {
            int result = 17 + hashDouble(re);
            result = 31 * result + hashDouble(im);
            return result;
        }

        private int hashDouble(double val) {
            long longBits = Double.doubleToLongBits(re);
            return (int) (longBits ^ (longBits >>> 32));
        }

        @Override
        public String toString() {
            return "(" + re + " + " + im + "i)";
        }
    }

    /**
     * Immutable class with static factories instead of constructors
     */
    public static class Complex2 {
        private final double re;
        private final double im;

        private Complex2(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public static Complex2 valueOf(double re, double im) {
            return new Complex2(re, im);
        }

        public static Complex2 valueOfPolar(double r, double theta) {
            return new Complex2(r * Math.cos(theta), r * Math.sin(theta));
        }
    }

    /**
     * If you write a class whose security depends on the immutability of a BigInteger or BigDecimal argument from an untrusted client,
     * you must check to see if the argument is a "real" BigInteger or BigDecimal, rather than an instance of an untrusted subclass.
     * If it is the latter, you must defensively copy it under the assumption that it might be mutable.
     */
    public static BigInteger safeInstance(BigInteger val) {
        if (val.getClass() != BigInteger.class)
            return new BigInteger(val.toByteArray());
        return val;
    }



    public static void main(String[] args) {

    }

}
