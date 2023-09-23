package com.sat.common.event;

public interface EventHandler<T> {

    void handle(T event);
}
