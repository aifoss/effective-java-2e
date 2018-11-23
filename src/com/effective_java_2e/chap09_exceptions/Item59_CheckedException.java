package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 59: Avoid unnecessary use of checked exceptions.
 *
 * The burden to handle a checked exception by the user of an API is justified
 * if the exceptional condition cannot be prevented by proper use of the API
 * and the programmer using the API can take some useful action once confronted with the exception.
 * Unless both of these conditions hold, an unchecked exception is more appropriate.
 *
 * As a litmus test, ask yourself how the programmer will handle the exception.
 *
 *  } catch (TheCheckedException e) {
 *      throw new AssertionError(); // Can't happen
 *  }
 *
 *  } catch (TheCheckedException e) {
 *      e.printStackTrace();        // Oh well, we lose
 *      System.exit(1);
 *  }
 *
 * If the programmer using the API can do no better, an unchecked exception would be more appropriate.
 *
 * The additional burden on the programmer caused by a checked exception is substantially higher
 * if it is the sole checked exception thrown by a method.
 *
 * One technique for turning a checked exception into an unchecked exception
 * is to break the method that throws the exception into two methods,
 * the first of which returns a boolean that indicates whether the exception would be thrown.
 *
 * This API refactoring transforms the calling sequence from this:
 *
 *  // Invocation with checked exception
 *  try {
 *      obj.action(args);
 *  } catch (TheCheckedException e) {
 *      // Handle exceptional condition
 *  }
 *
 * to this:
 *
 *  // Invocation with state-testing method and unchecked exception
 *  if (obj.actionPermitted(args)) {
 *      obj.action(args);
 *  } else {
 *      // Handle exceptional condition
 *  }
 *
 * In cases where the programmer knows the call will succeed or is content to let the thread terminate if the call fails,
 * the refactoring also allows this simple calling sequence:
 *
 *  obj.action(args);
 *
 * If an object is to be accessed concurrently without external synchronization or it is subject to externally induced state transitions,
 * this refactoring is inappropriate,
 * as the object's state may change between the invocations of actionPermitted and action.
 * If a separate actionPermitted method would duplicate the work of the action method,
 * the refactoring may be ruled out by performance concerns.
 */
public class Item59_CheckedException {

    public static void main(String[] args) {

    }

}
