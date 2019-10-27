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
            function.applyAsInt("other2");
            function.applyAsInt("other3");
        }
    }

    @Benchmark
    public void stringSwitch() {
        testingFunction(StringSwitchExample::stringSwitch);
    }
    @Benchmark
    public void stringGuardWithTest() {
        testingFunction(StringSwitchExample::stringSwitch2);
    }
    @Benchmark
    public void stringGuardWithTestInlineCache() {
        testingFunction(StringSwitchExample::stringSwitch3);
    }

}