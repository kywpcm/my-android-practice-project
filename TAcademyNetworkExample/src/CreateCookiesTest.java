import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateCookiesTest extends HttpServlet{

  public void doGet(HttpServletRequest request, 	HttpServletResponse    response)
  			       throws ServletException, IOException{
  	
    Cookie cookie1, cookie2, cookie3;
    response.setContentType("text/xml; charset=UTF-8");
    PrintWriter toClient = response.getWriter();
  	 int cookieCount = 0;
    for(int i=0; i<3; i++){  
      cookie1 = new Cookie("Cookie_Name_" +  (++cookieCount) , 
		  "Cookie_Value_" +cookieCount);
      response.addCookie(cookie1);
      
      cookie2 = new Cookie("10minute_cookie_" +  (++cookieCount) , 
		  "Cookie_Value_" +cookieCount);
      cookie2.setMaxAge(600);

      response.addCookie(cookie2);
      
      cookie3 = new Cookie("30minute_cookie" + (++cookieCount) ,
		  "Cookie_Value_" +cookieCount);
      cookie3.setMaxAge(1800);
      response.addCookie(cookie3);
    } 
    toClient.println("<?xml version=\'1.0\' encoding=\'utf-8\'?>");
	toClient.println("<result>ok</result>");
	toClient.close();
  }
}