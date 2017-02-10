package com.pyo.custom.provider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GirlsGroupDBOpenHelper extends SQLiteOpenHelper {
	public static  final String DEBUG_TAG = "GirlsGroupDBOpenHelper";
	public   static final String DB_NAME = "girlsGroupProvider.db";
    public   static final String DB_TABLE = "girls_group_tbl";
    public   static final int     DB_VERSION = 1;
    
	private static final String DB_CREATE_TABLE = 
		 "CREATE TABLE " +   DB_TABLE + 
        " (_id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "group_name   TEXT  NOT NULL, " +
        "group_number  INTEGER, " + 
        "group_company TEXT, " +
        "updated_time INTEGER, " +
        "created_time  INTEGER);";
	
    public GirlsGroupDBOpenHelper(final Context context) {
         super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public  void onCreate(final SQLiteDatabase db){
   	 try{
   		 db.execSQL(DB_CREATE_TABLE);
   		
   	 }catch(SQLException e){
   		 Log.e(DEBUG_TAG, e.toString());
   	 }
    }       
    @Override
    public  void onOpen(final SQLiteDatabase db){
   	  super.onOpen(db);
    }        
    public  void onUpgrade(final SQLiteDatabase db, 
   		        final int oldVersion, final int newVersion){
   	  
   	  db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
   	  this.onCreate(db);
    }
}
