package tasktrack.controllers;

import tasktrack.utils.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "EditAssignmentServlet", urlPatterns = {"/editAssignment"})
public class EditAssignmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String deadline = request.getParameter("deadline");
        String courseId = request.getParameter("courseId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE assignment SET title = ?, deadline = ?, course_id = ? WHERE id = ?"
            );
            ps.setString(1, title);
            ps.setDate(2, Date.valueOf(deadline));
            ps.setInt(3, Integer.parseInt(courseId));
            ps.setInt(4, Integer.parseInt(id));
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
