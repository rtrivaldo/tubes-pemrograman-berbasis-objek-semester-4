package tasktrack.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import tasktrack.models.User;
import tasktrack.services.AuthenticationService;
import tasktrack.exceptions.AuthenticationException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private AuthenticationService authService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        authService = new AuthenticationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = authService.login(email, password);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user instanceof tasktrack.models.Student ? "student" : "admin");

            if (user instanceof tasktrack.models.Student) {
                response.sendRedirect(request.getContextPath() + "/student");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin");
            }
        } catch (AuthenticationException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }
}
