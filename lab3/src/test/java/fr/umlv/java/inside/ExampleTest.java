package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {

    @Test
    void aStaticHello() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var m = Example.class.getDeclaredMethod("aStaticHello", int.class);
        m.setAccessible(true);
        assertEquals(m.invoke(new Example(), 5), "question 5");
    }

    @Test
    void anInstanceHello() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var m = Example.class.getDeclaredMethod("anInstanceHello", int.class);
        m.setAccessible(true);
        assertEquals(m.invoke(new Example(), 5), "question 5");
    }

    @Test
    void aStaticHello2() throws Throwable {
        var lookup = lookup().in(Example.class);
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class));
        assertEquals((String) methodHandle.invokeExact(6), "question 6");
    }

    @Test
    void anInstanceHello2() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findVirtual(Example.class, "anInstanceHello", methodType(String.class, int.class));
        assertEquals((String) methodHandle.invokeExact(new Example(), 6), "question 6");
    }

    @Test
    void aStaticHello3() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class));
        assertEquals((String) insertArguments(methodHandle, 0, 8).invokeExact(), "question 8");
    }

    @Test
    void anInstanceHello3() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findVirtual(Example.class, "anInstanceHello", methodType(String.class, int.class));
        assertEquals((String) insertArguments(methodHandle, 0, new Example(), 8).invokeExact(), "question 8");
    }

    @Test
    void aStaticHello4() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class));
        assertEquals((String) dropArguments(methodHandle, 0, String.class).invokeExact("lol", 8), "question 8");
    }

    @Test
    void anInstanceHello4() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findVirtual(Example.class, "anInstanceHello", methodType(String.class, int.class));
        assertEquals((String) dropArguments(methodHandle, 1, String.class).invokeExact(new Example(), "toto", 1), "question 1");
    }

    @Test
    void aStaticHello5() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class)).asType(methodType(String.class, Integer.class));
        assertEquals((String) methodHandle.invokeExact(Integer.valueOf(8)), "question 8");
    }

    @Test
    void anInstanceHello5() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findVirtual(Example.class, "anInstanceHello", methodType(String.class, int.class)).asType(methodType(String.class, Example.class, Integer.class));
        assertEquals((String) methodHandle.invokeExact(new Example(), Integer.valueOf(8)), "question 8");
    }

    @Test
    void aConstant() throws Throwable {
        var methodHandle = constant(String.class, "question 9");
        assertEquals((String) methodHandle.invokeExact(), "question 9");
    }

    @Test
    void aStaticHello6() throws Throwable {
        var privateLookup = privateLookupIn(Example.class, lookup().in(Example.class));
        var methodHandle = privateLookup.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class)).asType(methodType(String.class, Integer.class));
        assertEquals((String) methodHandle.invokeExact(Integer.valueOf(8)), "question 8");
    }

    @Test
    public void anIstanceHello5() throws Throwable{
        var str = "foo";
        var methodHandle = publicLookup().findVirtual(String.class,"equals",  methodType(boolean.class, Object.class));
        var methodHandle1 = guardWithTest(
                methodHandle.asType(methodType(boolean.class, String.class, String.class)),
                dropArguments(constant(int.class, 1), 0, String.class, String.class),
                dropArguments(constant(int.class, -1), 0, String.class, String.class)
        );
        methodHandle1 = insertArguments(methodHandle1, 1, str);
        assertEquals(1, (int) methodHandle1.invokeExact("foo"));
    }

}