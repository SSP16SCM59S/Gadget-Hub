import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
public class Welcome extends HttpServlet
{
	public static CentralHashMap obj;
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub?autoReconnect=true&useSSL=false";
	String user = "root";
	String pass = "tiger";
	String sql,sql1,sql2;
	ResultSet rs;
	Connection conn = null;
	Statement stmt = null;
	PhoneCatalog temp1 = null;
	
	String fileName = "D:/ProductCatalog.xml";
	public static HashMap<String, PhoneCatalog> list = new HashMap<String, PhoneCatalog>();
	HashMap<String,PhoneCatalog> selectedproducts=new HashMap<String,PhoneCatalog>(); 
	public String username = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		SAXProductHandler saxHandler = new SAXProductHandler();
							
							if(obj.Products == null)
							{
								
								try
								{
									obj.Products = saxHandler.readDataFromXML(fileName);
								}
								catch(SAXException e)
								{
									
								}
								catch(ParserConfigurationException e)
								{
									
								}
								
							}
		
		
							
		
							try
							{
								
								Class.forName("com.mysql.jdbc.Driver");
								
								
								conn = DriverManager.getConnection(DB_URL,user,pass);
								
								
								stmt = conn.createStatement();
								
																																
								sql = "SELECT * FROM productdetails";
								ResultSet rs = stmt.executeQuery(sql);
								
								
								PhoneCatalog temp_obj;
								
								while(!rs.next())
								{
									
									for(String key : obj.Products.keySet())
									{
										temp_obj = obj.Products.get(key);
										sql1 = "insert into productdetails values('"+temp_obj.getId()+"','"+temp_obj.getName()+"','"+temp_obj.getPrice()+"','"+temp_obj.getRetailer()+"','"+temp_obj.getCondition()+"','"+temp_obj.getImage()+"');";
										int i = stmt.executeUpdate(sql1);
									}
								}
								
								do
								{
									temp_obj = new PhoneCatalog();
									
									String id  = rs.getString("id");
									String name = rs.getString("name");
									String price = rs.getString("price");
									String retailer = rs.getString("retailer");
									String condition = rs.getString("cond");
									String image = rs.getString("image");
									
											
										temp_obj.setId(id);
										temp_obj.setName(name);
										temp_obj.setPrice(price);
										temp_obj.setRetailer(retailer);
										temp_obj.setCondition(condition);
										temp_obj.setImage(image);
										
										list.put(id,temp_obj);
								}while(rs.next());
								
								 rs.close();
								 
								 if(list.size() != obj.Products.size())
								 {
									 sql2 = "delete from productdetails;";
									 int i2 = stmt.executeUpdate(sql2);
									 for(String key : obj.Products.keySet())
									{
										temp_obj = obj.Products.get(key);
										sql1 = "insert into productdetails values('"+temp_obj.getId()+"','"+temp_obj.getName()+"','"+temp_obj.getPrice()+"','"+temp_obj.getRetailer()+"','"+temp_obj.getCondition()+"','"+temp_obj.getImage()+"');";
										int i = stmt.executeUpdate(sql1);
									}
								 }
								 
								 stmt.close();
								 conn.close();
							}
							catch(SQLException se)
							{	
								se.printStackTrace();
							}
							catch(Exception e)
							{	
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
		
		
		
		
		
		
		//set username
		HttpSession session=request.getSession(false);
		
		if(session != null)
		{
			System.out.println("session: "+session);
			if((request.getSession().getAttribute("userid")) != null)
			{
				username = request.getSession().getAttribute("userid").toString();

			}
		
		}
		if(session == null)
		{
			username = null;
		}
		
		
		//System.out.println("username: "+username);
		
		
		
		String docType = "<!doctype html>\n";

				out.println(docType+

				"<html>"+
				
				"<head>"+
				"<script src='javascript.js'>"+
				"</script>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Gadget Hub</title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"</head>"+

				"<body onload='init()'>"+
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
								"<li class='start selected single'><a href='#' style='font-size: 10px'>Home</a></li>"+//fill href tags
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
						"<article>"+
								"<h2 align='center'>We Beat Our Competition</h2>"+		
								
							"</article>");
						
		PhoneCatalog temp1 = new PhoneCatalog();
		String line = null;
		selectedproducts.clear();
		for(String key : obj.Products.keySet())
		{
			temp1 = obj.Products.get(key);
			if(selectedproducts.size()<2 && !selectedproducts.containsKey(temp1.getName()))
			{
				ServletContext sc=request.getSession().getServletContext();
				BufferedReader br=new BufferedReader(new FileReader(sc.getRealPath("DealMatches.txt")));
				line=br.readLine(); 
				if(line==null)
				{
					out.println("<h2 align='center'>No Offers Found</h2>");
					break; 
				}
				else
				{
					do
					{
						if(line.contains(temp1.getName()))
						{
							out.print("<h3>"+line+"</h3>"); 
							out.print("<br>");
							selectedproducts.put(key,temp1); 
							break;
						}
					}while((line = br.readLine()) != null);
				}
			}

		}
		out.println("<article class='expanded'>"+
					"<table>");
		PhoneCatalog temp2 = null;
								for(String key: selectedproducts.keySet())
								{
									temp2 = selectedproducts.get(key);
									
									out.println("<tr>"+
										"<td>"+
											"<img src = 'images/"+ temp2.getImage() +"' width = '100' height = '100' alt = 'phone'>"+
										"</td>"+
										"<td>"+
											"<p>Model: "+ temp2.getName() +"</p>"+
											"<p>Sold by: "+ temp2.getRetailer()+ "</p>"+
											"<p> Condition : "+ temp2.getCondition()+ "</p>"+
											"<p> Price: "+ temp2.getPrice()+ "</p>"+
										"</td>"+
										"<td>"+
											"<form method = 'post' action = '/assign2/cart'>"+
											"<input type='hidden' name = 'info' value='"+key+"'/>"+
											"<input type='hidden' name = 'info1' value='search'/>"+
											"<input type = 'submit' name = 'phone' value = 'Add to Cart'>"+
											"</form>"+
											"<br>"+
											
											"<form method = 'post' action = '/assign2/submitreview'>"+
											"<input type='hidden' name = 'info' value='"+key+"'/>"+
											"<input type = 'submit' name = 'phone' value = 'Submit Review'>"+
											"</form>"+
											"<br>"+
											"<form method = 'post' action = '/assign2/viewreview'>"+
											"<input type='hidden' name = 'info' value='"+key+"'/>"+
											"<input type = 'submit' name = 'phone' value = 'View Review'>"+
											"</form>"+
										"</td>"+
									"</tr>");	
									
								}
						
						out.println(
						"</table>"+
						"</article>"+
						"</section>"+
						
						"<aside class='sidebar'>"+
					
							"<ul>"+
							   "<li>"+
									"<h4>Products</h4>"+
									"<ul>"+
										"<li><a href='#'>Home Page</a></li>");
										if(username!=null)
										{
											if(!username.equals("storemanager") && !username.equals("sales"))
											{
												out.println("<li><a href='/assign2/orders'>View Orders</a></li>");
											}
										
										}
										out.println("<li><a href='/assign2/phones'>Smartphones</a></li>"+ 
										"<li><a href='/assign2/tablets'>Tablets</a></li>"+
										"<li><a href='/assign2/laptops'>Laptops</a></li>"+
										"<li><a href='/assign2/tv'>TV</a></li>"+
										"<li><a href='/assign2/accessories'>Accessories</a></li>"+
										"<li><a href='/assign2/trending'>Trending</a></li>"+
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
										//"<li class='text'>"+
										//"<input type='text' id='searchId' name='searchId' onkeyup='doCompletion()' placeholder='search here..' style='padding: 5px; font-size: 16px;'>"+
																				
										"<div name='autofillform'>"+
										"<input type='text' name='searchId' id='searchId' onkeyup='doCompletion()' placeholder='search here..' style='padding: 5px; font-size: 16px;' />"+
										"<div id='auto-row'>"+
										"<table id='complete-table' class='gridtable' style='position: absolute; width: 315px;'>"+
										"</table>"+
										"</div>"+
										
										"</div>"+
										
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
}

