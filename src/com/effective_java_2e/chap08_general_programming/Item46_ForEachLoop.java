package com.effective_java_2e.chap08_general_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 46: Prefer for-each loops to traditional for loops.
 *
 * If you are writing a type that represents a group of elements,
 * have it implement Iterable even if you choose not to have it implement Collection.
 * This will allow your users to iterate over your type using the for-each loop.
 *
 * In summary:
 * The for-each loop provides compelling advantages over the traditional for loop in clarity and bug prevention,
 * with no performance penalty.
 * But there are three common situations where you can't use a for-each loop:
 * 1. Filtering
 *    If you need to traverse a collection and remove selected elements,
 *    then you need to use an explicit iterator so that you can call its remove method.
 * 2. Transforming
 *    If you need to traverse a list or array and replace some or all of the values of its elements,
 *    then you need the list iterator or array index in order to set the value of an element.
 * 3. Parallel iteration
 *    If you need to traverse multiple collections in parallel,
 *    then you need explicit control over the iterator or index variable,
 *    so that all iterators or index variables can be advanced in lockstep.
 */
public class Item46_ForEachLoop {

    static class Element {

    }

    static class Card {
        private final Suit suit;
        private final Rank rank;

        Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }
    }

    enum Suit { CLUB, DIAMOND, HEART, SPADE }
    enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }

    enum Face { ONE, TWO, THREE, FOUR, FIVE, SIX }



    public static void main(String[] args) {
        Collection<Element> c = new ArrayList<>();
        Element[] a = new Element[10];

        /**
         * No longer the preferred idiom to iterate over a collection
         */
        for (Iterator i = c.iterator(); i.hasNext(); ) {
            doSomething((Element) i.next());
        }

        /**
         * No longer the preferred idiom to iterate over an array
         */
        for (int i = 0; i < a.length; i++) {
            doSomething(a[i]);
        }

        /**
         * The preferred idiom for iterating over collections and arrays
         */
        for (Element e : c) {
            doSomething(e);
        }
        for (Element e : a) {
            doSomething(e);
        }

        /**
         * Buggy code
         */
        Collection<Suit> suits = Arrays.asList(Suit.values());
        Collection<Rank> ranks = Arrays.asList(Rank.values());

        List<Card> deck = new ArrayList<>();
        for (Iterator<Suit> i = suits.iterator(); i.hasNext(); )
            for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); )
                // Calls i.next() from the inner loop
                // Throws NoSuchElementException
                deck.add(new Card(i.next(), j.next()));

        /**
         * Same bug, different symptom
         */
        Collection<Face> faces = Arrays.asList(Face.values());

        for (Iterator<Face> i = faces.iterator(); i.hasNext(); )
            for (Iterator<Face> j = faces.iterator(); j.hasNext(); )
                System.out.println(i.next() + " " + j.next());

        /**
         * Fixed, but ugly - you can do better
         */
        for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
            Suit suit = i.next();
            for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); )
                deck.add(new Card(suit, j.next()));
        }

        /**
         * Preferred idiom for nested iteration on collections and arrays
         */
        for (Suit suit : suits)
            for (Rank rank : ranks)
                deck.add(new Card(suit, rank));

    }

    private static void doSomething(Element e) {}

}
