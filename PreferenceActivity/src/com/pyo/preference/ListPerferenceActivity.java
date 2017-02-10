/*
 *  ListPerference ȯ�漳��
 */
package com.pyo.preference;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class ListPerferenceActivity extends PreferenceActivity {
   
   //���� ���οɼ� ����Ʈ ��
   private CharSequence [] optionValues;
   //���� ������ Ű�� �ɼǸ���Ʈ �ε��� ��
   private int defaultValue;
   private ListPreference shoppingOptionList;
	@Override
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       addPreferencesFromResource(R.xml.list_preference_screen);
       
       shoppingOptionList = (ListPreference)this.findPreference("selected_shopping_sort_option");
       
       shoppingOptionList.setOnPreferenceChangeListener(preferenceChangeImpl);
       optionValues = shoppingOptionList.getEntries();
       int defaultValue = Integer.parseInt(shoppingOptionList.getValue());
       Toast.makeText(getApplicationContext(), " ���� ������ ���� �ɼ� ������ [" +
    		      optionValues[defaultValue] + "] �Դϴ�.", Toast.LENGTH_LONG).show();
   }
	/*
	 *  ���� ������ �ɼǸ���� ���� �� �� ȣ�� ��
	 */
   private OnPreferenceChangeListener preferenceChangeImpl = new OnPreferenceChangeListener(){
	   //���� ������ư����Ʈ�� �ε��� ��
	   @Override
	   public boolean onPreferenceChange(Preference preference,Object newValue){
		   defaultValue = Integer.parseInt(newValue.toString());
		   Toast.makeText(getApplicationContext(), " ���� �� ������ ���� �ɼ� ������ [" +
	    		      optionValues[defaultValue] + "] �Դϴ�.", Toast.LENGTH_LONG).show();
		   return true;
	   }
   };
}
