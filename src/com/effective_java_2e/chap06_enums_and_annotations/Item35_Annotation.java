package com.effective_java_2e.chap06_enums_and_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 35: Prefer annotations to naming patterns.
 *
 * The declaration for the Test annotation type is itself annotated with Retention and Target annotations.
 * Such annotations on annotation type declarations are known as "meta-annotations".
 *
 * The @Retention(RetentionPolicy.RUNTIME) meta-annotation indicates that Test annotations should be retained at runtime.
 * The @Target(ElementType.METHOD) meta-annotation indicates that the Test annotation is legal only on method declarations;
 * it cannot be applied to class declarations, field declarations, or other program elements.
 *
 * The Test annotation is called a "marker annotation",
 * because it has no parameters but simply marks the annotated element.
 *
 * Annotations never change the semantics of the annotated code,
 * but enable it for special treatment by tools such as a test runner.
 *
 * If you write a tool that requires programmers to add information to source files,
 * define an appropriate set of annotation types.
 * That said, with the exception of toolsmiths, most programmers will have no need to define annotation types.
 * All programmers should, however, use the predefined annotation types provided by the Java platform.
 */
public class Item35_Annotation {

    /**
     * Defining an annotation type to designate simple tests that are run automatically
     * and fail if they throw an exception
     */
    /**
     * Indicates that the annotated method is a test method.
     * Use only on parameterless static methods.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Test {

    }

    /**
     * Program containing marker annotations
     */
    public static class Sample {
        @Test public static void m1() {}    // Test should pass
        public static void m2() {}
        @Test public static void m3() {     // Test should fail
            throw new RuntimeException("Boom");
        }
        public static void m4() {}
        @Test public void m5() {}           // INVALID USE: nonstatic method
        public static void m6() {}
        @Test public static void m7() {     // Test should fail
            throw new RuntimeException("Crash");
        }
        public static void m8() {}
    }

    /**
     * Program to process marker annotations
     */
    public static class RunTests {
        public static void main(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            Class testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Test.class)) {    // Indicate which method is annotated with @Test
                    tests++;
                    try {
                        m.invoke(null); // Run @Test-annotated method
                        passed++;
                    } catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        System.out.println(m + " failed: " + exc);
                    } catch (Exception exc) {   // Exception indicating an invalid use of @Test annotation
                        System.out.println("INVALID @Test: " + m);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
        }
    }

    /**
     * Annotation type with a parameter
     */
    /**
     * Indicates that the annotated method is a test method
     * that must throw the designated exception to succeed.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        Class<? extends Exception> value();
    }

    /**
     * Program containing annotations with a parameter
     */
    public static class Sample2 {
        @ExceptionTest(ArithmeticException.class)
        public static void m1() {   // Test should pass
            int i = 0;
            i = i / i;
        }
        @ExceptionTest(ArithmeticException.class)
        public static void m2() {   // Should fail (wrong exception)
            int[] a = new int[0];
            int i = a[1];
        }
        @ExceptionTest(ArithmeticException.class)
        public static void m3() {}  // Should fail (no exception)
    }

    /**
     * Program to test annotations with a parameter
     */
    public static class RunTests2 {
        public static void main(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            Class testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ExceptionTest.class)) {    // Indicate which method is annotated with @ExceptionTest
                    tests++;
                    try {
                        m.invoke(null); // Run @ExceptionTest-annotated method
                        System.out.printf("Test %s failed: no exception%n", m);
                    } catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        Class<? extends Exception> excType = m.getAnnotation(ExceptionTest.class).value();
                        if (excType.isInstance(exc)) {
                            passed++;
                        } else {
                            System.out.printf("Test %s failed: expected %s, got %s%n", m, excType.getName(), exc);
                        }
                    } catch (Exception exc) {   // Exception indicating an invalid use of @ExceptionTest annotation
                        System.out.println("INVALID @ExceptionTest: " + m);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
        }
    }

    /**
     * Annotation type with an array parameter
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionArrayTest {
        Class<? extends Exception>[] value();
    }

    /**
     * Code containing an annotation with an array parameter
     */
    @ExceptionArrayTest({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void doublyBad() {
        List<String> list = new ArrayList<>();
        // The spec permits this method to throw either IndexOutOfBoundsException or NullPointerException
        list.addAll(5, null);
    }

    /**
     * Program to test annotations with an array parameter
     */
    public static class RunTests3 {
        public static void main(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            Class testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ExceptionArrayTest.class)) {    // Indicate which method is annotated with @ExceptionArrayTest
                    tests++;
                    try {
                        m.invoke(null); // Run @ExceptionTest-annotated method
                        System.out.printf("Test %s failed: no exception%n", m);
                    } catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        Class<? extends Exception>[] excTypes = m.getAnnotation(ExceptionArrayTest.class).value();
                        int oldPassed = passed;
                        for (Class<? extends Exception> excType : excTypes) {
                            if (excType.isInstance(exc)) {
                                passed++;
                                break;
                            }
                        }
                        if (passed == oldPassed) {
                            System.out.printf("Test %s failed: %s %n", m, exc);
                        }
                    } catch (Exception exc) {   // Exception indicating an invalid use of @ExceptionArrayTest annotation
                        System.out.println("INVALID @ExceptionTest: " + m);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
        }
    }



    public static void main(String[] args) {

    }

}
