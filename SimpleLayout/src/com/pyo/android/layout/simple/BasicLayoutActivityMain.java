package com.pyo.android.layout.simple;

import java.util.Set;
import java.util.TreeMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BasicLayoutActivityMain extends ListActivity{
	
  private TreeMap<String,Intent> layoutActions = new TreeMap<String,Intent>();                
  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	addActionMap("1.���Ͼ�̾ƿ�",LinearLayoutActivity.class);
	addActionMap("2.���뷹�̾ƿ�",AbsoluteLayoutActivity.class);
	addActionMap("3.�����ӷ��̾ƿ�",FrameLayoutActivity.class);
	addActionMap("4.��뷹�̾ƿ�",RelativeLayoutActivity.class);
	addActionMap("5.���̺��̾ƿ�",TableLayoutActivity.class);
	
	Set<String> keys = layoutActions.keySet();
	String [] keyNames = new String[keys.size()];
	keyNames = keys.toArray(keyNames);
	
	setListAdapter(new ArrayAdapter<String>(this,
			         android.R.layout.simple_list_item_1, keyNames));
  }
  public  void  addActionMap(String keyName, Class<?> className){
	  layoutActions.put(keyName,new Intent(this, className));
  }
  public  void onListItemClick(ListView listView, View item, int position, long id){
		String keyName =  (String)listView.getItemAtPosition(position);
		startActivity(layoutActions.get(keyName));
  }
}