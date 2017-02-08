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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UpdateProduct1 extends HttpServlet
{
	public String username = null;
	public String key;
	public String producttype;
	public String fileName = null;
	public String id=null, name=null,price=null,condition=null,retailer=null;
	public static HashMap<String, PhoneCatalog> hm_prod = new HashMap<String, PhoneCatalog>();
	PhoneCatalog objPhoneCatalog = new PhoneCatalog();
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		id = request.getParameter("itemid");
		name = request.getParameter("itemname");
		price = request.getParameter("itemprice");
		retailer = request.getParameter("itemretailer");
		condition = request.getParameter("itemcondition");
		key = request.getParameter("info");
		
		
		
		if(key.startsWith("PH"))
		{
			fileName = "D:/ProductCatalog.xml";
			producttype = "phone";
		}
		else if(key.startsWith("AC"))
		{
			fileName = "D:/AccessoryCatalog.xml";
			producttype = "Accessory";
		}
		else if(key.startsWith("LP"))
		{
			fileName = "D:/LaptopCatalog.xml";
			producttype = "Laptop";
		}
		else if(key.startsWith("TB"))
		{
			fileName = "D:/TabletCatalog.xml";
			producttype = "Tablet";
		}
		else if(key.startsWith("TV"))
		{
			fileName = "D:/TvCatalog.xml";
			producttype = "tv";
		}
		
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
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
		PhoneCatalog value = hm_prod.get(key);
		
		objPhoneCatalog.setId(id);
		objPhoneCatalog.setName(name);
		objPhoneCatalog.setRetailer(retailer);
		objPhoneCatalog.setCondition(condition);
		objPhoneCatalog.setImage(value.getImage());
		objPhoneCatalog.setPrice(price);
		
		System.out.println("id:"+id+name+retailer+condition+value.getImage()+price);
		
		for(String keys : hm_prod.keySet())
		{
			PhoneCatalog value1 = hm_prod.get(keys);
			if(keys.equals(key))
			{
				hm_prod.remove(key);
				break;
			}
		}
		hm_prod.put(key,objPhoneCatalog);
		
		 try
		{
			WriteXML();	
			out.println("<h3><a href='/assign2/store'>Product Edited Successfully</a></h3>");
		}
							
		catch(ParserConfigurationException e)
		{
							
		}
		catch(TransformerException e)
		{
							
		} 
	
	}
	
	public void WriteXML() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
			{
				
				
				DocumentBuilder builder = null;
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = builder.newDocument();
				
				Element root = document.createElement("ProductCatalog");
								
				for(String keys : hm_prod.keySet())
				{
				 
					PhoneCatalog value = hm_prod.get(keys);
					
					//String[] arr = value.split(" ");
					Element newNode = document.createElement(producttype);
					Element nodeID = document.createElement("id");					
					Element nodeImage = document.createElement("image");
					Element nodeName = document.createElement("name");
					Element nodeCondition = document.createElement("condition");
					Element nodePrice = document.createElement("price");
					Element nodeRetailer = document.createElement("retailer");
					
					 
					nodeID.setTextContent(value.getId());
					nodeName.setTextContent(value.getName());
					nodeCondition.setTextContent(value.getCondition());
					nodePrice.setTextContent(value.getPrice());
					nodeImage.setTextContent(value.getImage());
					nodeRetailer.setTextContent(value.getRetailer());		

					newNode.appendChild(nodeID);
					newNode.appendChild(nodeImage);
					newNode.appendChild(nodeName);
					newNode.appendChild(nodeCondition);
					newNode.appendChild(nodePrice);
					newNode.appendChild(nodeRetailer);
					
					root.appendChild(newNode);
					
				}
				
				document.appendChild(root);
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				Source source = new DOMSource(document);																
				File file = new File(fileName);
				Result result = new StreamResult(file);
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(source, result);
				
								
			}
}
