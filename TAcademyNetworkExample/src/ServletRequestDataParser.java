/*
* �������ڿ��� �Ľ��ϴ� ������ ���
* author PYO IN SOO
*/

import  javax.servlet.http.*;

public  class  ServletRequestDataParser{
   
 
   public  static BloodEntityObject getBloodInfoDTOParsing(HttpServletRequest request){

       BloodEntityObject bloodInfo = new BloodEntityObject();
       
	   
	   bloodInfo.patientName = request.getParameter("patientName");
	   bloodInfo.bloodType = request.getParameter("bloodType");
	   bloodInfo.statusText = request.getParameter("statusText");
	   bloodInfo.donationType = request.getParameter("donationType");
	   bloodInfo.bloodValue = request.getParameter("bloodValue");
	   bloodInfo.hospital = request.getParameter("hospital");
	   bloodInfo.hospitalPhone = request.getParameter("hospitalPhone");
	   bloodInfo.relationText = request.getParameter("relationText");
	   bloodInfo.careName = request.getParameter("careName");
	   bloodInfo.carePhone = request.getParameter("carePhone");
       

       return  bloodInfo;
   }
}