import java.util.Scanner;

public class StudentManagementSystem {
    private static Student[] students = new Student[10];
    private static int studentCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = 0;
        while (choice != 8) {
            printMenu();
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1: addStudent(); break;
                case 2: displayAllStudents(); break;
                case 3: searchStudent(); break;
                case 4: displayStudentResult(); break;
                case 5: updateStudentMarks(); break;
                case 6: displayClassStatistics(); break;
                case 7: displaySubjectWiseMarks(); break;
                case 8: System.out.println("Exiting System. Goodbye!"); break;
                default: System.out.println("Invalid menu choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Display Student Result");
        System.out.println("5. Update Student Marks");
        System.out.println("6. Class Statistics");
        System.out.println("7. Subject-wise Marks");
        System.out.println("8. Exit");
    }

    private static void addStudent() {
        if (studentCount >= students.length) {
            System.out.println("Error: Maximum capacity (10 students) reached!");
            return;
        }

        int id;
        do {
            System.out.print("Enter Student ID: ");
            id = scanner.nextInt();
            scanner.nextLine();
            if (id <= 0) {
                System.out.println("ID must be positive.");
            } else if (findStudentIndexById(id) != -1) {
                System.out.println("Error: Duplicate Student ID detected.");
                id = -1;
            }
        } while (id <= 0);

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        int age;
        do {
            System.out.print("Enter Age: ");
            age = scanner.nextInt();
            scanner.nextLine();
            if (age <= 0 || age > 100) {
                System.out.println("Please enter a reasonable age.");
            }
        } while (age <= 0 || age > 100);

        double sub1 = readValidMarks("Enter Subject 1 Marks: ");
        double sub2 = readValidMarks("Enter Subject 2 Marks: ");
        double sub3 = readValidMarks("Enter Subject 3 Marks: ");

        students[studentCount] = new Student(id, name, age, sub1, sub2, sub3);
        studentCount++;
        System.out.println("Student added successfully.");
    }

    private static double readValidMarks(String prompt) {
        double marks;
        do {
            System.out.print(prompt);
            marks = scanner.nextDouble();
            scanner.nextLine();
            if (marks < 0 || marks > 100) {
                System.out.println("Invalid marks! Must be between 0 and 100.");
            }
        } while (marks < 0 || marks > 100);
        return marks;
    }

    private static void displayAllStudents() {
        if (studentCount == 0) {
            System.out.println("No student records available.");
            return;
        }
        System.out.println("\n--------------------------------------------------------");
        System.out.printf("%-6s %-12s %-6s %-8s %-8s %-8s%n", "ID", "Name", "Age", "Sub1", "Sub2", "Sub3");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < studentCount; i++) {
            students[i].displayStudent();
        }
        System.out.println("--------------------------------------------------------");
    }

    private static void searchStudent() {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        int index = findStudentIndexById(id);
        if (index != -1) {
            System.out.println("\n--------------------------------------------------------");
            System.out.printf("%-6s %-12s %-6s %-8s %-8s %-8s%n", "ID", "Name", "Age", "Sub1", "Sub2", "Sub3");
            System.out.println("--------------------------------------------------------");
            students[index].displayStudent();
            System.out.println("--------------------------------------------------------");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayStudentResult() {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        int index = findStudentIndexById(id);
        if (index == -1) {
            System.out.println("Student not found.");
            return;
        }
        Student s = students[index];
        double total = calculateTotal(s.subject1Marks, s.subject2Marks, s.subject3Marks);
        double avg = calculateAverage(s.subject1Marks, s.subject2Marks, s.subject3Marks);
        String grade = calculateGrade(avg);
        boolean passed = isPassed(s.subject1Marks, s.subject2Marks, s.subject3Marks);

        System.out.println("\n====================================");
        System.out.println("          STUDENT RESULT");
        System.out.println("====================================");
        System.out.println("Student ID     : " + s.studentId);
        System.out.println("Name           : " + s.name);
        System.out.println("Age            : " + s.age);
        System.out.println("\nSubject 1      : " + s.subject1Marks);
        System.out.println("Subject 2      : " + s.subject2Marks);
        System.out.println("Subject 3      : " + s.subject3Marks);
        System.out.println("\nTotal Marks    : " + total);
        System.out.printf("Average        : %.2f%n", avg);
        System.out.printf("Percentage     : %.2f%%%n", avg);
        System.out.println("Grade          : " + grade);
        System.out.println("Status         : " + (passed ? "PASS" : "FAIL"));
        System.out.println("====================================");
    }

    private static void updateStudentMarks() {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        int index = findStudentIndexById(id);
        if (index == -1) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Updating marks for: " + students[index].name);
        students[index].subject1Marks = readValidMarks("Enter new Subject 1 Marks: ");
        students[index].subject2Marks = readValidMarks("Enter new Subject 2 Marks: ");
        students[index].subject3Marks = readValidMarks("Enter new Subject 3 Marks: ");
        System.out.println("Student marks updated successfully.");
    }

    private static void displayClassStatistics() {
        if (studentCount == 0) {
            System.out.println("No statistics available.");
            return;
        }
        double totalClassPercentage = 0;
        double highest = calculateAverage(students[0].subject1Marks, students[0].subject2Marks, students[0].subject3Marks);
        double lowest = highest;
        int passCount = 0;
        int failCount = 0;

        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            double avg = calculateAverage(s.subject1Marks, s.subject2Marks, s.subject3Marks);
            totalClassPercentage += avg;
            if (avg > highest) highest = avg;
            if (avg < lowest) lowest = avg;
            if (isPassed(s.subject1Marks, s.subject2Marks, s.subject3Marks)) {
                passCount++;
            } else {
                failCount++;
            }
        }

        double classAverage = totalClassPercentage / studentCount;
        System.out.println("\n====================================");
        System.out.println("          CLASS STATISTICS");
        System.out.println("====================================");
        System.out.println("Number of Students : " + studentCount);
        System.out.printf("Average Percentage : %.2f%%%n", classAverage);
        System.out.printf("Highest Percentage : %.2f%%%n", highest);
        System.out.printf("Lowest Percentage  : %.2f%%%n", lowest);
        System.out.println("Pass Count         : " + passCount);
        System.out.println("Fail Count         : " + failCount);
        System.out.println("====================================");
    }

    private static void displaySubjectWiseMarks() {
        if (studentCount == 0) {
            System.out.println("No records available.");
            return;
        }
        double[][] marksMatrix = new double[studentCount][3];
        for (int i = 0; i < studentCount; i++) {
            marksMatrix[i][0] = students[i].subject1Marks;
            marksMatrix[i][1] = students[i].subject2Marks;
            marksMatrix[i][2] = students[i].subject3Marks;
        }

        System.out.println("\n--------------------------------------------------");
        System.out.printf("%-8s %-14s %-10s %-10s %-10s%n", "ID", "Name", "Sub1", "Sub2", "Sub3");
        System.out.println("--------------------------------------------------");

        double sub1Total = 0, sub2Total = 0, sub3Total = 0;
        for (int i = 0; i < studentCount; i++) {
            System.out.printf("%-8d %-14s ", students[i].studentId, students[i].name);
            for (int j = 0; j < 3; j++) {
                System.out.printf("%-10.2f ", marksMatrix[i][j]);
            }
            System.out.println();
            sub1Total += marksMatrix[i][0];
            sub2Total += marksMatrix[i][1];
            sub3Total += marksMatrix[i][2];
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Subject 1 Average : %.2f%n", (sub1Total / studentCount));
        System.out.printf("Subject 2 Average : %.2f%n", (sub2Total / studentCount));
        System.out.printf("Subject 3 Average : %.2f%n", (sub3Total / studentCount));
    }

    public static double calculateAverage(int mark1, int mark2) {
        return (double) (mark1 + mark2) / 2;
    }

    public static double calculateAverage(int mark1, int mark2, int mark3) {
        return (double) (mark1 + mark2 + mark3) / 3;
    }

    public static double calculateAverage(double mark1, double mark2, double mark3) {
        return (mark1 + mark2 + mark3) / 3.0;
    }

    public static double calculateTotal(double m1, double m2, double m3) {
        return m1 + m2 + m3;
    }

    public static String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        if (percentage >= 40) return "E";
        return "F";
    }

    public static boolean isPassed(double m1, double m2, double m3) {
        return m1 >= 40 && m2 >= 40 && m3 >= 40;
    }

    private static int findStudentIndexById(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].studentId == id) {
                return i;
            }
        }
        return -1;
    }
}
