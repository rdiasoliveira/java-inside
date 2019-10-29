package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.Objects;

public class Scheduler {

    private final ArrayDeque<Continuation> ad;

    public Scheduler() {
        this.ad = new ArrayDeque<>();
    }

    public void enqueue(ContinuationScope scope) {
        Objects.requireNonNull(Continuation.getCurrentContinuation(scope));
        ad.offer(Continuation.getCurrentContinuation(scope));
        Continuation.yield(scope);
    }

    public void runLoop() {
        while(!ad.isEmpty()) {
            ad.pollLast().run();
        }
    }


}
