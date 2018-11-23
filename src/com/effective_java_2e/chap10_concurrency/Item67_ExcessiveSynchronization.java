package com.effective_java_2e.chap10_concurrency;

import com.effective_java_2e.chap04_classes_and_interfaces.Item16_Composition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sofia on 5/23/17.
 */

/**
 * Item 67: Avoid excessive synchronization.
 *
 * To avoid liveness and safety failures, never cede control to the client within a synchronized method or block.
 * In other words, inside a synchronized region, do not invoke a method that is designed to be overridden,
 * or one provided by a client in the form of a function object.
 * From the perspective of the class with the synchronized region, such methods are alien.
 * Depending on what an alien method does, calling it from a synchronized region can cause exception, deadlocks, or data corruption.
 *
 * Suppose you were to invoke an alien method from within a synchronized region while the invariant protected by the synchronized region was temporarily invalid.
 * Because locks in the Java programming language are "reentrant", such calls won't deadlock.
 * Reentrant locks simplify the construction of multithreaded object-oriented programs,
 * but they can turn liveness failures into safety failures.
 *
 * It is usually not too hard to fix this sort of problem by moving alien method invocations out of synchronized blocks.
 *
 * There is a better way to move the alien method invocation out of the synchronized block.
 * Java libraries provide a concurrent collection known as CopyOnWriteArrayList, which is tailor-made for this purpose.
 * It is a variant of ArrayList in which all write operations are implemented by making a fresh copy of the entire underlying array.
 * Because the internal array is never modified, iteration requires no locking and is very fast.
 *
 * An alien method invoked outside of a synchronized region is known as an "open call".
 * Besides preventing failures, open calls can greatly increase concurrency.
 *
 * As a rule, you should do as little work as possible inside synchronized regions.
 * Obtain the lock, examine the shared data, transform it as necessary, and drop the lock.
 * If you must perform some time-consuming activity, find a way to move the activity out of the synchronized region.
 *
 * In a multicore world, the real cost of excessive synchronization is not the CPU time spent obtaining locks;
 * it is the lost opportunities for parallelism and the delays imposed by the need to ensure that every core has a consistent view of memory.
 * Another hidden cost of over-synchronization is that it can limit the VM's ability to optimize code execution.
 *
 * You should make a mutable class thread-safe if it is intended for concurrent use
 * and you can achieve significantly higher concurrency by synchronizing internally than you could by locking the entire object externally.
 * Otherwise, don't synchronize internally.
 * Let the client synchronize externally where it is appropriate.
 * When in doubt, do not synchronize your class, but document that it is not thread-safe.
 *
 * If a method modifies a static field, you must synchronize access to this field,
 * even if the method is typically used only by a single thread.
 *
 * In summary:
 * To avoid deadlock and data corruption, never call an alien method from within a synchronized region.
 * More generally, try to limit the amount of work that you do from within synchronized regions.
 * When you are designing a mutable class, think about whether it should do its own synchronization.
 * In the modern multicore era, it is more important than ever not to synchronize excessively.
 * Synchronize your class internally only if there is a good reason to do so, and document your decision clearly.
 */
public class Item67_ExcessiveSynchronization {

    /**
     * Broken - invokes alien method from synchronized block!
     */
    public static class ObservableSet<E> extends Item16_Composition.ForwardingSet<E> {
        public ObservableSet(Set<E> set) {
            super(set);
        }

        private final List<SetObserver<E>> observers = new ArrayList<>();

        public void addObserver(SetObserver<E> observer) {
            synchronized(observers) {
                observers.add(observer);
            }
        }

        public boolean removeObserver(SetObserver<E> observer) {
            synchronized (observers) {
                return observers.remove(observer);
            }
        }

        /**
         * Alien method in synchronized block
         */
        private void notifyElementAdded(E element) {
            synchronized (observers) {
                for (SetObserver<E> observer : observers) {
                    observer.added(this, element);
                }
            }
        }

        @Override
        public boolean add(E element) {
            boolean added = super.add(element);
            if (added)
                notifyElementAdded(element);
            return added;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c)
                result |= add(element); // calls notifyElementAdded
            return result;
        }
    }

    public interface SetObserver<E> {
        // Invoked when an element is added to the observable set
        void added(ObservableSet<E> set, E element);
    }

    /**
     * Fixed by moving alien method out of synchronization block
     */
    public static class ObservableSet2<E> extends Item16_Composition.ForwardingSet<E> {
        public ObservableSet2(Set<E> set) {
            super(set);
        }

        private final List<SetObserver2<E>> observers = new ArrayList<>();

        public void addObserver(SetObserver2<E> observer) {
            synchronized(observers) {
                observers.add(observer);
            }
        }

        public boolean removeObserver(SetObserver2<E> observer) {
            synchronized (observers) {
                return observers.remove(observer);
            }
        }

        /**
         * Alien method moved outside of synchronized block - open calls
         */
        private void notifyElementAdded(E element) {
            List<SetObserver2<E>> snapshot = null;

            synchronized (observers) {
                snapshot = new ArrayList<>(observers);
            }

            for (SetObserver2<E> observer : snapshot)
                observer.added(this, element);
        }

        @Override
        public boolean add(E element) {
            boolean added = super.add(element);
            if (added)
                notifyElementAdded(element);
            return added;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c)
                result |= add(element); // calls notifyElementAdded
            return result;
        }
    }

    public interface SetObserver2<E> {
        // Invoked when an element is added to the observable set
        void added(ObservableSet2<E> set, E element);
    }

    /**
     * Thread-safe observable set with CopyOnWriteArrayList
     */
    public static class ObservableSet3<E> extends Item16_Composition.ForwardingSet<E> {
        public ObservableSet3(Set<E> set) {
            super(set);
        }

        private final List<SetObserver3<E>> observers = new CopyOnWriteArrayList<>();

        /**
         * No need to synchronize
         */
        public void addObserver(SetObserver3<E> observer) {
            observers.add(observer);
        }

        public boolean removeObserver(SetObserver3<E> observer) {
            return observers.remove(observer);
        }

        /**
         * No need to synchronize
         * No need to take a snapshot
         */
        private void notifyElementAdded(E element) {
            for (SetObserver3<E> observer : observers)
                observer.added(this, element);
        }

        @Override
        public boolean add(E element) {
            boolean added = super.add(element);
            if (added)
                notifyElementAdded(element);
            return added;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c)
                result |= add(element); // calls notifyElementAdded
            return result;
        }
    }

    public interface SetObserver3<E> {
        // Invoked when an element is added to the observable set
        void added(ObservableSet3<E> set, E element);
    }



    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
            }
        });

        /**
         * Throws ConcurrentModificationException
         */
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23)
                    set.removeObserver(this);
            }
        });

        /**
         * Observer that uses a background thread needlessly
         * Causes deadlock
         */
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(final ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    final SetObserver<Integer> observer = this;
                    try {
                        executor.submit(new Runnable() {
                            public void run() {
                                // Background thread calls removeObserver, which attempts to lock observers,
                                // but it can't acquire the lock, because the main thread already has the lock.
                                // All the while, the main thread is waiting for the background thread to finish removing the observer,
                                // which explains the deadlock
                                set.removeObserver(observer);   // Deadlock
                            }
                        }).get();
                    } catch (ExecutionException ex) {
                        throw new AssertionError(ex.getCause());
                    } catch (InterruptedException ex) {
                        throw new AssertionError(ex.getCause());
                    } finally {
                        executor.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++)
            set.add(i);

    }

}
