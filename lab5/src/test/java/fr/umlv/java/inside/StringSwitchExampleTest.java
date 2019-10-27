package fr.umlv.java.inside;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class StringSwitchExampleTest {

    /* @Test
    public void stringSwitchTest() {
        assertAll(() ->assertEquals(0, stringSwitch2("foo")),
                () -> assertEquals(1, stringSwitch2("bar")),
                () -> assertEquals(2, stringSwitch2("bazz")),
                () ->assertEquals(-1, stringSwitch2("wat")));
    } */

    @ParameterizedTest
    @MethodSource("methodString")
    public void stringSwitchTest(ToIntFunction<String> methodString) {
        assertAll(() ->assertEquals(0, methodString.applyAsInt("foo")),
                () -> assertEquals(1, methodString.applyAsInt("bar")),
                () -> assertEquals(2, methodString.applyAsInt("bazz")),
                () ->assertEquals(-1, methodString.applyAsInt("wat")));
    }

    public static Stream<ToIntFunction<String>> methodString() {
        return Stream.of(StringSwitchExample::stringSwitch,
                StringSwitchExample::stringSwitch2,
                StringSwitchExample::stringSwitch3);
    }

}