package com.pyo.android.simple.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ButtonKindActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_kind_layout);
		
		final Button basicButton = (Button)findViewById(R.id.basic_button);
		basicButton.setOnClickListener(new View.OnClickListener(){
			 public  void onClick(View button){
				 Toast.makeText(ButtonKindActivity.this, "��ư Ŭ����! ", 
						            Toast.LENGTH_SHORT).show();
			 }
		});
		final Button selectorButton = (Button)findViewById(R.id.selector_applied_btn);
		final ImageButton imageButton = 
			      (ImageButton)findViewById(R.id.image_button);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!selectorButton.isEnabled()){
				  selectorButton.setEnabled(true);
				  Toast.makeText(ButtonKindActivity.this,"ó������ ��ư�� Ȱ��ȭ �˴ϴ�",
						            Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(ButtonKindActivity.this,"�̹��� ��ư �Դϴ�.",
				            Toast.LENGTH_SHORT).show();
				}
			}
		});
		final ToggleButton toggleButton = 
	                     (ToggleButton) findViewById(R.id.toggle_button);
        toggleButton.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View v) {
	          TextView tv = (TextView) findViewById(R.id.text_feature);
	          tv.setText(toggleButton.isChecked() ? "��� ON" : "���  off");
	       }
         });
		
        final Button submitButton = (Button) findViewById(R.id.submit_demo);
        submitButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v ){
	            ToggleButton tb = (ToggleButton) findViewById(R.id.toggle_button);
	            Toast.makeText(ButtonKindActivity.this, tb.isChecked() ?
		             "on": "off", Toast.LENGTH_LONG).show();
		              ButtonKindActivity.this.finish();
	        }
	    });
       final CheckBox checkButton = (CheckBox) findViewById(R.id.checkbox);
       checkButton.setOnClickListener(new View.OnClickListener() {
	     public void onClick (View v) {
		     TextView tv = (TextView)findViewById(R.id.checkbox);
		     tv.setText(checkButton.isChecked() ?
			             "�ɼ� üũ!": "�ɼ� ��üũ!");
	     }
       });
	
       final RadioGroup group = (RadioGroup)findViewById(R.id.RadioGroup01);
       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
       public void onCheckedChanged(RadioGroup group, int checkedId) {
	        TextView tv = (TextView) findViewById(R.id.TextView01);
	        if (checkedId != -1) {
	           RadioButton rb = (RadioButton) findViewById(checkedId);
	          if (rb != null) {
	                tv.setText("You chose: " + rb.getText());
	          }
	       }else{
	          tv.setText("Choose 1");
	       }
         }
       });	
      final Button clearChoice = (Button) findViewById(R.id.Button01);
      clearChoice.setOnClickListener(new View.OnClickListener() {
	   public void onClick(View v) {
	     RadioGroup group = (RadioGroup) findViewById(R.id.RadioGroup01);
	     if (group != null) {
	       group.clearCheck(); //��ư �׷� ���� Ŭ����.
	    }
      }
     });
  }
/*
 *   android:onClick="selectorAppliedButtonOnClick" 
 */
 public void selectorAppliedButtonOnClick(View selectorButton){
	 String buttonTagMessage =  (String)selectorButton.getTag();
	 if(selectorButton.isEnabled()){
	   Toast.makeText(this, buttonTagMessage + " ��Ȱ��ȭ �˴ϴ�!", 1000).show();
	   selectorButton.setEnabled(false);
	 }
  }
}