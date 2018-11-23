package com.effective_java_2e.chap09_exceptions;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 63: Include failure-capture information in detail messages.
 *
 * The detail message of an exception should capture the failure for subsequent analysis.
 * To capture the failure, the detail message should contain the values of all parameters and fields that contributed to the exception.
 *
 * One way to ensure that exceptions contain adequate failure-capture information in their detail messages
 * is to require this information in their constructors instead of a string detail message.
 *
 * It may be appropriate for an exception to provide accessor methods for its failure-capture information.
 * It is more important to provide such accessor methods on checked exceptions than on unchecked exceptions,
 * because the failure-capture information could be useful in recovering from the failure.
 * Even for unchecked exceptions, however, it seems advisable to provide these accessors on general principle.
 */
public class Item63_FailureCapture {

    /**
     * Providing failure-capture information in constructor
     */
    public class IndexOutOfBoundsException extends RuntimeException {
        private final int lowerBound;
        private final int upperBound;
        private final int index;

        /**
         * Construct an IndexOutOfBoundsException
         *
         * @param lowerBound the lowest legal index value
         * @param upperBound the highest legal index value plus one
         * @param index      the actual index value
         */
        public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
            // Generate a detail message that captures the failure
            super("Lower bound: " + lowerBound + ", Upper bound: " + upperBound + ", Index: " + index);

            // Save failure information for programmatic access
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.index = index;
        }
    }



    public static void main(String[] args) {

    }

}
