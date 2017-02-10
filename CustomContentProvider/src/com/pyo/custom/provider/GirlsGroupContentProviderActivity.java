/*
 *  Simple ContentProvider Example
 *  author PYO IN SOO
 */
package com.pyo.custom.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GirlsGroupContentProviderActivity extends Activity{
	private EditText addName;
	private EditText addNumber;
	private EditText addCompany;
	private EditText editName;
	private EditText editNumber;
	private EditText editCompany;
	private Button   addButton;
	private Button   editButton;
	private ListView listView;
	
	//DB에서의 PK 값
	private long itemId;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_explorer);
        
        addName = (EditText)findViewById(R.id.add_name);
        addNumber  = (EditText)findViewById(R.id.add_number);
        addCompany = (EditText)findViewById(R.id.add_company);
        editName  = (EditText)findViewById(R.id.edit_name);
        editNumber = (EditText)findViewById(R.id.edit_number);
        editCompany = (EditText)findViewById(R.id.edit_company);
        addButton = (Button)findViewById(R.id.add_button);
        listView = (ListView)findViewById(R.id.provider_list);
        
        //추가
        addButton.setOnClickListener(new View.OnClickListener(){
        	public  void onClick(final View editView){
        		insertGirlsInfo();
        	}
        });
        //갱신
        editButton = (Button)findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				 editGirlsInfo();
			}
		});
        listView = (ListView)findViewById(R.id.provider_list);
        Uri uri = GirlsGroupContentProviderInfo.CONTENT_URI;
        uri = uri.buildUpon().appendPath(GirlsGroupContentProviderInfo.PATH_MULTIPLE).build();
   
        Cursor cursor = managedQuery(uri, null, null, null, null);
        final SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.girls_list_view_layout,
                cursor,
                new String[]{
                		GirlsGroupContentProviderInfo.GirlsGroupColumnInfo._ID,
                		GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME,
                		GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER,
                		GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY
                },
               new int[]{
    	             R.id.girls_id,
    	             R.id.girls_group_name,
    	             R.id.girls_group_number,
    	             R.id.girls_group_company
                }
          );
         listView.setAdapter(cursorAdapter);
         startManagingCursor(cursor);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			    //현재 포인터로 가리키는 커서를 가져온다
				Cursor cursor = (Cursor)parent.getItemAtPosition(position);
				AlertDialog diaBox = createDialogBox(id,cursor);
				diaBox.show();
			}
         });
    }
    private  AlertDialog createDialogBox(final long id,final Cursor cursor){
    	AlertDialog myQuittingDialogBox = 
    		
   		 //set message, title, and icon
   		 new AlertDialog.Builder(this).setTitle("선택")
   		.setMessage("다음 중 하나를 선택하세요?")
       
   		//옵션버튼 등록
   		.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
   			public void onClick(DialogInterface dialog, int whichButton){
   				deleteGirlsInfo(id);
   			}
   		}).setNeutralButton("수정",new DialogInterface.OnClickListener(){
   			public void onClick(DialogInterface dialog, int whichButton){
   			   itemId = id;
   			   String groupName = cursor.getString(cursor.getColumnIndex(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME));
			    String groupNumber = 
			    	String.valueOf(cursor.getInt(cursor.getColumnIndex(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER)));
			    String groupCompany = cursor.getString(cursor.getColumnIndex(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY));
			    editName.setText(groupName);
			    editNumber.setText(groupNumber);
			    editCompany.setText(groupCompany);
   			}
   		}).setNegativeButton("편집취소", new DialogInterface.OnClickListener(){
   			public void onClick(DialogInterface dialog, int whichButton){
   				addName.requestFocus();
   			}
   		}).create();
   		
   		return myQuittingDialogBox;
    }
    private void insertGirlsInfo(){
    	 Uri uri = GirlsGroupContentProviderInfo.CONTENT_URI;
    	 uri = uri.buildUpon().appendPath(GirlsGroupContentProviderInfo.PATH_MULTIPLE).build();
    	 ContentValues values = new ContentValues();
         values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME, 
        		                     addName.getText().toString());
         values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER, 
        		                     addNumber.getText().toString());
         values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY, 
        		                     addCompany.getText().toString());
         values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.CREATED_TIME, 
        		                    System.currentTimeMillis());
         uri = getContentResolver().insert(uri, values);
         startActivity(new Intent(this, GirlsGroupContentProviderActivity.class));
         finish();
    }
    private void editGirlsInfo(){
    	Uri uri  = GirlsGroupContentProviderInfo.CONTENT_URI;
    	//Uri를 맞춤
    	uri = uri.buildUpon().appendPath(GirlsGroupContentProviderInfo.PATH_SINGLE_GROUP_ID).appendPath(Long.toString(this.itemId)).build();
    	//uri = uri.buildUpon().appendPath(Long.toString(this.itemId)).build();
    	ContentValues  values = new ContentValues();
    	values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME, 
                              editName.getText().toString());
        values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER, 
                              editNumber.getText().toString());
         values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY, 
                             editCompany.getText().toString());	
    	values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.UPDATED_TIME,
    			System.currentTimeMillis());
    	getContentResolver().update(uri, values, null, null);
    	
    	startActivity(new Intent(this, GirlsGroupContentProviderActivity.class));
    	finish();
    }
    private  void  deleteGirlsInfo(long id){
   
    	Uri uri = GirlsGroupContentProviderInfo.CONTENT_URI;
    	//다음과 같이 해도 됨
    	uri = ContentUris.appendId(uri.buildUpon().appendPath(
    			GirlsGroupContentProviderInfo.PATH_SINGLE_GROUP_ID), 
    			id).build();
    	getContentResolver().delete(uri, null,null);
    	
    	startActivity(new Intent(this, GirlsGroupContentProviderActivity.class));
    	finish();
    }
}