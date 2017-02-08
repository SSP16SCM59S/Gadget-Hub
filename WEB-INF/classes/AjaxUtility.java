import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;

public class AjaxUtility
{
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	public static final String DB_URL="jdbc:mysql://localhost:3306/gadgethub?autoReconnect=true&useSSL=false";
	public static String user = "root";
	public static String pass = "tiger";
	public static String sql;
	public static ResultSet rs;
	public static Connection conn = null;
	public static Statement stmt = null;
	
	public static HashMap<String,Product> getData()
	{	
	HashMap<String,Product> hm=new HashMap<String,Product>();
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(DB_URL,user,pass);
	
	stmt=conn.createStatement();
	String selectCustomerQuery="select * from productdetails";
	rs = stmt.executeQuery(selectCustomerQuery); 
	while(rs.next())
	{
	
	Product p = new Product(rs.getString("id"), rs.getString("name"));
	hm.put(rs.getString("id"), p);
	}
	}
	catch(Exception e)
	{
		
	}

	return hm;
	}
	
	
	public  StringBuffer readdata(String searchId)
	{
	
	StringBuffer sb = new StringBuffer();
	HashMap<String,Product> data;
	data=getData();
	Iterator it = data.entrySet().iterator();
	while (it.hasNext())
	{
	Map.Entry pi = (Map.Entry)it.next(); Product p=(Product)pi.getValue();
	if (p.getName().toLowerCase().startsWith(searchId))
	{
		
	sb.append("<product>"); 
	sb.append("<id>" + p.getId() + "</id>");
	sb.append("<productName>" + p.getName() + "</productName >"); 
	sb.append("</product>");
	}
	}
	return sb;
	}

}

