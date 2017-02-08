import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;

public class Logout extends HttpServlet
{
	 public Logout() 
	 {
        super();
     }
	
	static OrderServlet obj1;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		obj1 = new OrderServlet();
		//obj2 = new Cart();
		
		HttpSession session=request.getSession(false);
		//System.out.println(session.getId());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if(session!=null)
		{
			obj1.list.clear();
			
			session.invalidate();
		}
		
		//response.getWriter().println("<h3><a href='/assign2'>Logged out Successfully</a></h3>");
		
		response.sendRedirect("/assign2/Welcome");
		 
		
	}
	
	
	
}