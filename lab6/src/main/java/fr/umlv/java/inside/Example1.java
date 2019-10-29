package fr.umlv.java.inside;

public class Example1 {

    public static void main(String[] args) {

        var scope = new ContinuationScope("hello1");
        var continuation = new Continuation(scope, () -> {
            Continuation.yield(scope);
            System.out.println("hello continuation");
        });

        continuation.run();
        continuation.run();

    }

}
