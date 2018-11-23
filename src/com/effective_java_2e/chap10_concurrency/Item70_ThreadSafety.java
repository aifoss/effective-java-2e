package com.effective_java_2e.chap10_concurrency;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 70: Document thread safety.
 *
 * How a class behaves when its instances or static methods are subjected to concurrent use
 * is an important part of the contract the class makes with its clients.
 *
 * The presence of the synchronized modifier in a method declaration is an implementation detail,
 * not a part of its exported API.
 * It does not reliably indicate that a method is thread-safe.
 *
 * To enable safe concurrent use, a class must clearly document what level of thread safety it supports.
 *
 * The following list summarizes levels of thread safety:
 *
 * (a) immutable - Instances of this class appear constant. No external synchronization is necessary.
 *
 *     e.g., String, Long, BigInteger
 *
 * (b) unconditionally thread-safe - Instances of this class are mutable, but the class has sufficient internal synchronization
 *     that its instances can be used concurrently without the need for any external synchronization.
 *
 *     e.g., Random, ConcurrentHashMap
 *
 * (c) conditionally thread-safe - Like unconditionally thread-safe, except that some methods require external synchronization.
 *
 *     e.g., collections returned by Collections.synchronized wrappers
 *
 * (d) not thread-safe - Instances of this class are mutable.
 *     To use them concurrently, clients must surround each method invocation (or invocation sequence)
 *     with external synchronization of the clients' choosing.
 *
 *     e.g., general-purpose collection implementations, such as ArrayList, HashMap
 *
 * (e) thread-hostile - This class is not safe for concurrent use even if all method invocations are surrounded by external synchronization.
 *     Thread hostility usually results from modifying static data without synchronization.
 *     Such classes result from failure to consider concurrency.
 *
 *     e.g., System.runFinalizerOnExit (deprecated)
 *
 * These categories (apart from thread-hostile) correspond roughly to the "thread safety annotations" in [Java Concurrency in Practice],
 * which are Immutable, ThreadSafe, and NotThreadSafe.
 * The unconditionally and conditionally thread-safe categories in the above taxonomy are both covered under the ThreadSafe annotation.
 *
 * Documenting a conditionally thread-safe class requires care.
 * You must indicate which invocation sequences require external synchronization,
 * and which lock (or which locks) must be acquired to execute these sequences.
 * Typically it is the lock on the instance itself, but there are exceptions.
 * If an object represents a view on some other object,
 * the client generally must synchronize on the backing object,
 * so as to prevent its direct modification.
 *
 * For example, the documentation for Collections.synchronizedMap says this:
 *
 *      It is imperative that the user manually synchronize on the returned map when
 *      iterating over any of its collection views:
 *
 *      Map<K, V> m = Collections.synchronizedMap(new HashMap<K, V>());
 *      ...
 *      Set<K> s = m.keySet();  // Needn't be in synchronized block
 *      ...
 *      synchronized(m) { // Synchronizing on m, not s!
 *          for (K key : s)
 *              key.f();
 *      }
 *
 *      Failure to follow this advice may result in non-deterministic behavior.
 *
 * The description of a class's thread safety generally belongs in its documentation comment,
 * but methods which special thread safety properties should describe these properties in their own documentation comments.
 * It is not necessary to document the immutability of enum types.
 * Unless it is obvious from the return type, static factories must document the thread safety of the returned object.
 *
 * When a class commits to using a publicly accessible lock, it enables clients to execute a sequence of method invocations atomically,
 * but this flexibility comes at a price.
 * It is incompatible with high-performance internal concurrency control,
 * of the sort used by concurrent collections such as ConcurrentHashMap and ConcurrentLinkedQueue.
 * Also, a client can mount a denial-of-service attack by holding the publicly accessible lock for a prolonged period.
 *
 * To prevent this denial-of-service attack, you can use a "private lock object" instead of using synchronized methods:
 *
 *      // Private lock object idiom - thwarts denial-of-service attack
 *      private final Object lock = new Object();
 *
 *      public void foo() {
 *          synchronized (lock) {
 *              ...
 *          }
 *      }
 *
 * Because the private lock object is inaccessible to clients of the class,
 * it is impossible for them to interfere with the object's synchronization.
 *
 * Declaring the lock field final prevents you from inadvertently changing its contents,
 * which could result in catastrophic unsynchronized access to the containing object.
 *
 * The private lock object idiom can be used only on unconditionally thread-safe classes.
 * Conditionally thread-safe classes can't use this idiom because they must document which lock their clients are to acquire
 * when performing certain method invocation sequences.
 *
 * The private lock object idiom is particularly well-suited for classes designed for inheritance.
 * If such a class were to use its instances for locking,
 * a subclass could easily and unintentionally interfere with the operation of the base class, or vice versa.
 *
 * To summarize:
 * Every class should clearly document its thread safety properties
 * with a carefully worded prose description or a thread safety annotation.
 * The synchronized modifier plays no part in this documentation.
 * Conditionally thread-safe classes must document which method invocation sequences require external synchronization,
 * and which lock to acquire when executing these sequences.
 * If you write an unconditionally thread-safe class,
 * consider using a private lock object in place of synchronized methods.
 * This protects you against synchronization interference by clients and subclasses
 * and gives you the flexibility to adopt a more sophisticated approach to concurrency control in a later release.
 */
public class Item70_ThreadSafety {

    public static void main(String[] args) {

    }

}
