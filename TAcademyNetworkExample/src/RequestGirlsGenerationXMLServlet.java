import  java.io.*;
import  javax.servlet.*;
import  javax.servlet.http.*;

public  class RequestGirlsGenerationXMLServlet extends HttpServlet{
   private StringBuffer girlsInfoBuf = null;
   public void init() throws  ServletException{
        girlsInfoBuf = new StringBuffer();
		BufferedReader inFile = null;
		try{
		  inFile  = new BufferedReader(new FileReader(
			  "C:\\apache-tomcat-6.0.32\\webapps\\androidNetwork\\" +
			   "girlsGeneration_images\\girls_generation_info.xml"));
		  String line = "";
		  while( ( line = inFile.readLine()) != null){
		      girlsInfoBuf.append(line);
		  }
		  inFile.close();
		}catch(IOException e){
		    System.err.println(" RequestGirlsGenerationXMLServlet init() " + " 파일 읽는 중 에러 발생!");
		}
		
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
   
        request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/xml;charset=utf-8");
        
        PrintWriter toClient = response.getWriter();
        toClient.println(girlsInfoBuf.toString());
		toClient.flush();
		toClient.close();
   }
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException{
        doPost(request, response);
   }
}