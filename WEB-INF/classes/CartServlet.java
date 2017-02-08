import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CartServlet extends HttpServlet 
{
	public String key;
	public String producttype;
	public String fileName = null;
	public static HashMap<String, PhoneCatalog> hm_prod = new HashMap<String, PhoneCatalog>();
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
  public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub?autoReconnect=true&useSSL=false";	
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	int qty=0;
	String key1;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	
	{
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		key = request.getParameter("info");
		key1 = request.getParameter("info1");
		if(key.startsWith("PH"))
		{
			fileName = "D:/ProductCatalog.xml";
			producttype = "phones";
		}
		else if(key.startsWith("AC"))
		{
			fileName = "D:/AccessoryCatalog.xml";
			producttype = "accessories";
		}
		else if(key.startsWith("LP"))
		{
			fileName = "D:/LaptopCatalog.xml";
			producttype = "laptops";
		}
		else if(key.startsWith("TB"))
		{
			fileName = "D:/TabletCatalog.xml";
			producttype = "tablets";
		}
		else if(key.startsWith("TV"))
		{
			fileName = "D:/TvCatalog.xml";
			producttype = "tv";
		}
		
		
		hm_prod.clear();
		
		//System.out.println("reached after clear");
		SAXProductHandler saxHandler = new SAXProductHandler();
		try
		{
			hm_prod = saxHandler.readDataFromXML(fileName);
		}
		catch(SAXException e)
		{
				
		}
		catch(ParserConfigurationException e)
		{
						
		}
		
		PhoneCatalog temp1 = hm_prod.get(key);
		
		try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
						
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql,sql1,sql2;
			sql = "insert into cart values("+null+",'"+key+"','"+temp1.getName()+"','"+temp1.getPrice()+"','"+temp1.getRetailer()+"','"+1+"');";
			sql1 = "select * from cart where id1 = '"+key+"';";
			
			
			
			ResultSet rs = stmt.executeQuery(sql1);
			
			while(rs.next())
			{
				 qty = rs.getInt("Quantity");
				// System.out.println(qty);
			}
			if(qty!=0)
			{
				qty++;
				sql2 = "update cart set quantity = "+qty+" where id1 = '"+key+"';";
				int i = stmt.executeUpdate(sql2);
				 if(i!=0)
				{
					if(key1 == null)
					{
						response.sendRedirect("/assign2/cart");
					}
					else
					{
						out.println("<h3><a href='/assign2/Welcome'> Product Added Successfully</a></h3>");
					}
				}
			}
			else
			{
				int i = stmt.executeUpdate(sql);
				
				 if(i!=0)
				{
					if(key1 == null)
					{
						response.sendRedirect("/assign2/cart");
					}
					else
					{
						out.println("<h3><a href='/assign2/Welcome'> Product Added Successfully</a></h3>");
					}
					
				}
			}
			
			/* else
			{
				out.println("<h3><a href='/assign2/home'>Account cannot be created contact the admin</a></h3>");
			}  */
			
			
			 
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