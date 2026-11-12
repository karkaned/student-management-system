public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private int age;
    private String course;

    public Student(String studentId, String firstName, String lastName, int age, String course) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.course = course;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return studentId + "," + firstName + "," + lastName + "," + age + "," + course;
    }

    public String toDisplayString() {
        return "ID: " + studentId + " | Name: " + firstName + " " + lastName + " | Age: " + age + " | Course: " + course;
    }
}
