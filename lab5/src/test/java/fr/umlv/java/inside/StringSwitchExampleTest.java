package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;

import static fr.umlv.java.inside.StringSwitchExample.stringSwitch;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringSwitchExampleTest {

    @Test
    void stringSwitchTest() {
        assertAll(() ->assertEquals(0, stringSwitch("foo")),
                () -> assertEquals(1, stringSwitch("bar")),
                () -> assertEquals(2, stringSwitch("bazz")),
                () ->assertEquals(-1, stringSwitch("wat")));
    }
}