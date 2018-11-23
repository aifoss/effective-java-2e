package com.effective_java_2e.chap06_enums_and_annotations;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 37: Use marker interfaces to define types.
 *
 * A "marker interface" is an interface that contains no method declarations,
 * but merely designates (or "marks") a class that implements the interface as having some property.
 *
 * Marker interfaces have two advantages over marker annotations:
 *
 * 1. Marker interfaces define a type that is implemented by instances of the marked class;
 *    marker annotations do not.
 *
 *    e.g., Serializable
 *
 * 2. Marker interfaces can be targeted more precisely.
 *    If you define a marker interface, you can have it extend the sole interface to which it is applicable,
 *    guaranteeing that all marked types are also subtypes of the sole interface to which it is applicable.
 *
 *    The Set interface is just such a "restricted marker interface".
 *    It is applicable only to Collection subtypes, but it adds no methods beyond those defined by Collection.
 *
 * Marker annotations have two advantages over marker interfaces:
 *
 * 1. It is possible to add more information to an annotation type after it is already in use,
 *    by adding one or more annotation type elements with defaults.
 *
 * 2. Marker annotations are part of the larger annotation facility.
 *    Therefore, marker annotations allow for consistency in frameworks that permit annotation of a variety of program elements.
 *
 * When should you use a marker annotation and when should you use a marker interface?
 *
 * You must use an annotation if the marker applies to any program element other than a class or interface,
 * as only classes and interfaces can be made to implement or extend an interface.
 *
 * If the marker applies only to classes and interfaces, ask yourself the question:
 * Might I want to write one or more methods that accept only objects that have this marking?
 * If so, you should use a marker interface.
 * If you answered no, ask yourself one more question:
 * Do I want to limit the use of this marker to elements of a particular interface, forever?
 * If so, it makes sense to define the marker as a subinterface of that interface.
 * If you answered no, you should probably use a marker annotation.
 *
 * In summary:
 * If you want to define a type that does not have any new methods associated with it, a marker interface is the way to go.
 * If you want to mark program elements other than classes and interfaces,
 * to allow for the possibility of adding more information to the marker in the future,
 * or to fit the marker into a framework that already makes heavy use of annotation types,
 * then a marker annotation is the correct choice.
 * If you find yourself writing a marker annotation type whose target is ElementType.TYPE,
 * take the time to figure out whether it really should be an annotation type,
 * or whether a marker interface would be more appropriate.
 */
public class Item37_MarkerInterface {

    public static void main(String[] args) {

    }

}
