package com.effective_java_2e.chap06_enums_and_annotations;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sofia on 5/20/17.
 */

/**
 * Item 33: Use EnumMap instead of ordinal indexing.
 *
 * When you access an array indexed by an enum's ordinal, it is your responsibility to use the correct int value;
 * ints do not provide the type safety of enums.
 *
 * A much better way to achieve the same effect is to use a Map, more specifically, an EnumMap.
 * The EnumMap constructor takes the Class object of the key type:
 * this is a "bounded type token", which provides runtime generic type information.
 *
 * In summary:
 * It is rarely appropriate to use ordinals to index arrays; use EnumMap instead.
 * If the relationship that you are representing is multidimensional, use EnumMap<..., EnumMap<...>>.
 */
public class Item33_EnumMap {

    public static class Herb {
        public enum Type { ANNUAL, PERENNIAL, BIENNIAL }

        private final String name;
        private final Type type;

        Herb(String name, Type type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Using ordinal() to index array of arrays - DON'T DO THIS
     */
    public enum Phase {
        SOLID, LIQUID, GAS;

        public enum Transition {
            MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

            // Rows indexed by src-ordinal, cols by dst-ordinal
            private static final Transition[][] TRANSITIONS = {
                    { null, MELT, SUBLIME },
                    { FREEZE, null, BOIL },
                    { DEPOSIT, CONDENSE, null }
            };

            // Returns the phase transition from one phase to another
            public static Transition from(Phase src, Phase dst) {
                return TRANSITIONS[src.ordinal()][dst.ordinal()];
            }
        }
    }

    /**
     * Using a nested EnumMap to associate data with enum pairs
     */
    public enum Phase2 {
        SOLID, LIQUID, GAS;

        public enum Transition2 {
            MELT(SOLID, LIQUID),
            FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS),
            CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS),
            DEPOSIT(GAS, SOLID);

            final Phase2 src;
            final Phase2 dst;

            Transition2(Phase2 src, Phase2 dst) {
                this.src = src;
                this.dst = dst;
            }

            // Initialize the phase transition map
            private static final Map<Phase2, Map<Phase2, Transition2>> m = new EnumMap<>(Phase2.class);
            static {
                for (Phase2 p : Phase2.values())
                    m.put(p, new EnumMap<>(Phase2.class));
                for (Transition2 t : Transition2.values())
                    m.get(t.src).put(t.dst, t);
            }

            public static Transition2 from(Phase2 src, Phase2 dst) {
                return m.get(src).get(dst);
            }
        }
    }



    public static void main(String[] args) {
        Herb[] garden = new Herb[5];

        /**
         * Using ordinal() to index into an array - DON'T DO THIS
         */
        Set<Herb>[] herbsByType = (Set<Herb>[]) new Set[Herb.Type.values().length];
        for (int i = 0; i < herbsByType.length; i++)
            herbsByType[i] = new HashSet<>();
        for (Herb h : garden)
            herbsByType[h.type.ordinal()].add(h);
        for (int i = 0; i < herbsByType.length; i++) {
            System.out.printf("%s: %s%n", Herb.Type.values()[i], herbsByType[i]);
        }

        /**
         * Using an EnumMap to associate date with an enum
         */
        Map<Herb.Type, Set<Herb>> herbsByType2 = new EnumMap<>(Herb.Type.class);
        for (Herb.Type t : Herb.Type.values())
            herbsByType2.put(t, new HashSet<>());
        for (Herb h : garden)
            herbsByType2.get(h.type).add(h);
        System.out.println(herbsByType2);
    }

}
