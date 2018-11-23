package com.effective_java_2e.chap11_serialization;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 77: For instance control, prefer enum types to readResolve.
 *
 * The readResolve feature allows you to substitute another instance for the one created by readObject.
 * If the class of an object being deserialized defines a readResolve method with proper declaration,
 * this method is invoked on the newly created object after it is deserialized.
 * The object reference returned by this method is then returned in place of the newly created object.
 * In most uses of this feature, no reference to the newly created object is retained,
 * so it immediately becomes eligible for garbage collection.
 *
 * If you depend on readResolve for instance control,
 * all instance fields with object references types must be declared transient.
 *
 * Otherwise, it is possible for a determined attacker to secure a reference to the deserialized object
 * before its readResolve method is run.
 * If a singleton contains a nontransient object reference field,
 * the contents of this field will be deserialized before the singleton's readResolve method is run.
 * This allows a carefully crafted steam to steal a reference to the originally deserialized singleton
 * at the time the contents of the object reference field are deserialized.
 *
 * Historically, the readResolve method was used for all serializable instance-controlled classes.
 * As of release 1.5, this is no longer the best way to maintain instance control in a serializable class.
 *
 * If instead you write your serializable instance-controlled class as an enum,
 * you get an ironclad guarantee that there can be no instances besides the declared constants.
 *
 * The use of readResolve for instance control is not obsolete.
 * If you have to write a serializable instance-controlled class whose instances are not known at compile time,
 * you will not be able to represent the class as an enum type.
 *
 * The accessibility of readResolve is significant.
 *
 * If you place a readResolve method on a final class, it should be private.
 * If you place a readResolve method on a nonfinal class, you must carefully consider its accessibility.
 * If a readResolve method is protected or public and a subclass does not override it,
 * deserializing a serialized subclass instance will produce a superclass instance,
 * which is likely to cause a ClassCastException.
 *
 * To summarize:
 * You should use enum types to enforce instance control invariants wherever possible.
 * If this is not possible and you need a class to be both serializable and instance-controlled,
 * you must provide a readResolve method and ensure that all of the class's instance fields are either primitive or transient.
 */
public class Item77_ReadResolve {

    /**
     * Using readResolve for instance control in serializable singleton class
     */
    public static class Elvis implements Serializable {

        public static final Elvis INSTANCE = new Elvis();

        private Elvis() {}

        // readResolve for instance control - you can do better!
        private Object readResolve() {
            // Return the one true Elvis and let the garbage collector
            // take care of the Elvis impersonator.
            return INSTANCE;
        }
    }

    /**
     * Broken singleton - has nontransient object reference field!
     */
    public static class Elvis2 implements Serializable {

        public static final Elvis2 INSTANCE = new Elvis2();

        private Elvis2() {}

        private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }

        private Object readResolve() throws ObjectStreamException {
            return INSTANCE;
        }
    }

    public static class ElvisStealer implements Serializable {

        private static final long serialVersionUID = 0;

        static Elvis2 impersonator;
        private Elvis2 payload;

        private Object readResolve() {
            // Save a reference to the "unresolved" Elvis instance
            impersonator = payload;

            // Return an object of correct type for favorites field
            return new String[] { "A Fool Such as I" };
        }
    }

    public static class ElvisImpersonator {
        // Byte stream could not have come from real Elvis instance!
        private static final byte[] serializedForm = new byte[] {
                (byte)0xac, (byte)0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x05,
                0x45, 0x6c, 0x76, 0x69, 0x73, (byte)0x84, (byte)0xe6,
                (byte)0x93, 0x33, (byte)0xc3, (byte)0xf4, (byte)0x8b,
                0x32, 0x02, 0x00, 0x01, 0x4c, 0x00, 0x0d, 0x66, 0x61, 0x76,
                0x6f, 0x72, 0x69, 0x74, 0x65, 0x53, 0x6f, 0x6e, 0x67, 0x73,
                0x74, 0x00, 0x12, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c,
                0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a, 0x65, 0x63, 0x74,
                0x3b, 0x78, 0x70, 0x73, 0x72, 0x00, 0x0c, 0x45, 0x6c, 0x76,
                0x69, 0x73, 0x53, 0x74, 0x65, 0x61, 0x6c, 0x65, 0x72, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01,
                0x4c, 0x00, 0x07, 0x70, 0x61, 0x79, 0x6c, 0x6f, 0x61, 0x64,
                0x74, 0x00, 0x07, 0x4c, 0x45, 0x6c, 0x76, 0x69, 0x73, 0x3b,
                0x78, 0x70, 0x71, 0x00, 0x7e, 0x00, 0x02
        };

        static Object deserialize(byte[] serializedForm) {
            return null;
        }

        public static void main(String[] args) {
            // Initializes ElvisStealer.impersonator and returns
            // the real Elvis (which is Elvis.INSTANCE)
            Elvis2 elvis = (Elvis2) deserialize(serializedForm);
            Elvis2 impersonator = ElvisStealer.impersonator;

            elvis.printFavorites();
            impersonator.printFavorites();
        }
    }

    /**
     * Enum singleton - the preferred approach
     */
    public enum Elvis3 {
        INSTANCE;

        private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }
    }



    public static void main(String[] args) {

    }

}
