/****************************************************************************
 *  Girls Group DB를 사용하는 모든 Activity가 상속 받을 슈퍼 클래스 선언
 ****************************************************************************/
package com.example.sqlitecustomcursoradapter;

import android.app.Activity;
import android.os.Bundle;

public class GirlsGroupBaseActivity extends Activity {

	protected GirlsGroupDBHelper girlsDB = null;
//	protected static GirlsGroupDBHelper girlsDB = null;

	//SQLiteOpenHelper 인스턴스를 싱글톤으로 생성한다..
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