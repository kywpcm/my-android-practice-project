import  java.io.*;
import  javax.servlet.*;
import  javax.servlet.http.*;

public  class DOMWeatherServlet extends HttpServlet{
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
   
        request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=utf-8");
        PrintWriter toClient = response.getWriter();
		StringBuffer xmlBuf = new StringBuffer();

	    BufferedReader xmlFile = new BufferedReader(new FileReader("c:\\xml\\weather_sample.xml"));

		String line = null;
		while( ( line = xmlFile.readLine()) != null){
		     xmlBuf.append(line);
		}
		toClient.println(xmlBuf.toString());
		toClient.flush();
		toClient.close();

   }
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException{
        doPost(request, response);
   }
}