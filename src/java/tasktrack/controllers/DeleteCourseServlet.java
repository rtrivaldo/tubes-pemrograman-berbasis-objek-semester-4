package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import tasktrack.utils.DatabaseConnection;

@WebServlet(name = "DeleteCourseServlet", urlPatterns = {"/deleteCourse"})
public class DeleteCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM course WHERE id = ?");
            delete.setInt(1, Integer.parseInt(id));
            delete.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/admin");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
