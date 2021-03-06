package com.pyo.sqlite.girl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/****************************************************************************
 *  Girls Group DB를 사용하는 모든 Activity가 상속 받을 슈퍼 클래스 선언
 ****************************************************************************/
public class GirlsGroupBaseActivity extends Activity {

	protected GirlsGroupDBHelper girlsDB = null;
//	protected static GirlsGroupDBHelper girlsDB = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("GirlsGroupBaseActivity", "onCreate()");
		
		girlsDB = new GirlsGroupDBHelper(this);
//		girlsDB = GirlsGroupOpenHelperManager.generateGirlsOpenHelper(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if( girlsDB != null){
			girlsDB.close();
//			GirlsGroupOpenHelperManager.setClose(girlsDB);
		}
	}

}