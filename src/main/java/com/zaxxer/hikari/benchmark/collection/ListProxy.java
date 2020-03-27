/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2020 All Rights Reserved.
 */
package com.zaxxer.hikari.benchmark.collection;

import com.zaxxer.hikari.util.ConcurrentBag;
import com.zaxxer.hikari.util.ConcurrentBag.IBagStateListener;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author libing
 * @version $Id: ListProxy.java, v 0.1 2020年03月27日 下午3:01 zt Exp $
 */
public class ListProxy implements Proxy {
    public LinkedBlockingDeque<Entity> CB;

    private ThreadPoolExecutor addConnectionExecutor;

    public ListProxy() {
        LinkedBlockingQueue<Runnable> addConnectionQueue = new LinkedBlockingQueue<>(100);
        addConnectionExecutor= new ThreadPoolExecutor(1 /*core*/, 1 /*max*/, 5 /*keepalive*/, SECONDS, addConnectionQueue, Executors
                .defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        CB = new LinkedBlockingDeque();
    }

    private final Runnable poolEntryCreator = new Runnable() {
        @Override
        public void run() {
            CB.add(new Entity());
        }
    };



    @Override
    public Object borrow(long timeout, TimeUnit timeUnit) {
        ;
    }

    @Override
    public void addEntry(Entity e) {

    }
}