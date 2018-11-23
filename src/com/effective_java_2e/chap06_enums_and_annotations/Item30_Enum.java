package com.effective_java_2e.chap06_enums_and_annotations;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 30: Use enums instead of int constants.
 *
 * An "enumerated type" is a type whose legal value consists of a fixed set of constants.
 *
 * The "int enum pattern" has many shortcomings:
 *
 * 1. It provides nothing in the way of type safety and little in the way of convenience.
 *    Because int enums are compile-time constants, they are compiled into the clients that use them.
 *    If the int associated with an enum constant is changed, its clients must be recompiled.
 *
 * 2. There is no easy way to translate int enum constants into printable strings.
 *
 * 3. There is no reliable way to iterate over all the int enum constants in a group,
 *    or even to obtain the size of an int enum group.
 *
 * A "String enum pattern", where String constants are used in place of int constants, is even less desirable.
 * While it does provide printable strings for its constants,
 * it can lead to performance problems because it relies on string comparisons.
 * Worse, it can lead naive users to hard-code string constants into client code instead of using field names.
 *
 * Java's enum types are full-fledged classes, far more powerful than their counterparts in other languages (e.g., C, C++, C#).
 * The basic idea behind Java's enum types is simple:
 * they are classes that export one instance for each enumeration constant via a public static final field.
 * Enum types are effectively final, by virtue of having no accessible constructors.
 * Because clients can neither create an instances of an enum type nor extend it,
 * there can be no instances but the declared enum constants.
 * In other words, enum types are instance-controlled.
 * They are a generalization of singletons, which are essentially single-element enums.
 *
 * The advantages of enums over the int enum pattern:
 *
 * 1. Enums provide compile-time type safety.
 *
 * 2. Enum types with identically named constants coexist peacefully because each type has its own namespace.
 *
 * 3. You can add or reorder constants in an enum type without recompiling its clients:
 *    the constant values are not compiled into the clients as they are in the int enum pattern.
 *
 * 4. You can translate enums into printable strings by calling their toString method.
 *
 * 5. Enum types let you add arbitrary methods and fields and implement arbitrary interfaces.
 *
 * To associate data with enum constants, declare instance fields and write a constructor that takes the data and stores it in the fields.
 * Enums are by their nature immutable, so all fields should be final.
 * They can be public, but it is better to make them private and provide public accessors.
 *
 * Some behaviors associated with enum constants may need to be used only from within the class or package in which the enum is defined.
 * Such behaviors are best implemented as private or package-private methods.
 *
 * If an enum is generally useful, it should be a top-level class;
 * if its use is tied to a specific top-level class, it should be a member class of that top-level class.
 *
 * Sometimes you need to associate fundamentally different behavior with each constant.
 *
 * One way to achieve this is to switch on the value of the enum.
 * But the code using this method is fragile:
 * If you add a new enum constant but forget to add a corresponding case to the switch,
 * the enum will still compile, but it will fail at runtime when you try to apply the new operation.
 *
 * A better way to associate a different behavior with each enum constant
 * is to declare an abstract apply method in the enum type
 * and override it with a concrete method for each constant in a "constant-specific class body".
 * Such methods are known as "constant-specific method implementations".
 *
 * Constant-specific method implementations can be combined with constant-specific data.
 *
 * Enum types have an automatically generated valueOf(String) method that translates a constant's name into the constant itself.
 * If you override the toString method in an enum type,
 * consider writing a fromString method to translate the custom string representation back to the corresponding enum.
 *
 * A disadvantage of constant-specific method implementations is that they make it harder to share code among enum constants.
 *
 * Switches on enums are not a good choice for implementing constant-specific behavior on enums,
 * but they are good for augmenting external enum types with constant-specific behavior.
 *
 * In summary:
 * The advantages of enum types over int constants are compelling.
 * Enums are far more readable, safer, and more powerful.
 * Many enums require no explicit constructors or members,
 * but many others benefit from associating data with each constant and providing methods whose behavior is affected by this data.
 * Far fewer enums benefit from associating multiple behaviors with a single method.
 * In this relatively rare case, prefer constant-specific methods to enums that switch on their own values.
 * Consider the "strategy enum" pattern if multiple enum constants share common behaviors.
 */
public class Item30_Enum {

    /**
     * The int enum pattern - severely deficient
     */
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;

    /**
     * Enum types
     */
    public enum Apple { FUJI, PIPPIN, GRANNY_SMITH }
    public enum Orange { NAVEL, TEMPLE, BLOOD }

    /**
     * Enum type with data and behavior
     */
    public enum Planet {
        MERCURY(3.302e+23, 2.439e6),
        VENUS  (4.869e+24, 6.052e6),
        EARTH  (5.975e+24, 6.378e6),
        MARS   (6.419e+23, 3.393e6),
        JUPITER(1.899e+27, 7.149e7),
        SATURN (5.685e+26, 6.027e7),
        URANUS (8.683e+25, 2.556e7),
        NEPTUNE(1.024e+26, 2.477e7);

        private final double mass;              // In kilograms
        private final double radius;            // In meters
        private final double surfaceGravity;    // In m / s^2

        // Universal gravitational constant in m^3 / kg s^2
        private static final double G = 6.67300E-11;

        // Constructor
        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
            this.surfaceGravity = G * mass / (radius * radius);
        }

        public double mass() { return mass; }
        public double radius() { return radius; }
        public double surfaceGravity() { return surfaceGravity; }

        public double surfaceWeight(double mass) {
            return mass * surfaceGravity;       // F = ma
        }
    }

    public static class WeightTable {
        public static void main(String[] args) {
            double earthWeight = Double.parseDouble(args[0]);
            double mass = earthWeight / Planet.EARTH.surfaceGravity();
            for (Planet p : Planet.values())
                System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
        }
    }

    /**
     * Enum type that switches on its own value - questionable
     */
    public enum Operation {
        PLUS, MINUS, TIMES, DIVIDE;

        // Do the arithmetic op represented by this constant
        double apply(double x, double y) {
            switch(this) {
                case PLUS:      return x + y;
                case MINUS:     return x - y;
                case TIMES:     return x * y;
                case DIVIDE:    return x / y;
            }
            throw new AssertionError("Unknown op: "+this);
        }
    }

    /**
     * Enum type with constant-specific method implementations
     */
    public enum Operation2 {
        PLUS    { double apply(double x, double y) { return x + y; } },
        MINUS   { double apply(double x, double y) { return x - y; } },
        TIMES   { double apply(double x, double y) { return x * y; } },
        DIVIDE  { double apply(double x, double y) { return x / y; } };

        abstract double apply(double x, double y);
    }

    /**
     * Enum type with constant-specific class bodies and data
     */
    public enum Operation3 {
        PLUS("+")   { double apply(double x, double y) { return x + y; } },
        MINUS("-")  { double apply(double x, double y) { return x - y; } },
        TIMES("*")  { double apply(double x, double y) { return x * y; } },
        DIVIDE("/") { double apply(double x, double y) { return x / y; } };

        private final String symbol;

        Operation3(String symbol) {
            this.symbol = symbol;
        }

        abstract double apply(double x, double y);

        @Override
        public String toString() {
            return symbol;
        }

        // Implementing a fromString method on an enum type
        private static final Map<String, Operation3> stringToEnum = new HashMap<>();
        static { // Initialize map from constant name to enum constant
            for (Operation3 op : values())
                stringToEnum.put(op.toString(), op);
        }
        // Returns Operation3 from string, or null if string is invalid
        public static Operation3 fromString(String symbol) {
            return stringToEnum.get(symbol);
        }
    }

    /**
     * Enum that switches on its values to share code - questionable
     */
    public enum PayrollDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
        SATURDAY, SUNDAY;

        private static final int HOURS_PER_SHIFT = 8;

        double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;

            double overtimePay;
            switch(this) {
                case SATURDAY:
                case SUNDAY:
                    overtimePay = hoursWorked * payRate / 2;
                default: // Weekdays
                    overtimePay = hoursWorked <= HOURS_PER_SHIFT ? 0 : (hoursWorked - HOURS_PER_SHIFT) * payRate / 2;
                    break;
            }

            return basePay + overtimePay;
        }
    }

    /**
     * Strategy enum pattern
     */
    public enum PayrollDay2 {
        MONDAY(PayType.WEEKDAY),
        TUESDAY(PayType.WEEKDAY),
        WEDNESDAY(PayType.WEEKDAY),
        THURSDAY(PayType.WEEKDAY),
        FRIDAY(PayType.WEEKDAY),
        SATURDAY(PayType.WEEKEND),
        SUNDAY(PayType.WEEKEND);

        private final PayType payType;

        PayrollDay2(PayType payType) {
            this.payType = payType;
        }

        double pay(double hoursWorked, double payRate) {
            return payType.pay(hoursWorked, payRate);
        }

        // Strategy enum type
        private enum PayType {
            WEEKDAY {
                double overtimePay(double hours, double payRate) {
                    return hours <= HOURS_PER_SHIFT ? 0 : (hours - HOURS_PER_SHIFT) * payRate / 2;
                }
            },
            WEEKEND {
                double overtimePay(double hours, double payRate) {
                    return hours * payRate / 2;
                }
            };

            private static final int HOURS_PER_SHIFT = 8;

            abstract double overtimePay(double hrs, double payRate);

            double pay(double hoursWorked, double payRate) {
                double basePay = hoursWorked * payRate;
                return basePay + overtimePay(hoursWorked, payRate);
            }
        }
    }

    /**
     * Switch on an enum to simulate a missing method
     */
    public static Operation3 inverse(Operation3 op) {
        switch(op) {
            case PLUS:      return Operation3.MINUS;
            case MINUS:     return Operation3.PLUS;
            case TIMES:     return Operation3.DIVIDE;
            case DIVIDE:    return Operation3.TIMES;
            default:        throw new AssertionError("Unknown op: "+op);
        }
    }



    public static void main(String[] args) {
        // Int enum pattern does not provide type safety
        int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPPIN;

        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        for (Operation3 op : Operation3.values())
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
    }

}
