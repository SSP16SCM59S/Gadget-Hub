import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;

public class UpdateOrderServlet2 extends HttpServlet
{
	public String username = null;
	public String key;
	public String price;
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
	public static HashMap<Integer,OrderDetails> list = new HashMap<>();
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	static UpdateOrderServlet1 obj1;
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		obj1 = new UpdateOrderServlet1();
		
		username = request.getParameter("userdata");
		key = request.getParameter("info");
		price = request.getParameter("price");
		
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
			String sql,sql1,sql2;
			sql = "update orders set price = "+Integer.parseInt(price)+" where username='"+username+"' and ord_date='"+key+"';";
			//sql2 = "Delete FROM orderdetails where ord_date='"+key+"' and username ='"+username+"';";
			sql1 = "SELECT * FROM orders";
			
			int i = stmt.executeUpdate(sql);
			//int i1 = stmt.executeUpdate(sql2);
			ResultSet rs = stmt.executeQuery(sql1);
			OrderDetails obj;
			// Extract data from result set
			
			while(rs.next())
			{
				obj = new OrderDetails();
				obj.setAddress(rs.getString("address"));
				obj.setPrice(rs.getString("price"));
				obj.setDate(rs.getString("ord_date"));
				obj1.list.put(rs.getInt("orderid"),obj);
				//System.out.println("reached inside rs.next");
			}
			if(!rs.next())
			{
				obj1.list.clear();
			}
			if(i!=0)
			{
				out.println("<h3><a href='/assign2/updorder'> Updated Succesfully</a></h3>");
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