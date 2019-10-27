package fr.umlv.java.inside;

import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;
import org.openjdk.jmh.annotations.*;

@Warmup(iterations = 0, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class StringSwitchExampleBenchmark {

    private void testingFunction(ToIntFunction<String> function){
        for(var i = 0; i < 1_000_000; i++) {
            function.applyAsInt("foo");
            function.applyAsInt("bar");
            function.applyAsInt("baz");
            function.applyAsInt("other");
        }
    }

    @Benchmark
    public void StringSwitch1() {
        testingFunction(StringSwitchExample::stringSwitch);
    }
    @Benchmark
    public void StringSwitch2() {
        testingFunction(StringSwitchExample::stringSwitch2);
    }
    @Benchmark
    public void StringSwitch3() {
        testingFunction(StringSwitchExample::stringSwitch3);
    }

}