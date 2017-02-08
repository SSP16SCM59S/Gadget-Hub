import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;

public class AddProduct extends HttpServlet
{
		public String id;
		public String name;
		public String price;
		public String retailer;
		public String image;
		public String condition; 
		public String producttype;
		public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
		public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";
		String user = "root";
		String pass = "tiger";
		Statement stmt = null;
		Connection conn = null;
		public static CentralHashMap obj;
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		
		name = request.getParameter("item_name");
		price = request.getParameter("price");
		retailer = request.getParameter("Retailer");
		condition = request.getParameter("condition");
		image = request.getParameter("Image");
		producttype = request.getParameter("product_type");
		
		
		obj = new CentralHashMap();
		PhoneCatalog ob = new PhoneCatalog();
		
		if(producttype.equalsIgnoreCase("phone"))
		{
			id = "PH";
		}
		else if(producttype.equalsIgnoreCase("Accessory"))
		{
			id = "AC";			
		}
		else if(producttype.equalsIgnoreCase("Tablet"))
		{
			id = "TB";		
		}
		else if(producttype.equalsIgnoreCase("Laptop"))
		{
			id = "LP";	
		}
		else if(producttype.equalsIgnoreCase("tv"))
		{
			id = "TV";		
		}	
		
		Random r = new Random();
		int Low = 10;
		int High = 100;
		int Result = r.nextInt(High-Low) + Low;
		id += Integer.toString(Result);
		
		ob.setId(id);
		ob.setName(name);
		ob.setPrice(price);
		ob.setRetailer(retailer);
		ob.setCondition(condition);
		ob.setImage(image);
		obj.Products.put(id,ob);
		
		
		try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql;
			sql = "insert into productdetails values('"+id+"','"+name+"','"+price+"','"+retailer+"','"+condition+"','"+image+"');";
			
			int i = stmt.executeUpdate(sql);
			if(i!=0)
			{
				out.println("<h3><a href='/assign2/store'>Product Added Successfully</a></h3>");
			}
			else
			{
				out.println("<h3><a href='/assign2/home'>Product cannot be added</a></h3>");
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