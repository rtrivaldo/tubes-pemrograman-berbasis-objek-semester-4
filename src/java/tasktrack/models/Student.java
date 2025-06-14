package tasktrack.models;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private int level;
    private int assignmentsCompleted;
    private List<Course> courses;
    private ToDoList todoList;

    public Student(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = 1;
        this.assignmentsCompleted = 0;
        this.courses = new ArrayList<>();
        this.todoList = new ToDoList(this);
    }

    public void checkProgress() {
        System.out.println(name + " telah menyelesaikan " + assignmentsCompleted + " tugas.");
    }

    public void levelUp() {
        if (assignmentsCompleted >= level * 5) {
            level++;
            System.out.println(name + " naik ke level " + level);
        }
    }

    public void completeAssignment() {
        assignmentsCompleted++;
        levelUp();
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean login() {
        System.out.println(name + " login sebagai Student");
        return true;
    }

    public ToDoList getTodoList() {
        return todoList;
    }

    public int getId() {
       return id;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAssignmentsCompleted() {
        return assignmentsCompleted;
    }

    public void setAssignmentsCompleted(int completed) {
        this.assignmentsCompleted = completed;
    }
}
