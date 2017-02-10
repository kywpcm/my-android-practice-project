/*
 *  ListPerference 환경설정
 */
package com.pyo.preference;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class ListPerferenceActivity extends PreferenceActivity {
   
   //현재 쇼핑옵션 리스트 값
   private CharSequence [] optionValues;
   //현재 설정된 키의 옵션리스트 인덱스 값
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
       Toast.makeText(getApplicationContext(), " 현재 설정된 쇼핑 옵션 내용은 [" +
    		      optionValues[defaultValue] + "] 입니다.", Toast.LENGTH_LONG).show();
   }
	/*
	 *  현재 설정된 옵션목록이 변경 될 때 호출 됨
	 */
   private OnPreferenceChangeListener preferenceChangeImpl = new OnPreferenceChangeListener(){
	   //현재 라디오버튼리스트의 인덱스 값
	   @Override
	   public boolean onPreferenceChange(Preference preference,Object newValue){
		   defaultValue = Integer.parseInt(newValue.toString());
		   Toast.makeText(getApplicationContext(), " 변경 후 설정된 쇼핑 옵션 내용은 [" +
	    		      optionValues[defaultValue] + "] 입니다.", Toast.LENGTH_LONG).show();
		   return true;
	   }
   };
}
