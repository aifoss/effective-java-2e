package com.effective_java_2e.chap04_classes_and_interfaces;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sofia on 5/14/17.
 */

/**
 * Item 13: Minimize the accessibility of classes and members.
 *
 * The single most important factor that distinguishes a well-designed module from a poorly designed one
 * is the degree to which the module hides its internal data and other implementation details from other modules.
 * A well-designed module hides all of its implementation details, cleanly separating its API from its implementation.
 * Modules then communicate only through their APIs and are oblivious to each others' inner workings.
 * This concept, known as "information hiding" or "encapsulation", is one of the fundamental tenets of software design.
 *
 * The "access control" mechanism specifies the "accessibility" of classes, interfaces, and members.
 * Proper use of these modifiers is essential to information hiding.
 *
 * The rule of thumb is simple: make each class or member as inaccessible as possible.
 *
 * For top-level (non-nested) classes and interfaces, there are only two possible access levels: package-private and public.
 *
 * By making it package-private, you make it part of the implementation rather than the exported API,
 * and you can modify it, replace it, or eliminate it in a subsequent release without fear of harming existing clients.
 *
 * If a package-private top-level class (or interface) is used by only one class,
 * consider making the top-level class a private nested class of the sole class that uses it.
 *
 * But it is far more important to reduce the accessibility of a gratuitously public class than of a package-private top-level class:
 * the public class is part of the package's API, while the package-private top-level class is already part of its implementation.
 *
 * For members (fields, methods, nested classes, and nested interfaces), there are four possible access levels:
 * - private: The member is accessible only from the top-level class where it is declared.
 * - package-private: The member is accessible from any class in thepackage where it is declared.
 * - protected: The member is accessible from subclasses of the class where it is declared and from any class in the package where it is declared.
 * - public: The member is accessible from anywhere.
 *
 * Instance fields should never be public.
 * Classes with public mutable fields are not thread-safe.
 *
 * A nonzero-length array is always mutable,
 * so it is wrong for a class to have a public static final array field or an accessor that returns such a field.
 *
 * There are two ways to fix the problem:
 * 1. You can make the public array private and add a public immutable list.
 * 2. Alternatively, You can make the array private and add a public method that returns a copy of the private array.
 *
 * To summarize:
 * You should always reduce accessibility as much as possible.
 * After carefully designing a minimal public API, you should prevent any stray classes, interfaces, or members from becoming a part of the API.
 * With the exception of public static final fields, public classes should have no public fields.
 * Ensure that objects referenced by public static final fields are immutable.
 */
public class Item13_Accessibility {

    public static final class Thing {

    }

    /**
     * Potential security hole - public static final array field
     */
    public static final Thing[] PUBLIC_VALUES = {};

    /**
     * Fixing the above problem by making the array private and adding a public immutable list
     */
    private static final Thing[] PRIVATE_VALUES = {};
    public static final List<Thing> IMMUTABLE_VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

    /**
     * Fixing the above problem by making the array private and adding a public method that returns a copy of the array
     */
    public static final Thing[] values() {
        return PRIVATE_VALUES.clone();
    }



    public static void main(String[] args) {

    }

}
