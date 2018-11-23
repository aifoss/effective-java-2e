package com.effective_java_2e.chap06_enums_and_annotations;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 32: Use EnumSet instead of bit fields.
 *
 * If the elements of an enumerated type are used primarily in sets,
 * it is traditional to use the int enum pattern, assigning a different power of 2 to each constant.
 *
 * The bit field representation lets you use the bitwise OR operation to combine several constants into a set, known as a "bit field".
 * The bit field representation also lets you perform set operations such as union and intersection efficiently using bitwise arithmetic.
 *
 * But bit fields have all the disadvantages of int enum constants and more.
 *
 * The java.util package provides the EnumSet class to efficiently represent sets of values drawn from a single enum type.
 *
 * In summary:
 * Just because an enumerated type will be used in sets, there is no reason to represent it with bit fields.
 * The EnumSet class combines the conciseness and performance of bit fields with all the many advantages of enum types.
 * The one real disadvantage of EnumSet is that it is not possible (as of 1.6) to create an immutable EnumSet.
 * You can wrap an EnumSet with Collections.unmodifiableSet.
 */
public class Item32_EnumSet {

    /**
     * Bit field enumeration constants - OBSOLETE
     */
    public static class Text {
        public static final int STYLE_BOLD          = 1 << 0;   // 1
        public static final int STYLE_ITALIC        = 1 << 1;   // 2
        public static final int STYLE_UNDERLINE     = 1 << 2;   // 4
        public static final int STYLE_STRIKETHROUGH = 1 << 3;   // 8

        // Parameter is bitwise OR of zero or more STYLE constants
        public void applyStyles(int styles) {}

        public static void main(String[] args) {
            Text text = new Text();
            text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
        }
    }

    /**
     * EnumSet - a modern replacement for bit fields
     */
    public static class Text2 {
        public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }

        // Any Set could be passed in, but EnumSet is clearly best
        public void applyStyles(Set<Style> styles) {}

        public static void main(String[] args) {
            Text2 text = new Text2();
            text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
        }
    }



    public static void main(String[] args) {

    }

}
