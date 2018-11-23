package com.effective_java_2e.chap06_enums_and_annotations;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 34: Emulate extensible enums with interfaces.
 *
 * There is at least one compelling use case for extensible enumerated types,
 * which is "operation codes", also known as "opcodes".
 * An opcode is an enumerated type whose elements represent operations on some machine.
 * Sometimes it is desirable to let the users of an API provide their own operations,
 * effectively extending the set of operations provided by the API.
 *
 * There is a nice way to achieve this effect using enum types.
 * The basic idea is to take advantage of the fact that enum types can implement arbitrary interfaces
 * by defining an interface for the opcode type and an enum that is the standard implementation of the interface.
 * You don't have to declare an abstract method in the enum as you do in a nonextensible enum with instance-specific method implementations.
 * This is because the abstract method is a member of the interface.
 *
 * A minor disadvantage of the use of interfaces to emulate extensible enums
 * is that implementations cannot be inherited from one enum type to another.
 * If there were a large amount of shared functionality,
 * you could encapsulate it in a helper class or a static helper method to eliminate code duplication.
 *
 * In summary:
 * While you cannot write an extensible enum type,
 * you can emulate it by writing an interface to go with a basic enum type that implements the interface.
 * This allows clients to write their own enums that implement the interface.
 * These enums can then be used wherever the basic enum type can be used,
 * assuming APIs are written in terms of the interface.
 */
public class Item34_ExtensibleEnum {

    /**
     * Emulated extensible enum using an interface
     */
    public interface Operation {
        double apply(double x, double y);
    }

    public enum BasicOperation implements Operation {
        PLUS("+") {
            public double apply(double x, double y) { return x + y; }
        },
        MINUS("-") {
            public double apply(double x, double y) { return x - y; }
        },
        TIMES("*") {
            public double apply(double x, double y) { return x * y; }
        },
        DIVIDE("/") {
            public double apply(double x, double y) { return x / y; }
        };

        private final String symbol;

        BasicOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public enum ExtendedOperation implements Operation {
        EXP("^") {
            public double apply(double x, double y) { return Math.pow(x, y); }
        },
        REMAINDER("%") {
            public double apply(double x, double y) { return x % y; }
        };

        private final String symbol;

        ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }


    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        test(ExtendedOperation.class, x, y);
        test2(Arrays.asList(ExtendedOperation.values()), x, y);
    }

    private static <T extends Enum<T> & Operation> void test(Class<T> opSet, double x, double y) {
        for (Operation op : opSet.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    private static void test2(Collection<? extends Operation> opSet, double x, double y) {
        for (Operation op : opSet) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

}
