package com.effective_java_2e.chap08_general_programming;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 45: Minimize the scope of local variables.
 *
 * The most powerful technique for minimizing the scope of a local variable is to declare it where it is first used.
 *
 * Nearly every local variable declaration should contain an initializer.
 * One exception to this rule concerns try-catch statements.
 * If a variable is initialized by a method that throws a checked exception, it must be initialized inside a try bock.
 * If the value must be used outside of the try block, then it must be declared before the try block.
 *
 * The for loop allows you to declare "loop variables", limiting their scope to the exact region where they're needed.
 * Therefore, prefer for loops to while loops.
 *
 * A final technique to minimize the scope of local variables is to keep methods small and focused.
 */
public class Item45_LocalVariable {

    public static void main(String[] args) {

    }

}
