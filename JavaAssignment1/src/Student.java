public class Student {
    int studentId;
    String name;
    int age;
    double subject1Marks;
    double subject2Marks;
    double subject3Marks;

    public Student(int studentId, String name, int age, double subject1Marks, double subject2Marks, double subject3Marks) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.subject1Marks = subject1Marks;
        this.subject2Marks = subject2Marks;
        this.subject3Marks = subject3Marks;
    }

    public void displayStudent() {
        System.out.printf("%-6d %-12s %-6d %-8.1f %-8.1f %-8.1f%n", 
            studentId, name, age, subject1Marks, subject2Marks, subject3Marks);
    }
}
