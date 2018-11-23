package com.effective_java_2e.chap11_serialization;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 78: Consider serialization proxies instead of serialized instances.
 *
 * There is a technique that greatly reduces the security risks associated with implementing Serializable,
 * known as the "serialization proxy" pattern.
 *
 * The serialization proxy pattern is reasonably straightforward.
 *
 * 1. First, design a private static nested class of the serializable class that concisely represents the logical state
 *    of an instance of the enclosing class.
 *
 *    This nested class, known as the "serialization proxy", should have a single constructor, whose parameter type is the enclosing class.
 *    This constructor merely copies the data from its argument: it need not do any consistency checking or defensive copying.
 *    By design, the default serialized form of the serialization proxy is the perfect serialized form of the enclosing class.
 *    Both the enclosing class and its serialization proxy must be declared to implement Serializable.
 *
 * 2. Next, add a writeReplace method to the enclosing class.
 *
 *    The presence of this method causes the serialization system to emit a SerializationProxy instead of an instance of the enclosing class.
 *    In other words, the writeReplace method translates an instance of the enclosing class to its serialization proxy prior to serialization.
 *
 * 3. Next, add a readObject method to the enclosing class.
 *
 *    This guarantees that an attack that fabricates a serialized instance of the enclosing class to violate the class's invariants would fail.
 *
 * 4. Finally, provide a readResolve method on the SerializationProxy class that returns a logically equivalent instance of the enclosing class.
 *
 *    The presence of this method causes the serialization system to translate the serialization proxy
 *    back into an instance of the enclosing class upon deserialization.
 *
 * Like the defensive copying approach, the serialization proxy approach stops the bogus byte-stream attack and the internal field theft attack.
 * Unlike the previous two approaches, this one allows the fields of Period to be final,
 * which is required in order for the Period class to be truly immutable.
 * And unlike the two previous approaches, this one doesn't involve a great deal of thought.
 *
 * There is another way in which the serialization proxy pattern is more powerful than defensive copying.
 * The serialization proxy pattern allows the deserialized instance to have a different class from the originally serialized instance.
 *
 * The serialization proxy pattern has 2 limitations:
 *
 * 1. It is not compatible with classes that are extendable by their clients.
 *
 * 2. It is not compatible with some classes whose object graphs contain circularities:
 *    if you attempt to invoke a method on an object from within its serialization proxy's readResolve method,
 *    you'll get a ClassCastException, as you don't have the object yet, only its serialization proxy.
 *
 * The added power and safety of the serialization proxy pattern are not free.
 * It is more expensive to serialize and deserialize instances with serialization proxies than it is with defensive copying.
 *
 * In summary:
 * Consider the serialization proxy pattern whenever you find yourself having to write a readObject or writeObject method
 * on a class that is not extendable by its clients.
 * This pattern is perhaps the easiest way to robustly serialize objects with nontrivial invariants.
 */
public class Item78_SerializationProxy {

    public static final class Period implements Serializable {

        /**
         * Serialization proxy
         */
        private static class SerializationProxy implements Serializable {

            private static final long serialVersionUID = 234098243823485285L;

            private final Date start;
            private final Date end;

            SerializationProxy(Period p) {
                this.start = p.start;
                this.end = p.end;
            }

            /**
             * readResolve method for serialization proxy pattern
             */
            private Object readResolve() {
                return new Period(start, end);  // Uses public constructor
            }
        }

        private final Date start;
        private final Date end;

        public Period(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
            if (this.start.compareTo(this.end) > 0)
                throw new IllegalArgumentException(start + " after " + end);
        }

        /**
         * writeReplace method for the serialization proxy pattern
         */
        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        /**
         * readObject method for the serialization proxy pattern
         */
        private void readObject(ObjectInputStream s) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        @Override
        public String toString() {
            return start + " - " + end;
        }
    }

    /**
     * EnumSet's serialization proxy
     */
    private static class SerializationProxy <E extends Enum<E>> implements Serializable {

        private static final long serialVersionUID = 362491234563181265L;

        // The element type of this enum set
        private Class<E> elementType;

        // The elements contained in this enum set
        private Enum[] elements;

        SerializationProxy(EnumSet<E> set) {
//            elementType = set.elementType;
//            elements = set.toArray(EMPTY_ENUM_ARRAY);
        }

        private Object readResolve() {
            EnumSet<E> result = EnumSet.noneOf(elementType);
            for (Enum e : elements)
                result.add((E) e);
            return result;
        }
    }



    public static void main(String[] args) {

    }

}
