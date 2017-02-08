import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
public class Login1 extends HttpServlet
{
	String username=null,password=null;
	HashMap<String,String> hmap = new HashMap<String,String>();
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String user = "root";
		String pass = "tiger";
		Statement stmt = null;
		Connection conn = null;
		String title = "";
		String docType = "<!doctype html>\n";
		
		username = request.getParameter("uname");
		password = request.getParameter("passwd");
		
		try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT username,passwd FROM userinfo";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			while(rs.next())
			{
				String p  = rs.getString("passwd");
				String uid = rs.getString("username");
				
				hmap.put(uid,p);
			}
			 rs.close();
			 stmt.close();
			 conn.close();
		}
		catch(SQLException se)
		{	//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e)
		{	//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt!=null)
                stmt.close();
			}
			catch(SQLException se2)
			{
				
			}
			
			try
			{
				if(conn!=null)
                conn.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		}
		
		
		

			for (Map.Entry<String,String> entry : hmap.entrySet()) 
			{
			  String key = entry.getKey();
			  String value = entry.getValue();
			  
			 
			  if(key.equals(username) && value.equals(password))
			  {
					request.getSession().setAttribute("userid",username);

					if(username.equals("storemanager"))
					{
						response.sendRedirect("/assign2/store"); 
					}
					else if(username.equals("sales"))
					{
						response.sendRedirect("/assign2/sales"); 
					}
					
					else
					{
						response.sendRedirect("/assign2/Welcome"); 
					}
					
			  }
			  else
			  {
				  out.println("<h3><a href='/assign2/login'>Incorrect Username Password pair</a></h3> ");
			  }
			  
			}


		
	}
	
}
