//package com.pyo.sqlite.girl;
//
//import android.content.Context;
//
////���� ������Ʈ������ ������� ����..
//public class GirlsGroupOpenHelperManager {
//
//	private static GirlsGroupDBHelper dbHandler;
//
//	private GirlsGroupOpenHelperManager(Context context){
//		dbHandler = new GirlsGroupDBHelper( context);
//	}
//
//	public static GirlsGroupDBHelper  
//	generateGirlsOpenHelper(Context context){
//		if( dbHandler != null){
//			return dbHandler;
//		}else{
//			new GirlsGroupOpenHelperManager(context);
//		}
//		return generateGirlsOpenHelper(null);
//	}
//
//	public static void  setClose(GirlsGroupDBHelper dbHelper){
//		dbHelper.close();
//	}
//
//}
