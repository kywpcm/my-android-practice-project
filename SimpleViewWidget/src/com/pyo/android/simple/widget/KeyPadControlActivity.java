package com.pyo.android.simple.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyPadControlActivity extends Activity{
	  private InputMethodManager keyPadManager;
	   @Override
	   public void onCreate(Bundle bundle){
		   super.onCreate(bundle);
		   /*
		    *  AndroidMenifest.xml�� <activity android:windowSoftInputMode="adjustResize"�� ����
		    *  android:windowSoftInputMode="adjustPan" �� ����Ʈ
		    */
		  //��Ƽ��Ƽ�� ����� �ٿ����� ��ü ȭ���� �������� ��
		   getWindow().setSoftInputMode(
				   WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		   setContentView(R.layout.ime_key_board_control_layout);
		   //IMF Manager ���� ��ü�� ��´�
		   keyPadManager = 
			    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		   final Button keyPadShow = (Button)findViewById(R.id.key_pad_show);
		   final Button keyPadHide = (Button)findViewById(R.id.key_pad_hide);
		   final EditText keyPadControl = (EditText)findViewById(R.id.key_pad_control_edit);
		   keyPadShow.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				// Ű�е� ���̱�
				keyPadManager.
				  showSoftInput(keyPadControl, InputMethodManager.RESULT_UNCHANGED_SHOWN);
			 }
	       });
		   keyPadHide.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					// Ű�е� �����
					keyPadManager.hideSoftInputFromWindow(
							keyPadControl.getWindowToken(),
							InputMethodManager.RESULT_UNCHANGED_SHOWN);
				 }
		   });
	   }
}