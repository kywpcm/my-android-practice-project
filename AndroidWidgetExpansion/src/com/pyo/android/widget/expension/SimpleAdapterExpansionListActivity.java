package com.pyo.android.widget.expension;

import java.util.Set;
import java.util.TreeMap;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleAdapterExpansionListActivity extends ListActivity{
	   private TreeMap<String,Intent> actions = new TreeMap<String,Intent>();
	   @Override
	   public void onCreate(Bundle bundle){
	      super.onCreate(bundle);
	      
	      addActionMap("1.BaseAdapter확장예제", TARABaseAdapterActivity.class);
	      addActionMap("2.ArrayAdapter확장예제",TARAArrayAdapterActivity.class);
	      addActionMap("3.Adapter편집가능예제", AdapterItemEditActivity.class);
	      addActionMap("4.Adapter멀티편집예제", AdapterItemMultiEditActivity.class);
	      addActionMap("5.ListActivity예제",SimpleListAdapterActivity.class);
	      addActionMap("6.Gallery사용예제", TARAGalleryAdapterActivity.class);
	      addActionMap("7.SimpleGridView예제", SimpleGridView.class);
	      addActionMap("8.CursorAdapter예제",DBContactAdapterActivity.class);
	      Set<String> keys = actions.keySet();
		  String [] keyNames = new String[keys.size()];
		  keyNames = keys.toArray(keyNames);
				
		  setListAdapter(new ArrayAdapter<String>(this,
		 	      android.R.layout.simple_list_item_1, keyNames));   
	   }
	   private  void  addActionMap(String keyName, Class<?> className){
		    actions.put(keyName,new Intent(this, className));
	   }
	   public  void onListItemClick(ListView listView, View item, int position, long id){
		    String keyName =  (String)listView.getItemAtPosition(position);
		    startActivity(actions.get(keyName));
	   }
	}
