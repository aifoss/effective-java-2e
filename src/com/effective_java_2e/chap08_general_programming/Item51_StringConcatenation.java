package com.effective_java_2e.chap08_general_programming;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 51: Beware the performance of string concatenation.
 *
 * Using the string concatenation operator repeatedly to concatenate n strings requires time quadratic to n.
 * It is an unfortunate consequence of the fact that strings are immutable.
 * When two strings are concatenated, the contents of both are copied.
 * To achieve acceptable performance, use a StringBuilder in place of a String.
 */
public class Item51_StringConcatenation {

    /**
     * Inappropriate use of string concatenation
     */
    public String statement() {
        String result = "";
        for (int i = 0; i < numItems(); i++)
            result += lineForItem(i);   // String concatenation
        return result;
    }

    private int numItems() { return 10; }
    private String lineForItem(int i) { return ""; }

    /**
     * Using StringBuilder instead of String
     */
    public String statement2() {
        StringBuilder sb = new StringBuilder(numItems() * LINE_WIDTH);
        for (int i = 0; i < numItems(); i++)
            sb.append(lineForItem(i));
        return sb.toString();
    }

    static final int LINE_WIDTH = 10;



    public static void main(String[] args) {

    }

}
