import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;


public class UpdateOrderServlet extends HttpServlet
{
	public String username = null;
	
	
	public static HashMap<Integer, String> users = new HashMap<Integer, String>();
	
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";	
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		
		HttpSession session=request.getSession(false);
		
		if(session != null)
		{
			System.out.println("session: "+session);
			if((request.getSession().getAttribute("userid")) != null)
			{
				username = request.getSession().getAttribute("userid").toString();
			}
		
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
			sql = "SELECT username,id FROM userinfo";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			while(rs.next())
			{
				int id  = rs.getInt("id");
				String uid = rs.getString("username");
				
				users.put(id,uid);
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
								"<li><a href='/assign2/sales' style='font-size: 10px'>Home</a></li>"+//fill href tags
							"</div>"+
							"<li><a href='#body' style='font-size: 10px' style='font-size: 10px'>Manage</a></li>");
							/* "<li><a href='#' style='font-size: 12px'>Accessories</a></li>"+
							"<li><a href='#' style='font-size: 12px'>Cart ("+0+")</a></li>"+
							"<li><a href='#' style='font-size: 12px'>Checkout</a></li>" */
							
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
						
						
							"<header><h1>Update Customer order</h1></header>"			
							);
							
							
							
							
							
							
									out.println(
										
											"<form action='/assign2/updorder1' method='post'  autofocus>"+
											"<label>Select User </label><br>"+
											"<select name='userslist' style='width:85%;margin-top:0px;margin-left:0px;'>");
											for(int keys: users.keySet())
												{
												if(!users.get(keys).equalsIgnoreCase("storemanager") && !users.get(keys).equalsIgnoreCase("sales"))
												out.println("<option value = '"+users.get(keys)+"'>"+users.get(keys)+"</option>");
												
												}										
											out.println("</select>"+
											"<br/><br/><input type = 'submit' name = 'users' value = 'View  Orders' align='center'>"+
											"</form>");
									
											
							
						
							
						out.println("</section>"+
						
						"<aside class='sidebar'>"+
					
							"<ul>"+
							   "<li>"+
									"<h4>Products</h4>"+
									"<ul>"+
										"<li><a href='/assign2/sales'>Home Page</a></li>"+
										
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
								
								/* "<li>"+
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
								"</li>"+	 */							
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
}