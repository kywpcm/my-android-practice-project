package com.pyo.sqlite.fianalsqliteproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MediaStoreGirlsBaseActivty extends Activity {
    protected GirlsGroupDBHelper3 girlsDBHelper;
    protected SQLiteDatabase   girlsDB;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        girlsDBHelper = new GirlsGroupDBHelper3(getApplicationContext());
        //쓰기 가능한 DB Instance 객체를 얻는다
        girlsDB = girlsDBHelper.getWritableDatabase();
    }
	@Override
	public  void onDestroy(){
		super.onDestroy();
		if(girlsDB != null){
			girlsDB.close();
		}
		if(girlsDBHelper != null){
			girlsDBHelper.close();
		}
	}
}