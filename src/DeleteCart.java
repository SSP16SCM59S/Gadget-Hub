import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;

public class DeleteCart extends HttpServlet 
{	public String key;
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	static Cart obj1;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	
	{
	
	obj1 = new Cart();
	
	PrintWriter out = response.getWriter();		
	response.setContentType("text/html");
	
	
	key = request.getParameter("info");
	
	try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql,sql1;
			sql = "Delete FROM cart where id1='"+key+"';";
			sql1 = "SELECT * FROM cart";
			
			int i = stmt.executeUpdate(sql);
			
			ResultSet rs = stmt.executeQuery(sql1);
			CartDetails obj;
			// Extract data from result set
			while(rs.next())
			{
				obj = new CartDetails();
				obj.setName(rs.getString("name"));
				obj.setRetailer(rs.getString("retailer"));
				obj.setPrice(rs.getString("price"));
				obj.setQuantity(rs.getInt("Quantity"));
				obj1.list.put(rs.getString("id1"),obj);
				System.out.println("reached inside rs.next");
			}
			if(!rs.next())
			{
				obj1.list.clear();
			}
			if(i!=0)
			{
				response.sendRedirect("/assign2/cart1");
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