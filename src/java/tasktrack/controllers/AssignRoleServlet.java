package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import tasktrack.utils.DatabaseConnection;

@WebServlet(name = "AssignRoleServlet", urlPatterns = {"/assignRole"})
public class AssignRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement getUser = conn.prepareStatement("SELECT name, email, password FROM user WHERE id = ?");
            getUser.setInt(1, userId);
            ResultSet rs = getUser.executeQuery();

            if (!rs.next()) {
                request.setAttribute("error", "User not found.");
                request.getRequestDispatcher("/admin/assignRole.jsp").forward(request, response);
                return;
            }

            String name = rs.getString("name");
            String oldEmail = rs.getString("email");
            String password = rs.getString("password");

            String newEmail = "admin" + oldEmail;

            PreparedStatement check = conn.prepareStatement("SELECT id FROM user WHERE email = ?");
            check.setString(1, newEmail);
            ResultSet checkRs = check.executeQuery();

            if (checkRs.next()) {
                request.setAttribute("error", "Admin account already exists for this user.");
                request.getRequestDispatcher("/admin/assignRole.jsp").forward(request, response);
                return;
            }

            PreparedStatement insertAdminUser = conn.prepareStatement(
                "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertAdminUser.setString(1, name);
            insertAdminUser.setString(2, newEmail);
            insertAdminUser.setString(3, password);
            insertAdminUser.setString(4, "admin");
            insertAdminUser.executeUpdate();

            ResultSet generatedKeys = insertAdminUser.getGeneratedKeys();
            int newAdminId = -1;
            if (generatedKeys.next()) {
                newAdminId = generatedKeys.getInt(1);
            }

            PreparedStatement insertAdmin = conn.prepareStatement("INSERT INTO admin (id) VALUES (?)");
            insertAdmin.setInt(1, newAdminId);
            insertAdmin.executeUpdate();

            request.setAttribute("message", "Admin account created: " + newEmail);
            request.getRequestDispatcher("/admin/assignRole.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM user WHERE role = 'student'");
            ResultSet rs = ps.executeQuery();

            List<Map<String, Object>> users = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("id"));
                user.put("name", rs.getString("name"));
                users.add(user);
            }

            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/assignRole.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
