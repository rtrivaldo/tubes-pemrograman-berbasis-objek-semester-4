package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import tasktrack.utils.DatabaseConnection;

@WebServlet(name = "AdminPanelServlet", urlPatterns = {"/admin"})
public class AdminPanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Map<String, Object>> students = new ArrayList<>();
            List<Map<String, Object>> courses = new ArrayList<>();
            List<Map<String, Object>> assignments = new ArrayList<>();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT u.id, u.name, s.level," +
                "COUNT(DISTINCT ce.course_id) AS courses, " +
                "COUNT(DISTINCT ac.assignment_id) AS assignments_completed, " +
                "GROUP_CONCAT(DISTINCT c.name SEPARATOR ', ') AS course_names " +
                "FROM user u " +
                "JOIN student s ON u.id = s.id " +
                "LEFT JOIN course_enrollments ce ON u.id = ce.student_id " +
                "LEFT JOIN course c ON ce.course_id = c.id " +
                "LEFT JOIN assignment_completions ac ON u.id = ac.student_id " +
                "GROUP BY u.id, u.name, s.level " +
                "ORDER BY assignments_completed ASC"
            );
            while (rs.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("id", rs.getInt("id"));
                student.put("name", rs.getString("name"));
                student.put("level", rs.getInt("level"));
                student.put("courses", rs.getInt("courses"));
                student.put("completed", rs.getInt("assignments_completed"));
                student.put("course_names", rs.getString("course_names"));
                students.add(student);
            }

            rs = stmt.executeQuery("SELECT * FROM course");
            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("id", rs.getInt("id"));
                course.put("name", rs.getString("name"));
                course.put("description", rs.getString("description"));
                courses.add(course);
            }

            rs = stmt.executeQuery("SELECT a.id, a.title, a.deadline, a.course_id, c.name AS course_name " + "FROM assignment a JOIN course c ON a.course_id = c.id");
            while (rs.next()) {
                Map<String, Object> a = new HashMap<>();
                a.put("id", rs.getInt("id"));
                a.put("title", rs.getString("title"));
                a.put("deadline", rs.getDate("deadline"));
                a.put("course_id", rs.getInt("course_id"));
                a.put("course_name", rs.getString("course_name"));
                assignments.add(a);
            }

            request.setAttribute("students", students);
            request.setAttribute("courses", courses);
            request.setAttribute("assignments", assignments);
            request.getRequestDispatcher("/admin/adminPanel.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
