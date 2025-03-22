

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Hey
 */
@WebServlet("/Hey")
public class Hey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hey() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Step 1: Log out the user
	        jakarta.servlet.http.HttpSession session = request.getSession(false);
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
	        response.sendRedirect("LoginPage.html?message=LoggedOut");
	    }

}
