/****************************************************************************
 *  Girls Group DB�� ����ϴ� ��� Activity�� ��� ���� ���� Ŭ���� ����
 ****************************************************************************/
package com.example.sqlitecustomcursoradapter;

import android.app.Activity;
import android.os.Bundle;

public class GirlsGroupBaseActivity extends Activity {

	protected GirlsGroupDBHelper girlsDB = null;
//	protected static GirlsGroupDBHelper girlsDB = null;

	//SQLiteOpenHelper �ν��Ͻ��� �̱������� �����Ѵ�..
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		girlsDB = new GirlsGroupDBHelper(this);
		girlsDB = GirlsGroupOpenHelperManager.generateGirlsOpenHelper(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if( girlsDB != null){
//			girlsDB.close();
			GirlsGroupOpenHelperManager.setClose(girlsDB);
		}
	}
	
}