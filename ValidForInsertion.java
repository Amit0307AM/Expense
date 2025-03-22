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

@SuppressWarnings("serial")
@WebServlet("/ValidForInsertion")
public class ValidForInsertion extends HttpServlet
{
	String username;
	String email;
	String password;
	String password1;


	String user;
	String em;
	String pass;
	String pass1;
	int id;
	int ID;
	int UserID;
	Connection con=null;
	 PreparedStatement pt1;
	 PreparedStatement pt;
	 PreparedStatement pt3;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter pw=response.getWriter();
		email=request.getParameter("email");
		username=request.getParameter("username");
		password=request.getParameter("password");
		password1=request.getParameter("password1");

		try
		{
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","radheradhe");
		    pt=con.prepareStatement("select * from Users");
			ResultSet rs=pt.executeQuery();
			while(rs.next())
			{
				if(email.equals(rs.getString(4)))
				{
					 
		              em=email;
		              break;
				}
				
			}
			/* HttpSession session = request.getSession();
             session.setAttribute("userID", id);
             session.setAttribute("username", username);
             session.setAttribute("email", email);*/
		 if(this.em.equals(email))
		  {
		              request.getRequestDispatcher("newuser.html").include(request, response);
		              pw.print("  Email allready in system");

		  }
		 else if(!password.equals(password1))
			 {
				 request.getRequestDispatcher("newuser.html").include(request, response);
	              pw.print("   Password mismatch");
			 }
			 else if(password.length()<5)
			 {
				 request.getRequestDispatcher("newuser.html").include(request, response);
	              pw.print("   Enter atleast 6 digiti password! ");
			 }
		  else if(!em.equals(email) && em!=null )
		  {

           pt1=con.prepareStatement("insert into users (Username,Password,Email) values('"+username+"','"+password+"','"+email+"')");

           pt1.executeUpdate();
           pt1.close();


		   pt3=con.prepareStatement("select UserID from Users where username='"+username+"'");
		   ResultSet r= pt3.executeQuery();
		   if(r.next())
		   {
			    UserID=r.getInt("UserID");
		   }
		   HttpSession session = request.getSession();
           session.setAttribute("userID", UserID);
           request.getRequestDispatcher("Homepage.html").forward(request, response);

		  }
		 }
		catch(Exception e)
		{
			pw.print(e.getMessage());
		}
		finally
		{
			try
			{
				if(pt!=null) {
					pt.close();
				}
				if(pt3!=null) {
					pt3.close();
				}
				if(pt1!=null) {
					pt1.close();
				}
				if(con!=null) {
					con.close();
				}

			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
