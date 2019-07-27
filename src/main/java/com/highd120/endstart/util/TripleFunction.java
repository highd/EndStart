package com.highd120.endstart.util;

@FunctionalInterface
public interface TripleFunction<T, U, V> {
    void run(T val1, U val2, V val3);
}
