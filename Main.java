import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "students.txt";
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();

        int choice;
        do {
            System.out.println("\n=== Student Management System - Phase 2 ===");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student details");
            System.out.println("5. Delete student record");
            System.out.println("6. Sort students by Last Name");
            System.out.println("7. Sort students by ID");
            System.out.println("8. Exit");
            System.out.print("Choose an option (1-8): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    sortStudentsByLastName();
                    break;
                case 7:
                    sortStudentsById();
                    break;
                case 8:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 8.");
            }
        } while (choice != 8);
    }

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                System.out.println("Error: Student with this ID already exists!");
                return;
            }
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid age format.");
            return;
        }

        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        students.add(new Student(id, firstName, lastName, age, course));
        saveToFile();
        System.out.println("Student successfully added and saved!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("Student list is empty.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println(s.toDisplayString());
        }
    }

    private static void searchStudent() {
        System.out.print("Enter ID to search: ");
        String id = scanner.nextLine();

        try {
            Student found = findStudentById(id);
            System.out.println("Student found:\n" + found.toDisplayString());
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Student findStudentById(String id) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Student with ID " + id + " not found.");
    }

    private static void updateStudent() {
        System.out.print("Enter ID of the student to update: ");
        String id = scanner.nextLine();

        try {
            Student s = findStudentById(id);

            System.out.print("Enter new first name (" + s.getFirstName() + "): ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty())
                s.setFirstName(firstName);

            System.out.print("Enter new last name (" + s.getLastName() + "): ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty())
                s.setLastName(lastName);

            System.out.print("Enter new age (" + s.getAge() + "): ");
            String ageStr = scanner.nextLine();
            if (!ageStr.isEmpty()) {
                try {
                    s.setAge(Integer.parseInt(ageStr));
                } catch (NumberFormatException ignored) {
                }
            }

            System.out.print("Enter new course (" + s.getCourse() + "): ");
            String course = scanner.nextLine();
            if (!course.isEmpty())
                s.setCourse(course);

            saveToFile();
            System.out.println("Student details updated successfully!");

        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter ID of the student to delete: ");
        String id = scanner.nextLine();

        try {
            Student s = findStudentById(id);
            students.remove(s);
            saveToFile();
            System.out.println("Student deleted successfully!");
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void sortStudentsByLastName() {
        if (students.isEmpty()) {
            System.out.println("Student list is empty.");
            return;
        }
        students.sort(Comparator.comparing(Student::getLastName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("\n--- Students Sorted by Last Name ---");
        viewStudents();
    }

    private static void sortStudentsById() {
        if (students.isEmpty()) {
            System.out.println("Student list is empty.");
            return;
        }
        students.sort(Comparator.comparing(Student::getStudentId));
        System.out.println("\n--- Students Sorted by ID ---");
        viewStudents();
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String firstName = parts[1];
                    String lastName = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    String course = parts[4];
                    students.add(new Student(id, firstName, lastName, age, course));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
