package com.effective_java_2e.chap10_concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sofia on 5/23/17.
 */

/**
 * Item 66: Synchronize access to shared mutable data.
 *
 * The "synchronized" keyword ensures that only a single thread can execute a method or block at one time.
 * Not only does synchronization prevent a thread from observing an object in an inconsistent state,
 * but it ensures that each thread entering a synchronized method or block sees the effects of all previous modifications
 * that were guarded by the same lock.
 *
 * Reading or writing a variable is "atomic" unless the variable is of type long or double.
 * In other words, reading a variable other than a long or double is guaranteed to return a value
 * that was stored into that variable by some thread, even if multiple threads modify the variable concurrently and without synchronization.
 *
 * While the language specification guarantees that a thread will not see an arbitrary value when reading a field,
 * it does not guarantee that a value written by one thread will be visible to another.
 *
 * Synchronization is required for reliable communication between threads as well as for mutual exclusion.
 * This is due to a part of the language specification known as the "memory model",
 * which specifies when and how changes made by one thread become visible to others.
 *
 * The consequences of failing to synchronize access to shared mutable data can be dire
 * even if the data is atomically readable and writable.
 * The Thread.stop method, which allows one thread to stop another, is inherently unsafe
 * - its use can result in data corruption.
 * Do not use Thread.stop.
 * A recommended way to stop one thread from another is to have the first thread poll a boolean field
 * that is initially false but can be set to true by the second thread to indicate that the first thread is to stop itself.
 *
 * In the absence of synchronization, it's quite acceptable for the virtual machine to transform this code:
 *
 *  while (!done)
 *      i++;
 *
 * into this code:
 *
 *  if (!done)
 *      while (true)
 *          i++;
 *
 * This optimization is known as "hoisting", and it is precisely what the HotSpot server VM does.
 * The result is a "liveness failure": the program fails to make progress.
 * One way to fix the problem is to synchronize access to the boolean field.
 *
 * It is not sufficient to synchronize only the write method.
 * Synchronization has no effect unless both read and write operations are synchronized.
 *
 * The synchronized locking can be omitted if the boolean variable is declared volatile.
 * While the "volatile" modifier performs no mutual exclusion,
 * it guarantees that any thread that reads the field will see the most recently written value.
 *
 * You do have to be careful when using volatile.
 * The problem is that the increment operator (++) is not atomic.
 * It performs two operation: first it reads the value, then it writes back a new value, equal to the old value plus one.
 * If a second thread reads the field between the time a thread reads the old value and writes back a new one,
 * the second thread will see the same value as the first and return the same number.
 * This is a "safety failure": the program computes the wrong results.
 *
 * One way to fix this problem is to add the "synchronized" modifier.
 * Better still, use the class AtomicLong, which is part of java.util.concurrent.atomic.
 * It does exactly what you want and is likely to perform better than the synchronized version.
 *
 * The best way to avoid the problems discussed in this item is not to share mutable data.
 * Either share immutable data, or don't share at all.
 * In other words, confine mutable data to a single thread.
 *
 * It is acceptable for one thread to modify a data object for a while and then to share it with other threads,
 * synchronizing only the act of sharing the object reference.
 * Other threads can then read the object without further synchronization,
 * so long as it isn't modified again.
 * Such objects are said to be "effectively immutable".
 * Transferring such an object reference from one thread to others is called "safe publication".
 * There are many ways to safely publish an object reference:
 * You can store it in a static field as part of class initialization.
 * You can store it in a volatile filed, a final field, or a field that is accessed with normal locking.
 * You can put it into a concurrent collection.
 *
 * In summary:
 * When multiple threads share mutable data, each thread that reads or writes the data must perform synchronization.
 * The penalties for failing to synchronize shared mutable data are liveness and safety failures.
 * If you need only inter-thread communication, and not mutual exclusion, the "volatile" modifier is an acceptable form of synchronization,
 * but it can be tricky to use correctly.
 */
public class Item66_SynchronizedAccess {

    /**
     * Broken! - How long would you expect this program to run? - never terminates
     */
    public static class StopThread {
        private static boolean stopRequested;

        public static void main(String[] args) throws InterruptedException {
            Thread backgroundThread = new Thread(new Runnable() {
                public void run() {
                    int i = 0;
                    /**
                     * In the absence of synchronization, there is no guarantee as to when, if ever,
                     * the background thread will see the change in the value of stopRequested
                     * that was made by the main thread.
                     */
                    while (!stopRequested)
                        i++;
                }
            });
            backgroundThread.start();

            TimeUnit.SECONDS.sleep(1);
            stopRequested = true;
        }
    }

    /**
     * Properly synchronized cooperative thread termination
     */
    public static class StopThread2 {
        private static boolean stopRequested;

        private static synchronized void requestStop() {
            stopRequested = true;
        }

        private static synchronized boolean stopRequested() {
            return stopRequested;
        }

        public static void main(String[] args) throws InterruptedException {
            Thread backgroundThread = new Thread(new Runnable() {
                public void run() {
                    int i = 0;
                    while (!stopRequested())
                        i++;
                }
            });
            backgroundThread.start();

            TimeUnit.SECONDS.sleep(1);
            requestStop();
        }
    }

    /**
     * Cooperative thread termination with a volatile field
     */
    public static class StopThread3 {
        private static volatile boolean stopRequested;

        public static void main(String[] args) throws InterruptedException {
            Thread backgroundThread = new Thread(new Runnable() {
                public void run() {
                    int i = 0;
                    while (!stopRequested)
                        i++;
                }
            });
            backgroundThread.start();

            TimeUnit.SECONDS.sleep(1);
            stopRequested = true;
        }
    }

    /**
     * Broken - requires synchronization
     */
    private static volatile int nextSerialNumber = 0;

    public static int generateSerialNumber() {
        return nextSerialNumber++;  // ++ is not atomic
    }

    /**
     * Fixed with AtomicLong
     */
    private static final AtomicLong nextSerialNum = new AtomicLong();

    public static long generateSerialNumber2() {
        return nextSerialNum.getAndIncrement();
    }



    public static void main(String[] args) {

    }

}
