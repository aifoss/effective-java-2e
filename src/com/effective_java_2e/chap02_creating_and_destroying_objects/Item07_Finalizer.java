package com.effective_java_2e.chap02_creating_and_destroying_objects;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 7: Avoid finalizers.
 *
 * Finalizers are unpredictable, often dangerous, and generally unnecessary.
 *
 * One shortcoming of finalizers is that there is no guarantee they'll be executed promptly.
 * This means that you should never do anything time-critical in a finalizer.
 * For example, it is a grave error to depend on a finalizer to close files.
 *
 * It is entirely possible, even likely, that a program terminates without executing finalizers on some objects that are no longer reachable.
 * As a consequence, you should never depend on a finalizer to update critical persistent state.
 * For example, depending on a finalizer to release a persistent lock on a shared resource such as a database.
 *
 * If an uncaught exception is thrown during finalization, the exception is ignored, and finalization of that object terminates.
 * Uncaught exceptions can leave objects in a corrupt state.
 *
 * There is a severe performance penalty for using finalizers.
 *
 * Instead of writing a finalizer, provide an explicit termination method,
 * and require clients of the class to invoke this method on each instance when it is no longer needed.
 * Typical examples of explicit termination methods are the close methods on InputStream, OutputStream, and java.sql.Connection.
 * Another example is the cancel method on java.util.Timer.
 *
 * Explicit termination methods are typically used in combination with the try-finally construct to ensure termination.
 * Invoking the explicit termination method inside the finally clause ensures that it will get executed
 * even if an exception is thrown while the object is being used.
 *
 * Two legitimate uses of finalizers:
 * 1. Act as a "safety net" in case the owner of an object forgets to call its explicit termination method.
 *    The finalizer should log a warning if it finds that the resources has not been terminated.
 * 2. Reclaim "native peers".
 *    A native peer is a native object to which a normal object delegates via native methods.
 *    Because a native peer is not a normal object, the garbage collector doesn't know about it and can't reclaim it when its Java peer is reclaimed.
 *    A finalizer is an appropriate vehicle for performing this task, assuming the native peer holds no critical resources.
 *
 * It is important to note that "finalizer chaining" is not performed automatically.
 * If a class (other than Object) has a finalizer and a subclass overrides it,
 * the subclass must invoke the superclass finalizer manually.
 * You should finalize the subclass in a try block and invoke the superclass finalizer in the finally block.
 *
 * If a subclass implementor overrides a superclass finalizer but forgets to invoke it, the superclass finalizer will never be invoked.
 * It is possible to defend against such a careless or malicious subclass at the cost of creating an additional object for every object to be finalized.
 * Instead of putting the finalizer on the class requiring finalization,
 * put the finalizer on an anonymous class whose sole purpose is to finalize its enclosing instance.
 * A single instance of the anonymous class, called a "finalizer guardian", is created for each instance of the enclosing class.
 *
 * Don't use finalizers except as a safety net or to terminate noncritical native resources.
 * Remember to invoke super.finalize.
 * If you use a finalizer as a safety net, remember to log the invalid usage from the finalizer.
 * If you need to associate a finalizer with a public, nonfinal class, consider using a finalizer guardian,
 * so finalization can take place even if a subclass finalizer fails to invoke super.finalize.
 */
public class Item07_Finalizer {

    /**
     * Explicit termination method
     */
    public static class Foo1 {
        public void terminate() {}
    }

    /**
     * Manual finalizer chaining
     */
    public static class Foo2 {
        @Override
        protected void finalize() throws Throwable {
            try {
                // Finalize subclass state
            } finally {
                super.finalize();
            }
        }
    }

    /**
     * Finalizer guardian idiom
     */
    public static class Foo3 {
        // Sole purpose of this object is to finalize outer Foo object
        private final Object finalizerGuardian = new Object() {
            @Override
            protected void finalize() throws Throwable {
                // Finalize outer Foo object
            }
        };
    }



    public static void main(String[] args) {
        /**
         * try-finally block guarantees execution of termination method
         */
        Foo1 foo = new Foo1();
        try {
            // Do what must be done with foo
        } finally {
            foo.terminate(); // Explicit termination method
        }
    }

}
