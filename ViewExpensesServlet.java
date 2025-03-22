import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ViewExpensesServlet
 */
@WebServlet("/ViewExpensesServlet")
public class ViewExpensesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewExpensesServlet() {
        super();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        // Retrieve logged-in user's ID from the session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            pw.println("You must be logged in to view expenses.");
            return;
        }
        int loggedInUserID = (int) session.getAttribute("userID");

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String category = request.getParameter("category");

        Connection con = null;
        PreparedStatement pt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "radheradhe");
            if(!category.equals("All Categories")) {
            // Build SQL query dynamically
            StringBuilder query = new StringBuilder("SELECT * FROM expenses WHERE userID = ?");
            if (startDate != null && !startDate.isEmpty()) {
                query.append(" AND date >= ?");
            }
            if (endDate != null && !endDate.isEmpty()) {
                query.append(" AND date <= ?");
            }
            if (category != null && !category.isEmpty()) {
                query.append(" AND category = ?");
            }

            pt = con.prepareStatement(query.toString());
            pt.setInt(1, loggedInUserID); // Filter by logged-in user's ID

            int paramIndex = 2;
            if (startDate != null && !startDate.isEmpty()) {
                pt.setString(paramIndex++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                pt.setString(paramIndex++, endDate);
            }
            if (category != null && !category.isEmpty()) {
                pt.setString(paramIndex++, category);
            }

            // Execute query and display results
            rs = pt.executeQuery();
            pw.println("<table border='1'>");
            pw.println("<tr><th>Expense ID</th><th>User ID</th><th>Amount</th><th>Date</th><th>Category</th><th>Notes</th></tr>");
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt("expenseID") + "</td>");
                pw.println("<td>" + rs.getInt("userID") + "</td>");
                pw.println("<td>" + rs.getInt("amount") + "</td>");
                pw.println("<td>" + rs.getDate("date") + "</td>");
                pw.println("<td>" + rs.getString("category") + "</td>");
                pw.println("<td>" + rs.getString("notes") + "</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        }
        else
        {
        	StringBuilder query = new StringBuilder("SELECT * FROM expenses WHERE userID = ?");
            if (startDate != null && !startDate.isEmpty()) {
                query.append(" AND date >= ?");
            }
            if (endDate != null && !endDate.isEmpty()) {
                query.append(" AND date <= ?");
            }


            pt = con.prepareStatement(query.toString());
            pt.setInt(1, loggedInUserID); // Filter by logged-in user's ID

            int paramIndex = 2;
            if (startDate != null && !startDate.isEmpty()) {
                pt.setString(paramIndex++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                pt.setString(paramIndex++, endDate);
            }


            // Execute query and display results
            rs = pt.executeQuery();
            pw.println("<table border='1'>");
            pw.println("<tr><th>Expense ID</th><th>User ID</th><th>Amount</th><th>Date</th><th>Category</th><th>Notes</th></tr>");
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt("expenseID") + "</td>");
                pw.println("<td>" + rs.getInt("userID") + "</td>");
                pw.println("<td>" + rs.getInt("amount") + "</td>");
                pw.println("<td>" + rs.getDate("date") + "</td>");
                pw.println("<td>" + rs.getString("category") + "</td>");
                pw.println("<td>" + rs.getString("notes") + "</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        }
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("An error occurred while fetching the expenses.");
        } finally {
            try {
                if (rs != null) {
					rs.close();
				}
                if (pt != null) {
					pt.close();
				}
                if (con != null) {
					con.close();
				}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
