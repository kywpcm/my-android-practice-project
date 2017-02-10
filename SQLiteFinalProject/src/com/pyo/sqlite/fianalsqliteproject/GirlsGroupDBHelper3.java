package com.pyo.sqlite.fianalsqliteproject;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GirlsGroupDBHelper3 extends SQLiteOpenHelper {
	private static final String DB_NAME = "girlsGroupDB3.db";
	   private static final  int    DB_VERSION = 1;
	   
	   public GirlsGroupDBHelper3(Context context){
		   super(context,DB_NAME,null, DB_VERSION);
	   }
	   //DB���� ��� �� ��Ű���� ���� ��
	   //shell ��ɾ�� ���� �����ص� ��� ����
	   @Override
	   public void onCreate(SQLiteDatabase girlsGroupDB) {
	       StringBuilder  groupTBLSQL = new StringBuilder();
	       StringBuilder  musicTBLSQL = new StringBuilder();
	       groupTBLSQL
	                   .append("CREATE TABLE ")
	                   .append(GirlsGroupDB3.GirlsGroupInfo.TABLE_NAME )
	                   .append(" ( ")
	                     .append(GirlsGroupDB3.GirlsGroupInfo._ID)
	                     .append(" INTEGER PRIMARY KEY AUTOINCREMENT ,")
	                     .append(GirlsGroupDB3.GirlsGroupInfo.TEAM_NAME)
	                     .append(" TEXT ")
	                   .append(" ); ");
	       
	       //Image ������ ��� �ִ� �κ��� �߰� ��
	      musicTBLSQL
	                  .append("CREATE TABLE ")
	                  .append(GirlsGroupDB3.GirlsGroupMusic.TABLE_NAME)
	                  .append(" ( ")
	                   .append(GirlsGroupDB3.GirlsGroupMusic._ID)
	                   .append(" INTEGER PRIMARY KEY AUTOINCREMENT , ")
	                   .append(GirlsGroupDB3.GirlsGroupMusic.MUSIC_TITLE)
	                   .append(" TEXT , ")
	                   .append(GirlsGroupDB3.GirlsGroupMusic.GROUP_IMAGE_URI)
	                   .append(" TEXT ,")
	                   .append(GirlsGroupDB3.GirlsGroupMusic.GROUP_IMAGE_ID)
	                   .append(" INTEGER ,")
	                   .append(GirlsGroupDB3.GirlsGroupMusic.GIRLS_GROUP_ID)
	                   .append(" INTEGER ")
	                  .append(" ); ");
	      girlsGroupDB.execSQL(groupTBLSQL.toString());
	      girlsGroupDB.execSQL(musicTBLSQL.toString());
	   }
	   //�ʿ��ϴٸ� DB Instance�� ������Ʈ �Ѵ�
	    @Override
	   public void onUpgrade(SQLiteDatabase girlsGroupDB, int oldVersion, int newVersion) {
	    	 Log.d("DB Upgrade "," old version =" + oldVersion + ", newVersion = "+ newVersion);
	         girlsGroupDB.execSQL("DROP TABLE IF EXISTS " +
	        		 GirlsGroupDB3.GirlsGroupInfo.TABLE_NAME);
	         girlsGroupDB.execSQL("DROP TABLE IF EXISTS " + 
	        		 GirlsGroupDB3.GirlsGroupMusic.TABLE_NAME);
	         onCreate(girlsGroupDB);
	    }
		@Override
	   public void onOpen(SQLiteDatabase girlsGroupDB){
			super.onOpen(girlsGroupDB);
		}  
}
