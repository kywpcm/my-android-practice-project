
package com.example.sqlitecustomcursoradapter;

import android.content.Context;

public class GirlsGroupOpenHelperManager {
	
	private static GirlsGroupDBHelper dbHandler;

	//老馆 积己磊
	private GirlsGroupOpenHelperManager(Context context){
		dbHandler = new GirlsGroupDBHelper( context);
	}
	
	//教臂沛 菩畔
	public static GirlsGroupDBHelper generateGirlsOpenHelper(Context context){
		if( dbHandler != null) {
			return dbHandler;
		} else {
			new GirlsGroupOpenHelperManager(context);
		}
		return generateGirlsOpenHelper(null);
	}
	
	public static void setClose(GirlsGroupDBHelper dbHelper){
		dbHelper.close();
	}
	
}
