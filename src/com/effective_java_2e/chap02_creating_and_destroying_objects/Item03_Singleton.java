package com.effective_java_2e.chap02_creating_and_destroying_objects;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 3: Enforce the singleton property with a private constructor or an enum type.
 *
 * Making a class a singleton can make it difficult to test its clients,
 * as it's impossible to substitute a mock implementation for a singleton unless it implements an interface that serves as its type.
 *
 * Ways to implement singleton before Java 1.5:
 * 1. Use a public final field.
 *    The declarations make it clear that the class is a singleton: The public static field is final, so it will always contain the same object reference.
 * 2. Use a static factory method.
 *    Gives you the flexibility to change your mind about whether the class should be a singleton without changing its API.
 *
 * To make a singleton class that is using either approach serializable, it is not sufficient merely to add "implements Serializable".
 * To maintain the singleton guarantee, you have to declare all instance fields transient and provide a readResolve method.
 *
 * New approach to implementing singletons:
 * Make an enum type with a single element.
 *
 * Advantages:
 * 1. More concise.
 * 2. Provides the serialization machinery for free.
 * 3. Provides an ironclad guarantee against multiple instantiation.
 */
public class Item03_Singleton {

    /**
     * Singleton with public final field
     */
    public static class Elvis {
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {}

        public void leaveTheBuilding() {}

        // readResolve method to preserve singleton property
        private Object readResolve() {
            // Return the one true Elvis and let the garbage collector take care of the Elvis impersonator
            return INSTANCE;
        }
    }

    /**
     * Singleton with static factory
     */
    public static class Elvis2 {
        private static final Elvis2 INSTANCE = new Elvis2();
        private Elvis2() {}
        public static Elvis2 getInstance() {
            return INSTANCE;
        }

        public void leaveTheBuilding() {}

        // readResolve method to preserve singleton property
        private Object readResolve() {
            // Return the one true Elvis and let the garbage collector take care of the Elvis impersonator
            return INSTANCE;
        }
    }

    /**
     * Enum singleton - the preferred approach
     */
    public enum Elvis3 {
        INSTANCE;

        public void leaveTheBuilding() {}
    }



    public static void main(String[] args) {

    }

}
