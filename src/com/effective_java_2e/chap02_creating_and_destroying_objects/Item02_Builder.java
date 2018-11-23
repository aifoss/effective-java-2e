package com.effective_java_2e.chap02_creating_and_destroying_objects;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 2: Consider a builder when faced with many constructor parameters.
 *
 * cf. "telescoping constructor" pattern
 *      provides a constructor with only required parameters, another with a single optional parameter, a third with two optional parameters, etc.
 *      does not scale well
 *
 * cf. "JavaBeans" pattern
 *      calls a parameterless constructor to create the object and then call setter methods to set each required parameters and each optional parameter of interest
 *      allows inconsistency, mandates mutability
 *
 * The Builder pattern simulates named optional parameters as found in Ada and Python.
 *
 * Like a constructor, a builder can impose variants on its parameters.
 *
 * The build method can check these invariants.
 * It is critical that they be checked after copying the parameters from the builder to the object,
 * and that they be checked on the object fields rather than the builder fields.
 * If any invariants are violated, the build method should throw an IllegalStateException.
 *
 * Another way to impose invariants involving multiple parameters is to have setter methods that take entire groups of parameters
 * on which some invariants must hold.
 * If the invariant isn't satisfied, the setter method throws an IllegalArgumentException.
 * This has the advantage of detecting the invariant failure as soon as the invalid parameters are passed,
 * instead of waiting for build to be invoked.
 *
 * A minor advantage of builders over constructors is that builders can have multiple varargs parameters.
 *
 * A builder whose parameters have been set makes a fine Abstract Factory.
 * A client can pass such a builder to a method to enable the method to create one or more objects for the client.
 * To enable this usage, you need a type to represent the builder.
 * Methods that take a typed builder instance would typically constrain the builder's type parameter using a "bounded wildcard type".
 *
 * Disadvantages:
 * 1. In order to create an object, you must first create its builder.
 * 2. The Builder pattern is more verbose than the telescoping constructor pattern.
 */
public class Item02_Builder {

    /**
     * Builder pattern
     */
    public static class NutritionFacts {
        private final int servingSize;
        private final int servings;
        private final int calories;
        private final int fat;
        private final int sodium;
        private final int carbohydrate;

        private NutritionFacts(Builder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }

        public static class Builder {
            // Required parameters
            private final int servingSize;
            private final int servings;

            // Optional parameters - initialized to default values
            private int calories = 0;
            private int fat = 0;
            private int carbohydrate = 0;
            private int sodium = 0;

            public Builder(int servingSize, int servings) {
                this.servingSize = servingSize;
                this.servings = servings;
            }

            public Builder calories(int val) {
                calories = val;
                return this;
            }

            public Builder fat(int val) {
                fat = val;
                return this;
            }

            public Builder carbohydrate(int val) {
                carbohydrate = val;
                return this;
            }

            public Builder sodium(int val) {
                sodium = val;
                return this;
            }

            public NutritionFacts build() {
                return new NutritionFacts(this);
            }
        }
    }

    /**
     * Typed builder
     */
    // A builder for objects of type T
    public interface Builder<T> {
        public T build();
    }

    // Constraining builder's type parameter using bounded wildcard type
    // Tree buildTree(Builder<? extends Node> nodeBuilder) { ... }



    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .build();
    }

}
