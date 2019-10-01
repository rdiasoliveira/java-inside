package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    public static class Alien {

        private final String planet;
        private final int age;

        public Alien(String planet, int age) {
            if (age <= 0) {
                throw new IllegalArgumentException("Too young...");
            }
            this.planet = Objects.requireNonNull(planet);
            this.age = age;
        }

        public String getPlanet() {
            return planet;
        }

        public int getAge() {
            return age;
        }

    }

    public static class Person {

        private final String firstName;
        private final String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = Objects.requireNonNull(firstName);
            this.lastName = Objects.requireNonNull(lastName);
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

    }

    @Test
    void toJSON() {
        assertEquals("{firstName : John, lastName : Doe}",
                Main.toJSON(new Person("John", "Doe")));
    }

    @Test
    void toJSON2() {
        assertEquals("{age : 100, planet : E.T.}",
                Main.toJSON(new Alien("E.T.", 100)));
    }

}