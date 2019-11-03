package fr.umlv.java.inside;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SchedulerTest {

    private static ArrayList<String> creatingOutput(Scheduler scheduler) {
        var scope = new ContinuationScope("hello");
        var output = new ArrayList<String>();
        var continuation1 = new Continuation(scope, () -> {
            output.add("start 1");
            scheduler.enqueue(scope);
            output.add("middle 1");
            scheduler.enqueue(scope);
            output.add("end 1");
        });
        var continuation2 = new Continuation(scope, () -> {
            output.add("start 2");
            scheduler.enqueue(scope);
            output.add("middle 2");
            scheduler.enqueue(scope);
            output.add("end 2");
        });
        var list = List.of(continuation1, continuation2);
        list.forEach(Continuation::run);
        scheduler.runLoop();
        return output;
    }

    @Test
    public void schedulerNull() {
        assertThrows(NullPointerException.class, () -> new Scheduler(null));
    }

    @Test
    @Tag("FIFO")
    public void schedulerFifo() {
        var scheduler = new Scheduler(Scheduler.Strategy.FIFO);
        var expectedList = new ArrayList<String>();
        expectedList.add("start 1");
        expectedList.add("start 2");
        expectedList.add("middle 1");
        expectedList.add("middle 2");
        expectedList.add("end 1");
        expectedList.add("end 2");
        assertEquals(creatingOutput(scheduler), expectedList);
    }

    @Test
    @Tag("STACK")
    public void schedulerStack() {
        var scheduler = new Scheduler(Scheduler.Strategy.STACK);
        var expectedList = new ArrayList<String>();
        expectedList.add("start 1");
        expectedList.add("start 2");
        expectedList.add("middle 2");
        expectedList.add("end 2");
        expectedList.add("middle 1");
        expectedList.add("end 1");
        assertEquals(creatingOutput(scheduler), expectedList);
    }

    @Test
    @Tag("RANDOM")
    public void schedulerRandom() {
        var scheduler = new Scheduler(Scheduler.Strategy.STACK);
        var expectedRandomList = creatingOutput(scheduler);
        var allPossibleValues = new ArrayList<String>();
        allPossibleValues.add("start 1");
        allPossibleValues.add("start 2");
        allPossibleValues.add("middle 1");
        allPossibleValues.add("middle 2");
        allPossibleValues.add("end 1");
        allPossibleValues.add("end 2");
        assertAll(
                () -> assertTrue(allPossibleValues.size() == expectedRandomList.size()),
                () -> assertTrue(expectedRandomList.containsAll(allPossibleValues))
        );
    }

}