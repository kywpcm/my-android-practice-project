/*
 *  멀티미디어 예제 종합
 */
package com.pyo.android.multimedia;

import java.util.Set;
import java.util.TreeMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidMultiMediaListActivity extends ListActivity {
	
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
	public  void displayActivityList(){
		addActionMap("1.인텐트를 이용한 이미지 캡쳐", CameraPhoneGalleryIntentActivity.class);
		addActionMap("2.카메라직접구현(SurfaceView)", CameraSurfaceViewActivity.class);
		addActionMap("3.AudioMediaPlayerActivity", AudioMediaPlayerActivity.class);
		addActionMap("4.AndroidVideoViewUsing",AndroidVideoViewUsingActivity.class);
		addActionMap("5.VideoViewSurfaceUsing", VideoViewSurfaceUsingActivity.class);
		addActionMap("6.VideoRecorderIntentActivity", VideoRecorderIntentActivity.class);
		addActionMap("7.VideoRecorderSurfaceUsing", VideoRecorderSurfafceUsingActivity.class);
		addActionMap("8.AudioRecorderIntentActivity", AudioRecorderIntentActivity.class);
		addActionMap("9.AudioRecorderActivity", AudioRecorderActivity.class);
		addActionMap("9_1.AudioTrackSineWave", AudioTrackSineWaveActivity.class);
		addActionMap("9_2.SoundPoolUsing",SoundPoolUsingActivity.class);
	}
	public  void  addActionMap(String keyName, Class<?> className){
		actions.put(keyName,new Intent(this, className));
	}
	public  void onListItemClick(ListView listView, View item, int position, long id){
		String keyName =  (String)listView.getItemAtPosition(position);
		startActivity(actions.get(keyName));
	}
}