/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2020 All Rights Reserved.
 */
package com.zaxxer.hikari.benchmark;

import com.zaxxer.hikari.benchmark.collection.Entity;
import com.zaxxer.hikari.util.ConcurrentBag;
import com.zaxxer.hikari.util.ConcurrentBag.IBagStateListener;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author libing
 * @version $Id: ConcurentBagBench.java, v 0.1 2020年03月26日 下午6:49 zt Exp $
 */
@Warmup(iterations=3)
@Measurement(iterations=8)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ConcurentBagBench {

    public static ConcurrentBag<Entity> CB;




    @Setup(Level.Trial)
    public void setup(BenchmarkParams params) {
        setup();
    }


    public void setup() {

        for (int i = 0; i <= 100; i++) {
            CB.add(new Entity());
        }
    }


    @Benchmark
    @CompilerControl(CompilerControl.Mode.INLINE)
    public static void testConcurrentBag() throws SQLException
    {
        try {
            Entity connection = CB.borrow(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //public static void main(String[] args) throws RunnerException {
    //
    //    // 可以通过注解
    //    Options opt = new OptionsBuilder()
    //            .include(ConcurentBagBench.class.getSimpleName())
    //            .warmupIterations(3) // 预热3次
    //            .measurementIterations(8)
    //            .mode(Mode.Throughput)
    //            .build();
    //
    //    new Runner(opt).run();
    //}

}