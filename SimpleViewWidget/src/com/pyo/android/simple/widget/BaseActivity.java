package com.pyo.android.simple.widget;

import java.util.Set;
import java.util.TreeMap;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class BaseActivity extends ListActivity {
	private TreeMap<String,Intent> actions = new TreeMap<String,Intent>();

	@Override
	protected void onCreate(Bundle savedInstanceState){
	 super.onCreate(savedInstanceState);
		
		displayActivityList();
		Set<String> keys = actions.keySet();
		String [] keyNames = new String[keys.size()];
		keyNames = keys.toArray(keyNames);
		
		setListAdapter(new ArrayAdapter<String>(this,
				         android.R.layout.simple_list_item_1, keyNames));
	
	}
	public abstract void displayActivityList();
	
	public  void  addActionMap(String keyName, Class<?> className){
		actions.put(keyName,new Intent(this, className));
	}
	public  void onListItemClick(ListView listView, View item, int position, long id){
		String keyName =  (String)listView.getItemAtPosition(position);
		startActivity(actions.get(keyName));
	}
}