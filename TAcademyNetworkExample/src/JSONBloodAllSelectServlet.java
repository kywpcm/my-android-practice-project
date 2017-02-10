import  java.io.*;
import  javax.servlet.*;
import  javax.servlet.http.*;

public  class JSONBloodAllSelectServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=utf-8");

		PrintWriter toClient = response.getWriter();

		ServletBloodDAO servletDAO = new ServletBloodDAO();
		String jsonMessage = servletDAO.getJSONBloodAllInfos();
		toClient.println(jsonMessage);
		toClient.flush();
		toClient.close();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		doPost(request, response);
	}
}