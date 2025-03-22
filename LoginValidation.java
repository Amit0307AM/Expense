import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginValidation")
public class LoginValidation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginValidation() {
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "radheradhe");
            PreparedStatement pst = con.prepareStatement("SELECT UserID, email , Password FROM users WHERE email = ? AND Password = ?");
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Retrieve user details from the database
                int userId = rs.getInt("UserID"); // Assuming there's an "id" column


                // Store user details in the session
                HttpSession session = request.getSession();
                session.setAttribute("userID", userId);


                // Redirect to the homepage
                response.sendRedirect("Homepage.html");
            } else {
                // Invalid credentials
            	//response.sendRedirect("LoginPage.html");
                request.getRequestDispatcher("LoginPage.html").include(request, response);
            	pw.print("Invalid username or password!");
            }

            con.close();
        } catch (Exception e) {
            pw.print("Error: " + e.getMessage());
        }
    }
}
