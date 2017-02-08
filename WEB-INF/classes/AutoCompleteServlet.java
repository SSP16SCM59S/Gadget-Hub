import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;

public class AutoCompleteServlet extends HttpServlet
{	String username;
	public String searchId,action,id;
	public static CentralHashMap obj;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		searchId = request.getParameter("searchId");
		action = request.getParameter("action");
		id = request.getParameter("id");
		PrintWriter out = response.getWriter();
			try
				{
					//System.out.println(searchId);	
					StringBuffer sb = new StringBuffer(); 
					boolean namesAdded = false;
					if (action.equals("complete"))
					{
						if (!searchId.equals(""))
						{		
							AjaxUtility a = new AjaxUtility(); 
							
							sb = a.readdata(searchId);
							if(sb!=null || !sb.equals(""))
								{
								namesAdded=true;
								}
							if (namesAdded)
								{
								response.setContentType("text/xml");
								response.getWriter().write("<products>" + sb.toString() + "</products >");
								}
						}
					}
					else if(action.equals("lookup"))
					{
						response.setContentType("text/html");
						
						
						//set username
						HttpSession session=request.getSession(false);
						
						if(session != null)
						{
							//System.out.println("session: "+session);
							if((request.getSession().getAttribute("userid")) != null)
							{
								username = request.getSession().getAttribute("userid").toString();

							}
						
						}
						if(session == null)
						{
							username = null;
						}
							
							
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
								"<h2 align='center'>Products</h2>"+		
								"<p align='center'>Browse through our collection of Products</p>"+
							"</article>"+
							
							"<article class='expanded'>"+
							"<h2>Products</h2>"+					
							"<table>");
							PhoneCatalog temp1 = null;
								
									temp1 = obj.Products.get(id);
									System.out.println(id);
									System.out.println(temp1.getName());
									out.println("<tr>"+
										"<td>"+
											"<img src = 'images/"+ temp1.getImage() +"' width = '100' height = '100' alt = 'phone'>"+
										"</td>"+
										"<td>"+
											"<p>Model: "+ temp1.getName() +"</p>"+
											"<p>Sold by: "+ temp1.getRetailer()+ "</p>"+
											"<p> Condition : "+ temp1.getCondition()+ "</p>"+
											"<p> Price: "+ temp1.getPrice()+ "</p>"+
										"</td>"+
										"<td>"+
											"<form method = 'post' action = '/assign2/cart'>"+
											"<input type='hidden' name = 'info' value='"+id+"'/>"+
											"<input type='hidden' name = 'info1' value='search'/>"+
											"<input type = 'submit' name = 'phone' value = 'Add to Cart'>"+
											"</form>"+
											"<br>"+
											"<form method = 'post' action = '/assign2/submitreview'>"+
											"<input type='hidden' name = 'info' value='"+id+"'/>"+
											"<input type = 'submit' name = 'phone' value = 'Submit Review'>"+
											"</form>"+
											"<br>"+
											"<form method = 'post' action = '/assign2/viewreview'>"+
											"<input type='hidden' name = 'info' value='"+id+"'/>"+
											"<input type = 'submit' name = 'phone' value = 'View Review'>"+
											"</form>"+
										"</td>"+
									"</tr>");	
									
								
					out.println("</table>"+
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
				catch(Exception e)
				{
					
				}
	}
}