/*************************************************
 * Connection Pool
 * DB ���� �ӵ��� ������ �ϱ� ����
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
			System.out.println(" ����Ŭ ����̹� �ε� �� ���� �߻� : " + cnfe.toString());
			System.out.flush();
		}
	}
	
	public OracleDBConnectionPool(){
		connections = new HashMap<Connection,Boolean>(30);

		//�ʱ⿡ ���� Ŀ�ؼ� ��
		for(int i = 0 ; i <= 4 ; i++){
			try{
				connections.put(DriverManager.getConnection(
						"jdbc:oracle:thin:@127.0.0.1:1521:XE", "android","android"),Boolean.FALSE);
			}catch(SQLException sqle){
				System.out.println(" �ʱ� Ŀ�ؼ� ���� �� ���� �߻�! : " + sqle.toString());
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
				System.out.println(" �߰� Ŀ�ؼ� ���� �� ���� �߻�! : " + sqle.toString());
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