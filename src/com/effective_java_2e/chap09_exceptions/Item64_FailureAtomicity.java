package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 64: Strive for failure atomicity.
 *
 * After an object throws an exception, it is generally desirable that the object still be in a well-defined, usable state,
 * even if the failure occurred in the midst of performing an operation.
 * This is especially true for checked exceptions, from which the caller is expected to recover.
 *
 * Generally speaking, a failed method invocation should leave the object in the state that it was in prior to the invocation.
 * A method with this property is said to be "failure atomic".
 *
 * There are several ways to achieve this effect:
 *
 * 1. The simplest is to design immutable objects.
 *
 * 2. For methods that operate on mutable objects,
 *    the most common way to achieve failure atomicity is to check parameters for validity before performing the operation.
 *
 *    public Object pop() {
 *        if (size == 0)
 *              throw new EmptyStackException();
 *        ...
 *    }
 *
 *    A closely related approach is to order the computation
 *    so that any part that may fail takes place before any part that modifies the object.
 *
 * 3. A far less common approach is to write "recovery code" that intercepts a failure that occurs in the midst of an operation
 *    and causes the object to roll back its state to the point before the operation began.
 *
 *    This approach is used mainly for durable (disk-based) data structures.
 *
 * 4. A final approach is to perform the operation on a temporary copy of the object
 *    and to replace the contents of the object with the temporary copy once the operation is complete.
 *
 *    This approach occurs naturally when the computation can be performed more quickly once the data has been stored in a temporary data structure.
 *
 * While failure atomicity is generally desirable, it is not always achievable.
 * For example, if two threads attempt to modify the same object concurrently without proper synchronization,
 * the object may be left in an inconsistent state.
 * As a rule, errors, as opposed to exceptions, are unrecoverable,
 * and methods need not even attempt to preserve failure atomicity when throwing errors.
 *
 * Even where failure atomicity is possible, it is not always desirable.
 * For some operations, it would significantly increase the cost or complexity.
 *
 * As a general rule, any generated exception that is part of a method's specification should leave the object
 * in the same state it was in prior to the method invocation.
 * Where this rule is violated, the API documentation should clearly indicate what state the object will be left in.
 */
public class Item64_FailureAtomicity {

    public static void main(String[] args) {

    }

}
