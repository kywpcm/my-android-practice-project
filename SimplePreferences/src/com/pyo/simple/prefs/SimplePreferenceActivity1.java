package com.pyo.simple.prefs;

import android.content.SharedPreferences;
import android.os.Bundle;

//처음 화면에 보이는 액티비티
public class SimplePreferenceActivity1 extends SimplePreferenceSuper {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        // 현재  이 액티비티의 전용선호 설정이 있다면 가져온다 
    	SharedPreferences privatePrefs = getPreferences(MODE_PRIVATE);
    	//존재 한다면
    	if(privatePrefs.contains(PREFERENCE_ACTIVATY_NAME) == false){
    		//새로운 전용선호설정을 위한 편집객체 획득
            SharedPreferences.Editor privateEditor = privatePrefs.edit();
            privateEditor.putBoolean("Boolean_Private", false); 
            privateEditor.putFloat("Float_Private", java.lang.Float.MAX_VALUE); 
            privateEditor.putInt("Int_Private", java.lang.Integer.MIN_VALUE);
            //현재 클래스 이름도 전용으로 설정
            privateEditor.putString(PREFERENCE_ACTIVATY_NAME, this.getLocalClassName());
            //반드시 커밋한다.[롤백은없음]
            privateEditor.commit();
    	}
    	super.onCreate(savedInstanceState);
    }
	@Override
	public Class<?> getSwitchActivityClass() {
		return SimplePreferenceActivity2.class;
	}
}