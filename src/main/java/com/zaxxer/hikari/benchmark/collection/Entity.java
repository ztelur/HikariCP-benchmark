/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2020 All Rights Reserved.
 */
package com.zaxxer.hikari.benchmark.collection;

import com.zaxxer.hikari.util.ConcurrentBag.IConcurrentBagEntry;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 *
 * @author libing
 * @version $Id: Entity.java, v 0.1 2020年03月26日 下午6:54 zt Exp $
 */
public class Entity implements IConcurrentBagEntry {
    private static final AtomicIntegerFieldUpdater<Entity> stateUpdater;
    private volatile int state = 0;
    static
    {
        stateUpdater = AtomicIntegerFieldUpdater.newUpdater(Entity.class, "state");
    }

    @Override
    public boolean compareAndSet(int i, int i1) {
        return stateUpdater.compareAndSet(this,i, i1);
    }

    @Override
    public void setState(int i) {
        stateUpdater.set(this, i);
    }

    @Override
    public int getState() {
        return state;
    }
}