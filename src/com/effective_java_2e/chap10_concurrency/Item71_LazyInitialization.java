package com.effective_java_2e.chap10_concurrency;

/**
 * Created by sofia on 5/24/17.
 */

/**
 * Item 71: Use lazy initialization judiciously.
 *
 * Lazy initialization is the act of delaying the initialization of a field until its value is needed.
 * This technique is applicable to both static and instance fields.
 *
 * As is the case for most optimizations, the best advice for lazy initialization is "don't do it unless you need to".
 * It decreases the cost of initializing a class or creating an instance,
 * at the expense of increasing the cost of accessing the lazily initialized field.
 *
 * Lazy initialization has its uses.
 * If a field is accessed only on a fraction of the instances of a class and it is costly to initialize the field,
 * then lazy initialization may be worthwhile.
 * The only way to know for sure is to measure the performance of the class with and without lazy initialization.
 *
 * In the presence of multiple threads, lazy initialization is tricky.
 * If two or more threads share a lazily initialized field,
 * it is critical that some form of synchronization be employed,
 * or severe bugs can result.
 *
 * Under most circumstances, normal initialization is preferable to lazy initialization.
 * Here is a typical declaration for a normally initialized instance field:
 *
 *      // Normal initialization of an instance field
 *      private final FieldType field = computeFieldValue();
 *
 * If you use lazy initialization to break an initialization circularity, use a synchronized accessor:
 *
 *      // Lazy initialization of instance field - synchronized accessor
 *      private FieldType field;
 *
 *      synchronized FieldType getField() {
 *          if (field == null)
 *              field = computeFieldValue();
 *          return field;
 *      }
 *
 * Both of these idioms are unchanged when applied to static fields.
 *
 * If you need to use lazy initialization for performance on a static filed,
 * use the "lazy initialization holder class" idiom.
 * This idiom (also known as the "initialize-on-demand holder class" idiom) exploits the guarantee
 * that a class will not be initialized until it is used:
 *
 *      // Lazy initialization holder class idiom for static fields
 *      private static class FiledHolder {
 *          static final FieldType field = computeFieldValue();
 *      }
 *      static FieldType getField() { return FieldHolder.field; }
 *
 * When the getField method is invoked for the first time,
 * it reads FieldHolder.field for the first time, causing the FieldHolder class to get initialized.
 * The beauty of this idiom is that the getField method is not synchronized and performs only a field access,
 * so lazy initialization adds practically nothing to the cost of access.
 *
 * A modern VM will synchronize field access only to initialize the class.
 * Once the class is initialized, the VM will patch the code so that subsequent access to the field does not involve any testing or synchronization.
 *
 * If you need to use lazy initialization for performance on an instance field,
 * use the "double-check" idiom.
 * This idiom avoids the cost of locking when accessing the field after it has been initialized.
 * The idea behind the idiom is to check the value of the field twice:
 * once without locking, and then, if the field appears to be uninitialized, a second time with locking.
 * Only if the second check indicates that the field is uninitialized does the call initialize the field.
 * Because there is no locking if the field is already initialized,
 * it is critical that the field be declared volatile:
 *
 *      // Double-check idiom for lazy initialization of instance fields
 *      private volatile FieldType field;
 *
 *      FieldType getField() {
 *          FieldType result = field;
 *          if (result == null) { // First check (no locking)
 *              synchronized (this) {
 *                  result = field;
 *                  if (result == null) {   // Second check (with locking)
 *                      field = result = computeFieldValue();
 *                  }
 *              }
 *              return result;
 *          }
 *      }
 *
 * Today, the double-check idiom is the technique of choice for lazily initializing an instance field.
 * For lazily initializing a static field, the lazy initialization holder class idiom is a better choice.
 *
 * Occasionally, you may need to lazily initialize an instance field that can tolerate repeated initialization.
 * In this situation, you can use a variant of the double-check idiom that dispenses with the second check.
 * It is known as the "single-check" idiom:
 *
 *      // Single-check idiom - can cause repeated initialization
 *      private volatile FieldType field;
 *
 *      private FieldType getField() {
 *          FieldType result = field;
 *          if (result == null)
 *              field = result = computeFieldValue();
 *          return result;
 *      }
 *
 * If you don't care whether every thread recalculates the value of a field,
 * and the type of the field is a primitive other than long or double,
 * then you may choose to remove the volatile modifier from the field declaration in the single-check idiom.
 * This variant is known as the "racy single-check" idiom.
 * It speeds up field access on some architectures, at the expenses of additional initializations.
 *
 * In summary:
 * You should initialize most fields normally, not lazily.
 * If you must initialize a field lazily in order to achieve performance goals,
 * or to break a harmful initialization circularity,
 * then use the appropriate lazy initialization technique.
 * For instance fields, it is the double-check idiom;
 * for static fields, the lazy initialization holder class idiom.
 * For instance fields that can tolerate repeated initialization,
 * you may also consider the single-check idiom.
 */
public class Item71_LazyInitialization {

    static class FieldType {

    }

    FieldType computeFieldValue() {
        return null;
    }

    /**
     * Normal initialization of an instance field
     */
    private final FieldType field = computeFieldValue();

    /**
     * Lazy initialization of instance field - synchronized accessor
     */
    private FieldType field2;

    synchronized FieldType getField() {
        if (field2 == null)
            field2 = computeFieldValue();
        return field;
    }

    /**
     * Lazy initialization holder class idiom for static fields
     */
    private static class FieldHolder {
        static final FieldType field = computeFieldValue();

        static FieldType computeFieldValue() {
            return null;
        }
    }

    static FieldType getField2() {
        return FieldHolder.field;
    }

    /**
     * Double-check idiom for lazy initialization of instance fields
     */
    private volatile FieldType field3;

    FieldType getField3() {
        FieldType result = field3;  // Ensures that field is read only once in the common case where it's already initialized

        if (result == null) {   // First check (no locking)
            synchronized (this) {
                result = field3;
                if (result == null) {   // Second check (with locking)
                    field3 = result = computeFieldValue();
                }
            }
        }

        return result;
    }

    /**
     * Single-check idiom - can cause repeated initialization
     */
    private volatile FieldType field4;

    private FieldType getField4() {
        FieldType result = field4;
        if (result == null)
            field4 = result = computeFieldValue();
        return result;
    }



    public static void main(String[] args) {

    }

}
