//package com.pyo.sqlite.girl;
//
//import android.content.Context;
//
////현재 프로젝트에서는 사용하지 않음..
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
