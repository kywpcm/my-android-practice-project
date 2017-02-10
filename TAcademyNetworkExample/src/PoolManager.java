/*********************************************************
 * DB Pool을 관리하는 Singleton 객체
 * author PYO IN SOO
 ********************************************************/

import  java.sql.*;

/*
 * 커넥션 풀링을 관리하는 클래스
 */
public class PoolManager{
	
	private static OracleDBConnectionPool dbPool;
	public static Connection getDBConnection(){
		if(dbPool != null){
			return dbPool.getOracleConnection();
		}else{
			new PoolManager();
		}
		return getDBConnection();
	}
	
	private PoolManager(){
		dbPool = new OracleDBConnectionPool();
	}
	
	public  static void  returndConnection(Connection conn){
		if(conn != null)
			dbPool.returndConnection(conn);
	}
	
}