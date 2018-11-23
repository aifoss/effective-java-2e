package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 58: Use checked exceptions for recoverable conditions and runtime exceptions for programming errors.
 *
 * The Java programming language provides 3 kinds of throwables: checked exceptions, runtime exceptions, and errors.
 *
 * The cardinal rule in deciding whether to use a checked or an unchecked exception is this:
 * use checked exceptions for conditions from which the caller can reasonably be expected to recover.
 *
 * By throwing a checked exception, you force the caller to handle the exception in a catch clauses or to propagate it outward.
 * Each checked exception that a method is declared to throw is therefore a potent indication to the API user
 * that the associated condition is a possible outcome of invoking the method.
 *
 * There are 2 kinds of unchecked throwables: runtime exceptions and errors.
 * They are identical in their behavior: both are throwables that needn't, and generally shouldn't, be caught.
 * If a program throws an unchecked exception or an error,
 * it is generally the case that recovery is impossible and continued execution would do more harm than good.
 * If a program does not catch such a throwable, it will cause the current thread to halt with an appropriate error message.
 *
 * Use runtime exceptions to indicate programming errors.
 *
 * The great majority of runtime exceptions indicate precondition violations.
 *
 * There is a strong convention that errors are reserved for use by the JVM
 * to indicate resource deficiencies, invariant failures, or other conditions that make it impossible to continue execution.
 *
 * It's best not to implement any new Error subclasses.
 * Therefore, all of the unchecked throwables you implement should subclass RuntimeException.
 *
 * Because checked exceptions generally indicate recoverable conditions,
 * it's important for such exceptions to provide methods that furnish information that could help the caller to recover.
 */
public class Item58_CheckedVsUnchecked {

    public static void main(String[] args) {

    }

}
