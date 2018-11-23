package com.effective_java_2e.chap08_general_programming;

import java.math.BigDecimal;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 48: Avoid float and double if exact answers are required.
 *
 * The float and double types are designed primarily for scientific and engineering calculations.
 * They perform binary floating-point arithmetic.
 * They do not provide exact results and should not be used where exact results are required.
 *
 * The float and double types are particularly ill-suited for monetary calculations,
 * because it is impossible to represent 0.1 (or any other negative power of ten) as a float or double exactly.
 *
 * The right away to solve this problem is to use BigDecimal, int, or long for monetary calculations.
 * Using BigDecimal has the added advantage that it gives you full control over rounding.
 * There are, however, two disadvantages to using BigDecimal:
 * it's less convenient than using a primitive arithmetic type and it's slower.
 *
 * An alternative to using BigDecimal is to use int or long.
 * If the quantities don't exceed 9 decimal digits, you can use int;
 * if they don't exceed 18 decimal digits, you can use long.
 * If the quantities might exceed 18 digits, you must use BigDecimal.
 */
public class Item48_FloatAndDouble {

    public static void main(String[] args) {
        /**
         * Broken - uses floating point for monetary calculation
         */
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = .10; funds >= price; price += .10) {
            funds -= price;
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Change: $" + funds);

        /**
         * Fixed using BigDecimal
         */
        final BigDecimal TEN_CENTS = new BigDecimal(".10");
        int itemsBought2 = 0;
        BigDecimal funds2 = new BigDecimal("1.00");
        for (BigDecimal price = TEN_CENTS; funds2.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
            itemsBought2++;
            funds2 = funds2.subtract(price);
        }
        System.out.println(itemsBought2 + " items bought.");
        System.out.println("Money left over: $" + funds2);

        /**
         * Fixed using int
         */
        int itemsBought3 = 0;
        int funds3 = 100;
        for (int price = 10; funds3 >= price; price += 10) {
            itemsBought3++;
            funds3 -= price;
        }
        System.out.println(itemsBought3 + " items bought.");
        System.out.println("Money left over: $" + funds3);
    }

}
