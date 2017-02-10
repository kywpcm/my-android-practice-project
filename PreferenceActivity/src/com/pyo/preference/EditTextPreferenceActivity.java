/*
 *  EditText 환경설정 값
 */
package com.pyo.preference;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class EditTextPreferenceActivity extends PreferenceActivity {
	private  EditTextPreference editTextPrefer;
	public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       addPreferencesFromResource(R.xml.edit_text_preference_screen);
	       
	       editTextPrefer = (EditTextPreference)this.findPreference("edit_text_input_item_key");
	
	       editTextPrefer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				//현재 대화상자가 닫혔을 때 동작
				editTextPrefer.getDialog().setOnDismissListener(dismissListener);
				return true;
			}
		   });
	}
	private DialogInterface.OnDismissListener dismissListener = new DialogInterface.OnDismissListener(){
	   public void onDismiss(DialogInterface dialog){ 
		    //현재 환경설정되어 있는 값을 가져옴 
		    //editTextPrefer.getText();
		   
		    //사용자가 입력한 값을 가져옴
		   int inputDigitValue = 0 ;
		   try{
		      inputDigitValue = Integer.parseInt(editTextPrefer.getEditText().getText().toString());
		      //입력값이 정수값이면 직접 입력 한다
			  if(inputDigitValue >= 0 || inputDigitValue < 0){
			    //다음과 같이 현재 어플의 공유 선호 설정을 가져 올 수 있음
			    SharedPreferences contextPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			        
			     //현재 환경설정을 공유선호설정으로 가져오는 방법
			        /*
			         * String preferenceName =  getPreferenceManager().getSharedPreferencesName();
			         * SharedPreferences contextPreference = EditTextPreferenceActivity.this.getSharedPreferences(preferenceName,0);
			         */
			        //SharedPreferences contextPreference = EditTextPreferenceActivity.this.getSharedPreferences("com.pyo.preference_preferences",0);
			        //SharedPreferences contextPreference = getPreferenceManager().getSharedPreferences();
			        	        
			        SharedPreferences.Editor editor = contextPreference.edit();
			        editor.putString(editTextPrefer.getKey(), String.valueOf(inputDigitValue));
			        editor.commit();
			        Toast.makeText(EditTextPreferenceActivity.this, "현재 설정된 값은 [" + 
			        		inputDigitValue + "] 입니다", Toast.LENGTH_LONG).show();
			  }
		   }catch(NumberFormatException nfe){
			 //수치가 아니면 다시 대화상자를 뛰움
		    	editTextPrefer.getDialog().show();
		    	Toast.makeText(EditTextPreferenceActivity.this,
		    			"정수값[ " + nfe.getMessage() + "] 을 입력해 주세요 ", Toast.LENGTH_LONG).show();
		   }
	   }
	};
}
