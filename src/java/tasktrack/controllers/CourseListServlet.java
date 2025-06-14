package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import tasktrack.models.Course;
import tasktrack.utils.DatabaseConnection;


@WebServlet(name = "CourseListServlet", urlPatterns = {"/courseList"})
public class CourseListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (id != null) {
                PreparedStatement getCourse = conn.prepareStatement("SELECT * FROM course WHERE id = ?");
                getCourse.setInt(1, Integer.parseInt(id));
                ResultSet rs = getCourse.executeQuery();
                if (rs.next()) {
                    Course course = new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    request.setAttribute("course", course);
                }
            }

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM course");
            ResultSet rs = ps.executeQuery();
            request.setAttribute("courses", rs);
            request.getRequestDispatcher("/admin/courseForm.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
