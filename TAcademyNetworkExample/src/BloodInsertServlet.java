/*
 * 혈액요청을 입력하기 위한 서블릿
 * author PYO IN SOO
 */

import  java.io.*;
import  javax.servlet.*;
import  javax.servlet.http.*;

public  class BloodInsertServlet extends HttpServlet{
   protected void doPost(HttpServletRequest request, 
	        HttpServletResponse response) 
          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=utf-8");
        PrintWriter toClient = response.getWriter();
		BloodEntityObject bloodDTO = 
			ServletRequestDataParser.getBloodInfoDTOParsing(request);
		ServletBloodDAO servletDAO = new ServletBloodDAO();
		String xmlMessage = servletDAO.insertBlood(bloodDTO);
        toClient.println(xmlMessage);
		toClient.flush();
		toClient.close();
   }
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException{
        doPost(request, response);
   }
}