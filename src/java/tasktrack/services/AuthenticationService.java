package tasktrack.services;

import tasktrack.models.*;
import tasktrack.exceptions.AuthenticationException;
import tasktrack.utils.DatabaseConnection;
import java.sql.*;

public class AuthenticationService {
    public User login(String email, String password) throws AuthenticationException {
        String query = "SELECT * FROM user WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new AuthenticationException("Email tidak ditemukan!");
            }

            String storedPassword = rs.getString("password");
            if (!storedPassword.equals(password)) {
                throw new AuthenticationException("Password salah!");
            }

            int id = rs.getInt("id");
            String name = rs.getString("name");
            String role = rs.getString("role");

            if ("student".equalsIgnoreCase(role)) {
                return loadStudent(conn, id, name, email, password);
            } else if ("admin".equalsIgnoreCase(role)) {
                return new Admin(id, name, email, password);
            } else {
                throw new AuthenticationException("Role tidak dikenal.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException("Terjadi kesalahan koneksi database.");
        }
    }

    public void register(Student student) throws AuthenticationException {
        String checkQuery = "SELECT id FROM user WHERE email = ?";
        String insertUser = "INSERT INTO user(name, email, password, role) VALUES (?, ?, ?, 'student')";
        String insertStudent = "INSERT INTO student(id, level, assignments_completed) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, student.getEmail());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    throw new AuthenticationException("Email sudah terdaftar!");
                }
            }

            int userId;
            try (PreparedStatement userStmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, student.getName());
                userStmt.setString(2, student.getEmail());
                userStmt.setString(3, student.getPassword());
                userStmt.executeUpdate();

                ResultSet keys = userStmt.getGeneratedKeys();
                if (keys.next()) {
                    userId = keys.getInt(1);
                } else {
                    throw new AuthenticationException("Gagal mendapatkan ID user.");
                }
            }

            try (PreparedStatement studentStmt = conn.prepareStatement(insertStudent)) {
                studentStmt.setInt(1, userId);
                studentStmt.setInt(2, 1);
                studentStmt.setInt(3, 0);
                studentStmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Registrasi berhasil untuk " + student.getName());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException("Gagal registrasi: " + e.getMessage());
        }
    }

    private Student loadStudent(Connection conn, int id, String name, String email, String password) throws SQLException {
        String query = "SELECT level, assignments_completed FROM student WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(id, name, email, password);
                return student;
            } else {
                throw new SQLException("Data student tidak ditemukan.");
            }
        }
    }
}
