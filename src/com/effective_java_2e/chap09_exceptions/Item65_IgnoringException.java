package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 65: Don't ignore exceptions.
 *
 * It is easy to ignore exceptions by surrounding a method invocation with a try statement with an empty catch block:
 *
 *  // Empty catch block ignores exception - Highly suspect!
 *  try {
 *      ...
 *  } catch (SomeException e) {
 *
 *  }
 *
 * An empty catch block defeats the purpose of exceptions, which is to force you to handle exceptional conditions.
 * At the very least, the catch block should contain a comment explaining why it is appropriate to ignore the exception.
 *
 * The advice in this item applies equally to checked and unchecked exceptions.
 */
public class Item65_IgnoringException {

    public static void main(String[] args) {

    }

}
