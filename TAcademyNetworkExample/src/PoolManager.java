/*********************************************************
 * DB Pool�� �����ϴ� Singleton ��ü
 * author PYO IN SOO
 ********************************************************/

import  java.sql.*;

/*
 * Ŀ�ؼ� Ǯ���� �����ϴ� Ŭ����
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