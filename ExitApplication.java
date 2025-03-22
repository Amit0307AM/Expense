import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ExitApplication")
public class ExitApplication extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Step 1: Log out the user
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Step 2: Close the database connection safely
        Object connObject = getServletContext().getAttribute("DBConnection");
        if (connObject instanceof Connection) {
            Connection conn = (Connection) connObject;
            try {
                conn.close();
                getServletContext().removeAttribute("DBConnection");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Step 3: Redirect to the login page with a message
        response.sendRedirect("LoginPage.html");
    }
}
