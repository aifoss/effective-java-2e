package com.effective_java_2e.chap04_classes_and_interfaces;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 14: In public classes, use accessor methods, not public fields.
 *
 * If a class is accessible outside its package, provide accessor methods,
 * to preserve the flexibility to change the class's internal representation.
 *
 * If a class is package-private or is a private nested class,
 * there is nothing inherently wrong with exposing its data fields.
 *
 * While it's never a good idea for a public class to expose fields directly,
 * it is less harmful if the fields are immutable.
 *
 * In summary:
 * Public classes should never expose mutable fields.
 * It is less harmful, though still questionable, for public classes to expose immutable fields.
 * It is, however, sometime desirable for package-private or private nested classes to expose fields,
 * whether mutable or immutable.
 */
public class Item14_Accessor {

    /**
     * Degenerate classes like this should not be public
     */
    class Point1 {
        public double x;
        public double y;
    }

    /**
     * Encapsulation of data by accessor methods and mutators
     */
    class Point2 {
        private double x;
        private double y;

        public Point2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    /**
     * Public class with exposed immutable fields - questionable
     */
    public final class Time {
        private static final int HOURS_PER_DAY = 24;
        private static final int MINUTES_PER_HOUR = 60;

        public final int hour;
        public final int minute;

        public Time(int hour, int minute) {
            if (hour < 0 || hour >= HOURS_PER_DAY)
                throw new IllegalArgumentException("Hour: "+hour);
            if (minute < 0 || minute >= MINUTES_PER_HOUR)
                throw new IllegalArgumentException("Min: "+minute);

            this.hour = hour;
            this.minute = minute;
        }
    }



    public static void main(String[] args) {

    }

}
