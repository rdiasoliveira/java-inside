package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.concurrent.ThreadLocalRandom.current;

public class Scheduler {

    enum Strategy { STACK, FIFO, RANDOM }

    private final Supplier<Continuation> function;
    private final Collection<Continuation> collection;
    private final Strategy strategy;

    public Scheduler(Strategy strategy) {
        switch (strategy) {
            case FIFO:
                this.strategy = strategy;
                collection = new ArrayDeque<>();
                function = ((ArrayDeque<Continuation>) collection)::pollFirst;
                break;
            case STACK:
                this.strategy = strategy;
                collection = new ArrayDeque<>();
                function = ((ArrayDeque<Continuation>) collection)::pollLast;
                break;
            case RANDOM:
                this.strategy = strategy;
                collection = new ArrayList<>();
                function = () -> ((ArrayList<Continuation>) collection).get(current().nextInt(0, collection.size()));
                break;
            default: throw new IllegalArgumentException("Strategy not supported.");
        }
    }

    public void enqueue(ContinuationScope scope) {
        Objects.requireNonNull(Continuation.getCurrentContinuation(scope));
        collection.add(Continuation.getCurrentContinuation(scope));
        Continuation.yield(scope);
    }

    public void runLoop() {
        switch (strategy) {
            case RANDOM:
                while (!collection.isEmpty()) {
                    var continuation = function.get();
                    collection.remove(continuation);
                    continuation.run();
                }
            default:
                while (!collection.isEmpty()) {
                    function.get().run();
                }
        }
    }

}