import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class DisplayReview extends HttpServlet 
{
	public static MongoClient mongo = new MongoClient("localhost",27017);

	public String productModelName;
	public String productCategory;
	public String productPrice;
	public String retailerName;
	public String retailerZip;
	public String retailerCity;
	public String retailerState;
	public String productOnSale;
	public String manufacturerName;
	public String manufacturerRebate;
	public String userId;
	public String userAge;
	public String userGender;
	public String userOccupation;
	public String ReviewRating;
	public String ReviewDate;
	public String ReviewText;
	
	public String key;
	public String username;
	public String producttype;
	
	static PhoneServlet Phobj;
	static AccessoriesServlet Acobj;
	static TabletServlet Tbobj;
	static LaptopServlet Lpobj;
	static TvServlet Tvobj;
	
	public static HashMap<String, PhoneCatalog> list = new HashMap<String, PhoneCatalog>();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	
	{
		
		HttpSession session=request.getSession(false);
		PrintWriter out = response.getWriter();		
		response.setContentType("text/html");
		
		key = request.getParameter("info");
		
		if(key.startsWith("PH"))
		{
			Phobj = new PhoneServlet();
			producttype = "phones";
			list = Phobj.hm1;
		}
		else if(key.startsWith("AC"))
		{
			Acobj = new AccessoriesServlet();
			producttype = "accessories";
			list = Acobj.hm1;
		}
		else if(key.startsWith("LP"))
		{
			Lpobj = new LaptopServlet();
			producttype = "laptops";
			list = Lpobj.hm1;
		}
		else if(key.startsWith("TB"))
		{
			Tbobj = new TabletServlet();
			producttype = "tablets";
			list = Tbobj.hm1;
		}
		else if(key.startsWith("TV"))
		{
			Tvobj = new TvServlet();
			producttype = "tv";
			list = Tvobj.hm1;
		}
		
		PhoneCatalog obj = list.get(key);
		
		productModelName = obj.getName();
		
		
		
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
		
		try
		{
			DB db = mongo.getDB("assign2");
		
			DBCollection myReviews = db.getCollection("myReviews");
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("productModelName", productModelName);
			
			DBCursor cursor = myReviews.find(searchQuery);
		
		
		String docType = "<!doctype html>\n";

				out.println(docType+

				"<html>"+
				
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Gadget Hub</title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"<link rel='stylesheet' href='a6.css' type='text/css' />"+
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
					
						"<section id='content'>");
						
						while(cursor.hasNext()!=false)
						{
						DBObject dob = cursor.next();
						out.println(
						
							"<header>Product Reviews</header>"+
							
							"<form action='/assign2/"+producttype+"' method='get'>"+
							"<label>Product Model Name </label>"+
							"<input type='text' name='productmodelname' value='"+dob.get("productModelName")+"' readonly  />"+
							
							"<label>Product Category </label>"+
							"<input type = 'text' name='productcategory'  value='"+dob.get("productCategory")+"' readonly/>"+
							
							"<label>Product Price </label>"+
							"<input type='text' name='price' value='"+dob.get("productPrice")+"'  readonly/>"+
							
							"<label>Retailer Name </label>"+
							"<input type='text' name='retailername' value='"+dob.get("retailerName")+"' readonly />"+
							
							"<label>Retailer Zip </label>"+
							"<input type='number' name='retailerzip' value='"+dob.get("retailerZip")+"'readonly />"+
							
							"<label>Retailer City </label>"+
							"<input type='text' name='retailercity' value='"+dob.get("retailerCity")+"'readonly />"+
							
							"<label>Retailer State </label>"+
							"<input type='text' name='retailerstate' value='"+dob.get("retailerState")+"' readonly/>"+
							
							"<label>Product on sale? </label>"+
							"<input type='text' name='productsale' value='"+dob.get("productOnSale")+"' readonly/>"+
							
							"<label>Manufacturer Name </label>"+
							"<input type='text' name='manufacturername' value='"+dob.get("manufacturerName")+"' readonly/>"+
							
							"<label>Manufacturer Rebate </label>"+
							"<input type='text' name='manufacturerrebate' value='"+dob.get("manufacturerRebate")+"' readonly/>");
							
							if(username != null)
							{
								out.println(
								"<label>User Id </label>"+
							"<input type='text' name='userId' value='"+dob.get("userId")+"' readonly/>");
								
							}
							else
							{
								out.println( "<label>User Id </label>"+
							"<input type='text' name='userId' value='"+dob.get("userId")+"' readonly />");
								
							}
					
					out.println(
					
							"<label>User Age </label>"+
							"<input type='text' name='userage' value='"+dob.get("userAge")+"'readonly />"+
							
							"<label>User Gender </label>"+
							"<input type='text' name='usergender' value='"+dob.get("userGender")+"' readonly/>"+
							
							"<label>User Occupation </label>"+
							"<input type='text' name='useroccupation' value='"+dob.get("userOccupation")+"' readonly/>"+
							
							"<label>Review Rating </label>"+
							"<input type='number' name='rating' min='0' max='5' value='"+dob.get("ReviewRating")+"' readonly />"+
							
							"<label>Review Date </label>"+
							"<input type='text' name='reviewdate' value='"+dob.get("ReviewDate")+"' readonly />"+
							
							"<br>"+
							"<label>Review Text </label>"+
							"<textarea name='reviewtext' cols='80' rows='5' readonly>"+dob.get("ReviewText")+"</textarea>"+
					
							
							"<input type = 'submit' value='Back' class='center' style='margin-left:25px'  />"+
					
					
					
							"</form>"
						);
						
						}
							out.println(
						"</section>"+
						
						"<aside class='sidebar'>"+
					
							"<ul>"+
							   "<li>"+
									"<h4>Products</h4>"+
									"<ul>"+
										"<li><a href='/assign2/Welcome'>Home Page</a></li>"+ 
										"<li><a href='/assign2/phones'>Smartphones</a></li>"+ 
										"<li><a href='/assign2/tablets'>Tablets</a></li>"+
										"<li><a href='/assign2/laptops'>Laptops</a></li>"+
										"<li><a href='/assign2/tv'>TV</a></li>"+
										"<li><a href='/assign2/accessories'>Accessories</a></li>"+
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
		catch (MongoException e) 
		{
				e.printStackTrace();
		}
		
	}
}