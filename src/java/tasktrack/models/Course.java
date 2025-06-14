package tasktrack.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String name;
    private String description;
    private List<Student> enrolledStudents;
    private List<Assignment> assignments;

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enrolledStudents = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public void assignStudent(Student student) {
        enrolledStudents.add(student);
        student.getCourses().add(this);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public String getName() {
        return name;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
}
