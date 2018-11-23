package com.effective_java_2e.chap07_methods;

import java.util.Date;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 39: Make defensive copies when needed.
 *
 * Java is a "safe language".
 * This means that in the absence of native methods it is immune to buffer overruns, array overruns, wild pointers,
 * and other memory corruption errors that plague unsafe languages such as C and C++.
 * In a safe language, it is possible to write classes and to know with certainty that their invariants will remain true,
 * no matter what happens in any other part of the system.
 *
 * Even in a safe language, you aren't insulated from other classes without some effort on your part.
 * You must program defensively, with the assumption that clients of your class will do their best to destroy its invariants.
 * To protect the internals of an immutable class instance,
 * it is essential to make a defensive copy of each mutable parameter to the constructor.
 *
 * Defensive copies are made before checking the validity of the parameters,
 * and the validity check is performed on the copies rather than on the originals.
 * This protects the class against chances to the parameters from another thread during the "window of vulnerability"
 * between the time the parameters are checked and the time they are copied.
 * This known as a "time-of-check/time-of-use" or TOCTOU attack.
 *
 * Do not use the clone method to make a defensive copy of a parameter whose type is subclassable by untrusted parties.
 *
 * Return defensive copies of mutable internal fields.
 *
 * Defensive copying of parameters is not just for immutable classes.
 * Anytime you write a method or constructor that enters a client-provided object into an internal data structure,
 * think about whether the client-provided object is potentially mutable.
 * If it is, think about whether your class could tolerate a change in the object after it was entered into the data structure.
 * If the answer is no, you must defensively copy the object and enter the copy into the data structure in place of the original.
 * The same is true for defensive copying of internal components prior to returning them to clients.
 *
 * The real lesson in all of this is that you should, where possible, use immutable objects as components of your objects,
 * so that you don't have to worry about defensive copying.
 *
 * Defensive copying can have a performance penalty associated with it and isn't always justified.
 * If a class trusts its caller not to modify an internal component, then it may be appropriate to dispense with defensive copying.
 * Under these circumstances, the class documentation must make it clear that the caller must not modify the affected parameters or return values.
 *
 * There are some methods and constructors whose invocation indicates an explicit "handoff" of the object referenced by a parameter.
 * When invoking such a method, the client promises that it will no longer modify the object directly.
 * A method or constructor that expects to take ownership of a client-provided mutable object must make this clear in its documentation.
 * Classes containing methods or constructors whose invocation indicates a transfer of control cannot defend themselves against malicious clients.
 * Such classes are acceptable only when there is mutual trust between the class and its client
 * or when damage to the class's invariants would harm no one but the client.
 *
 * In summary:
 * If a class has mutable components that it gets from or returns to its clients,
 * the class must defensively copy these components.
 * If the cost of the copy would be prohibitive and the class trusts its clients not to modify the components inappropriately,
 * then the defensive copy may be replaced by documentation outlining the client's responsibility not to modify the affected components.
 */
public class Item39_DefensiveCopy {

    /**
     * Broken "immutable" time period class
     */
    public static final class Period {
        private final Date start;
        private final Date end;

        /**
         * @param start the beginning of the period
         * @param end the end of the period; must not precede start
         * @throws IllegalArgumentException if start is after end
         * @throws NullPointerException if start or end is null
         */
        public Period(Date start, Date end) {
            if (start.compareTo(end) > 0)
                throw new IllegalArgumentException(start + " after " + end);
            this.start = start;
            this.end = end;
        }

        public Date start() {
            return start;
        }

        public Date end() {
            return end;
        }
    }

    /**
     * Broken time period class with a repaired constructor - make defensive copies of parameters
     * Still vulnerable to attacks on the internals using accessors
     */
    public static final class Period2 {
        private final Date start;
        private final Date end;

        /**
         * @param start the beginning of the period
         * @param end the end of the period; must not precede start
         * @throws IllegalArgumentException if start is after end
         * @throws NullPointerException if start or end is null
         */
        public Period2(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());

            if (this.start.compareTo(this.end) > 0)
                throw new IllegalArgumentException(start + " after " + end);
        }

        public Date start() {
            return start;
        }

        public Date end() {
            return end;
        }
    }

    /**
     * Immutable time period class
     */
    public static final class Period3 {
        private final Date start;
        private final Date end;

        /**
         * @param start the beginning of the period
         * @param end the end of the period; must not precede start
         * @throws IllegalArgumentException if start is after end
         * @throws NullPointerException if start or end is null
         */
        public Period3(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());

            if (this.start.compareTo(this.end) > 0)
                throw new IllegalArgumentException(start + " after " + end);
        }

        public Date start() {
            return new Date(start.getTime());
        }

        public Date end() {
            return new Date(end.getTime());
        }
    }



    public static void main(String[] args) {
        /**
         * Attack the internals of a Period instance
         */
        Date start = new Date();
        Date end = new Date();
        Period p = new Period(start, end);
        end.setYear(78);    // Modifies internals of p

        /**
         * Second attack on the internals of a Period instance
         */
        start = new Date();
        end = new Date();
        Period2 p2 = new Period2(start, end);
        p2.end().setYear(78);   // Modifies internals of p2
    }

}
