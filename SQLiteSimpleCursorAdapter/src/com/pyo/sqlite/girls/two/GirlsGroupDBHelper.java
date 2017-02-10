package com.pyo.sqlite.girls.two;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//�������� Database�� ��Ȱ�� �ڵ鸵�� ���� ���Ǹ� ���� �� �ִ� SQLiteHelper Ŭ������ ��ӹ޾� ����..
public class GirlsGroupDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "girlsGroupDB.db";
	private static final int DB_VERSION = 1;

	public GirlsGroupDBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
		Log.i("GirlsGroupDBHelper", "GirlsGroupDBHelper() ������");
	}

	//DB���� ��� �� ��Ű���� ���� ��
	//shell ��ɾ�� ���� �����ص� ��� ����
	//DB ��Ű���� ������� �ʿ䰡 �־ ������� �� �ݹ�..
	@Override
	public void onCreate(SQLiteDatabase girlsGroupDB) {
		Log.i("SQLiteOpenHelper", "onCreate()");
		
		StringBuilder  groupTBLSQL = new StringBuilder();
		StringBuilder  musicTBLSQL = new StringBuilder();

		groupTBLSQL
		.append("CREATE TABLE ")
		.append(GirlsGroupDB.GirlsGroupInfo.TABLE_NAME )
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
		.append(" INTEGER")
		.append(");");

		girlsGroupDB.execSQL(groupTBLSQL.toString());
		girlsGroupDB.execSQL(musicTBLSQL.toString());
	}

	//�ʿ��ϴٸ� DB Instance�� ������Ʈ �Ѵ�
	@Override
	public void onUpgrade(SQLiteDatabase girlsGroupDB, int oldVersion, int newVersion) {}

	@Override
	public void onOpen(SQLiteDatabase girlsGroupDB){
		super.onOpen(girlsGroupDB);
	}  

}

