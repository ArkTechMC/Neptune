package com.iafenvoy.neptune.util.function.consumer;

@FunctionalInterface
public interface Consumer2<T1, T2> {
    void accept(T1 t1, T2 t2);
}
