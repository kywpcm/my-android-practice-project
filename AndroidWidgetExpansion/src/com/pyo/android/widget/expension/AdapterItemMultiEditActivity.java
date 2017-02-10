package com.pyo.android.widget.expension;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AdapterItemMultiEditActivity extends Activity{
   private ArrayList<String> adapterItems;
   private ArrayAdapter<String> editableAdapter;
   private ListView memberList;
   private Button multiSelectedBtn;

	@Override
   public void onCreate(Bundle bundle){
	   super.onCreate(bundle);
	   setContentView(R.layout.adapter_multi_edit_list_view);
	   memberList = (ListView)findViewById(R.id.girl_member_multi_list_view);
	   multiSelectedBtn = (Button)findViewById(R.id.multi_selected_btn);
	   adapterItems = new ArrayList<String>();
	   
	   adapterItems.add("�ҿ�");
	   adapterItems.add("����");
	   adapterItems.add("����");
	   adapterItems.add("�Ը�");
	   adapterItems.add("ȿ��");
	   adapterItems.add("����");
	   
	   editableAdapter = new ArrayAdapter<String>(this, 
			            android.R.layout.simple_list_item_multiple_choice,adapterItems);
	   memberList.setAdapter(editableAdapter);
	   //����Ʈ �Ӽ� ����
	   memberList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	   //��ư �̺�Ʈ
	   multiSelectedBtn.setOnClickListener(new View.OnClickListener(){
		   @Override
		   public void onClick(View view){
			    int selectedCount = 0;
			    StringBuilder strBuf = new StringBuilder();
			    //üũ�� �������� id��(���⼱ �迭)�� ���� �� ��
			    //long  ids [] = memberList.getCheckedItemIds();
			    //position�� �� ���� boolean�� ��� ���� ������ �ִ��� map ������ ��Ÿ���� ���� ����
				SparseBooleanArray sparseMap= memberList.getCheckedItemPositions();
				int itemLength = sparseMap.size();
				if(itemLength > 0 ){
					for(int itemPosition = 0; itemPosition < itemLength; itemPosition++){
						if (sparseMap.valueAt(itemPosition)){
							selectedCount ++;
							strBuf.append((String)memberList.getItemAtPosition(itemPosition));
							strBuf.append(",");
						}
					}
					Toast.makeText(getApplicationContext(), 
							"���õ� ��������[" + strBuf.toString() + "] �Դϴ�.",
							Toast.LENGTH_LONG).show();
				}
		   }
	   });
   }
}