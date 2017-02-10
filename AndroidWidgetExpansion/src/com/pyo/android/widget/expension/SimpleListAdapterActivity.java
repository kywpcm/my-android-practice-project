package com.pyo.android.widget.expension;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleListAdapterActivity extends ListActivity {
	private String[]	items	=  {"리스트뷰1", "리스트뷰2"};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.list_activity_simple_adapter);
        
        ListView  listView = this.getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //ListActivity는 이렇게 간단하게 처리 됨
        setListAdapter(new ArrayAdapter<String>
             (this,android.R.layout.simple_list_item_single_choice, items));
    }
	
	//ListActivity적용시  어댑터 아이템 이벤트 처리.
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		 TextView tv = (TextView)v;
		 Toast.makeText(getApplicationContext(), tv.getText(), 2000).show();
	}
}