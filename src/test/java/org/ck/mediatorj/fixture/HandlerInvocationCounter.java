package org.ck.mediatorj.fixture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Chris
 * @since Dec 1, 2021
 *
 */
public class HandlerInvocationCounter {

    private final static ConcurrentHashMap<Class<?>, AtomicInteger> COUNTER = new ConcurrentHashMap<>();

    public static void addHandledBy(Class<?> handlerType) {

        COUNTER.compute(handlerType, (key, value) -> {

            if (value == null) {
                return new AtomicInteger(1);
            }

            value.incrementAndGet();

            return value;
        });

    }

    public static int totalCountOf(Class<?> handlerType) {
        return COUNTER.getOrDefault(handlerType, new AtomicInteger(0)).get();
    }

    public static void cleanUp() {
        COUNTER.clear();
    }

}
