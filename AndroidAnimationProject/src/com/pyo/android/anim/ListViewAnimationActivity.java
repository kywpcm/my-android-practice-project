/*
 *  ListView에 애니메이션 적용 하기
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewAnimationActivity extends Activity {
   private String[] exampleItems = new String[] {"R", "E","V","E","R","S","E"}; 
	@Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.animation_list_layout);
	   ArrayAdapter<Object> listItemAdapter =  new ArrayAdapter<Object>(this 
			          ,android.R.layout.simple_list_item_1 ,exampleItems);
	   ListView listView = (ListView)this.findViewById(R.id.anim_list_id); 
	   listView.setAdapter(listItemAdapter);
   }
}