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


public class DeleteProduct extends HttpServlet
{
	public String username = null;
	public String producttype;
	public String fileName = null;
	public static HashMap<String, PhoneCatalog> hm_prod = new HashMap<String, PhoneCatalog>();
	PhoneCatalog objPhoneCatalog = new PhoneCatalog();
	public static CentralHashMap obj;
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		
		producttype = request.getParameter("product_type");
				
					if(producttype.equalsIgnoreCase("phone"))
					{
						fileName = "PH";
					}
					else if(producttype.equalsIgnoreCase("Accessory"))
					{
						fileName = "AC";
					}
					else if(producttype.equalsIgnoreCase("Tablet"))
					{
						fileName = "TB";
					}
					else if(producttype.equalsIgnoreCase("Laptop"))
					{
						fileName = "LP";
					}
					else if(producttype.equalsIgnoreCase("tv"))
					{
						fileName = "TV";
					}
		
		HttpSession session=request.getSession(false);
		
		if(session != null)
		{
			System.out.println("session: "+session);
			if((request.getSession().getAttribute("userid")) != null)
			{
				username = request.getSession().getAttribute("userid").toString();
			}
		
		}
		
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		obj = new CentralHashMap();		
				
				
		String docType = "<!doctype html>\n";

				out.println(docType+

				"<html>"+
				
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Gadget Hub</title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				//"<link rel='stylesheet' href='a5.css' type='text/css' />"+
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
								"<li class='start selected single'><a href='#' style='font-size: 10px'>Home</a></li>"+//fill href tags
							"</div>"+
							"<li><a href='#body' style='font-size: 10px' style='font-size: 10px'>Manage</a></li>");
							/* "<li><a href='#' style='font-size: 12px'>Accessories</a></li>"+
							"<li><a href='#' style='font-size: 12px'>Cart ("+0+")</a></li>"+
							"<li><a href='#' style='font-size: 12px'>Checkout</a></li>" */
							
							if(username!=null)
							{
								out.println("<li style='float:right;'><a href='/assign2/Logout'  style='font-size: 12px'>Logout</a></li>");
								out.println("<li style='float:right;'><a href='/assign2/store'  style='font-size: 12px'>Welcome, "+username+"</a></li>");
								
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
								"<h2 align='center'>"+producttype+"</h2>"+		
								"<p align='center'>Delete "+producttype+"</p>"+
							"</article>"+
							
							"<article class='expanded'>"+
							"<h2>Brands</h2>"+					
							"<table>");
						PhoneCatalog temp1 = null;
						for(String key: obj.Products.keySet())
						{
							temp1 = obj.Products.get(key);
							//System.out.println("Inside UpdateProduct: "+temp1.getId());
							if(temp1.getId().startsWith(fileName))
							{
							out.println("<tr>"+
										"<td>"+
											"<img src = 'images/"+ temp1.getImage() +"' width = '100' height = '100' alt = 'phone'>"+
										"</td>"+
										"<td>"+
							"<form method = 'post' action = '/assign2/delete2'>"+
							" Id:<br><input type = 'text' value='"+temp1.getId()+"' name='itemid' readonly></p>"+
							"Item Name:<br><input type = 'text' value='"+temp1.getName()+"' name='itemname' readonly></p>"+
							"Retailer:<br><input type = 'text' value='"+temp1.getRetailer()+"'name='itemretailer' readonly></p>"+
							"Condition:<br><input type = 'text' value='"+temp1.getCondition()+"'name='itemcondition' readonly></p>"+
							"Price:<br><input type = 'text' value='"+temp1.getPrice()+"'name='itemprice' readonly></p>"+
							"<input type='hidden' name = 'info' value='"+key+"'/>"+
							"<input type = 'submit' name = 'phone' value = 'Delete'>"+
							"</form>"+
							"</td>"+
							"</tr>");	
						}
						}
						out.println("</table>"+
						"</article>"+	
						
						"</section>"+
						
						"<aside class='sidebar'>"+
					
							"<ul>"+
							   "<li>"+
									"<h4>Products</h4>"+
									"<ul>"+
										"<li><a href='/assign2/store'>Home Page</a></li>"+ 
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