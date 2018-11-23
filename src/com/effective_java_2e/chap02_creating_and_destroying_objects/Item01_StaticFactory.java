package com.effective_java_2e.chap02_creating_and_destroying_objects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sofia on 5/13/17.
 */

/**
 * Item 1: Consider static factory method instead of constructors.
 *
 * Advantages:
 * 1. Unlike constructors, static factory methods have names.
 * 2. Unlike constructors, static factory methods are not required to create a new object each time they're invoked.
 *    cf. "instance-controlled classes" - singleton, noninstantiable, immutable
 * 3. Unlike constructors, static factory methods can return an object of any subtype of their return type.
 *    cf. "interface-based frameworks"
 *    cf. "service provider frameworks" (e.g., JDBC)
 *        (1) service interface - provides implement (e.g., Connection)
 *        (2) provider registration API - system uses to register implementations (e.g., DriverManager.registerDriver)
 *        (3) service access API - clients use to obtain an instance of the service - "flexible static factory" (e.g., DriverManager.getConnection)
 *        (4) service provider interface - provides implement to create instances of their service implementation (e.g., Driver)
 * 4. Static factory methods reduce the verbosity of creating parameterized type instances.
 *    cf. "type inference"
 *
 * Disadvantages:
 * 1. Classes without public or protected constructors cannot be subclassed.
 * 2. Static factory methods are not readily distinguishable from other static methods.
 *    cf. Common naming conventions for static factory methods:
 *        (1) valueOf - returns an instance that has the same value as its parameters
 *        (2) of - concise alternative to valueOf
 *        (3) getInstance - returns an instance that is described by the parameters but cannot be said to have the same value
 *        (4) newInstance - guarantees that each instance returned is distinct from all others
 *        (5) getType - like getInstance, but used when the factory method is in a different class
 *        (6) newType - like newInstance, but used when the factory method is in a different class
 */
public class Item01_StaticFactory {

    /**
     * Service provider framework sketch
     */

    /* Service interface */
    public interface Service {
        // Service-specific methods go here
    }

    /* Service provider interface */
    public interface Provider {
        Service newService();
    }

    /* Noninstantiable class for service registration and access */
    public static class Services {
        private Services() {} // Prevents instantiation

        // Maps service names to services
        private static final Map<String, Provider> providers = new ConcurrentHashMap<>();

        public static final String DEFAULT_PROVIDER_NAME = "<def>";

        // Provider registration API
        public static void registerDefaultProvider(Provider p) {
            registerProvider(DEFAULT_PROVIDER_NAME, p);
        }

        public static void registerProvider(String name, Provider p) {
            providers.put(name, p);
        }

        // Service access API
        public static Service newInstance() {
            return newInstance(DEFAULT_PROVIDER_NAME);
        }

        public static Service newInstance(String name) {
            Provider p = providers.get(name);
            if (p == null) {
                throw new IllegalArgumentException("No provider registered with name: "+name);
            }
            return p.newService();
        }
    }



    public static void main(String[] args) {

    }

}
