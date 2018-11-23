package com.effective_java_2e.chap10_concurrency;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 73: Avoid thread groups.
 *
 * Along with threads, locks, and monitors, a basic abstraction offered by the threading system is "thread groups".
 *
 * Thread groups are obsolete.
 *
 * To summarize:
 * Thread groups don't provide much in the way of useful functionality,
 * and much of the functionality they do provide is flawed.
 * You should simply ignore their existence.
 * If you design a class that deals with logical groups of threads,
 * you should probably use thread pool executors.
 */
public class Item73_ThreadGroup {

    public static void main(String[] args) {

    }

}
