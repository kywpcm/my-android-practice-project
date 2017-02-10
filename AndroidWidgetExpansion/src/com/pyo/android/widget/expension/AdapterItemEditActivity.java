package com.pyo.android.widget.expension;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AdapterItemEditActivity extends Activity{
   private ArrayList<String> adapterItems;
   private ArrayAdapter<String> editableAdapter;
   private ListView memberList;
   private Button memberInsert;
   private Button memberDelete;
   private EditText memberInput;
	@Override
   public void onCreate(Bundle bundle){
	   super.onCreate(bundle);
	   setContentView(R.layout.adapter_edit_list_view);
	   memberList = (ListView)findViewById(R.id.girl_member_list);
	   
	   memberInsert = (Button)findViewById(R.id.member_add);
	   memberDelete = (Button)findViewById(R.id.member_delete);
	   memberInput = (EditText)findViewById(R.id.input_member);
	   
	   memberInsert.setOnClickListener(new View.OnClickListener(){
		   @Override
		   public void  onClick(View v){
			   String inputedMemberName = memberInput.getText().toString();
			   if( inputedMemberName != null && (inputedMemberName.length() > 0)){
				   //��������  �߰�
				   adapterItems.add(inputedMemberName);
				   memberInput.setText("");
				   //����Ϳ��� �������� ������ �ٲ������ ���� �Ѵ�.
				   editableAdapter.notifyDataSetChanged();
			   }else{
				   Toast.makeText(getApplicationContext(), "�Է°��� ���׿�!", Toast.LENGTH_LONG).show();
			   }
		   }
	   });
	   memberDelete.setOnClickListener(new View.OnClickListener(){
		   @Override
		   public void  onClick(View v){
			   int checkedPosition = memberList.getCheckedItemPosition();
			   if(  checkedPosition >= 0){
				   adapterItems.remove(checkedPosition);
				   memberList.clearChoices();
				   //����Ϳ��� �������� ������ �ٲ������ ���� �Ѵ�.
				   editableAdapter.notifyDataSetChanged();
			   }else{
				   Toast.makeText(getApplicationContext(), "���õ� �������� ���׿�!", Toast.LENGTH_LONG).show();
			   }
		   }
	   });
	   
	   adapterItems = new ArrayList<String>();
	   
	   adapterItems.add("�ҿ�");
	   adapterItems.add("����");
	   adapterItems.add("����");
	   adapterItems.add("�Ը�");
	   adapterItems.add("ȿ��");
	   adapterItems.add("����");
	   
	   editableAdapter = new ArrayAdapter<String>(this, 
			                     android.R.layout.simple_list_item_single_choice,adapterItems);
	   memberList.setAdapter(editableAdapter);
	   //����Ʈ �Ӽ� ����
	   memberList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
   }
}
