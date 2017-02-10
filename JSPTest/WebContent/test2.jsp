<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MySQL select 예제</title>
</head>

<body>
	<h1>MySQL과의 커넥션 연동 JSP</h1>
	<%
		// 프로그램 내부에서 euckr로 컨버팅한 흔적이 없습니다. (연동 세팅만 잘 하면 문제 없음!)

		String DB_URL = "jdbc:mysql://54.249.38.98:3306/test_kwon_database";
		String DB_USER = "root";
		String DB_PASSWORD = "1234";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rstset = null;
		String query = "SELECT * FROM person";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			stmt = conn.createStatement();

			//String name = "권영우짱";
			//String qry = "UPDATE person SET name =" + name + " where id=1";
			//int ret = stmt.executeUpdate(qry); // DML문이 처리된 row의 수를 리턴
			rstset = stmt.executeQuery(query); //ResultSet 객체 리턴

			out.print("person table 조회 결과 : </br></br>"); //html 코드 출력
			String str;
			while (rstset.next()) {
				out.println(rstset.getString(1)); //컹엄 인덱스..
				out.println(rstset.getString("name")); //컬럼 라벨..
				out.println(rstset.getString(3));
				out.println("<br>");
			}
		} catch (SQLException e) {
			out.print("SQLException Error...");
			out.print(e.toString());
		} finally {
			if (rstset != null)
				try {
					rstset.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
	%>
</body>

</html>