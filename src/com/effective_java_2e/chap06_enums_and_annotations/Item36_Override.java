package com.effective_java_2e.chap06_enums_and_annotations;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sofia on 5/21/17.
 */

/**
 * Item 36: Consistently use the Override annotation.
 *
 * The Override annotation can be used only on method declarations,
 * and it indicates that the annotated method declaration overrides a declaration in a supertype.
 *
 * You should use the Override annotation on every method declaration that you believe to override a superclass declaration.
 * There is one minor exception to this rule.
 * If you are writing a class that is not labeled abstract, and you believe that it overrides an abstract method,
 * you needn't bother putting the Override annotation on that method.
 *
 * In an abstract class or an interface, however, it is worth annotating all methods that you believe to override superclass or superinterface methods,
 * whether concrete or abstract.
 */
public class Item36_Override {

    public static class Bigram {
        private final char first;
        private final char second;

        public Bigram(char first, char second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Bigram))
                return false;
            Bigram b = (Bigram) o;
            return b.first == first && b.second == second;
        }

        public int hashCode() {
            return 31 * first + second;
        }

        public static void main(String[] args) {
            Set<Bigram> s = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    s.add(new Bigram(ch, ch));
                }
            }
            System.out.println(s.size());
        }
    }



    public static void main(String[] args) {

    }

}
