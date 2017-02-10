/*************************************************
 * Connection Pool
 * DB 연결 속도를 빠르게 하기 위함
 * author PYO IN SOO
 ************************************************/

import java.sql.*;
import java.util.*;

public  class OracleDBConnectionPool{
	private HashMap<Connection,Boolean> connections;
	private int incrementCount = 5;

	static{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			System.out.println(" 오라클 드라이버 로드 중 문제 발생 : " + cnfe.toString());
			System.out.flush();
		}
	}
	
	public OracleDBConnectionPool(){
		connections = new HashMap<Connection,Boolean>(30);

		//초기에 만들 커넥션 수
		for(int i = 0 ; i <= 4 ; i++){
			try{
				connections.put(DriverManager.getConnection(
						"jdbc:oracle:thin:@127.0.0.1:1521:XE", "android","android"),Boolean.FALSE);
			}catch(SQLException sqle){
				System.out.println(" 초기 커넥션 생성 중 문제 발생! : " + sqle.toString());
				System.out.flush();
			}
		}
	}
	
	public  Connection  getOracleConnection(){
		Connection conn = null;
		Set<Connection> keys = connections.keySet();
		Iterator<Connection>  iters = keys.iterator();
		while(iters.hasNext()){
			synchronized(connections){
				conn = iters.next();
				Boolean flags = connections.get(conn);
				if(flags != Boolean.TRUE){
					try{
						conn.setAutoCommit(true);
						connections.put(conn,Boolean.TRUE);

					}catch(SQLException e){
						try{
							conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "android","android");
							connections.put(conn,Boolean.TRUE);
						}catch(SQLException sqle){}

					}
					return conn;
				}
			}
		}
		for(int i = 0 ; i < incrementCount ; i++){
			try{
				connections.put(DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "android","android"),Boolean.FALSE);
			}catch(SQLException sqle){
				System.out.println(" 추가 커넥션 생성 중 문제 발생! : " + sqle.toString());
				System.out.flush();
			}
		}
		return  getOracleConnection();
	}
	
	public  void returndConnection(Connection conn){
		Set<Connection> keys = connections.keySet();
		Iterator<Connection>  iters = keys.iterator();
		Connection poolConn = null;
		while(iters.hasNext()){
			poolConn = iters.next();
			if(poolConn == conn){
				connections.put(conn,Boolean.FALSE);
				return ;
			}
		}
		connections.put(conn, Boolean.TRUE);
	}
	
}