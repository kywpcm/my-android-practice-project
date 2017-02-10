import  java.io.*;
import  javax.servlet.*;
import  javax.servlet.http.*;

public  class RequestXMLPullServlet extends HttpServlet{
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
   
        request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/xml;charset=utf-8");
        
        PrintWriter toClient = response.getWriter();

		ServletBloodDAO servletDAO = new ServletBloodDAO();
		String xmlMessage = servletDAO.getXMLPullFile(request);
        toClient.println(xmlMessage);
		toClient.flush();
		toClient.close();
   }
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException{
        doPost(request, response);
   }
}