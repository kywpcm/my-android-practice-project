package com.pyo.android.anim;

import java.util.TreeMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AnimationMainActivity extends ListActivity { 
	private TreeMap<String, Intent> actions = new TreeMap<String, Intent>();
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		String key = (String) l.getItemAtPosition(position);
		startActivity(actions.get(key));
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		prepareActivityList();

		String[] keys = actions.keySet().toArray(
				new String[actions.keySet().size()]);

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, keys));
	}	
	private void addInsertItem(String label, Class<?> className){
		actions.put(label, new Intent(this, className	));
	}
    private void prepareActivityList() {
     addInsertItem("1.FrameByFrame_ImageView",FrameByFrameAnimationActivity1.class);
     addInsertItem("2.FrameByFrame_Switcher",FrameByFrameAnimationActivity2.class);
     addInsertItem("3,FrameByFrame XML ", FrameByFrameAnimationActivity3.class);
     addInsertItem("4.트위닝애니메이션",TweenAnimationActivity.class);
     addInsertItem("5.리스트뷰애니메이션적용",ListViewAnimationActivity.class);
     addInsertItem("6.레이아웃애니메이션적용",LayoutAnimationActivity.class);
     addInsertItem("7.인터폴레이터 적용",InterPolatorAnimationActivity.class);
     addInsertItem("8.변환행렬을이용한애니메이션",TransformationMatrixAnimationActivity.class);
     addInsertItem("9.Camera를이용한애니메이션",TransformationCameraAnimationActivity.class);
  }
}