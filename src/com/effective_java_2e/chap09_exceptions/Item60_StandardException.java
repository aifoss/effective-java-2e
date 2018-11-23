package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 60: Favor the use of standard exceptions.
 *
 * One of the attributes that most strongly distinguishes expert programmers from less experienced ones
 * is that experts strive for and usually achieve a high degree of code reuse.
 * Exceptions are no exception to the general rule that code reuse is good.
 *
 * Reusing preexisting exceptions has several benefits.
 * Chief among these, it makes your API easier to learn and use.
 * A close second is that programs using your API are easier to read.
 * Last, fewer exception classes mean a smaller memory footprint and less time spent loading classes.
 *
 * Most commonly used exceptions:
 *
 * Exception                        Occasion for Use
 * IllegalArgumentException         Non-null parameter value is inappropriate
 * IllegalStateException            Object state is inappropriate for method invocation
 * NullPointerException             Parameter value is null where prohibited
 * IndexOutOfBoundsException        Index parameter value is out of range
 * ConcurrentModificationException  Concurrent modification of an object has been detected where it is prohibited
 * UnsupportedOperationException    Object does not support method
 */
public class Item60_StandardException {

    public static void main(String[] args) {

    }

}
