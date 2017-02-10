package com.pyo.sqlite.girl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.pyo.sqlite.girl.GirlsGroupDB.GirlsGroupInfo;
import com.pyo.sqlite.girl.GirlsGroupDB.GirlsGroupMusic;

//����Ʈ�� ���� �� ��, Adapter�� ���� ���� �ʴ�..
public class GirlsGroupListActivity extends GirlsGroupBaseActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.girls_list_layout);	   

		displayGirlsGroupList();

		final Button finishBtn = (Button)findViewById(R.id.historyBackBtn);
		finishBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void displayGirlsGroupList(){
		final TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayoutGroupList);
		
		SQLiteDatabase dbHandler = girlsDB.getReadableDatabase();

		//������ �� ���̺� �̻��� JOIN ���� �۾��� �� ���� SQLiteQueryBuilder ��ü�� ����Ѵ�.
		//�ɱ׷� ���̺��� _id�� ���� ���̺��� �ɱ׷� ID ���� �ش��ϴ� �����͸� �������� ������ ����
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(GirlsGroupMusic.TABLE_NAME + ", " + GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(GirlsGroupMusic.TABLE_NAME+"."+GirlsGroupMusic.GIRLS_GROUP_ID 
				+ "=" + GirlsGroupInfo.TABLE_NAME+"."+GirlsGroupInfo._ID);

		//��� �������� ������ �÷��� �̸���(2�� �̻� ���̺�� ����� Ǯ������ �־�� ��)
		String columnsToReturn [] = {
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo.TEAM_NAME
		};
		
		//����� ������ �ش� ��� ������ ���� �´�
		Cursor joinResultSet = queryBuilder.query(dbHandler,
				columnsToReturn,null,null,null,null,
				GirlsGroupMusic.SORT_ORDER);
		
		//Adapter�� ���� �ʾƼ� ��� Ŀ�� �������� ���� �κ� �ڵ尡 �ſ� ����������..
		if(joinResultSet.moveToFirst()){
			int resultSetSize = joinResultSet.getCount();
			
			Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
			int rgbValue = Color.rgb(0,0,0);

			for(int i = 0; i < resultSetSize; i++){
				//���̺� ���̾ƿ��� ���ڵ� ������ �߰� �Ѵ�
				TableRow insertRow = new TableRow(this);
				TextView musicName = new TextView(this);
				TextView groupName = new TextView(this);

				musicName.setTypeface(typeface);
				musicName.setTextSize(14);
				musicName.setTextColor(rgbValue);
				groupName.setTypeface(typeface);
				groupName.setTextSize(14);
				groupName.setTextColor(rgbValue);

				//TableRow ��ü�� �ش� ID�� �±׷� ���� ���߿� ���̾ƿ����� ���� ���� �����ϱ� ����
				insertRow.setTag(joinResultSet.getLong(joinResultSet.getColumnIndex(GirlsGroupMusic._ID)));

				//�� ���ڵ��� ���� �ش� Widget�� ���� �Ѵ�
				musicName.setText(joinResultSet.getString(joinResultSet.getColumnIndex(GirlsGroupMusic.MUSIC_TITLE)));
				groupName.setText(joinResultSet.getString(joinResultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME)));

				//�ɱ׷��� ���� �� �� ȣ�� �� ��ư ����
				Button deleteBtn = new Button(this);  //����° �÷�
				deleteBtn.setText("����");
				deleteBtn.setTypeface(typeface);
				
				//ID���� ���߿� ���� ��ư�� ��ġ �� �� ���ϰ� �����ϵ��� Tag�� �����Ѵ�
				deleteBtn.setTag(joinResultSet.getLong(joinResultSet.getColumnIndex(GirlsGroupInfo._ID)));
				deleteBtn.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View btn){
						
						//������ ID���� Button Tag���� ���� �´�
						Long id = (Long)btn.getTag();
						String toastMessage = null;
						if(!deleteGirlsGroup(id)){ //���� �޼ҵ� ȣ��
							toastMessage = "�ɰ��� ���� �߻�! LogCat���� Ȯ�� �ٶ�!";
						}else{
							toastMessage = "���������� ���� �Ǿ���!";
							//ID���� �±׷� ������ TableRow ��ü�� ã�ƿ´�
							TableRow removeRow = (TableRow)tableLayout.findViewWithTag(id);
							//���� �������� �ش� ���� ���� �Ѵ�
							tableLayout.removeView(removeRow);
						}
						Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
					}
				});
				
				//TableRow�� �ش� ������ ����
				insertRow.addView(musicName);
				insertRow.addView(groupName);
				insertRow.addView(deleteBtn);

				//TableRow�� TableLayout�� �߰�
				tableLayout.addView(insertRow);
				joinResultSet.moveToNext();
			} //for�� ��
		} else{
			TableRow insertRow = new TableRow(this);
			TextView  noRecord = new TextView(this);
			noRecord.setText("��ϵ� ������ ����!");
			insertRow.addView(noRecord);
			tableLayout.addView(insertRow);
		}
		
		joinResultSet.close();
		dbHandler.close();
	}
	
	private boolean deleteGirlsGroup(Long groupID){
		SQLiteDatabase dbHandler = null;
		boolean flag = false;
		try{
			dbHandler = girlsDB.getWritableDatabase();
			dbHandler.beginTransaction();
			dbHandler.delete(GirlsGroupInfo.TABLE_NAME,
					GirlsGroupInfo._ID + "=?", new String[]{String.valueOf(groupID)});
			dbHandler.delete(GirlsGroupMusic.TABLE_NAME,
					GirlsGroupMusic._ID + "=?", new String[]{String.valueOf(groupID)});
			dbHandler.setTransactionSuccessful();
			flag = true; //���� ó��
		}catch(SQLiteException sql){
			Log.e("deleteGirlsGroup_ERROR", sql.toString());
		}finally{
			dbHandler.endTransaction();
			dbHandler.close();
		}
		return flag;
	}
	
}