package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.List;

public class Example1 {

    private static final Object lock = new Object();

    public static void main(String[] args) {

        var scope = new ContinuationScope("hello1");

        /* var continuation = new Continuation(scope, () -> { */
            /* synchronized (lock) {
                Continuation.yield(scope);
            } */
            /* Continuation.yield(scope);
            System.out.println("hello continuation");
            System.out.println(Continuation.getCurrentContinuation(scope));
            System.out.println(Thread.currentThread());
        });

        continuation.run();
        continuation.run(); */

        /*
        var continuation1 = new Continuation(scope, () -> {
            System.out.println("start 1");
            Continuation.yield(scope);
            System.out.println("middle 1");
            Continuation.yield(scope);
            System.out.println("end 1");
        });
        var continuation2 = new Continuation(scope, () -> {
            System.out.println("start 2");
            Continuation.yield(scope);
            System.out.println("middle 2");
            Continuation.yield(scope);
            System.out.println("end 2");
        });

        var list = List.of(continuation1, continuation2);
        var ad = new ArrayDeque<>(list);
        while(!ad.isEmpty()) {
            var c = ad.poll();
            c.run();
            if(!c.isDone()) ad.push(c);
        } */

        var scheduler = new Scheduler(Scheduler.Strategy.FIFO);
        var continuation1 = new Continuation(scope, () -> {
            System.out.println("start 1");
            scheduler.enqueue(scope);
            System.out.println("middle 1");
            scheduler.enqueue(scope);
            System.out.println("end 1");
        });
        var continuation2 = new Continuation(scope, () -> {
            System.out.println("start 2");
            scheduler.enqueue(scope);
            System.out.println("middle 2");
            scheduler.enqueue(scope);
            System.out.println("end 2");
        });
        var list = List.of(continuation1, continuation2);
        list.forEach(Continuation::run);
        scheduler.runLoop();

    }

}
