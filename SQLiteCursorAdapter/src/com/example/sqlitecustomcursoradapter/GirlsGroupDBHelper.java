package com.example.sqlitecustomcursoradapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GirlsGroupDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "girlsGroupDB.db";
	private static final int DB_VERSION = 1;

	public GirlsGroupDBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	//DB에서 사용 할 스키마을 생성 함
	//shell 명령어로 직접 생성해도 상관 없음
	@Override
	public void onCreate(SQLiteDatabase girlsGroupDB) {
		StringBuilder groupTBLSQL = new StringBuilder();
		StringBuilder musicTBLSQL = new StringBuilder();
		
		groupTBLSQL
		.append("CREATE TABLE ")
		.append(GirlsGroupDB.GirlsGroupInfo.TABLE_NAME)
		.append(" (")
		.append(GirlsGroupDB.GirlsGroupInfo._ID)
		.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
		.append(GirlsGroupDB.GirlsGroupInfo.TEAM_NAME)
		.append(" TEXT")
		.append(");");
		musicTBLSQL
		.append("CREATE TABLE ")
		.append(GirlsGroupDB.GirlsGroupMusic.TABLE_NAME)
		.append(" (")
		.append(GirlsGroupDB.GirlsGroupMusic._ID)
		.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
		.append(GirlsGroupDB.GirlsGroupMusic.MUSIC_TITLE)
		.append(" TEXT, ")
		.append(GirlsGroupDB.GirlsGroupMusic.GIRLS_GROUP_ID)
		.append(" LONG")
		.append(");");
		
		girlsGroupDB.execSQL(groupTBLSQL.toString());
		girlsGroupDB.execSQL(musicTBLSQL.toString());
	}
	
	//필요하다면 DB Instance를 업데이트 한다
	@Override
	public void onUpgrade(SQLiteDatabase girlsGroupDB, int oldVersion, int newVersion) {}
	
	@Override
	public void onOpen(SQLiteDatabase girlsGroupDB){
		super.onOpen(girlsGroupDB);
	}
	
}