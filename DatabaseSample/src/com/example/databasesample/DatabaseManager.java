package com.example.databasesample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.example.databasesample.DBConstant.PersonTable;

public class DatabaseManager {
	private static final String TAG = "DatabaseManager";

	Context mContext;

	DatabaseOpenHelper mHelper;

	public interface OnQueryCompleteListener {
		public void onCompleted(ArrayList<Person> personlist);
	}

	private static DatabaseManager instance;
	public static DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	private DatabaseManager() {
		mContext = MyApplication.getContext();
		mHelper = new DatabaseOpenHelper(mContext);
	}

	public void savePerson(Person person) {
		SQLiteDatabase db = mHelper.getWritableDatabase();  //db 열기, 쓰기 모드로..

		//ContentResolver가 처리할 수 있는 데이터를 저장하는 클래스이다.
		ContentValues values = new ContentValues();

		values.put(PersonTable.NAME, person.name);  //key, value
		values.put(PersonTable.ADDRESS, person.address);

		db.insert(PersonTable.TABLE_NAME, null, values);  //내용 넣고,
		db.close();  //닫기
	}

	public void getPersonTable(final OnQueryCompleteListener listener, final Handler handler) {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.i(TAG, "다른 스레드");

				final ArrayList<Person> list = new ArrayList<Person>();
				
				SQLiteDatabase db = mHelper.getReadableDatabase();  //db 열기, 읽기 모드로..
				String[] columns = {PersonTable.ID, PersonTable.NAME, PersonTable.ADDRESS};

				Cursor c = db.query(PersonTable.TABLE_NAME, columns, null, null, null, null, null);  //커서 얻어와서,
				int indexID = c.getColumnIndex(PersonTable.ID);  //컬럼 인덱스를 구한 뒤,
				int indexName = c.getColumnIndex(PersonTable.NAME);
				int indexAddress = c.getColumnIndex(PersonTable.ADDRESS);

				while(c.moveToNext()) {  //커서를 이동시키면서,
					Person p = new Person();
					p.id = c.getInt(indexID);  //컬럼 인덱스를 통해 데이터를 뽑아낸다.
					p.name = c.getString(indexName);
					p.address = c.getString(indexAddress);
					list.add(p);
				}
				
				c.close();  //다 쓰고, 커서 닫기
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						Log.i(TAG, "메인 스레드");
						listener.onCompleted(list);
					}
				});
			}
		}).start();
	}
	
}
