package com.effective_java_2e.chap10_concurrency;

/**
 * Created by sofia on 5/23/17.
 */

/**
 * Item 68: Prefer executors and tasks to threads.
 *
 * The java.util.concurrent package contains an Executor Framework, which is a flexible interface-based task execution facility.
 *
 * Creating a work queue requires but a single line of code:
 *
 *  ExecutorService executor = Executor.newSingleThreadExecutor();
 *
 * Here is how to submit a runnable for execution:
 *
 *  executor.execute(runnable);
 *
 * And here is how to tell the executor to terminate gracefully:
 *
 *  executor.shutdown();
 *
 * If you want more than one thread to process requests from the queue,
 * simply call a different static factory that creates a different kind of executor service called a "thread pool".
 * You can create a thread pool with a fixed or variable number of threads.
 * That java.util.concurrent.Executors class contains static factories that provide most of the executors you'll ever need.
 * If, however, you want something out of the ordinary, you can use the ThreadPoolExecutor class directly.
 *
 * If you're writing a small program, or a lightly loaded server, using Executors.newCachedThreadPool is generally a good choice,
 * as it demands no configuration and generally does the right thing.
 * But a cached thread pool is not a good choice for a heavily loaded production server.
 * In a cached thread pool, submitted tasks are not queued but immediately handed off to a thread for execution.
 * If no threads are available, a new one is created.
 * Therefore, in a heavily loaded production server, you are much better off using Executors.newFixedThreadPool,
 * which gives you a pool with a fixed number of threads,
 * or using the ThreadPoolExecutor class directly, for maximum control.
 *
 * Not only should you refrain from writing your own work queues,
 * but you should generally refrain from working directly with threads.
 * The key abstraction is no longer Thread, which served as both the unit of work and the mechanism for executing it.
 * Now the unit of work and mechanism are separate.
 * The key abstraction is the unit of work, which is called a "task".
 * There are two kinds of tasks: Runnable and its close cousin Callable (which is like Runnable, except that it returns a value).
 * The general mechanism for executing tasks is the "executor service".
 * In essence, the Executor Framework does for execution what the Collections Framework did for aggregation.
 *
 * The Executor Framework also has a replacement for java.util.Timer,
 * which is ScheduledThreadPoolExecutor.
 * While it is easier to use a timer, a scheduled thread pool executor is much more flexible.
 * A timer uses only a single thread for task execution.
 * If a timer's sole thread throws an uncaught exception, the timer ceases to operate.
 * A scheduled thread pool executor supports multiple threads and recovers gracefully from tasks that throw unchecked exceptions.
 */
public class Item68_ExecutorAndTask {

    public static void main(String[] args) {

    }

}
