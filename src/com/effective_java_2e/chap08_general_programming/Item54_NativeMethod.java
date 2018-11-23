package com.effective_java_2e.chap08_general_programming;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 54: Use native methods judiciously.
 *
 * The Java Native Interface (JNI) allows Java applications to call "native methods",
 * which are special methods written in "native programming languages" such as C or C++.
 *
 * Historically, native methods have had three main uses:
 * 1. They provided access to platform-specific facilities such as registries and file locks.
 * 2. They provided access to libraries of legacy code, which could in turn provide access to legacy data.
 * 3. They were used to write performance-critical parts of applications in native languages for improved performance.
 *
 * It is legitimate to use native methods to access platform-specific facilities.
 * It is also legitimate to use native methods to access legacy code.
 * It is rarely advisable to use native methods for improved performance.
 *
 * The use of native methods has serious disadvantages:
 * 1. Because native languages are not safe, applications using native methods are no longer immune to memory corruption errors.
 * 2. Because native languages are platform dependent, applications using native methods are far less portable.
 * 3. Applications using native code are far more difficult to debug.
 * 4. Native method can decrease performance if they do only a small amount of work,
 *    as there is a fixed cost associated with going into and out of native code.
 * 5. Native methods require "glue code" that is difficult to read and tedious to write.
 */
public class Item54_NativeMethod {

    public static void main(String[] args) {

    }

}
