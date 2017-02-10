import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.util.*;

public class FileUpLoadServlet extends HttpServlet{
    private  String xmlHead =  "<?xml version=\'1.0\' encoding=\'utf-8\'?>";

	public void doPost(HttpServletRequest request,
		HttpServletResponse response)	 throws ServletException,IOException{

	 String savePath="C:\\upLoadedFilesFromAndroid"; 

	 int sizeLimit = 5 * 1024 * 1024 ;
	 response.setContentType("text/xml;charset=UTF-8");
	  PrintWriter toAndroid = response.getWriter();
	  StringBuffer xmlBuf = new StringBuffer();
	 try{
		MultipartRequest multi=
		  new MultipartRequest(request, savePath, 
			    sizeLimit,"UTF-8", new DefaultFileRenamePolicy());
     
		Enumeration formNames=multi.getFileNames();  // 폼의 이름 반환
		String formName=(String)formNames.nextElement(); // 
		String fileName=multi.getFilesystemName(formName); // 파일의 이름 얻기
		String memberName = multi.getParameter("memberName");
		System.out.println( "formName =>" + formName + ":fileName => " +
			 fileName  + ", memberName => " + memberName);
		System.out.flush();
        xmlBuf.append(xmlHead);
		xmlBuf.append("<upload_result>");
		if(fileName == null) {   // 파일이 업로드 되지 않았을때
			xmlBuf.append("FAIL");
		} else {  // 파일이 업로드 되었을때
		    xmlBuf.append("OK");
		} // end if
        xmlBuf.append("</upload_result>");
		toAndroid.println(xmlBuf.toString());
		toAndroid.flush();
		toAndroid.close();
		} catch(Exception e) {
		  System.out.println( " 업로드시 문제 발생! " );
		   e.printStackTrace();
	   } 
	 }
}