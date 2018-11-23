package com.effective_java_2e.chap08_general_programming;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 52: Refer to objects by their interfaces.
 *
 * You should favor the use of interfaces rather than classes to refer to objects.
 * If appropriate interface types exist,
 * then parameters, return values, variables, and fields should all be declared using interface types.
 * The only time you really need to refer to an object's class is when you're creating it with a constructor.
 *
 * If you get into the habit of using interfaces as types, your program will be much more flexible.
 * If you decide that you want to switch implementations,
 * all you have to do is change the class name in the constructor.
 * There is one caveat:
 * if the original implementation offered some special functionality not required by the general contract of the interface
 * and the code depended on that functionality,
 * then it is critical that the new implementation provide the same functionality.
 *
 * It is entirely appropriate to refer to an object by a class rather than an interface if no appropriate interface exists.
 * For example, "value classes", such as String and BigInteger, are often final and rarely have corresponding interfaces.
 * A second case in which there is no appropriate interface types is that of objects belonging to a framework
 * whose fundamental types are classes rather than interfaces.
 * if an object belongs to such a "class-based framework", it is preferable to refer to it by the relevant "base class".
 * A final case in which there is no appropriate interface type is that of classes that implement an interface
 * but provide extra methods not found in the interface, e.g., LinkedHashMap.
 */
public class Item52_InterfaceForObjectReference {

    static class Subscriber {

    }



    public static void main(String[] args) {
        /**
         * Good - uses interface as type
         */
        List<Subscriber> subscribers = new Vector<>();
        subscribers = new ArrayList<>();

        /**
         * Bad - uses class as type
         */
        Vector<Subscriber> subscribers2 = new Vector<>();
    }

}
