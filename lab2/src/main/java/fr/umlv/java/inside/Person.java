package fr.umlv.java.inside;

import java.util.Objects;

public class Person {

    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
    }

    @JSONProperty
    public String getFirstName() {
        return firstName;
    }

    @JSONProperty
    public String getLastName() {
        return lastName;
    }

}
