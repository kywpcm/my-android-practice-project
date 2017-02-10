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
	   
	   adapterItems.add("소연");
	   adapterItems.add("은정");
	   adapterItems.add("보람");
	   adapterItems.add("규리");
	   adapterItems.add("효민");
	   adapterItems.add("지연");
	   
	   editableAdapter = new ArrayAdapter<String>(this, 
			            android.R.layout.simple_list_item_multiple_choice,adapterItems);
	   memberList.setAdapter(editableAdapter);
	   //리스트 속성 설정
	   memberList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	   //버튼 이벤트
	   multiSelectedBtn.setOnClickListener(new View.OnClickListener(){
		   @Override
		   public void onClick(View view){
			    int selectedCount = 0;
			    StringBuilder strBuf = new StringBuilder();
			    //체크된 아이템의 id값(여기선 배열)을 리턴 해 줌
			    //long  ids [] = memberList.getCheckedItemIds();
			    //position의 각 값이 boolean의 어떠한 값을 가지고 있는지 map 구조로 나타내어 리턴 해줌
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
							"선택된 아이템은[" + strBuf.toString() + "] 입니다.",
							Toast.LENGTH_LONG).show();
				}
		   }
	   });
   }
}