package com.example.sqlitecustomcursoradapter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlitecustomcursoradapter.R;
import com.example.sqlitecustomcursoradapter.GirlsGroupDB.GirlsGroupInfo;
import com.example.sqlitecustomcursoradapter.GirlsGroupDB.GirlsGroupMusic;

public class GirlsGroupEntryPoint extends GirlsGroupBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.girls_group_layout);

		//��Ƽ��Ƽ ȭ���� ������ DB�� ���� �ϴ� �̺�Ʈ
		Button saveBtn = (Button)findViewById(R.id.girlsGroupInsert);
		saveBtn.setOnClickListener(girlsSaveListener);

		//��ü �ɱ׷��� ����Ʈ�� ����ϴ� ����Ʈ ���� ��ư
		Button displayBtn = (Button)findViewById(R.id.girlGroupDisplayBtn);
		displayBtn.setOnClickListener(new  OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CustomCursorAdapterActivity.class);
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

	//�ش� ���̺��� ��ϵ� �ɱ׷��� �̸��� ArrayAdapter�� ä��
	private void applyAutoCompleteFromTBLGirlsGroupInfo(){

		//�б� ������ DB�� ��´�.
		SQLiteDatabase dbHandler = girlsDB.getReadableDatabase();
		//������ ���� �Ѵ�.
		Cursor resultSet = dbHandler.query(
				GirlsGroupInfo.TABLE_NAME,
				new String[]{GirlsGroupInfo.TEAM_NAME},
				null, null, null, null,
				GirlsGroupInfo.SORT_ORDER);
		//��ϵ� �ɱ׷��� ������ ���� �´�
		int numberOfGroupNames = resultSet.getCount();
		String numberOfAutoText [] = new String[numberOfGroupNames];

		if(numberOfGroupNames > 0) {
			resultSet.moveToFirst();
			for(int i = 0 ; i < numberOfGroupNames ; i++){
				/*************************************************************************
				 *  ���� ����� �������� �÷��� �̸��� �̿��� ���� �÷��� ��ġ�� �ľ��ϰ�
				 *  Ŀ���� ���� ��ġ������ ���� ���� �����´�
				 *************************************************************************/
				numberOfAutoText[i] = resultSet.getString(
						resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
				//���� ����� ���� ������ Ŀ���� �̵� ��Ų��
				resultSet.moveToNext();
			}

			ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_dropdown_item_1line,
					numberOfAutoText);

			//EditView�� ���� DB���� ������ ������ �������� �ڵ� �ϼ� ����� �߰� �Ѵ�
			AutoCompleteTextView autoText = (AutoCompleteTextView)findViewById(R.id.autoTextGirlsGroupFinder);
			autoText.setAdapter(autoTextAdapter);
		}

		resultSet.close();
		dbHandler.close();
	}

	private OnClickListener girlsSaveListener = new OnClickListener() {

		public void onClick(View  button) {
			final EditText girlsGroupMusicName = (EditText)findViewById(R.id.musicNameEdit);
			final EditText girlsGroupName = (EditText)findViewById(R.id.autoTextGirlsGroupFinder);

			//�� ���̺� ���ڵ带 �����ϱ� ����
			SQLiteDatabase dbHandler = girlsDB.getWritableDatabase();

			//Ʈ������� ����
			dbHandler.beginTransaction();
			Cursor resultExist  = null;

			//�������̺�� �Է��Ͽ� �ܷ�Ű�� ȿ���� �ֱ� ����
			long girlGroupID = 0 ;
			try {
				String groupName = girlsGroupName.getText().toString();

				//�ɱ׷��̸��� ��ϵǾ����� Ȯ���� ���� ���� ����
				SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
				queryBuilder.setTables(GirlsGroupInfo.TABLE_NAME);
				queryBuilder.appendWhere(GirlsGroupInfo.TEAM_NAME +  "='" + groupName + "'");

				//����� ������ ��������� �˾� ����
				//���������� ��� �� ���� ù��° ���ڿ� SQLiteDatabase��ü�� �����
				resultExist = queryBuilder.query(dbHandler,null,null,null,null,null,null);

				if(resultExist.getCount() == 0) {  //�׷���� ���� ���� �ʴ´ٸ�
					//�ɱ׷��� �߰� �Ѵ�.
					ContentValues groupNameValues = new ContentValues();
					groupNameValues.put(GirlsGroupInfo.TEAM_NAME, groupName);
					//���� ����� �׷��̸��� ID ���� ���� �´�(�����ô� -1)
					girlGroupID = dbHandler.insert(GirlsGroupInfo.TABLE_NAME, "NODATA", groupNameValues);
				} else {  //�׷���� ���� �Ѵٸ� _ID���� ���� �´�
					resultExist.moveToFirst();
					girlGroupID = resultExist.getLong(resultExist.getColumnIndex(GirlsGroupInfo._ID));
				}

				//MUSIC ���̺� �ɱ׷��� ���ǰ� ID���� �߰� �Ѵ�.
				ContentValues musicRowValues = new ContentValues();
				musicRowValues.put(GirlsGroupMusic.MUSIC_TITLE, girlsGroupMusicName.getText().toString());
				musicRowValues.put(GirlsGroupMusic.GIRLS_GROUP_ID, girlGroupID);
				dbHandler.insert(GirlsGroupMusic.TABLE_NAME, "NODATA", musicRowValues);

				//Ʈ����� ����
				dbHandler.setTransactionSuccessful();
				Toast.makeText(GirlsGroupEntryPoint.this,"���� ó�� �Ǿ����ϴ�!", Toast.LENGTH_LONG).show();
			} catch(SQLiteException sqle) {
				Log.e("girlsSaveListenerERROR", sqle.toString());
				Toast.makeText(GirlsGroupEntryPoint.this, "�ɰ��� ���� �߻�!LogCat Ȯ�� �ٶ�!", Toast.LENGTH_LONG).show();
			} finally {
				dbHandler.endTransaction();
				dbHandler.close();
				resultExist.close();
			}
			
			girlsGroupMusicName.setText("");
			girlsGroupName.setText("");
		}
	};

}