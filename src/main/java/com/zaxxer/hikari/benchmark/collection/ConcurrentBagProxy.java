/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2020 All Rights Reserved.
 */
package com.zaxxer.hikari.benchmark.collection;

import com.zaxxer.hikari.util.ConcurrentBag;
import com.zaxxer.hikari.util.ConcurrentBag.IBagStateListener;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author libing
 * @version $Id: ConcurrentBagProxy.java, v 0.1 2020年03月27日 下午2:55 zt Exp $
 */
public class ConcurrentBagProxy implements Proxy {
    public  ConcurrentBag<Entity> CB;

    private ThreadPoolExecutor addConnectionExecutor;

    public ConcurrentBagProxy() {
        LinkedBlockingQueue<Runnable> addConnectionQueue = new LinkedBlockingQueue<>(100);
        addConnectionExecutor= new ThreadPoolExecutor(1 /*core*/, 1 /*max*/, 5 /*keepalive*/, SECONDS, addConnectionQueue, Executors
                .defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        CB = new ConcurrentBag<>(new IBagStateListener() {
            @Override
            public void addBagItem(int i) {
                addConnectionExecutor.submit(poolEntryCreator);
            }
        });
    }

    @Override
    public void addEntry(Entity e) {
        CB.add(e);
    }


    private final Runnable poolEntryCreator = new Runnable() {
        @Override
        public void run() {
            CB.add(new Entity());
        }
    };

    @Override
    public Object borrow(long timeout, TimeUnit timeUnit) {
        try {
            return CB.borrow(timeout, timeUnit);
        } catch (InterruptedException e) {
            return null;
        }
    }

}