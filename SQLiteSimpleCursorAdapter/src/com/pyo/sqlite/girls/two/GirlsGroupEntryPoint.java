package com.pyo.sqlite.girls.two;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.pyo.sqlite.girls.two.GirlsGroupDB.GirlsGroupInfo;
import com.pyo.sqlite.girls.two.GirlsGroupDB.GirlsGroupMusic;

public class GirlsGroupEntryPoint extends GirlsGroupBaseActivity{

	private static final String TAG = "GirlsGroupEntryPoint";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate()");
		setContentView(R.layout.girls_group_layout);

		//��Ƽ��Ƽ ȭ���� ������ DB�� ���� �ϴ� �̺�Ʈ
		Button saveBtn = (Button)findViewById(R.id.girlsGroupInsert);
		saveBtn.setOnClickListener(girlsSaveListener);

		//��ü �ɱ׷��� ����Ʈ�� ����ϴ� ����Ʈ ���� ��ư
		Button displayBtn = (Button)findViewById(R.id.girlGroupDisplayBtn);
		displayBtn.setOnClickListener(new  OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), GirlsGroupListActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//��ϵ� �ɱ׷��� �̸����� �̿��Ͽ� �ڵ� �ϼ� ��� ����
		applyAutoCompleteFromTBLGirlsGroupInfo();
	}

	//�ش� ���̺��� ��ϵ� �ɱ׷��� �̸��� ArrayAdatper�� ä��
	private void applyAutoCompleteFromTBLGirlsGroupInfo(){
		
		//�б� ������ DB�� ��´�.
		SQLiteDatabase dbHandler = girlsDB.getReadableDatabase();
		
		//������ ���� �Ѵ�.
		Cursor resultSet = dbHandler.query(
				GirlsGroupInfo.TABLE_NAME,
				new String[]{GirlsGroupInfo.TEAM_NAME},
				null, null, null, null,
				GirlsGroupInfo.SORT_ORDER);

		//Activity�� Ŀ����ü�� ������ ����
		//deprecated �ƴ�..
		//Ÿ���� API 17�� ����ָ� �� �� ������ ���� �״´�..
		//�ϴ� �ذ�å���� Cursor�� �������� ���� �ݾƾ� �ڴ�..
//		startManagingCursor(resultSet);
		
		//��ϵ� �ɱ׷��� ������ ���� �´�
		int numberOfGroupNames = resultSet.getCount();
		String[] numberOfAutoText = new String[numberOfGroupNames];

		if(numberOfGroupNames > 0){
			resultSet.moveToFirst();
			for(int i = 0 ; i < numberOfGroupNames ; i++){
				/*************************************************************************
				 *  ���� ����� �������� �÷��� �̸��� �̿��� ���� �÷��� ��ġ�� �ľ��ϰ�
				 *  Ŀ���� ���� ��ġ������ ���� ���� �����´�
				 *************************************************************************/
				Log.d(TAG, "applyAutoCompleteFromTBLGirlsGroupInfo()\n" +
						"���� Ŀ�� ��ġ : " + resultSet.getPosition());
				numberOfAutoText[i] = resultSet.getString(resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
				Log.d(TAG, "resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME)\n" + 
						"col_team_name�� �÷� �ε��� : " + resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
				//���� ����� ���� ������ Ŀ���� �̵� ��Ų��
				resultSet.moveToNext();
			}
			
			ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<String>(
							this,
							android.R.layout.simple_dropdown_item_1line,
							numberOfAutoText);
			
			//AutoCompleteTextView�� ���� DB���� ������ ������ �������� �ڵ� �ϼ� ����� �߰� �Ѵ�
			AutoCompleteTextView autoText = (AutoCompleteTextView)findViewById(R.id.autoTextGirlsGroupFinder);
			autoText.setAdapter(autoTextAdapter);
		}

		resultSet.close();
		dbHandler.close();
	}

	private OnClickListener girlsSaveListener = new OnClickListener(){
		
		public void onClick(View  button){
			
			final EditText girlsGroupMusicName = (EditText)findViewById(R.id.musicNameEdit);
			final EditText girlsGroupName = (EditText)findViewById(R.id.autoTextGirlsGroupFinder);
			
			//�� ���̺� ���ڵ带 �����ϱ� ����
			SQLiteDatabase dbHandler = girlsDB.getWritableDatabase();

			//Ʈ������� ����
			dbHandler.beginTransaction();
			Cursor resultExist = null;
			//�������̺�� �Է��Ͽ� �ܷ�Ű�� ȿ���� �ֱ� ����
			long girlGroupID = 0 ;
			try{
				String groupName = girlsGroupName.getText().toString();

				//�ɱ׷��̸��� ��ϵǾ����� Ȯ���� ���� ���� ����
				SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
				queryBuilder.setTables(GirlsGroupInfo.TABLE_NAME);
				queryBuilder.appendWhere(GirlsGroupInfo.TEAM_NAME + "='" + groupName + "'");
				
				//����� ������ ��������� �˾� ����
				resultExist = queryBuilder.query(dbHandler,null,null,null,null,null,null);
				
				if(resultExist.getCount() == 0){ //�׷���� ���� ���� �ʴ´ٸ�
					//�ɱ׷��� �߰� �Ѵ�.
					ContentValues groupNameValues = new ContentValues();
					groupNameValues.put(GirlsGroupInfo.TEAM_NAME, groupName);  // �÷���/�Ӽ����� key/value ����..

					//���� ����� �׷��̸��� ID ���� ���� �´�(�����ô� -1)
					girlGroupID = dbHandler.insert(GirlsGroupInfo.TABLE_NAME, "NODATA", groupNameValues);
				} else{ //�׷���� ���� �Ѵٸ� _ID���� ���� �´�
					resultExist.moveToFirst();
					Log.d(TAG, "resultExist.moveToFirst();\n" +
							" ���� Ŀ�� ��ġ : " + resultExist.getPosition());
					girlGroupID = resultExist.getLong(resultExist.getColumnIndex(GirlsGroupInfo._ID));
					Log.d(TAG, "resultExist.getColumnIndex(GirlsGroupInfo._ID)\n" + 
							" _id �÷� �ε��� : " + resultExist.getColumnIndex(GirlsGroupInfo._ID));
				}
				
				resultExist.close();

				//MUSIC ���̺� �ɱ׷��� ���ǰ� ID���� �߰� �Ѵ�.
				ContentValues addMusicRecord = new ContentValues();
				addMusicRecord.put(GirlsGroupMusic.MUSIC_TITLE, girlsGroupMusicName.getText().toString());
				addMusicRecord.put(GirlsGroupMusic.GIRLS_GROUP_ID, girlGroupID);
				dbHandler.insert(GirlsGroupMusic.TABLE_NAME, "NODATA", addMusicRecord);

				//Ʈ����� ���� ����
				dbHandler.setTransactionSuccessful();
				Toast.makeText(GirlsGroupEntryPoint.this, "���� ó�� �Ǿ����ϴ�!", Toast.LENGTH_LONG).show();
			} catch(SQLiteException sqle){
				Log.e("girlsSaveListenerERROR", sqle.toString());
				Toast.makeText(GirlsGroupEntryPoint.this,
						"�ɰ��� ���� �߻�!LogCat Ȯ�� �ٶ�!", 
						Toast.LENGTH_LONG).show();
			} finally{
				dbHandler.endTransaction();
				dbHandler.close();
				resultExist.close();
			}
			
			girlsGroupMusicName.setText("");
			girlsGroupName.setText("");
		}
	};

}