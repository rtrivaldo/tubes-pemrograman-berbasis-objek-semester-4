package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import tasktrack.models.*;
import tasktrack.utils.DatabaseConnection;

@WebServlet(name = "EnrollCourseServlet", urlPatterns = {"/enroll"})
public class EnrollCourseServlet extends HttpServlet {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");

        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int studentId = student.getId();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM course WHERE id NOT IN (SELECT course_id FROM course_enrollments WHERE student_id = ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, studentId);
                ResultSet rs = stmt.executeQuery();

                java.util.List<java.util.Map<String, Object>> courses = new java.util.ArrayList<>();
                while (rs.next()) {
                    java.util.Map<String, Object> course = new java.util.HashMap<>();
                    course.put("id", rs.getInt("id"));
                    course.put("name", rs.getString("name"));
                    course.put("description", rs.getString("description"));
                    courses.add(course);
                }

                request.setAttribute("courses", courses);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/student/enrollCourse.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");

        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int studentId = student.getId();
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO course_enrollments (student_id, course_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, studentId);
                stmt.setInt(2, courseId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/student");
    }
}
