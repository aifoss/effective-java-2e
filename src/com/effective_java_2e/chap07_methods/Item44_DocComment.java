package com.effective_java_2e.chap07_methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 44: Write doc comments for all exposed API elements.
 *
 * Javadoc generates API documentation automatically from source code with specially formatted "documentation comments",
 * more commonly known as "doc comments".
 *
 * To document your API properly, you must precede every exported class, interface, constructor, method, and field declaration with a doc comment.
 * To write maintainable code, you should also write doc comments for most unexported classes, interfaces, constructors, methods, and fields.
 *
 * The doc comment for a method should describe succinctly the contract between the method and its client.
 * With the exception of methods in classes designed for inheritance,
 * the contract should say what the method does rather than how it does the job.
 * The doc comment should enumerate all of the method's preconditions and postconditions.
 * Typically, preconditions are described implicitly by the @throws tags for unchecked exceptions;
 * each unchecked exception corresponds to a precondition violation.
 * Also, preconditions can be specified along with the affected parameters in their @param tags.
 * In addition to preconditions and postconditions, methods should document any side effects.
 * Finally, documentation comments should describe the thread safety of a class or method.
 *
 * To describe a method's contract fully,
 * the doc comment should have a @param tag for every parameter,
 * a @return tag unless the method has a void return type,
 * and a @throws tag for every exception thrown by the method, whether checked or unchecked.
 * By convention, the phrase or clause following a @param, @return, or @throws tag is not terminated by a period.
 *
 * The Javadoc {@code} tag is preferable to the HTML <code> or <tt> tags because it eliminates the ned to escape HTML metacharacters.
 * To include a multiline code example in a doc comment,
 * use a Javadoc {@code} tag wrapped inside an HTML <pre> tag: <pre>{@code ... }</pre>.
 *
 * By convention, the word "this" always refers to the object on which the method is invoked
 * when it is used in the doc comment for an instance method.
 *
 * The best way to get HTML metacharacters into documentation is to surround them with the {@literal} tag,
 * which suppress processing of HTML markup and nested Javadoc tags.
 *
 * The first sentence of each doc comment becomes the summary description of the element to which the comment pertains.
 * No two members or constructors in a class or interface should have the same summary description.
 * In order to prevent a summary description from prematurely being terminated with a period,
 * surround the offending period and any associated text with a {@literal} tag.
 *
 * When documenting a generic type or method, be sure to document all type parameters.
 *
 * When documenting an enum type, be sure to document the constants as well as the type and any public methods.
 *
 * When documenting an annotation type, be sure to document any members as well as the type itself.
 *
 * As of release 1.5, package-level doc comments should be placed in a file called package-info.java instead of package.html.
 * In addition to package-level doc comments, package-info.java can contain a package declaration and package annotations.
 *
 * Whether or not a class is thread-safe, you should document its thread-safety level.
 * If a class is serializable, you should document its serialized form.
 *
 * Javadoc has the ability to "inherit" method comments.
 * You can also inherit parts of doc comments from supertypes using the {@inheritDoc} tag.
 *
 * For complex APIs, it is often necessary to supplement the doc comments with an external document describing the overall architecture of the API.
 * If such a document exists, the relevant class or package doc comments should include a link to it.
 */
public class Item44_DocComment {

    /**
     * Example of doc comment
     */
    /**
     * Returns the element at the specified position in this list.
     *
     * <p>This method is <i> not</i> guaranteed to run in constant time.
     * In some implementations it may run in time proportional
     * to the element position.
     *
     * @param index index of element to return; must be
     *              non-negative and less than the size of this list
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index if out of range
     *          ({@code index < 0 || index >= this.size()})
     */
    // E get(int index);

    /**
     * Uses of {@literal} tag
     */
    /**
     * The triangle inequality is {@literal |x+y| < |x| + |y|}.
     */
    /**
     * A college degree, such as B.S., {@literal M.S.} or Ph.D.
     * College is a fountain of knowledge where many go to drink.
     */

    /**
     * Documenting type parameters for a generic type or method
     */
    /**
     * An object that maps keys to values. A map cannot contain
     * duplicate keys; each key map to at most one value.
     *
     * @param <K> the type of keys maintained by this map
     * @param <V> the type of mapped values
     */
    public interface Map<K,V> {

    }

    /**
     * Documenting enum type
     */
    /**
     * An instrument section of a symphony orchestra.
     */
    public enum OrchestraSection {
        /** Woodwinds, such as flute, clarinet, and oboe. */
        WOODWIND,

        /** Brass instruments, such as fresh horn and trumpet. */
        BRASS,

        /** Percussion instruments, such as timpani and cymbals. */
        PERCUSSION,

        /** Stringed instruments, such as violin and cello. */
        STRING;
    }

    /**
     * Documenting annotation type
     */
    /**
     * Indicates that the annotation method is a test method that
     * must throw the designated exception to succeed.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        /**
         * The exception that the annotated test method must throw
         * in order to pass. (The test is permitted to throw any
         * subtype of the type described by this class object.)
         */
        Class<? extends Exception> value();
    }



    public static void main(String[] args) {

    }

}
