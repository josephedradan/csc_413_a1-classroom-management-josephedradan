package edu.csc413.collections;

import java.util.*;

public class Classroom {
    // Constants. You can refer to these anywhere within the Classroom class.
    private static final int CLASS_CAPACITY = 15;
    private static final int WAIT_LIST_CAPACITY = 5;

    // Instance variables.
    private HashMap<Integer, Student> registeredStudents;
    private HashSet<Integer> enrolledIds;
    private Queue<Integer> waitlistIds;

    public Classroom() {
        // TODO: Implement. Initialize any instance variables here.
    }

    public void registerStudent(Student student) {
        // TODO: Implement. The student should be registered, but not enrolled in the class or added to the waitlist.
    }

    public void enrollStudent(int id) {
        // TODO: Implement. The student with the provided ID should be added the enrolled students set if there is
        //       capacity. If there is not, but there is capacity in the waitlist, the student should be added to that
        //       instead. If there is no capacity in the enrollment set or the waitlist, the request can be ignored.
    }

    public void dropStudent(int id) {
        // TODO: Implement. Attempt to remove the student with the provided ID from the enrolled students set. If the
        //       student was removed, backfill the enrolled students set with a student from the waitlist.
    }

    public ArrayList<String> getEnrolledStudents() {
        // TODO: Implement. Return the names of all students that are enrolled in an ArrayList<Student>.
        return new ArrayList<>();
    }

    public ArrayList<String> getWaitlistedStudents() {
        // TODO: Implement. Return the names of all of the students that are in the waitlist in an ArrayList<Student>.
        //       They should be in the same order that they are in the waitlist.
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        Classroom classroom = new Classroom();
        if (NAMES.length != IDS.length) {
            throw new RuntimeException("Oops! The NAMES and IDS arrays don't match. Did they get modified?");
        }

        // Register all of the students defined by NAMES and IDS below.
        for (int i = 0; i < NAMES.length; i++) {
            classroom.registerStudent(new Student(NAMES[i], IDS[i]));
        }

        // Attempt to enroll all students. This will go in alphabetical order by student name.
        for (int i = 0; i < IDS.length; i++) {
            classroom.enrollStudent(IDS[i]);
        }

        // Attempt to drop a few students from the class, and re-enroll one.
        classroom.dropStudent(IDS[4]);   // Eli
        classroom.dropStudent(IDS[17]);  // Rupert (not enrolled)
        classroom.dropStudent(IDS[10]);  // Klay
        classroom.enrollStudent(IDS[4]);

        // Print out all enrolled students.
        System.out.println("Enrolled students:");
        for (String studentName: classroom.getEnrolledStudents()) {
            System.out.println(studentName);
        }
        System.out.println();

        // Print out all enrolled students.
        System.out.println("Waitlist:");
        for (String studentName: classroom.getWaitlistedStudents()) {
            System.out.println(studentName);
        }
        System.out.println();
    }

    // List of names and IDs used to generate Student data in main.
    private static final String[] NAMES = {
            "Alice", "Buster", "Carol", "Davante", "Eli", "Fiona", "Gob", "Harold", "Ian", "Jesse", "Klay", "Lindsay",
            "Maebe", "Nelly", "Oscar", "Parmesan", "Queen Latifa", "Rupert", "Serena", "Tobias", "Uma", "Viggo",
            "Wyatt", "Xavier", "Yoda", "Zoey",
    };
    private static final int[] IDS = {
            200, 201, 202, 203, 199, 198, 197, 147, 148, 149, 150, 151, 276,
            275, 274, 273, 272, 233, 234, 235, 236, 237, 172, 171, 170, 169,
    };
}
