package fr.umlv.java.inside;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class LoggerBenchMark {

    private static class A {
        private static final StringBuilder sb = new StringBuilder("lol");
        private static final Logger logger = Logger.of(A.class, __ -> { });
    }

    private static class B {
        private static final StringBuilder sb = new StringBuilder("lol");
        private static final Logger logger = Logger.fastOf(B.class, __ -> { });
    }

    @Benchmark
    public void no_op() {
        // empty
    }

    @Benchmark
    public void simple_logger() {
        //TODO
    }

    @Benchmark
    public void of() { A.logger.log("of"); }

    @Benchmark
    public void fastOf() { B.logger.log("fastOf"); }

}