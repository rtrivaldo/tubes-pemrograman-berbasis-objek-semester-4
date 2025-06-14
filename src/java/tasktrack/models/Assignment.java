package tasktrack.models;

import java.util.Date;

public class Assignment {
    private int id;
    private String title;
    private Date deadline;
    private String status;
    private Course course;

    public Assignment(int id, String title, Date deadline, Course course) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.course = course;
        this.status = "Pending";
    }

    public void markAsDone() {
        this.status = "Done";
    }

    public boolean isLate() {
        return new Date().after(deadline);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public Course getCourse() {
        return course;
    }

    public int getCourseId() {
        return course != null ? course.getId() : 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
