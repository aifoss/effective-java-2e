package com.effective_java_2e.chap03_methods_common_to_all_objects;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 8: Obey the general contract when overriding equals.
 *
 * The easiest way to avoid problems is not to override the equals method,
 * in which case each instance of the class is equal only to itself.
 * This is the right thing to do if any of the following conditions apply:
 * 1. Each instance of the class is inherently unique.
 *    e.g., Thread
 * 2. You don't care whether the class provides a "logical equality" test.
 *    e.g., Random
 * 3. A superclass has already overridden equals, and the superclass behavior is appropriate for this class.
 *    e.g., Set, List, Map
 * 4. The class is private or package-private, and you are certain that its equals method will never be invoked.
 *    @Override
 *    public boolean equals(Object o) {
 *        throw new AssertionError(); // Method is never called
 *    }
 *
 * When is it appropriate to override Object.equals?
 * When a class has a notion of logical equality that differs from mere object identity,
 * and a superclass has not already overridden equals to implement this desired behavior.
 *
 * This is generally the case for "value classes".
 * A value class is simply a class that represents a value, such as Integer or Date.
 *
 * One kind of value class that does not require the equals method to be overridden is a class that uses instance control
 * to ensure that at most one object exists with each value.
 * Enum types fall into this category.
 * For these classes, logical equality is the same as object identity.
 *
 * When you override the equals method, you must adhere to its general contract.
 * The equals method implements an equivalence relation that is:
 * (1) Reflexive
 * (2) Symmetric
 * (3) Transitive
 * (4) Consistent: For any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false.
 * (5) "Non-nullible": For any non-null reference value x, x.equals(null) must return false.
 *
 * There is no way to extend an instantiable class and add a value component while preserving the equals contract.
 * A workaround is to favor composition over inheritance.
 * You can add a value component to a subclass of an abstract class without violating the equals contract.
 *
 * Whether or not a class is immutable, do not write an equals method that depends on unreliable resources.
 *
 * Recipe for a high-quality equals method:
 *
 * 1. Use the == operator to check if the argument is a reference to this object.
 *
 *    If so, return true.
 *
 * 2. Use the instanceof operator to check if the argument has the correct type.
 *
 *    If not, return false.
 *
 * 3. Cast the argument to the correct type.
 *
 *    Because this cast was preceded by an instanceof test, it is guaranteed to succeed.
 *
 * 4. For each "significant" field in the class, check if that field of the argument matches the corresponding field of this object.
 *
 *    If all these tests succeed, return true; otherwise, return false.
 *    For primitive fields whose type is not float or double, use the == operator for comparisons.
 *    For object references, invoke the equals method recursively.
 *    For float, use Float.compare; for double, use Double.compare.
 *    For array fields, apply these guidelines to each element.
 *    If every element in an array field is significant, you can use one of the Arrays.equals methods.
 *
 *    To avoid the possibility of NullPointerException, use this idiom:
 *    (field == null ? o.field == null : field.equals(o.field))
 *
 *    This alternative may be faster if field and o.field are often identical:
 *    (field == o.field || (field != null && field.equals(o.field)))
 *
 *    For best performance, you should compare fields that are more likely to differ, less expensive to compare, or, ideally, both.
 *    You must not compare fields that are not part of an object's logical state, such as Lock.
 *    You need not compare redundant fields, which can be calculated from significant fields, but doing so may improve the performance.
 *
 * 5. When you are finished writing your equals method, ask yourself three questions:
 *    Is it symmetric? Is it transitive? Is it consistent?
 *
 * A few final caveats:
 * - Always override hashCode when you override equals.
 * - Don't try to be too clever.
 * - Don't substitute another type for object in the equals declaration.
 */
public class Item08_Equals {

    /**
     * Violation of symmetry
     */
    public static final class CaseInsensitiveString1 {
        private final String s;

        public CaseInsensitiveString1(String s) {
            if (s == null) throw new NullPointerException();
            this.s = s;
        }

        // Broken - violates symmetry
        @Override
        public boolean equals(Object o) {
            if (o instanceof CaseInsensitiveString1) {
                return s.equalsIgnoreCase(((CaseInsensitiveString1) o).s);
            }
            if (o instanceof String) { // One-way interoperability
                return s.equalsIgnoreCase((String) o);
            }
            return false;
        }
    }

    /**
     * Preservation of symmetry
     */
    public static final class CaseInsensitiveString2 {
        private final String s;

        public CaseInsensitiveString2(String s) {
            if (s == null) throw new NullPointerException();
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof CaseInsensitiveString2) &&
                    ((CaseInsensitiveString2) o).s.equalsIgnoreCase(s);
        }
    }


    /**
     * Superclass with equals method
     */
    public static class Point1 {
        private final int x;
        private final int y;

        public Point1(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point1))
                return false;
            Point1 p = (Point1)o;
            return p.x == x && p.y == y;
        }
    }

    /**
     * Subclass with overridden equals method that violates symmetry
     */
    public static class ColorPoint1 extends Point1 {
        private final Color color;

        public ColorPoint1(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        // Broken - violate symmetry
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ColorPoint1))
                return false;
            return super.equals(o) && ((ColorPoint1) o).color == color;
        }
    }

    /**
     * Subclass with overridden equals method that violates transitivity
     */
    public static class ColorPoint2 extends Point1 {
        private final Color color;

        public ColorPoint2(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        // Broken - violate transitivity
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point1))
                return false;

            // If o is a normal Point, do a color-blind comparison
            if (!(o instanceof ColorPoint2))
                return o.equals(this);

            // If o is a ColorPoint, do a full comparison
            return super.equals(o) && ((ColorPoint2) o).color == color;
        }
    }

    /**
     * Superclass with equals method that violates Liskov substitution principle
     */
    public static class Point2 {
        private final int x;
        private final int y;

        public Point2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Broken - violates Liskov substitution principle
        // Has the effect of equating object only if they have the same implementation
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != getClass())
                return false;

            Point2 p = (Point2)o;
            return p.x == x && p.y == y;
        }
    }

    /**
     * Subclass that doesn't add a value component
     */
    public static class CounterPoint extends Point2 {
        private static final AtomicInteger counter = new AtomicInteger();

        public CounterPoint(int x, int y) {
            super(x, y);
            counter.incrementAndGet();
        }

        public int numberCreated() {
            return counter.get();
        }
    }

    // Initialize unitCircle to contain all Points on the unit circle
    private static final Set<Point2> unitCircle;
    static {
        unitCircle = new HashSet<>();
        unitCircle.add(new Point2(1, 0));
        unitCircle.add(new Point2(0, 1));
        unitCircle.add(new Point2(-1, 0));
        unitCircle.add(new Point2(0, -1));
    }

    public static boolean onUnitCircle(Point2 p) {
        return unitCircle.contains(p);
    }

    /**
     * Class that adds a value component without violating the equals contract
     */
    public static class ColorPoint3 {
        private final Point1 point;
        private final Color color;

        public ColorPoint3(int x, int y, Color color) {
            if (color == null)
                throw new NullPointerException();
            point = new Point1(x, y);
            this.color = color;
        }

        // Returns the point-view of this color point
        public Point1 asPoint() {
            return point;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ColorPoint3))
                return false;
            ColorPoint3 cp = (ColorPoint3) o;
            return cp.point.equals(point) && cp.color.equals(color);
        }
    }

    /**
     * Class with equals method that includes unnecessary null check
     */
    public static class MyType1 {
        @Override
        public boolean equals(Object o) {
            if (o == null) // Unnecessary explicit test for null
                return false;
            return true;
        }
    }

    /**
     * Class with equals method that uses instanceof type check
     */
    public static class MyType2 {
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MyType2)) // Check if argument is of correct type; Returns false if null is passed
                return false;
            MyType2 mt = (MyType2) o;
            return true;
        }
    }



    public static void main(String[] args) {

    }

}
