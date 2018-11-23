package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 61: Throw exceptions appropriate to the abstraction.
 *
 * Higher layers should catch lower-level exceptions, and, in their place,
 * throws exceptions that can be explained in terms of the higher-level abstraction.
 * This idiom is known as "exception translation":
 *
 *  // Exception translation
 *  try {
 *      // Use lower-level abstraction to do our bidding
 *      ...
 *  } catch (LowerLevelException e) {
 *      throw new HigherLevelException();
 *  }
 *
 * Example of exception translation from AbstractSequentialList:
 *
 *  public E get(int index) {
 *      ListIterator<E> i = listIterator(index);
 *      try {
 *          return i.next();
 *      } catch (NoSuchElementException e) {
 *          throw new IndexOutOfBoundsException("Index: " + index);
 *      }
 *  }
 *
 * A special form of exception translation called "exception chaining" is appropriate
 * in cases where the lower-level exception might be helpful to someone debugging the problem that cause the higher-level exception.
 * The lower-level exception (the cause) is passed to the higher-level exception,
 * which provides an accessor method (Throwable.getCause) to retrieve the lower-level exception:
 *
 *  // Exception chaining
 *  try {
 *      ... // Use lower-level abstraction to do our bidding
 *  } catch (LowerLevelException cause) {
 *      throw new HigherLevelException(cause);
 *  }
 *
 * The higher-level exception's constructor passes the cause to a chaining-aware superclass constructor,
 * so it is ultimately passed to one of Throwable's chaining-aware constructors, such as Throwable(Throwable):
 *
 *  // Exception with chaining-aware constructor
 *  class HigherLevelException extends Exception {
 *      HigherLevelException(Throwable cause) {
 *          super(cause);
 *      }
 *  }
 *
 * Most standard exceptions have chaining-aware constructors.
 * For exceptions that don't, you can set the cause using Throwable's initCause method.
 *
 * While exception translation is superior to mindless propagation of exceptions from lower layers,
 * it should not be overused.
 *
 * The best way to deal with exceptions from lower layers is to avoid them,
 * by ensuring that lower-level methods succeed.
 * Sometimes you can do this by checking the validity of the higher-level method's parameters.
 * If it is impossible to prevent exceptions from lower layers,
 * the next best thing is to have the higher layer silently work around these exceptions,
 * insulating the caller of the higher-level method from lower-level problems.
 * Under these circumstances, it may be appropriate to log the exception.
 *
 * In summary:
 * If it isn't feasible to prevent or to handle exceptions from lower layers,
 * use exception translation, unless the lower-level method happens to guarantee
 * that all of its exceptions are appropriate to the higher level.
 * Chaining provides the best of both worlds:
 * it allows you to throw an appropriate higher-level exception,
 * while capturing the underlying cause for failure analysis.
 */
public class Item61_AppropriateException {

    public static void main(String[] args) {

    }

}
