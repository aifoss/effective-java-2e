package com.effective_java_2e.chap04_classes_and_interfaces;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 20: Prefer class hierarchies to tagged classes.
 *
 * Occasionally you may run across a class whose instances come in two or more flavors and contain a "tag" field indicating the flavor of the instance.
 *
 * Such "tagged classes" have numerous shortcomings:
 * 1. They are cluttered with boilerplate, including enum declarations, tag fields, and switch statements.
 * 2. Readability is further harmed because multiple implementations are jumbled together in a single class.
 * 3. Memory footprint is increased because instances are burdened with irrelevant fields belonging to other flavors.
 * 4. Fields can't be made final unless constructors initialize irrelevant fields, resulting in more boilerplate.
 * 5. Constructors must set the tag field and initialize the right data fields with no help from the compiler:
 *    if you initialize the wrong fields, the program will fail at runtime.
 * 6. You can't add a flavor to a tagged class unless you can modify its source file.
 * 7. If you do add a flavor, you must remember to add a case to every switch statement, or the class will fail at runtime.
 * 8. The data type of an instance gives no clue as to its flavor.
 *
 * Tagged classes are verbose, error-prone, and inefficient.
 *
 * Object-oriented languages such as Java offer a far better alternative
 * for defining a single data type capable of representing objects of multiple flavors:
 * subtyping.
 *
 * A tagged class is just a pallid imitation of a class hierarchy.
 *
 * To transform a tagged class into a class hierarchy:
 *
 * 1. First define an abstract class containing an abstract method for each method in the tagged class whose behavior depends on the tag value.
 *
 *    If there are any methods whose behavior does not depend on the value of the tag, put them in this class.
 *    Similarly, if there are any data fields used by all the flavors, put them in this class.
 *
 * 2. Next, define a concrete subclass of the root class for each flavor of the original tagged class.
 *
 *    Include in each subclass the data fields particular to its flavor.
 *    Also include in each subclass the appropriate implementation of each abstract method in the root class.
 *
 * The class hierarchy corrects every shortcoming of tagged classes.
 * Another advantage of class hierarchies is that they can be made to reflect natural hierarchical relationships among types,
 * allowing for increased flexibility and better compile-time type checking.
 */
public class Item20_ClassHierarchy {

    /**
     * Tagged class - vastly inferior to a class hierarchy
     */
    static class Figure1 {
        enum Shape { RECTANGLE, CIRCLE };

        // Tag field - the shape of this figure
        final Shape shape;

        // These fields are used only if shape is RECTANGLE
        double length;
        double width;

        // This field is used only if shape is CIRCLE
        double radius;

        // Constructor for circle
        Figure1(double radius) {
            shape = Shape.CIRCLE;
            this.radius = radius;
        }

        // Constructor for rectangle
        Figure1(double length, double width) {
            shape = Shape.RECTANGLE;
            this.length = length;
            this.width = width;
        }

        double area() {
            switch(shape) {
                case RECTANGLE: return length * width;
                case CIRCLE: return Math.PI * (radius * radius);
                default: throw new AssertionError();
            }
        }
    }

    /**
     * Class hierarchy replacement for a tagged class
     */
    abstract class Figure2 {
        abstract double area();
    }

    class Circle extends Figure2 {
        final double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        double area() {
            return Math.PI * (radius * radius);
        }
    }

    class Rectangle extends Figure2 {
        final double length;
        final double width;

        Rectangle(double length, double width) {
            this.length = length;
            this.width = width;
        }

        double area() {
            return length * width;
        }
    }

    class Square extends Rectangle {
        Square(double side) {
            super(side, side);
        }
    }



    public static void main(String[] args) {

    }

}
