package com.effective_java_2e.chap11_serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 75: Consider using a custom serialized form.
 *
 * Do not accept the default serialized form without first considering whether it is appropriate.
 *
 * The default serialized form of an object is a reasonably efficient encoding of the physical representation of the object graph rooted at the object.
 * In other words, it describes the data contained in the object and in every object that is reachable from this object.
 * It also describes the topology by which all of these objects are interlinked.
 * The ideal serialized form of an object contains only the logical data represented by the object.
 * It is independent of the physical representation.
 *
 * The default serialized form is likely to be appropriate if an object's physical representation is identical to its logical content.
 *
 * Even if you decide that the default serialized form is appropriate,
 * you often must provide a readObject method to ensure invariants and security.
 *
 * Using the default serialized form when an object's physical representation differs substantially from its logical data content
 * has 4 disadvantages:
 *
 * 1. It permanently ties the exported API to the current internal representation.
 * 2. It can consume excessive space.
 * 3. It can consume excessive time.
 * 4. It can cause stack overflow.
 *
 * If all instance fields are transient,
 * it is technically permissible to dispense with invoking defaultWriteObject and defaultReadObject,
 * but it is not recommended.
 *
 * Whether or not you use the default serialized form,
 * every instance field that is not labeled transient will be serialized when the defaultWriteObject method is invoked.
 * Therefore, every instance field that can be made transient should be made so.
 * This includes redundant fields, whose values can be computed from primary data fields, such as a cached hash value.
 * It also includes fields whose values are tied to one particular run of the JVM, such as a long field representing a pointer to a native data structure.
 *
 * Before deciding to make a field nontransient, convince yourself that its value is part of the logical state of the object.
 *
 * If you use a custom serialized form, most or all of the instance fields should be labeled transient.
 *
 * If you are using the default serialized form and you have labeled one or more fields transient,
 * remember that these fields will be initialized to their default values when an instance is deserialized:
 * null for object reference fields, zero for numeric primitive fields, and false for boolean fields.
 * If these values are unacceptable for any transient fields,
 * you must provide a readObject method that invokes the defaultReadObject method and then restore transient fields to acceptable values.
 * Alternatively, these fields can be lazily initialized the first time they are used.
 *
 * Whether or not you use the default serialized form,
 * you must impose any synchronization on object serialization that you would impose on any other method that reads the entire state of the object.
 *
 * For example, if you have a thread-safe object that achieves its thread safety by synchronizing every method,
 * and you elect to use the default serialized form, use a synchronized writeObject method.
 * If you put synchronization in the writeObject method, you must ensure that it adheres to the same lock-ordering constraints as other activity,
 * or you risk a resource-ordering deadlock.
 *
 * Regardless of what serialized form you choose,
 * declare an explicit serial version UID in every serializable class you write:
 *
 *      private static final long serialVersionUID = randomLongValue;
 *
 * This eliminates the serial version UID as a potential source of incompatibility.
 * There is also a small performance benefit:
 * if no serial version UID is provided, an expensive computation is required to generate one at runtime.
 *
 * If you write a new class, it doesn't matter what value you choose for serial version UID.
 * If you modify an existing class that lacks a serial version UID, and you want the new version to accept existing serialized instances,
 * you must use the value that was automatically generated for the old version.
 * You can get this number by running the serialver utility on the old version of the class.
 * If you ever want to make a new version of a class that is incompatible with existing versions,
 * merely change the value in the serial version UID declaration.
 *
 * To summarize:
 * When you have decided that a class should be serializable, think hard about what the serialized form should be.
 * Use the default serialized form only if it is a reasonable description of the logical state of the object;
 * otherwise design a custom serialized form that aptly describes the object.
 * Choosing the wrong serialized form can have a permanent, negative impact on the complexity and performance of a class.
 */
public class Item75_CustomSerializedForm {

    /**
     * Good candidate for default serialized form
     */
    public class Name implements Serializable {
        /**
         * Last name. Must be non-null.
         * @serial
         */
        private final String lastName;

        /**
         * First name. Must be non-null.
         * @serial
         */
        private final String firstName;

        /**
         * Middle name, or null if there is none.
         * @serial
         */
        private final String middleName;

        public Name(String lastName, String firstName, String middleName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
        }
    }

    /**
     * Awful candidate for default serialized form
     */
    public static final class StringList implements Serializable {
        private int size = 0;
        private Entry head = null;

        private static class Entry implements Serializable {
            String data;
            Entry next;
            Entry previous;
        }
    }

    /**
     * StringList with a reasonable custom serialized form
     */
    public static final class StringList2 implements Serializable {
        private transient int size = 0;
        private transient Entry2 head = null;

        // No longer serializable
        private static class Entry2 {
            String data;
            Entry2 next;
            Entry2 previous;
        }

        // Appends the specified string to the list
        public final void add(String s) {
            // ...
        }

        /**
         * Serialize this {@code StringList2} instance.
         *
         * @serialData The size of the list (the number of strings it contains) is emitted ({@code int}),
         * followed by all of its elements (each a {@code String}), in the proper sequence.
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
            s.defaultWriteObject();
            s.writeInt(size);

            // Write out all elements in the proper order
            for (Entry2 e = head; e != null; e = e.next)
                s.writeObject(e.data);
        }

        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            int numElements = s.readInt();

            // Read in all elements and insert them in list
            for (int i = 0; i < numElements; i++)
                add((String) s.readObject());
        }
    }

    /**
     * writeObject for synchronized class with default serialized form
     */
    private synchronized void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }



    public static void main(String[] args) {

    }

}
