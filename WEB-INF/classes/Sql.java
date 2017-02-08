import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;

public class Sql extends HttpServlet 
{
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	String username;
	public static PhoneServlet ob1;
	public static LaptopServlet ob2;
	public static AccessoriesServlet ob3;
	public static TabletServlet ob4;
	public static TvServlet ob5;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	
	{
		PrintWriter out = response.getWriter();		
		response.setContentType("text/html");
		
		
		try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql;
			PhoneCatalog temp_obj = null;
			
			for(String key: ob5.hm1.keySet())
			{
				temp_obj = ob5.hm1.get(key);
				sql = "insert into productdetails values('"+temp_obj.getId()+"','"+temp_obj.getName()+"','"+temp_obj.getPrice()+"','"+temp_obj.getRetailer()+"','"+temp_obj.getCondition()+"','"+temp_obj.getImage()+"');";
				int i = stmt.executeUpdate(sql);
			}
			
			
			
			
			
			
			
			
			
			 
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
		
	}
}