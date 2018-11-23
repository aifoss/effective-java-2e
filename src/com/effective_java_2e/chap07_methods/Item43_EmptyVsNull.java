package com.effective_java_2e.chap07_methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 43: Return empty arrays or collections, not nulls.
 *
 * There is no reason ever to return null from an array- or collection-valued method instead of returning an empty array or collection.
 */
public class Item43_EmptyVsNull {

    static class Cheese {
        private final String name;

        public Cheese(String name) {
            this.name = name;
        }
    }

    static class Shop {
        private final List<Cheese> cheesesInStock = Arrays.asList(new Cheese("White"), new Cheese("Blue"));

        /**
         * The wrong way to return an array from a collection
         */
        public Cheese[] getCheeses() {
            if (cheesesInStock.size() == 0)
                return null;
            return cheesesInStock.toArray(new Cheese[cheesesInStock.size()]);
        }

        /**
         * The right away to return an array from a collection
         */
        private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];
        public Cheese[] getCheeses2() {
            return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
        }

        /**
         * The right away to return a copy of a collection
         */
        public List<Cheese> getCheeseList() {
            if (cheesesInStock.isEmpty())
                return Collections.emptyList(); // Always returns same list
            else
                return new ArrayList<>(cheesesInStock);
        }
    }



    public static void main(String[] args) {

    }

}
