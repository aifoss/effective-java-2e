package com.effective_java_2e.chap06_enums_and_annotations;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 31: Use instance fields instead of ordinals.
 *
 * All enums have an ordinal() method, which returns the numerical position of each enum constant in its type.
 * You may be tempted to derive an associated int value from the ordinal.
 * While this works, it is a maintenance nightmare.
 *
 * Never derive a value associated with an enum from its ordinal;
 * store it in an instance field instead.
 */
public class Item31_InstanceField {

    /**
     * Abuse of ordinal to derive an associated value - DON'T DO THIS
     */
    public enum Ensemble {
        SOLO, DUET, TRIO, QUARTET, QUINTET,
        SEXTET, SEPTET, OCTET, NONET, DECTET;

        public int numberOfMusicians() {
            return ordinal() + 1;
        }
    }

    /**
     * Enum that stores enum value in instance field
     */
    public enum Ensemble2 {
        SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
        SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
        NONET(9), DECTET(10), TRIPLE_QUARTET(12);

        private final int numberOfMusicians;

        Ensemble2(int size) {
            numberOfMusicians = size;
        }

        public int numberOfMusicians() {
            return numberOfMusicians;
        }
    }



    public static void main(String[] args) {

    }

}
