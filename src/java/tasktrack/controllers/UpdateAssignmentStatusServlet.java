package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import tasktrack.models.Student;
import java.util.Date;
import tasktrack.utils.DatabaseConnection;

@WebServlet(name = "UpdateAssignmentStatusServlet", urlPatterns = {"/updateAssignmentStatus"})
public class UpdateAssignmentStatusServlet extends HttpServlet {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        String newStatus = request.getParameter("status");

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");
        int studentId = student.getId();

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            boolean alreadyCompleted = false;
            Date deadline = null;

            try (PreparedStatement check = conn.prepareStatement("""
                SELECT a.deadline, ac.completed_at
                FROM assignment a
                LEFT JOIN assignment_completions ac ON a.id = ac.assignment_id AND ac.student_id = ?
                WHERE a.id = ?
            """)) {
                check.setInt(1, studentId);
                check.setInt(2, assignmentId);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        deadline = rs.getDate("deadline");
                        alreadyCompleted = rs.getTimestamp("completed_at") != null;
                    }
                }
            }

            int level = student.getLevel();
            int completed = student.getAssignmentsCompleted();

            if (newStatus.equalsIgnoreCase("Done") && !alreadyCompleted) {
                try (PreparedStatement insert = conn.prepareStatement("""
                    INSERT INTO assignment_completions (student_id, assignment_id, completed_at)
                    VALUES (?, ?, NOW())
                """)) {
                    insert.setInt(1, studentId);
                    insert.setInt(2, assignmentId);
                    insert.executeUpdate();
                }

                completed++;
                if (completed >= level * 5) {
                    level++;
                }

            } else if (newStatus.equalsIgnoreCase("Pending") && alreadyCompleted) {
                try (PreparedStatement delete = conn.prepareStatement("""
                    DELETE FROM assignment_completions WHERE student_id = ? AND assignment_id = ?
                """)) {
                    delete.setInt(1, studentId);
                    delete.setInt(2, assignmentId);
                    delete.executeUpdate();
                }

                completed = Math.max(0, completed - 1);
                if (completed < (level - 1) * 5 && level > 1) {
                    level--;
                }
            }

            student.setAssignmentsCompleted(completed);
            student.setLevel(level);
            
            try (PreparedStatement update = conn.prepareStatement("""
                UPDATE student SET level = ?, assignments_completed = ? WHERE id = ?
            """)) {
                update.setInt(1, level);
                update.setInt(2, completed);
                update.setInt(3, studentId);
                update.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/student");
    }
}
