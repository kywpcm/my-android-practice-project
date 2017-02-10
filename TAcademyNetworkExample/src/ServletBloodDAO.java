/*
 * 요청정보를 저장하는 클래스
 * author PYO IN SOO
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ServletBloodDAO{

	private static String  xmlHead = "<?xml version=\'1.0\' encoding=\'utf-8\'?>";

	public String getXMLPullFile(HttpServletRequest request) {
		StringBuffer  buf = null;
		BufferedReader   fromAndroid = null;
		BufferedReader   xmlInFile = null;
		File file = null;
		try{
			fromAndroid = new BufferedReader(request.getReader());
			System.out.println(" = IP 주소 " +  request.getRemoteHost() + 
					"  안드로이드에서 넘어온 데이터 == ");
			for( String line = null ; ( line = fromAndroid.readLine()) != null ; ){
				System.out.println(line);
			}
			file = new File(
					"C:\\apache-tomcat-6.0.32\\webapps\\androidNetwork\\xmlDoc\\tvmz_banner.xml");
			buf = new StringBuffer();
			xmlInFile = new BufferedReader( new FileReader(file));
			for(String line = null ; ( line = xmlInFile.readLine()) != null ; ){
				buf.append(line);
			}
		}catch(Exception e){
			System.err.println(  " getXMLPullFile 에서 발생 ! " + e);
		}finally{
			if( fromAndroid != null){
				try{
					fromAndroid.close();
				}catch(IOException ioe){}
			}
			if( xmlInFile != null){
				try{
					xmlInFile.close();
				}catch(IOException ioe){}
			}
			return   buf.toString();
		}
	}
	
	/*
	 * 모든 혈액형 정보를 리턴 함.
	 */
	public String getBloodAllInfos(){
		Connection conn = null ;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		StringBuilder xmlBuf = null;
		String infosAllQuery = "SELECT * FROM android_blood_tbl";

		try{
			xmlBuf = new StringBuilder();
			conn = PoolManager.getDBConnection();
			pStmt = conn.prepareStatement(infosAllQuery);

			rs = pStmt.executeQuery();
			xmlBuf.append(xmlHead);
			xmlBuf.append("<blood_list>");
			while(rs.next()){
				xmlBuf.append("<blood_info>")
				.append("<bloodId>" + rs.getInt(1) + "</bloodId>")
				.append("<patientName>" + rs.getString(2) + "</patientName>")
				.append("<bloodType>" + rs.getString(3) + "</bloodType>")
				.append("<statusText>" + rs.getString(4) + "</statusText>")
				.append("<donationType>" + rs.getString(5) + "</donationType>")
				.append("<bloodValue>" + rs.getString(6) + "</bloodValue>")
				.append("<hospital>" + rs.getString(7) + "</hospital>")
				.append("<hospitalPhone>" + rs.getString(8) + "</hospitalPhone>")
				.append("<relationText>" + rs.getString(9) + "</relationText>")
				.append("<careName>" + rs.getString(10) + "</careName>")
				.append("<carePhone>" + rs.getString(11) + "</carePhone>")
				.append("<insertDate>" + rs.getString(12) + "</insertDate>")
				.append("</blood_info>");
			}
			xmlBuf.append("</blood_list>");
		}catch(SQLException sqle){
			System.out.println(" insertBlood(BloodEntityObject) 에서 문제 발생 ! " + sqle);
		}finally{
			if( rs != null){
				try{
					rs.close();
				}catch(SQLException e){}
			}
			if( pStmt != null){
				try{
					pStmt.close();
				}catch(SQLException e){}
			}
			PoolManager.returndConnection(conn);
		}
		return xmlBuf.toString();
	}

	/*********************************************************
	 * 혈액 요청 정보를 저장
	 *********************************************************/
	public String insertBlood(BloodEntityObject bloodDTO){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		StringBuilder xmlBuf = null;
		String bloodInsertQuery = 
				"INSERT INTO android_blood_tbl VALUES("+
						"android_blood_tbl_pk.NEXTVAL,?,?,?,?,?,?,?,?,?,?,sysdate)";
		try{
			xmlBuf = new StringBuilder();
			conn = PoolManager.getDBConnection();
			pStmt = conn.prepareStatement(bloodInsertQuery);

			int increment = 1;

			pStmt.setString(increment++, bloodDTO.patientName);
			pStmt.setString(increment++, bloodDTO.bloodType);
			pStmt.setString(increment++, bloodDTO.statusText);
			pStmt.setString(increment++, bloodDTO.donationType);
			pStmt.setString(increment++, bloodDTO.bloodValue);
			pStmt.setString(increment++, bloodDTO.hospital);
			pStmt.setString(increment++, bloodDTO.hospitalPhone);
			pStmt.setString(increment++, bloodDTO.relationText);
			pStmt.setString(increment++, bloodDTO.careName);
			pStmt.setString(increment++, bloodDTO.carePhone);

			int flag = pStmt.executeUpdate();

			xmlBuf.append(xmlHead);
			xmlBuf.append("<blood_status>\n");
			if( flag > 0 ){
				xmlBuf.append("<result_value>OK</result_value>");
			}else{
				xmlBuf.append("<result_value>FAIL</result_value>");
			}
			xmlBuf.append("</blood_status>");
			System.out.println(xmlBuf.toString());
		}catch(SQLException sqle){
			System.out.println(" insertBlood(BloodEntityObject) 에서 문제 발생 ! " + sqle);
		}finally{
			if( rs != null){
				try{
					rs.close();
				}catch(SQLException e){}
			}
			if( pStmt != null){
				try{
					pStmt.close();
				}catch(SQLException e){}
			}
			PoolManager.returndConnection(conn);
		}
		return xmlBuf.toString();
	}
	
	/*
	 *  모든 혈액정보를  JSON  Type으로 리턴함.
	 */
	public String getJSONBloodAllInfos(){
		Connection conn = null ;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String infosAllQuery = "SELECT * FROM android_blood_tbl";
		JSONArray  jsonsData  = null;
		try{
			conn = PoolManager.getDBConnection();
			pStmt = conn.prepareStatement(infosAllQuery);

			rs = pStmt.executeQuery();
			jsonsData = new JSONArray();
			while(rs.next()){
				JSONObject bloodData = new JSONObject();
				bloodData.put("bloodId", rs.getInt(1));
				bloodData.put("patientName", rs.getString(2) );     
				bloodData.put("bloodType" , rs.getString(3) );
				bloodData.put("statusText" , rs.getString(4) );
				bloodData.put("donationType" , rs.getString(5));
				bloodData.put("bloodValue", rs.getString(6) );
				bloodData.put("hospital" , rs.getString(7));
				bloodData.put("hospitalPhone" , rs.getString(8) );
				bloodData.put("relationText" , rs.getString(9) );
				bloodData.put("careName" , rs.getString(10));
				bloodData.put("carePhone" , rs.getString(11) );
				bloodData.put("insertDate" , rs.getString(12)) ;

				jsonsData.add(bloodData);
			}
		}catch(Exception e){
			System.out.println("getJSONBloodAllInfos ! " + e);
		}finally{
			if( rs != null){
				try{
					rs.close();
				}catch(SQLException e){}
			}
			if( pStmt != null){
				try{
					pStmt.close();
				}catch(SQLException e){}
			}
			PoolManager.returndConnection(conn);
		}
		//System.out.println(jsonsData.toString());
		return jsonsData.toString();
	}
}