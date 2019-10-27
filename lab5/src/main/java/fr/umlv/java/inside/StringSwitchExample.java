package fr.umlv.java.inside;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.List;

public class StringSwitchExample {

    public static int stringSwitch(String str) {
        switch (str) {
            case ("foo"):
                return 0;
            case ("bar"):
                return 1;
            case ("bazz"):
                return 2;
            default:
                return -1;
        }
    }

    private static final MethodHandle STRINGS_EQUALS;

    static {
        var lookup = MethodHandles.lookup();
        try {
            STRINGS_EQUALS =
                    lookup.findVirtual(String.class, "equals", MethodType.methodType(boolean.class, Object.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    public static int stringSwitch2(String str) throws Throwable {
        var mh = createMHFromStrings2("foo", "bar", "bazz");
        return (int) mh.invokeExact(str);
    }

    public static MethodHandle createMHFromStrings2(String ... strings) {
        var mh = MethodHandles.dropArguments(MethodHandles.constant(int.class, -1), 0, String.class);
        for(var i = 0; i < strings.length; i++) {
            mh = MethodHandles.guardWithTest(
                    MethodHandles.insertArguments(STRINGS_EQUALS, 1, strings[i]),
                    MethodHandles.dropArguments(MethodHandles.constant(int.class, i), 0, String.class),
                    mh);
        }
        return mh;
    }

    public static int stringSwitch3(String str) throws Throwable {
        var mh = createMHFromStrings3("foo", "bar", "bazz");
        return (int) mh.invokeExact(str);
    }

    public static MethodHandle createMHFromStrings3(String... matches) {
        return new InliningCache(matches).dynamicInvoker();
    }

    static class InliningCache extends MutableCallSite {

        private static final MethodHandle SLOW_PATH;
        private final List<String> matches;

        static {
            try {
                SLOW_PATH = MethodHandles.
                        lookup().
                        findVirtual(InliningCache.class, "slowPath", MethodType.methodType(int.class, String.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }

        public InliningCache(String... matches) {
            super(MethodType.methodType(int.class, String.class));
            this.matches = List.of(matches);
            setTarget(MethodHandles.insertArguments(SLOW_PATH, 0, this));
        }

        private int slowPath(String value) {
            var index = matches.indexOf(value);
            var mh = MethodHandles.guardWithTest(MethodHandles.insertArguments(STRINGS_EQUALS, 1, value),
                    MethodHandles.dropArguments(MethodHandles.constant(int.class, index), 0, String.class),
                    getTarget());
            setTarget(mh);
            return index;
        }

    }

}