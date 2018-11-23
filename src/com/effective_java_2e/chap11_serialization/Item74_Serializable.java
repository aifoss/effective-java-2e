package com.effective_java_2e.chap11_serialization;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 74: Implement Serializable judiciously.
 *
 * The "object serialization" API provides a framework for encoding objects as byte streams and reconstructing objects from their byte-stream encodings.
 * Encoding an object as a byte stream is known as "serializing" the object;
 * the reverse process is known as "deserializing" it.
 * Serialization provides the standard wire-level object representation for remote communication,
 * and the standard persistent data format for the JavaBeans component architecture.
 *
 * A major cost of implementing Serializable is that it decreases the flexibility to change a class's implementation once it has been released.
 *
 * When a class implements Serializable, its byte-stream encoding (or "serialized form") becomes part of its exported API.
 * If you do not make the effort to design a "custom serialized form", but merely accept the default,
 * the serialized form will forever be tied to the class's original internal representation.
 * Therefore, you should carefully design a high-quality serialized form that you are willing to live with for the long haul.
 *
 * A simple example of the constraints on evolution that accompany serializability concerns "stream unique identifiers",
 * more commonly known as "serial version UIDs".
 * Every serializable class has a unique identification number associated with it.
 * If you do not specify this number explicitly by declaring a static final long field named serialVersionUID,
 * the system automatically generates it at runtime by applying a complex procedure to the class.
 * If you fail to declare an explicit serial version UID, compatibility will be broken,
 * resulting in an InvalidClassException at runtime.
 *
 * A second cost of implementing Serializable is that it increases the likelihood of bugs and security holes.
 *
 * Normally, objects are created using constructors;
 * serialization is an "extralinguistic mechanism" for creating objects.
 * Deserialization is a "hidden constructor" with all of the same issues as other constructors.
 * Relying on the default deserialization mechanism can easily leave objects open to invariant corruption and illegal access.
 *
 * A third cost of implementing Serializable is that it increases the testing burden associated with releasing a new version of a class.
 *
 * The amount of testing required is proportional to the product of the number of serializable classes and the number of releases.
 * These tests cannot be constructed automatically because, in addition to "binary compatibility",
 * you must test for "semantic compatibility".
 * In other words, you must ensure both that the serialization-deserialization process succeeds
 * and that it results in a faithful replica of the original object.
 *
 * Implementing the Serializable interface is not a decision to be undertaken lightly.
 *
 * Serialization is essential if a class is to participate in a framework that relies on serialization for object transmission or persistence.
 * Also, it greatly eases the use of a class as a component in another class that must implement Serializable.
 * There are, however, many real costs associated with implementing Serializable.
 * As a rule of thumb, value classes such as Date and BigInteger should implement Serializable,
 * as should most collection classes.
 * Classes representing active entities, such as thread pools, should rarely implement Serializable.
 *
 * Classes designed for inheritance should rarely implement Serializable,
 * and interfaces should rarely extend it.
 *
 * If a class or interface exists primarily to participate in a framework that requires all participants to implement Serializable,
 * then it makes perfect sense for the class or interface to implement or extend Serializable.
 *
 * Classes designed for inheritance that do implement Serializable include Throwable, Component, and HttpServlet.
 *
 * Throwable implements Serializable so exceptions from remote method invocation (RMI) can be passed from server to client.
 * Component implements Serializable so GUIs can be sent, saved, and restored.
 * HttpServlet implement Serializable so session state can be cached.
 *
 * If you implement a class with instance fields that is serializable and extendable,
 * there is a caution you should be aware of.
 * If the class has invariants that would be violated if its instance fields were initialized to their default values,
 * you must add this readObjectNoData method to the class:
 *
 *      // readObjectNoData for stateful extendable serializable classes
 *      private void readObjectNoData() throws InvalidObjectException {
 *          throw new InvalidObjectException("Stream data required");
 *      }
 *
 * You should consider providing a parameterless constructor on nonserializable classes designed for inheritance.
 *
 * If a class that is designed for inheritance is not serializable, it may be impossible to write a serializable subclass.
 * Specifically, it will be impossible if the superclass does not provide an accessible parameterless constructor.
 *
 * A way to add a parameterless constructor to a nonserializable extendable class
 * is to add a protected initialization method that has the same parameters as the normal constructor and establishes the same invariants.
 * With this mechanism in place, it is reasonably straightforward to implement a serializable subclass.
 *
 * Inner classes should not implement Serializable.
 *
 * They use compiler-generated "synthetic fields" to store references to "enclosing instances"
 * and to store values of local variables from enclosing scopes.
 * How these fields correspond to the class definition is unspecified, as are the names of anonymous and local classes.
 * Therefore, the default serialized form of an inner class is ill-defined.
 *
 * A static member class can, however, implement Serializable.
 *
 * To summarize:
 * Unless a class is to be thrown away after a short period of use,
 * implementing Serializable is a serious commitment that should be made with care.
 * Extra caution is warranted if a class is designed for inheritance.
 * For such classes, an intermediate design point between implementing Serializable and prohibiting it in subclasses
 * is to provide an accessible parameterless constructor.
 * This design point permits, but does not require, subclasses to implement Serializable.
 */
public class Item74_Serializable {

    /**
     * readObjectNoData for stateful extendable serializable classes
     */
    private void readObjectNoData() throws InvalidObjectException {
        throw new InvalidObjectException("Stream data required");
    }

    /**
     * Nonserializable stateful class allowing serializable subclass
     */
    public abstract static class AbstractFoo {

        private int x, y;   // Our state

        // This enum and field are used to track initialization
        private enum State { NEW, INITIALIZING, INITIALIZED };
        private final AtomicReference<State> init = new AtomicReference<>(State.NEW);

        public AbstractFoo(int x, int y) {
            initialize(x, y);
        }

        // This constructor and the following method allow
        // subclass's readObject method to initialize our state.
        protected AbstractFoo() {}

        protected final void initialize(int x, int y) {
            if (!init.compareAndSet(State.NEW, State.INITIALIZING))
                throw new IllegalStateException("Already initialized");
            this.x = x;
            this.y = y;
            init.set(State.INITIALIZED);
        }

        // These methods provide access to internal state so it can
        // be manually serialized by subclass's writeObject method.
        protected final int getX() {
            checkInit();
            return x;
        }

        protected final int getY() {
            checkInit();
            return y;
        }

        // Must call from all public and protected instance methods
        private void checkInit() {
            if (init.get() != State.INITIALIZED)
                throw new IllegalStateException("Uninitialized");
            // ...
        }
    }

    /**
     * Serializable subclass of nonserializable stateful class
     */
    public static class Foo extends AbstractFoo implements Serializable {

        private static final long serialVersionUID = 1856835860954L;

        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();

            // Manually deserialize and initialize superclass state
            int x = s.readInt();
            int y = s.readInt();
            initialize(x, y);
        }

        private void writeObject(ObjectOutputStream s) throws IOException {
            s.defaultWriteObject();

            // Manually serialize superclass state
            s.writeInt(getX());
            s.writeInt(getY());
        }

        // Constructor does not use the fancy mechanism
        public Foo(int x, int y) {
            super(x, y);
        }
    }



    public static void main(String[] args) {

    }

}
