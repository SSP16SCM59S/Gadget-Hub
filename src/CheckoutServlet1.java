import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class CheckoutServlet1 extends HttpServlet 
{
	String username;
	public static HashMap<String,CartDetails> list1 = new HashMap<>();
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	public int price=0;
	public String address;
	public String date;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//set username
		HttpSession session=request.getSession(false);
		if(session == null)
		{
			out.println("<h3><a href='/assign2/login'> Please Login to Continue </a></h3>");
		}
		else
		{
			if((request.getSession().getAttribute("userid")) != null)
			{
				username = request.getSession().getAttribute("userid").toString();

			}
			
			
		date = new SimpleDateFormat("MM-dd-yyyy/hh:mm").format(new java.util.Date());	
		address = request.getParameter("address");
		price = Integer.parseInt(request.getParameter("info"));	
			try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql,sql1,sql_end;
			sql = "SELECT * FROM cart";
			sql_end = "delete from cart";
			sql1 = "insert into orders values("+null+",'"+date+"',"+price+",'"+address+"','"+username+"');";
			ResultSet rs = stmt.executeQuery(sql);
			
			CartDetails obj;
			// Extract data from result set
			while(rs.next())
			{
				obj = new CartDetails();
				obj.setName(rs.getString("name"));
				obj.setRetailer(rs.getString("retailer"));
				obj.setPrice(rs.getString("price"));
				obj.setQuantity(rs.getInt("Quantity"));
				list1.put(rs.getString("id1"),obj);
			}
			rs.close();
			int i = stmt.executeUpdate(sql1);
			
			
			CartDetails temp1 = null;
			for(String key: list1.keySet())
			{
				
				
				temp1 = list1.get(key);
				String sql2 = "insert into orderdetails values("+null+",'"+date+"','"+temp1.getName()+"',"+Integer.parseInt(temp1.getPrice())+",'"+temp1.getRetailer()+"',"+temp1.getQuantity()+",'"+username+"');";
				int i2 = stmt.executeUpdate(sql2);
			}
			
			int i3 = stmt.executeUpdate(sql_end);
			if(i!=0)
			{	list1.clear();
				out.println("<h3><a href='/assign2/Welcome'> Order Placed return to home page</a></h3>");
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
}