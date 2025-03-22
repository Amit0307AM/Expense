

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ExpenseValidation")
public class ExpenseValidation extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Connection con=null;
	int userid;
	PreparedStatement pt=null;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	 PrintWriter pw=response.getWriter();
	 String amt=request.getParameter("amount");
	 String date1=request.getParameter("date");
	 String category=request.getParameter("category");
	 String note=request.getParameter("notes");
	Date date=Date.valueOf(date1);
	double amount=Double.parseDouble(amt);
	try

	{
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","radheradhe");



	    HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null)
        {
            pw.println("You must be logged in to view expenses.");
            return;
        }
        int loggedInUserID = (int) session.getAttribute("userID");

	    userid=loggedInUserID;

	    	String query="insert into expenses (UserID,Amount,Date,Category,Notes)  values('"+userid+"','"+amount+"','"+date+"','"+category+"','"+note+"')";

	    	pt=con.prepareStatement(query);

		pt.executeUpdate();
		response.sendRedirect("ExpenseAdded.html");
		pt.close();

	}
	catch (SQLException e)
	{
			pw.print(e);

	}

	finally {
		try
		{
			if(con!=null)
			{
				con.close();
			}
		}
		 catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	}
