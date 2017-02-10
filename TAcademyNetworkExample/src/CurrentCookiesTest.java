import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 

public class CurrentCookiesTest extends HttpServlet { 

   public void doGet(HttpServletRequest request, 
     HttpServletResponse response) throws IOException, ServletException   { 
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out =   response.getWriter();
		String remoteAddress =  request.getRemoteAddr();

       // �ܼ� ����Ʈ
       System.out.println( " ================  "  + remoteAddress + " ���� �Ѿ��  ��Ű ================== ");
       Cookie[] cookies = request.getCookies(); 

       for (int i = 0; i < cookies.length; i++) { 
           Cookie c = cookies[i]; 
           String name = c.getName(); 
           String value = c.getValue(); 
           System.out.println(name + " = " + value);
        } 
  }     
}
