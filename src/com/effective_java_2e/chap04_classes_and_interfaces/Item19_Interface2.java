package com.effective_java_2e.chap04_classes_and_interfaces;

import static com.effective_java_2e.chap04_classes_and_interfaces.Item19_Interface2.PhysicalConstants2.AVOGADROS_NUMBER;
import static org.java.effective_java_2e.chap04_classes_and_interfaces.Item19_Interface2.PhysicalConstants2.*;

/**
 * Created by sofia on 5/15/17.
 */

/**
 * Item 19: Use interfaces only to define types.
 *
 * When a class implements an interface, the interface serves as a type that can be used to refer to instances of the class.
 * One kind of interface that fails this test is the so-called "constant interface".
 * Such an interface contains no methods; it consists solely of static final fields, each exporting a constant.
 *
 * The constant interface pattern is a poor use of interfaces.
 * Implementing a constant interface causes this implementation detail to leak into the class's exported API.
 * If in a future release the class is modified so that it no longer needs to use the constants,
 * it still must implement the interface to ensure binary compatibility.
 * If a nonfinal class implements a constant interface,
 * all of its subclasses will have their namespaces polluted by the constants in the interface.
 *
 * If you want to export constants, there are several reasonable choices.
 * If the constants are strongly tied to an existing class or interface, you should add them to the class or interface.
 * If the constants are best viewed as members of an enumerated type, you should export them with an enum type.
 * Otherwise, you should export the constants with a noninstantiable utility class.
 * If you make heavy use of the constants exported by a utility class,
 * you can avoid the need for qualifying the constants with the class name by making use of the static import facility.
 *
 * In summary:
 * Interfaces should be used only to define types.
 * They should not be used to export constants.
 */
public class Item19_Interface2 {

    /**
     * Constant interface antipattern - do not use
     */
    public interface PhysicalConstants1 {
        // Avogadro's number (1/mol)
        static final double AVOGADROS_NUMBER = 6.02214199e23;

        // Boltzmann constant (J/K)
        static final double BOLTZMANN_CONSTANT = 1.3806503e-23;

        // Mass of the electron (kg)
        static final double ELECTRON_MASS = 9.10938188e-31;
    }

    /**
     * Constant utility class
     */
    public class PhysicalConstants2 {
        private PhysicalConstants2() {} // Prevents instantiation

        public static final double AVOGADROS_NUMBER = 6.02214199e23;
        public static final double BOLTZMANN_CONSTANT = 1.3806503e-23;
        public static final double ELECTRON_MASS = 9.10938188e-31;
    }

    /**
     * Example of using static import
     */
    public class Test {
        double atoms(double mols) {
            return AVOGADROS_NUMBER * mols; // Uses static import
        }
    }



    public static void main(String[] args) {

    }

}
