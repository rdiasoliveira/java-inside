package fr.umlv.java.inside;

import java.util.Objects;

public class Alien {

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
