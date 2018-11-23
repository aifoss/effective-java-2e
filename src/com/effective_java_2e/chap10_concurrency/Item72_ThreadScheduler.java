package com.effective_java_2e.chap10_concurrency;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 72: Don't depend on the thread scheduler.
 *
 * When many threads are runnable, the thread scheduler determines which ones get to run, and for how long.
 * Any program that relies on the thread scheduler for correctness or performance is likely to be nonportable.
 *
 * The best way to write a robust, responsive, portable program is to ensure
 * that the average number of runnable threads is not significantly greater than the number of processors.
 * This leaves the thread scheduler with little choice:
 * it simply runs the runnable threads till they're no longer runnable.
 *
 * The main technique for keeping the number of runnable threads down
 * is to have each thread do some useful work and then wait for more.
 * Threads should not run if they aren't doing useful work.
 *
 * In terms of the Executor Framework,
 * this means sizing your thread pools appropriately, and keeping tasks reasonably small and independent of one another.
 *
 * Threads should not "busy-wait", repeatedly checking a shared object waiting for something to happen.
 *
 * When faced with a program that barely works because some threads aren't getting enough CPU time relative to others,
 * resist the temptation to "fix" the program by putting in calls to Thread.yield.
 * Thread.yield has no testable semantics.
 * A better course of action is to restructure the application to reduce the number of concurrently runnable threads.
 *
 * A related technique, to which similar caveats apply, is adjusting thread priorities.
 * Thread priorities are among the least portable features of the Java platform.
 *
 * You should use Thread.sleep(1) instead of Thread.yield for concurrency testing.
 * Do not use Thread.sleep(0), which can return immediately.
 *
 * In summary:
 * Do not depend on the thread scheduler for the correctness of your program.
 * The resulting program will be neither robust nor portable.
 * As a corollary, do not rely on Thread.yield or thread priorities.
 */
public class Item72_ThreadScheduler {

    /**
     * Awful CountDownLatch implementation - busy-waits incessantly!
     */
    public class SlowCountDownLatch {
        private int count;

        public SlowCountDownLatch(int count) {
            if (count < 0)
                throw new IllegalArgumentException(count + " < 0");
            this.count = count;
        }

        /**
         * Busy-waiting
         */
        public void await() {
            while (true) {
                synchronized (this) {
                    if (count == 0)
                        return;
                }
            }
        }

        public synchronized void countDown() {
            if (count != 0)
                count--;
        }
    }



    public static void main(String[] args) {

    }

}
