package com.pyo.simple.prefs;

import android.content.SharedPreferences;
import android.os.Bundle;

//ó�� ȭ�鿡 ���̴� ��Ƽ��Ƽ
public class SimplePreferenceActivity1 extends SimplePreferenceSuper {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        // ����  �� ��Ƽ��Ƽ�� ���뼱ȣ ������ �ִٸ� �����´� 
    	SharedPreferences privatePrefs = getPreferences(MODE_PRIVATE);
    	//���� �Ѵٸ�
    	if(privatePrefs.contains(PREFERENCE_ACTIVATY_NAME) == false){
    		//���ο� ���뼱ȣ������ ���� ������ü ȹ��
            SharedPreferences.Editor privateEditor = privatePrefs.edit();
            privateEditor.putBoolean("Boolean_Private", false); 
            privateEditor.putFloat("Float_Private", java.lang.Float.MAX_VALUE); 
            privateEditor.putInt("Int_Private", java.lang.Integer.MIN_VALUE);
            //���� Ŭ���� �̸��� �������� ����
            privateEditor.putString(PREFERENCE_ACTIVATY_NAME, this.getLocalClassName());
            //�ݵ�� Ŀ���Ѵ�.[�ѹ�������]
            privateEditor.commit();
    	}
    	super.onCreate(savedInstanceState);
    }
	@Override
	public Class<?> getSwitchActivityClass() {
		return SimplePreferenceActivity2.class;
	}
}