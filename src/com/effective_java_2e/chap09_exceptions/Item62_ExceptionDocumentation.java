package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 62: Document all exceptions thrown by each method.
 *
 * Always declare checked exceptions individually,
 * and document precisely the conditions under which each one is thrown using the Javadoc @throws tag.
 *
 * It is wise to document unchecked exceptions as carefully as the checked exceptions.
 * A well-documented list of the unchecked exceptions that a method can throw effectively describes the preconditions for its successful execution.
 *
 * It is particularly important that methods in interfaces document the unchecked exceptions they may throw.
 * This documentation forms a part of the interface's general contract and enables common behavior among multiple implementations.
 *
 * Use the Javadoc @throws tag to document each unchecked exception that a method can throw,
 * but do not use the throws keyword to include unchecked exceptions in the method declaration.
 *
 * If an exception is thrown by many methods in a class for the same reason,
 * it is acceptable to document the exception in the class's documentation comment.
 */
public class Item62_ExceptionDocumentation {

    public static void main(String[] args) {

    }

}
