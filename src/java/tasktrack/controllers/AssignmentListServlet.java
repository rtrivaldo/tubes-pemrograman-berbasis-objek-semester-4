package tasktrack.controllers;

import tasktrack.utils.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(name = "AssignmentListServlet", urlPatterns = {"/assignmentList"})
public class AssignmentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (id != null && !id.isEmpty()) {
                PreparedStatement ps = conn.prepareStatement("SELECT a.*, c.name AS course_name FROM assignment a JOIN course c ON a.course_id = c.id WHERE a.id = ?");
                ps.setInt(1, Integer.parseInt(id));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Map<String, Object> assignment = new HashMap<>();
                    assignment.put("id", rs.getInt("id"));
                    assignment.put("title", rs.getString("title"));
                    assignment.put("deadline", rs.getDate("deadline"));
                    assignment.put("courseName", rs.getString("course_name"));
                    request.setAttribute("assignment", assignment);
                }
            }

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM course");
            ResultSet courseRs = ps.executeQuery();

            List<Map<String, Object>> courses = new ArrayList<>();
            while (courseRs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("id", courseRs.getInt("id"));
                course.put("name", courseRs.getString("name"));
                courses.add(course);
            }

            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/admin/assignmentForm.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String title = request.getParameter("title");
        String deadline = request.getParameter("deadline");
        String courseId = request.getParameter("courseId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            if ("edit".equals(action)) {
                String id = request.getParameter("id");
                PreparedStatement ps = conn.prepareStatement(
                    "UPDATE assignment SET title = ?, deadline = ?, course_id = ? WHERE id = ?"
                );
                ps.setString(1, title);
                ps.setDate(2, java.sql.Date.valueOf(deadline));
                ps.setInt(3, Integer.parseInt(courseId));
                ps.setInt(4, Integer.parseInt(id));
                ps.executeUpdate();
            } else if ("add".equals(action)) {
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO assignment (title, deadline, course_id) VALUES (?, ?, ?)"
                );
                ps.setString(1, title);
                ps.setDate(2, java.sql.Date.valueOf(deadline));
                ps.setInt(3, Integer.parseInt(courseId));
                ps.executeUpdate();
            }

            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
