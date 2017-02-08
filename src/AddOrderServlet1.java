import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
public class AddOrderServlet1 extends HttpServlet
{
		public String username = null;
		public String key = null;
		public int price=0;
		public String address;
		public String date;
		
		
		static AddOrderServlet obj;
		public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
		public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
		String user = "root";
		String pass = "tiger";
		Statement stmt = null;
		Connection conn = null;
		
		public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
		{
			date = new SimpleDateFormat("MM-dd-yyyy/hh:mm").format(new java.util.Date());
			response.setContentType("text/html");
			PrintWriter out= response.getWriter();
			
			username = request.getParameter("userslist");
			key = request.getParameter("info");
			
			date = new SimpleDateFormat("MM-dd-yyyy/hh:mm").format(new java.util.Date());
			
			
			obj = new AddOrderServlet();
			PhoneCatalog ob = obj.phone.get(key);
			
			try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql1,sql2,sql3;
			
			sql3 = "select address from userinfo where username = '"+username+"';";
			
			ResultSet rs = stmt.executeQuery(sql3);
			
			while(rs.next())
			{
				address = rs.getString("address");
			}
			rs.close();
			
			
			sql1 = "insert into orders values("+null+",'"+date+"',"+ob.getPrice()+",'"+address+"','"+username+"');";
			int i = stmt.executeUpdate(sql1);
			
			
			
			 sql2 = "insert into orderdetails values("+null+",'"+date+"','"+ob.getName()+"',"+Integer.parseInt(ob.getPrice())+",'"+ob.getRetailer()+"',"+1+",'"+username+"');";
				int i2 = stmt.executeUpdate(sql2);
			
			
			
			if(i!=0)
			{
				out.println("<h3><a href='/assign2/sales'> Order Placed return to home page</a></h3>");
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