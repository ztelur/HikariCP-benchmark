/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2020 All Rights Reserved.
 */
package com.zaxxer.hikari.benchmark.collection;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author libing
 * @version $Id: Proxy.java, v 0.1 2020年03月27日 下午2:58 zt Exp $
 */
public interface Proxy {
    Object borrow(long timeout, TimeUnit timeUnit);
    void addEntry(Entity e);
}