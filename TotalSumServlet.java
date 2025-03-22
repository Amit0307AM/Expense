import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TotalSumServlet")
public class TotalSumServlet extends HttpServlet {
    protected void Service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = 1; // Replace with dynamic User ID (from session or request)

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "root";
        String password = "your_password";

        String sql = "SELECT SUM(Amount) AS total_sum, " +
                     "SUM(CASE WHEN Category = 'Food' THEN Amount ELSE 0 END) AS food_sum, " +
                     "SUM(CASE WHEN Category = 'Travel' THEN Amount ELSE 0 END) AS travel_sum, " +
                     "SUM(CASE WHEN Category = 'Utilities' THEN Amount ELSE 0 END) AS utilities_sum, " +
                     "SUM(CASE WHEN Category = 'Other' THEN Amount ELSE 0 END) AS other_sum " +
                     "FROM expenses WHERE UserID = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("total_sum", rs.getDouble("total_sum"));
                request.setAttribute("food_sum", rs.getDouble("food_sum"));
                request.setAttribute("travel_sum", rs.getDouble("travel_sum"));
                request.setAttribute("utilities_sum", rs.getDouble("utilities_sum"));
                request.setAttribute("other_sum", rs.getDouble("other_sum"));
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("report.jsp");
        dispatcher.forward(request, response);
    }
}
