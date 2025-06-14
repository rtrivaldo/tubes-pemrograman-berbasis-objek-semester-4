package tasktrack.controllers;

import tasktrack.utils.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "AddAssignmentServlet", urlPatterns = {"/addAssignment"})
public class AddAssignmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String deadline = request.getParameter("deadline");
        String courseId = request.getParameter("courseId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO assignment (title, deadline, course_id) VALUES (?, ?, ?)"
            );
            ps.setString(1, title);
            ps.setDate(2, Date.valueOf(deadline));
            ps.setInt(3, Integer.parseInt(courseId));
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
