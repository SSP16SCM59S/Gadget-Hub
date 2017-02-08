import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;


public class PhoneServlet extends HttpServlet 
{
	public String username;
    public int cartcount;
	public static HashMap<String, PhoneCatalog> hm1 = new HashMap<String, PhoneCatalog>();
	public static CentralHashMap obj;
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub";
	String user = "root";
	String pass = "tiger";
	Statement stmt = null;
	Connection conn = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	
	{
		
		PrintWriter out = response.getWriter();		
		response.setContentType("text/html");
		
		String fileName = "D:/ProductCatalog.xml";
		
		
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
		
		if(obj.Products == null)
		{
			SAXProductHandler saxHandler = new SAXProductHandler();
			try
			{
				hm1 = saxHandler.readDataFromXML(fileName);
			}
			catch(SAXException e)
			{
				
			}
			catch(ParserConfigurationException e)
			{
				
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
			sql = "SELECT id, name,price,retailer,cond,image FROM productdetails";
			ResultSet rs = stmt.executeQuery(sql);
			
			
			// Extract data from result set
			while(rs.next())
			{
				PhoneCatalog temp_obj = new PhoneCatalog();
				
				String id  = rs.getString("id");
				String name = rs.getString("name");
				String price = rs.getString("price");
				String retailer = rs.getString("retailer");
				String condition = rs.getString("cond");
				String image = rs.getString("image");
				
				
				
				if(id.startsWith("P"))
				{
					
					temp_obj.setId(id);
					temp_obj.setName(name);
					temp_obj.setPrice(price);
					temp_obj.setRetailer(retailer);
					temp_obj.setCondition(condition);
					temp_obj.setImage(image);
					
					hm1.put(id,temp_obj);
					
					
				}
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
		
		}
		else
		{
			hm1 = obj.Products;
			
		}
		
		
		
		
		
		
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
						
							"<article>"+
								"<h2 align='center'>Smartphones</h2>"+		
								"<p align='center'>Browse through our collection of Smartphones</p>"+
							"</article>"+
							
							"<article class='expanded'>"+
							"<h2>Brands</h2>"+					
							"<table>");
							PhoneCatalog temp1 = null;
								for(String key: hm1.keySet())
								{
									temp1 = hm1.get(key);
									if(temp1.getId().startsWith("PH"))
									{
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
											"<input type='hidden' name = 'info' value='"+key+"'/>"+
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
								}
					out.println("</table>"+
						"</article>"+			
						"</section>"+
						
						"<aside class='sidebar'>"+
					
							"<ul>"+
							   "<li>"+
									"<h4>Products</h4>"+
									"<ul>"+
										"<li><a href='/assign2/Welcome'>Home Page</a></li>"+ 
										//"<li><a href='/assign2/phones'>Smartphones</a></li>"+ 
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
	
}


class PhoneCatalog 
{
	private String id;
	private String retailer;
	private String name;
	private String price;
	private String image;
	private String condition;
	
	public PhoneCatalog()
	{
		
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}

class SAXProductHandler extends DefaultHandler 
{

	

	boolean bphone = false;
	boolean bid = false;
	boolean bname = false;
	boolean bretailer = false;
	boolean bprice = false;
	boolean bcondition = false;
	boolean bimage = false;

	PhoneCatalog catalog;
	HashMap<String, PhoneCatalog> hpCatalog = new HashMap<String, PhoneCatalog>();

	public HashMap<String, PhoneCatalog> readDataFromXML(String fileName) throws ParserConfigurationException, SAXException, IOException 
	{

		SAXParserFactory factory = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser parser = factory.newSAXParser();

		parser.parse(new File(fileName), this);

		return hpCatalog;
	}

	
	public void startDocument() throws SAXException 
	{
		//System.out.println("Start document");
	}

	
	public void endDocument() throws SAXException 
	{
		//System.out.println("End document");
	}

	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
			
		//if(qName.equalsIgnoreCase("tv"))
			if(qName.matches("(?i)phone|tablet|laptop|tv|accessory"))
		{
			bphone = true;
			catalog = new PhoneCatalog();
		}
		
		if(bphone)
		{
		
			if (qName.equalsIgnoreCase("ID")) 
			{
				bid = true;
			}

			else if (qName.equalsIgnoreCase("NAME")) 
			{
				bname = true;
			}

			else if (qName.equalsIgnoreCase("retailer")) 
			{
				bretailer = true;
			}

			else if (qName.equalsIgnoreCase("PRICE")) 
			{
				bprice = true;
			}

			else if (qName.equalsIgnoreCase("CONDITION")) 
			{
				bcondition = true;
			}

			else if (qName.equalsIgnoreCase("image")) 
			{
				bimage = true;
			}
		}
			
		
	}

	
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		//if (qName.equalsIgnoreCase("tv"))
		if(qName.matches("(?i)phone|tablet|laptop|tv|accessory"))
				{
					
					hpCatalog.put(catalog.getId(), catalog);
					bphone = false;
				}
	}

	
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		
			if (bid) 
			{
				catalog.setId(new String(ch, start, length));
				bid = false;
			}
			
			else if (bname) 
			{
				catalog.setName(new String(ch, start, length));
				bname = false;
			}
			
			else if (bretailer)
			{
				catalog.setRetailer(new String(ch, start, length));
				bretailer = false;
			}
			
			else if (bcondition) 
			{
				catalog.setCondition(new String(ch, start, length));
				bcondition = false;
			}
			
			
			else if (bprice) 
			{
				catalog.setPrice(new String(ch, start, length));
				bprice = false;
			}
			
			else if (bimage) 
			{
				catalog.setImage(new String(ch, start, length));
				bimage = false;
			}
		
	}
}


