package com.effective_java_2e.chap08_general_programming;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 56: Adhere to generally accepted naming conventions.
 *
 * The Java platform has a well-defined set of "naming conventions".
 * Naming conventions fall into two categories: typographical and grammatical.
 *
 * Typographical naming conventions:
 *
 * Package names should be hierarchical with the components separated by periods.
 * Components should consist of lowercase alphabetic characters and, rarely, digits.
 *
 * The name of any package that will be used outside your organization
 * should begin with your organization's Internet domain name with the top-level domain first
 * (e.g. edu.cmu, com.sun, gov.nsa).
 * Users must not create packages whose names begin with java or javax.
 *
 * The remainder of a package name should consist of one or more components describing the package.
 * Components should generally consists of a single word or abbreviation.
 *
 * Class and interface names, including enum and annotation type names,
 * should consist of one or more words, with the first letter of each word capitalized.
 * Abbreviations are to be avoided, except for acronyms and certain common abbreviations (e.g., max and min).
 * There is little consensus as to whether acronyms should be uppercase or have only their first letter capitalized.
 *
 * Method and field names follow the same typographical conventions,
 * except that the first letter of a method or field name should be lowercase.
 *
 * The sole exception to the previous rule concerns "constant fields",
 * whose names should consist of one or more uppercase words separated by underscore character.
 * Constant fields constitute the only recommended use of underscores.
 *
 * Local variable names have similar typographical naming conventions to member names,
 * except that abbreviations are permitted, as are individual characters and short sequences of characters.
 *
 * Type parameter names usually consist of a single letter.
 * Most commonly, it is one of these 5:
 * T for an arbitrary type
 * E for the element type of a collection
 * K and V for the key and value types of a map
 * X for an exception
 * A sequence of arbitrary types can be T,U,V or T1,T2,T3.
 *
 * Examples of typographical conventions:
 *
 * Identifier Type              Examples
 * Package                      com.google.inject, org.joda.time.format
 * Class or Interface           Timer, FutureTask, LinkedHashMap, HttpServlet
 * Method or Field              remove, ensureCapacity, getCrc
 * Constant Field               MIN_VALUE, NEGATIVE_INFINITY
 * Local Variable               i, xref, houseNumber
 * Type Parameter               T, E, K, V, X, T1, T2
 *
 * Grammatical naming conventions:
 *
 * Classes, including enum types, are generally named with a singular noun or noun phrase.
 * Interfaces are named like classes, or with an adjective ending in "able" or "ible".
 *
 * Because annotation types have so many uses, no part of speech predominates.
 *
 * Methods that perform some action are generally named with a verb or verb phrase.
 * Methods that return a boolean value usually have names that begin with the word "is" or "has".
 * Methods that return a non-boolean function or attribute of an object are usually named with a noun, a noun phrase,
 * or a verb phrase beginning with the verb "get".
 * The form beginning with "get" is mandatory if the class containing the method is a Bean.
 * If a class contains a method to set the same attribute, the two methods should be name getAttribute and setAttribute.
 *
 * Methods that convert the type of an object, returning an independent object of a different type, are often called toType.
 * Methods that return a view whose type differs from that of the receiving object are often called asType.
 * Methods that return a primitive with the same value as the object on which they're invoked are often called typeValue.
 *
 * Common names for static factories are valueOf, of, getInstance, newInstance, getType, and newType.
 *
 * Grammatical conventions for field names are less well established and less important.
 *
 * Fields of type boolean are often named like boolean accessor methods with the initial "is" is omitted.
 * Fields of other types are usually named with nouns or noun phrases.
 */
public class Item56_NamingConvention {

    public static void main(String[] args) {

    }

}
