package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import tasktrack.utils.DatabaseConnection;

@WebServlet(name = "AddCourseServlet", urlPatterns = {"/addCourse"})
public class AddCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String desc = request.getParameter("description");

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO course (name, description) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/admin");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
