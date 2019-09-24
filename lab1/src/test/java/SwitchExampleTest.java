import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchExampleTest {

    @Test
    public void testSwitchExample() {
        assertEquals(1, SwitchExample.switchExample("dog"));
    }

    @Test
    public void testSwitchExample2() {
        assertEquals(2, SwitchExample.switchExample("cat"));
    }
}