package com.effective_java_2e.chap07_methods;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 40: Design method signatures carefully.
 *
 * Choose method names carefully.
 *
 * Don't go overboard in providing convenience methods.
 * For each action supported by your class or interface, provide a fully functional method.
 * Consider providing a "shorthand" only if it will be used often.
 * When in doubt, leave it out.
 *
 * Avoid long parameter lists.
 * Aim for 4 parameters or fewer.
 * Long sequences of identically typed parameters are especially harmful.
 *
 * There are 3 techniques for shortening overly long parameter lists:
 * 1. Break the method up into multiple methods, each of which requires only a subset of the parameters.
 * 2. Create helper classes to hold groups of parameters.
 * 3. Adapt the Builder pattern from object construction to method invocation.
 *
 * For parameter types, favor interfaces over classes.
 *
 * Prefer two-element enum types to boolean parameters.
 */
public class Item40_MethodSignature {

    public static void main(String[] args) {

    }

}
