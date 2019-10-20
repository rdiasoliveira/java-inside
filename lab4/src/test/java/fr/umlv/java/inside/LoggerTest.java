package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {

    private static class A {
        private static final StringBuilder sb = new StringBuilder();
        private static final Logger logger = Logger.of(A.class, sb::append);
    }

    @Test
    public void log() {
        A.logger.log("message");
        assertEquals("message", A.sb.toString());
    }

    @Test
    public void ofNull() {
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {
                }).log("lol")),
                () -> assertThrows(NullPointerException.class, () -> Logger.of(LoggerTest.class, null).log("lol"))
        );
    }

}