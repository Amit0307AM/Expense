

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int userid;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter pw=response.getWriter();
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null)
        {
            pw.println("You must be logged in to view expenses.");
            return;
        }
        int loggedInUserID = (int) session.getAttribute("userID");
        userid=loggedInUserID;
        try {
            // **Database Connection**
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "radheradhe");

            // **Query for Total Expense**
            String totalQuery = "SELECT SUM(Amount) FROM expenses where UserId=?";
            stmt = conn.prepareStatement(totalQuery);
            stmt.setInt(1, userid);
            rs = stmt.executeQuery();
            double totalSum = rs.next() ? rs.getDouble(1) : 0.0;

            // **Query for Category-Wise Sums**
            String categoryQuery = "SELECT Category, SUM(Amount) FROM expenses where UserId='"+userid+"' GROUP BY Category";
            stmt = conn.prepareStatement(categoryQuery);
            rs = stmt.executeQuery();

            double foodSum = 0, travelSum = 0, utilitiesSum = 0, otherSum = 0;

            while (rs.next()) {
                String category = rs.getString(1);
                double sum = rs.getDouble(2);

                switch (category.toLowerCase()) {
                    case "food": foodSum = sum; break;
                    case "travel": travelSum = sum; break;
                    case "utilities": utilitiesSum = sum; break;
                    default: otherSum += sum;
                }
            }

            // **Send Data to JSP**
            request.setAttribute("total_sum", totalSum);
            request.setAttribute("food_sum", foodSum);
            request.setAttribute("travel_sum", travelSum);
            request.setAttribute("utilities_sum", utilitiesSum);
            request.setAttribute("other_sum", otherSum);

            RequestDispatcher dispatcher = request.getRequestDispatcher("Report.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}