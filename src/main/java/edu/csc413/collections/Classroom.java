/*
Created by Joseph Edradan
Github: https://github.com/josephedradan

Date created: 2/6/2021

Purpose:
    Do assignment 1

Details:

Description:
    You got a classroom and some students trying to enroll into a class

Notes:
    registerStudent should be part of the School class

IMPORTANT NOTES:

Explanation:

Reference:
    Interface Queue<E>
        https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html

    Class LinkedList<E>
        https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html

    Class HashSet<E>
        https://docs.oracle.com/javase/7/docs/api/java/util/HashSet.html

    Cannot instantiate the type Queue. Why is this?
        https://stackoverflow.com/questions/28641605/cannot-instantiate-the-type-queue-why-is-this/28641802

    What is the best data structure to implement a queue? [closed]
        https://stackoverflow.com/questions/30516897/what-is-the-best-data-structure-to-implement-a-queue

    Best implementation of Java Queue?
        https://stackoverflow.com/questions/11149707/best-implementation-of-java-queue
*/
package edu.csc413.collections;

import java.util.*;
import java.util.stream.Collectors;

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

        registeredStudents = new HashMap<>();
        enrolledIds = new HashSet<>();
        waitlistIds = new LinkedList<>();

    }

    public void registerStudent(Student student) {
        // TODO: Implement. The student should be registered, but not enrolled in the class or added to the waitlist.

        // Add students ot HashMap
        registeredStudents.put(student.getId(), student);
        System.out.printf("Student %s with id %s was added to the registration.%n",
                student.getName(),
                student.getId());

    }

    public void enrollStudent(int id) {
        // TODO: Implement. The student with the provided ID should be added the enrolled students set if there is
        //       capacity. If there is not, but there is capacity in the waitlist, the student should be added to that
        //       instead. If there is no capacity in the enrollment set or the waitlist, the request can be ignored.


        // Check if there is available space to add students to the enrolled list
        if (enrolledIds.size() < CLASS_CAPACITY) {
            enrolledIds.add(id);
            System.out.printf("Student %s was added to the enroll list.%n", registeredStudents.get(id).getName());

        }
        // Check if there is available space to add students to the waitlist
        else if (waitlistIds.size() < WAIT_LIST_CAPACITY) {
            waitlistIds.add(id);
            System.out.printf("Student %s was added to the waitlist.%n", registeredStudents.get(id).getName());
        }
        // Deny students to be added to either enrolled or waitlisted
        else {
            System.out.printf("Student %s was unable to be enrolled/waitlisted.%n", registeredStudents.get(id).getName());
        }

    }

    public void dropStudent(int id) {
        // TODO: Implement. Attempt to remove the student with the provided ID from the enrolled students set. If the
        //       student was removed, backfill the enrolled students set with a student from the waitlist.

        // Catch exceptions when removing from an empty queue specifically from remove()
        try {

            // Remove student from enrolled list
            boolean result = enrolledIds.remove(id);

            // If the removal of the student from the enrolled list was successful
            if (result) {
                System.out.printf("Student %s was removed from the enrolled list.%n", registeredStudents.get(id).getName());

                // Id taken from the waitlist
                int waitlistIdTemp = waitlistIds.remove();

                System.out.printf("Student %s was removed from the waitlist and added to the enroll list.%n", registeredStudents.get(waitlistIdTemp).getName());

                // Add the head of the queue from the waitlist to the enrolled list
                enrolledIds.add(waitlistIdTemp);
            }
            // Handle Student was never enrolled
            else {
                System.out.printf("Student %s was never enrolled!%n", registeredStudents.get(id).getName());
            }
        }
        // Handle Exceptions on removing from an empty queue
        catch (NoSuchElementException e) {
            System.out.println("Enrolled list is empty! No student Id was added to the enrolled list automatically!");
            // System.out.println(e);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getEnrolledStudents() {
        // TODO: Implement. Return the names of all students that are enrolled in an ArrayList<Student>.

        // OPTIMIZE: Is this even that fast?
        // Stream the enrolled list then map then collect
        List<String> studentsNameList = enrolledIds.stream()
                .map(e -> registeredStudents.get(e).getName())
                .sorted()
                .collect(Collectors.toList());

        return (ArrayList<String>) studentsNameList;
    }

    public ArrayList<String> getWaitlistedStudents() {
        // TODO: Implement. Return the names of all of the students that are in the waitlist in an ArrayList<Student>.
        //       They should be in the same order that they are in the waitlist.

//        // OPTIMIZE: Is this even that fast?
//        // Stream the waitlist then map then collect
//        List<String> studentsNameList = waitlistIds.stream()
//                .map(e -> registeredStudents.get(e).getName())
//                .collect(Collectors.toList());
//
//        return (ArrayList<String>) studentsNameList;

        Stack<Integer> idStack = new Stack<>();
        ArrayList<String> studentNameArrayList = new ArrayList<>();

        //  Loop through waitlistIds (Non-modifying iterable)
        for (int e : waitlistIds) {
            // Add element to stack
            idStack.push(e);
        }

        // Loop through idStack (Body/Scope will modifying the iterable/Collection)
        while (!idStack.empty()) {

            // Pop id from stack and use the hashmap to get the name based on the id
            studentNameArrayList.add(registeredStudents.get(idStack.pop()).getName());
        }

        // Return the studentNameArrayList
        return studentNameArrayList;
    }

    public static void main(String[] args) {
        Classroom classroom = new Classroom();
        if (NAMES.length != IDS.length) {
            throw new RuntimeException("Oops! The NAMES and IDS arrays don't match. Did they get modified?");
        }
        System.out.println();

        // Register all of the students defined by NAMES and IDS below.
        for (int i = 0; i < NAMES.length; i++) {
            classroom.registerStudent(new Student(NAMES[i], IDS[i]));
        }
        System.out.println();

        // Attempt to enroll all students. This will go in alphabetical order by student name.
        for (int i = 0; i < IDS.length; i++) {
            classroom.enrollStudent(IDS[i]);
        }
        System.out.println();

        // Attempt to drop a few students from the class, and re-enroll one.
        classroom.dropStudent(IDS[4]);   // Eli
        classroom.dropStudent(IDS[17]);  // Rupert (not enrolled)
        classroom.dropStudent(IDS[10]);  // Klay
        classroom.enrollStudent(IDS[4]);
        System.out.println();

        // Print out all enrolled students.
        System.out.println("Enrolled students:");
        for (String studentName : classroom.getEnrolledStudents()) {
            System.out.println(studentName);
        }
        System.out.println();

        // Print out all enrolled students.
        System.out.println("Waitlist:");
        for (String studentName : classroom.getWaitlistedStudents()) {
            System.out.println(studentName);
        }
        System.out.println();
    }

    // List of names and IDs used to generate Student data in main.
    private static final String[] NAMES = {
            "Alice", "Buster", "Carol", "Davante", "Eli", "Fiona", "Gob", "Harold", "Ian", "Jesse", "Klay", "Lindsay",
            "Maebe", "Nelly", "Oscar", "Parmesan", "Queen Latifah", "Rupert", "Serena", "Tobias", "Uma", "Viggo",
            "Wyatt", "Xavier", "Yoda", "Zoey",
    };
    private static final int[] IDS = {
            200, 201, 202, 203, 199, 198, 197, 147, 148, 149, 150, 151, 276,
            275, 274, 273, 272, 233, 234, 235, 236, 237, 172, 171, 170, 169,
    };
}
