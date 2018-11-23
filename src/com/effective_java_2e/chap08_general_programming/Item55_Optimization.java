package com.effective_java_2e.chap08_general_programming;

/**
 * Created by sofia on 5/22/17.
 */

/**
 * Item 55: Optimize judiciously.
 *
 * There are 3 aphorisms concerning optimization that everyone should know:
 *
 * 1. "More computing sins are committed in the name of efficiency (without necessarily achieving it) than for any other single reason
 *    - including blind stupidity." - William A. Wulf
 *
 * 2. "We should forget about small efficiencies, say about 97% of the time: premature optimization is the root of all evil."
 *    - Donald E. Knuth
 *
 * 3. "We follow two rules in the matter of optimization:
 *    Rule 1. Don't do it.
 *    Rule 2 (for experts only). Don't do it yet - that is, not until you have a perfectly clear and unoptimized solution."
 *    - M. A. Jackson
 *
 * Don't sacrifice sound architectural principles for performance.
 *
 * Strive to write good programs rather than fast ones.
 * Good programs embody the principle of "information hiding":
 * where possible, they localize design decisions within individual modules,
 * so individual decisions can be changed without affecting the remainder of the system.
 * This does not mean that you can ignore performance concerns until your program is complete.
 * You must think about performance during the design process.
 *
 * Strive to avoid design decisions that limit performance.
 *
 * The components of a design that are most difficult to change after the fact
 * are those specifying interactions between modules and with the outside world.
 * Chief among these design components are APIs, wire-level protocols, and persistent data formats.
 * Not only are these design components difficult or impossible to change after the fact,
 * but all of them can place significant limitations on the performance that a system can ever achieve.
 *
 * Consider the performance consequences of your API design decisions.
 *
 * Making a public type mutable may require a lot of needless defensive copying.
 * Using inheritance in a public class ties the class forever to its superclass.
 * using an implementation type rather than an interface in an API ties you to a specific implementation.
 *
 * It is generally the case that good API design is consistent with good performance.
 * It is a very bad idea to warp an API to achieve good performance.
 *
 * Measure performance before and after each attempted optimization.
 *
 * Common wisdom says that programs spend 80 percent of their time in 20 percent of their code.
 * Profiling tools can help you decide where to focus your optimization efforts.
 * The need to measure the effects of attempted optimization is even greater on the Java platform than on more traditional platforms,
 * because Java does not have a strong "performance model".
 * Not only is Java's performance model ill-defined, but it varies from JVM implementation to JVM implementation,
 * from release to release, and from processor to processor.
 *
 * To summarize:
 * Do not strive to write fast programs - strive to write good ones; speed will follow.
 * Do think about performance issues while you are designing systems.
 * When you've finished building the system, measure its performance.
 * If it's not fast enough, locate the source of the problems with the aid of a profiler,
 * and go to work optimizing the relevant parts of the system.
 * The first step is to examine your choice of algorithms:
 * no amount of low-level optimization can make up for a poor choice of algorithm.
 * Repeat this process as necessary, measuring the performance after every change,
 * until you're satisfied.
 */
public class Item55_Optimization {

    public static void main(String[] args) {

    }

}
