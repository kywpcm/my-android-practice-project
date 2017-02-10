package com.pyo.sqlite.girls.two;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/****************************************************************************
 *  Girls Group DB�� ����ϴ� ��� Activity�� ��� ���� ���� Ŭ���� ����
 ****************************************************************************/
public class GirlsGroupBaseActivity extends Activity {

	protected GirlsGroupDBHelper girlsDB = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("GirlsGroupBaseActivity", "onCreate()");
		
		girlsDB = new GirlsGroupDBHelper(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("GirlsGroupBaseActivity", "onDestroy()");
		
		if( girlsDB != null){
			girlsDB.close();
		}
	}

}