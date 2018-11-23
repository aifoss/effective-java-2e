package com.effective_java_2e.chap10_concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by sofia on 5/23/17.
 */

/**
 * Item 69: Prefer concurrency utilities to wait and notify.
 *
 * Given the difficulty of using wait and notify correctly, you should use the higher-level concurrency utilities instead.
 *
 * The higher-level utilities in java.util.concurrent fall into 3 categories:
 * (1) the Executor Framework
 * (2) concurrent collections
 * (3) synchronizers
 *
 * The concurrent collections provide high-performance concurrent implementations of standard collection interfaces.
 * To provide high concurrency, these implementations manage their own synchronization internally.
 * Therefore, it is impossible to exclude concurrent activity from a concurrent collection;
 * locking it will have no effect but to slow the program.
 *
 * This means that clients can't atomically compose method invocations on concurrent collections.
 * Some of the collection interfaces have therefore been extended with "state-dependent modify operations",
 * which combine several primitives into a single atomic operation.
 * For example, ConcurrentMap extends Map and adds several methods.
 *
 * Unless you have a compelling reason to do otherwise,
 * use ConcurrentHashMap in preference to Collections.synchronizedMap or Hashtable.
 * More generally, use concurrent collections in preference to externally synchronized collections.
 *
 * Some of the collection interfaces have been extended with "blocking operations",
 * which wait (or block) until they can be successfully performed.
 * For example, BlockingQueue extends Queue and adds several methods,
 * including take, which removes and returns the head element from the queue, waiting if the queue is empty.
 * This allows blocking queues to be used for "work queues" (also known as "producer-consumer queues"),
 * to which one or more "producer threads" enqueue work items
 * and from which one or more "consumer threads" dequeue and process items as they become available.
 * Most ExecutorService implementations, including ThreadPoolExecutor, use a BlockingQueue.
 *
 * Synchronizers are objects that enable threads to wait for one another, allowing them to coordinate their activities.
 * The most commonly used synchronizers are CountDownLatch and Semaphore.
 * Less commonly used are CyclicBarrier and Exchanger.
 *
 * Countdown latches are single-use barriers that allow one or more threads to wait for one or more other threads to do something.
 * The sole constructor for CountDownLatch takes an int that is the number of times that countDown method must be invoked on the latch
 * before all waiting threads are allowed to proceed.
 *
 * For example, suppose you want to build a simple framework for timing the concurrent execution of an action.
 * This framework consists of a single method that takes an executor to execute the action,
 * a concurrently level representing the number of actions to be executed concurrently,
 * and a runnable representing the action.
 * All of the worker threads ready themselves to run the action before the timer thread starts the clock.
 * When the last worker thread is ready to run the action, the timer thread fires the starting gun,
 * allowing the worker threads to perform the action.
 * As soon as the last worker thread finishes performing the action, the timer thread stops the clock.
 * It is straightforward to implement this logic on top of CountDownLatch.
 *
 * The executor that is passed must allow for the creation of at least as many threads as the given concurrency level,
 * or the test will never complete.
 * This is known as a "thread starvation deadlock".
 * If a worker thread catches an InterruptedException, it reasserts the interrupt using the idiom Thread.currentThread().interrupt()
 * and returns from its run method.
 * This allows the executor to deal with the interrupt as it sees fit.
 *
 * For internal timing, always use System.nanoTime in preference to System.currentTimeMillis.
 * System.nanoTime is both more accurate and more precise, and it is not affected by adjustments to the system's real-time clock.
 *
 * You might have to maintain legacy code that uses wait and notify.
 * The wait method is used to make a thread wait for some operation.
 * It must be invoked inside a synchronized region that locks the object on which it is invoked.
 * Here is the standard idiom for using the wait method:
 *
 *  // The standard idiom for using the wait method
 *  synchronized (obj) {
 *      while (<condition does not hold>)
 *          obj.wait(); // Releases lock, and reacquires on wakeup
 *
 *      ... // Perform action appropriate to condition
 *  }
 *
 * Always use the wait loop idiom to invoke the wait method; never invoke it outside of a loop.
 * The loop serves to test the condition before and after waiting.
 *
 * Testing the condition before waiting and skipping the wait of the condition already holds are necessary to ensure liveness.
 * If the condition already holds and the notify (or notifyAll) method has already been invoked before a thread waits,
 * there is no guarantee that the thread will ever wake from the wait.
 *
 * Testing the condition after waiting and waiting again if the condition does not hold are necessary to ensure safety.
 * If the thread proceeds with the action when the condition does not hold, it can destroy the invariant guarded by the lock.
 *
 * There are several reasons a thread might wake up when the condition does not hold:
 *
 * (1) Another thread could have obtained the lock and changed the guarded state between the time a thread invoked notify
 *     and the time the waiting thread woke.
 *
 * (2) Another thread could have invoked notify accidentally or maliciously when the condition did not hold.
 *
 *     Classes expose themselves to this sort of mischief by waiting on publicly accessible objects.
 *     Any wait contained in a synchronized method of a publicly accessible object is susceptible to this problem.
 *
 * (3) The notifying thread could be overly "generous" in waking waiting threads.
 *
 *     For example, the notifying thread might invoke notifyAll even if only some of the waiting threads have their condition satisfied.
 *
 * (4) The waiting thread could (rarely) wake up in the absence of a notify.
 *
 *     This is known as a "spurious wakeup".
 *
 * A related issue is whether you should use notify or notifyAll to wake waiting threads.
 * It is often said that you should always use notifyAll.
 * It will always yield correct results because it guarantees that you'll wake the threads that need to be awakened.
 * As an optimization, you may choose to invoke notify instead of notifyAll
 * if all threads that could be in the wait-set are waiting for the same condition
 * and only one thread at a time can benefit from the condition becoming true.
 *
 * Even if these conditions appear true, there may be cause to use notifyAll in place of notify.
 * Just as placing the wait invocation in a loop protects against accidental or malicious notifications on a publicly accessible object,
 * using notifyAll in place of notify protects against accidental or malicious waits by an unrelated thread.
 * Such waits could otherwise "swallow" a critical notification, leaving its intended recipient waiting indefinitely.
 *
 * In summary:
 * Using wait and notify directly is like programming in "concurrency assembly language"
 * as compared to the higher-level language provided by java.util.concurrent.
 * There is seldom, if ever, a reason to use wait and notify in new code.
 * If you maintain code that uses wait and notify, make sure that it always invokes wait from within a while loop using the standard idiom.
 * The notifyAll method should generally be used in preference to notify.
 * If notify is used, great care must be taken to ensure liveness.
 */
public class Item69_ConcurrencyUtilities {

    /**
     * Concurrent canonicalizing map atop ConcurrentMap - not optimal
     */
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    public static String intern(String s) {
        String previousValue = map.putIfAbsent(s, s);
        return previousValue == null ? s : previousValue;
    }

    /**
     * Concurrent canonicalizing map atop ConcurrentMap - faster
     */
    public static String intern2(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null)
                result = s;
        }
        return result;
    }

    /**
     * Simple framework for timing concurrent execution using CountDownLatch
     */
    public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    ready.countDown();      // Tell timer we're ready

                    try {
                        start.wait();       // Wait till peers are ready
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();   // Tell timer we're done
                    }
                }
            });
        }

        ready.await();      // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown();  // And they're off
        done.await();       // Wait for all workers to finish
        return System.nanoTime() - startNanos;
    }



    public static void main(String[] args) {

    }

}
