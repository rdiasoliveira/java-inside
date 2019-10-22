package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("methodString")
    void stringSwitchTest(ToIntFunction<String> methodString) {
        assertAll(() ->assertEquals(0, methodString.applyAsInt("foo")),
                () -> assertEquals(1, methodString.applyAsInt("bar")),
                () -> assertEquals(2, methodString.applyAsInt("bazz")),
                () ->assertEquals(-1, methodString.applyAsInt("wat")));
    }

    static Stream<ToIntFunction<String>> methodString() {
        return Stream.of(StringSwitchExample::stringSwitch);
    }

}