package com.effective_java_2e.chap07_methods;

import java.math.BigInteger;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 38: Check parameters for validity.
 *
 * For public methods, use the Javadoc @throws tag to document the exception that will be thrown if a restriction on parameter values is violated.
 * Nonpublic methods should generally check their parameters using assertions.
 * Unlike normal validity checks, assertions throw AssertionError if they fail.
 * And unlike normal validity checks, they have no effect and essentially no cost unless you enable them,
 * which you do by passing the -ea (or -enableassertions) flag.
 *
 * It is particularly important to check the validity of parameters that are not used by a method but are stored away for later use.
 * It is critical to check the validity of constructor parameters to prevent the construction of an object that violates its class invariants.
 *
 * There are exceptions to the rule that you should check a method's parameters before performing its computation.
 *
 * An important exception is the case in which the validity check would be expensive or impractical
 * and the validity check is performed implicitly in the process of doing the computation.
 * However, indiscriminate reliance on implicit validity checks can result in a loss of failure atomicity.
 *
 * Occasionally, a computation implicitly performs a required validity check but throws the wrong exception if the check fails.
 * Under these circumstances, you should use the "exception translation" idiom, to translate the natural exception into the correct one.
 *
 * To summarize:
 * Each time you write a method or constructor, you should think about what restrictions exist on its parameters.
 * You should document these restrictions and enforce them with explicit checks at the beginning of the method body.
 */
public class Item38_Parameter {

    /**
     * Use of @throws in method documentation
     */
    /**
     * Returns a BigInteger whose value if (this mod m). This method
     * differs from the remainder method in that it always returns a
     * non-negative BigInteger.
     *
     * @param m the modulus, which must be positive
     * @return this mod m
     * @throws ArithmeticException if m is less than or equal to 0
     */
    public BigInteger mod(BigInteger m) {
        if (m.signum() <= 0)
            throw new ArithmeticException("Modulus <= 0: " + m);
        // Do the computation
        return null;
    }

    // Private helper function for a recursive sort
    private static void sort(long a[], int offset, int length) {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 && length <= a.length - offset;
        // Do the computation
    }



    public static void main(String[] args) {

    }

}
