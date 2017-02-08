import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;


public class DeleteOrderServlet1 extends HttpServlet
{
	public String username = null;

	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
	public static HashMap<Integer,OrderDetails> list = new HashMap<>();
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		
		username = request.getParameter("userslist");
		
		
		if(list.isEmpty() == false)
		{
			list.clear();
		}
		
		
		try
		{
			//Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//Opening Connection
			conn = DriverManager.getConnection(DB_URL,user,pass);
			
			//Execute Sql Query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM orders where username = '"+username+"';";
			ResultSet rs = stmt.executeQuery(sql);
			
			OrderDetails obj;
			// Extract data from result set
			while(rs.next())
			{
				obj = new OrderDetails();
				obj.setAddress(rs.getString("address"));
				obj.setPrice(rs.getString("price"));
				obj.setDate(rs.getString("ord_date"));
				list.put(rs.getInt("orderid"),obj);
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
		
			
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		String docType = "<!doctype html>\n";

				out.println(docType+

				"<html>"+
				
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Gadget Hub</title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"</head>"+

				"<body>"+
				"<div id='container'>"+
					"<header>"+
						"<div>"+
							"<h1 class ='single' style='font-family: blaze'><a href='/'>Gadget<span> Hub  </span></a></h1>"+
							"<img src='images/smartphone.jpg' alt='smartphone' width='40' height='40' style='float:center;'/>"+
							"<img src='images/tablet1.png' alt='smartphone' width='40' height='40' style='float:center;'/>"+
							"<img src='images/laptop.jpg' alt='smartphone' width='40' height='40' style='float:center;'/>"+
							"<img src='images/tv.png' alt='smartphone' width='40' height='40' style='float:center;'/>"+
						"</div>"+
					"<h2 class='caption'>Smartphones, Tablets, Laptops & TV's</h2>"+
					"</header>"+
					
							
					
					
					"<nav>"+
						"<ul>"+
							"<div>"+
								"<li><a href='/assign2/Welcome' style='font-size: 10px'>Home</a></li>"+//fill href tags
							"</div>"+
							"<li><a href='#body' style='font-size: 10px' style='font-size: 10px'>Products</a></li>"+
							"<li><a href='/assign2/accessories' style='font-size: 12px'>Accessories</a></li>"+
							"<li><a href='/assign2/cart1' style='font-size: 12px'>Cart ("+0+")</a></li>"+
							"<li><a href='/assign2/checkout' style='font-size: 12px'>Checkout</a></li>");
							
							if(username!=null)
							{
								out.println("<li style='float:right;'><a href='/assign2/Logout'  style='font-size: 12px'>Logout</a></li>");
								out.println("<li style='float:right;'><a href='#'  style='font-size: 12px'>Welcome, "+username+"</a></li>");
								
							}
							else
							{
								out.println("<li style='float:right;'><a href='/assign2/Signup'  style='font-size: 12px'>Signup</a></li>"+
							"<li style='float:right;'><a href='/assign2/login'  style='font-size: 12px'>Login</a></li>");
							
							}
		out.println(	"</ul>"+
					"</nav>"+

					"<img class='header-image' src='images/image.jpg' alt='Buildings' />"+

					"<div id='body'>"+
					
						"<section id='content'>"+
	
							
							"<article class='expanded'>"+
							"<table>"+
							"<tr>"+
							//"<th> Id </th>"+
							"<th> Order ID </th>"+
							"<th> Order Date </th>"+
							"<th> Total Price </th>"+
							"<th> Address </th>"+
							"<th> Products </th>"+
							"<th> Quantity </th>");
						OrderDetails temp1 = null;
						for(int key: list.keySet())
						{
							temp1 = list.get(key);
							out.println("<tr>"+
							"<td>"+key+"</td>"+
							"<td>"+temp1.getDate()+"</td>"+
							"<td>"+temp1.getPrice()+"$</td>"+
							"<td>"+temp1.getAddress()+"</td>");
							
							
							
							
							
								try
								{
									//Register JDBC Driver
									Class.forName("com.mysql.jdbc.Driver");
									
									//Opening Connection
									conn = DriverManager.getConnection(DB_URL,user,pass);
									
									//Execute Sql Query
									stmt = conn.createStatement();
									String sql,sql1;
									sql = "SELECT itemname FROM orderdetails where username = '"+username+"' and ord_date='"+temp1.getDate()+"';";
									sql1 = "SELECT quantity FROM orderdetails where username = '"+username+"' and ord_date='"+temp1.getDate()+"';";
									ResultSet rs = stmt.executeQuery(sql);
									
									
								   out.println("<td>");
									while(rs.next())
									{
										out.println(rs.getString("itemname")+"<br/>");
										//out.println(" * "+rs.getString("quantity")+"<br/>");
										
									}
									out.println("</td>");
									
									ResultSet rs1 = stmt.executeQuery(sql1);
									out.println("<td>");
									while(rs1.next())
									{
										out.println(rs1.getString("quantity")+"<br/>");
										
										
									}
									out.println("</td>");
									 
									 rs1.close();
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
							
							
							
							
							
							
							
							
							
							
							
							
								
							out.println("<td>"+
							"<form method = 'post' action = '/assign2/delorder2'>"+
							"<input type='hidden' name = 'info' value='"+temp1.getDate()+"'/>"+
							"<input type='hidden' name = 'userdata' value='"+username+"'/>"+
							"<input type = 'submit' name = 'Delete' value = 'Delete Order'>"+
							"</form>"+
							"</td>"+
							"</tr>"
							);
						}
		
		out.println("</table>"+
						"</article>"+			
						"</section>"+
						
						"<aside class='sidebar'>"+
					
							"<ul>"+
							   "<li>"+
									"<h4>Products</h4>"+
									"<ul>"+
										"<li><a href='/assign2/sales'>Home Page</a></li>"+ 
										"<li><a href='/assign2/delorder'>Back</a></li>"+
									"</ul>"+
								"</li>"+
								
								"<li>"+
									"<h4>About us</h4>"+
									"<ul>"+
										"<li class='text'>"+
											"<p style='margin: 0;'>We are an ecommerce company primarily dealing with Laptops Smartphones Tablets & Televisions. Please feel free to browse our store for our wide range of products.</p>"+
										"</li>"+
									"</ul>"+
								"</li>"+
								
								"<li>"+
									"<h4>Search site</h4>"+
									"<ul>"+
										"<li class='text'>"+
											"<form method='get' class='searchform' action='#' >"+//fill search forms
												"<p>"+
													"<input type='text' size='30' value='' name='s' class='s' style='float: center' align = 'center'/>"+
													
												"</p>"+
											"</form>"+
										"</li>"+
									"</ul>"+
								"</li>"+								
							"</ul>"+
						"</aside>"+
						"<div class='clear'></div>"+
					"</div>"+
					"<footer>"+
					"<div class='footer-content'>"+
								
						"<ul class='endfooter'>"+
							"<li><h3>Contact Us</h3></li>"+
							"<li>Gadget Hub</li>"+
							"<li>2801 South King Drive Apt 102</li>"+
							"<li>shash.4243gmail.com</li>"+
							"<li>(312)-678-4446</li>"+
						"</ul>"+
						
						"<div class='clear'></div>"+
					"</div>"+
					"<div class='footer-bottom'>"+
						"<p>&copy; YourSite 2013. <a href='#'>CSP 595 Assignment 2</a></p>"+
					"</div>"+
				 "</footer>"+
				"</div>"+
				"</body>"+
				"</html>");
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request,response);
	}
}