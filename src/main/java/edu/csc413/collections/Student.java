package edu.csc413.collections;

/**
 * Represents a student that can be registered for, enrolled in, and dropped from the class. For the sake of simplicity,
 * a student consists of just their name and ID.
 */
public class Student {
    private final String name;
    private final int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
