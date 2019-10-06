package fr.umlv.java.inside;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Comparator;

import static java.util.stream.Collectors.joining;
//import static java.util.function.Predicate.not;


public class Main {

    /* public static String toJSON(Person person) {
        return "{\n" +
                "  \"firstName\": \"" + person.getFirstName() + "\"\n" +
                "  \"lastName\": \"" + person.getLastName() + "\"\n" +
                "}\n";
    }

    public static String toJSON(Alien alien) {
        return "{\n" +
                "  \"planet\": \"" + alien.getPlanet() + "\"\n" +
                "  \"members\": \"" + alien.getAge() + "\"\n" +
                "}\n";
    } */

    private static String annotationValue(Method s) {
        var value = s.getAnnotation(JSONProperty.class).value();
        return value.isEmpty() ? propertyName(s.getName()) : s.getName();
    }

    private static String propertyName(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    public static String toJSON(Object object) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(method -> method.getName().startsWith("get") && method.isAnnotationPresent(JSONProperty.class))
                //.filter(not(method -> method.getDeclaringClass() == Object.class))
                .sorted(Comparator.comparing(Method::getName))
                .map(method -> annotationValue(method) + " : " + allGetter(object, method))
                .collect(joining(", ", "{", "}"));
    }

    private static Object allGetter(Object o, Method getter) {
        try {
            return getter.invoke(o);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            var cause = e.getCause();
            if(cause instanceof RuntimeException)
                throw (RuntimeException) cause;
            if(cause instanceof Error)
                throw (Error) cause;
            throw new UndeclaredThrowableException(cause);
        }
    }

    public static void main(String[] args) {
        var person = new Person("John", "Doe");
        System.out.println(toJSON(person));
        var alien = new Alien("E.T.", 100);
        System.out.println(toJSON(alien));
    }

}