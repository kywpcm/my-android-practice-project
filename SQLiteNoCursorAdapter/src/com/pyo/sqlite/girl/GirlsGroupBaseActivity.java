package com.pyo.sqlite.girl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/****************************************************************************
 *  Girls Group DB�� ����ϴ� ��� Activity�� ��� ���� ���� Ŭ���� ����
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