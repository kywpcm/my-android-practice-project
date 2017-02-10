package com.pyo.preference;

import java.util.TreeMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PreferenceMainActivity extends ListActivity {
	private TreeMap<String,Intent> actions = new TreeMap<String,Intent>(); 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareActivityList();

		String[] keys = actions.keySet().toArray(
				new String[actions.keySet().size()]);

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, keys));
    }
    private void addInsertItem(String actionName, Class<?> className){
		actions.put(actionName, new Intent(this, className));
	}
    private void prepareActivityList() {
        addInsertItem("1.ListPreferenceȰ��",ListPerferenceActivity.class);
        addInsertItem("2.CheckBoxPreferenceȰ��",CheckBoxPreferenceActivity.class);
        addInsertItem("3.EditTextPreferenceȰ��", EditTextPreferenceActivity.class);
        addInsertItem("4.PreferenceScreen��ȯ�漳��", PreferenceScreenNestedListActivity.class);
        addInsertItem("5.PreferenceCategory��ȯ�漳��",PreferenceCategoryListActivity.class);
    }
    @Override
	protected void onListItemClick(ListView lv, View v, int position, long id){
		String key = (String) lv.getItemAtPosition(position);
		startActivity(actions.get(key));
	}
}