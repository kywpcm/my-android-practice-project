package com.pyo.android.widget.expension;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TARABaseAdapterActivity extends Activity{
   @Override
   public void onCreate(Bundle bundle){
	   super.onCreate(bundle);
	   setContentView(R.layout.tara_base_adapter_list_layout);
	   
	   ListView  taraListView = (ListView)findViewById(R.id.tara_list);
	   
	   TARACustomBaseAdapter taraAdapter = new TARACustomBaseAdapter(this);
	   
	   Resources resource = getResources();
	   taraAdapter.addItem(new TARAIconValueObject("SOYEON", 
			                        resource.getDrawable(R.drawable.t_ara_icon_soyeon)));
	   taraAdapter.addItem(new TARAIconValueObject("JIYEON", 
               resource.getDrawable(R.drawable.t_ara_icon_jiyeon)));
	   taraAdapter.addItem(new TARAIconValueObject("QRI", 
               resource.getDrawable(R.drawable.t_ara_icon_qri)));
	   taraAdapter.addItem(new TARAIconValueObject("EUNJUNG", 
               resource.getDrawable(R.drawable.t_ara_icon_eunjung)));
	   taraAdapter.addItem(new TARAIconValueObject("HYOMIN", 
               resource.getDrawable(R.drawable.t_ara_icon_hyomin)));
	   taraAdapter.addItem(new TARAIconValueObject("BORAM", 
               resource.getDrawable(R.drawable.t_ara_icon_boram)));
	   //Display It!
	   taraListView.setAdapter(taraAdapter);
	   
	   taraListView.setOnItemClickListener(itemListener);
   }
   private OnItemClickListener itemListener = new OnItemClickListener(){
	   @Override
	   public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		   /* View 객체에서 데이터를 가져오는 방법
		    * TARAExpansionLayout  selectedRowView = (TARAExpansionLayout)view;
		    * TextView selectedTARA = (TextView)selectedRowView.getChildAt(1);
		    * String memberName = selectedTARA.getText().toString();
		   */
		   //AdapterView객체에서 데이터를 가져오는 방법
		   TARAIconValueObject  taraValueObject = 
			      (TARAIconValueObject)parent.getItemAtPosition(position);   
		   String memberName = taraValueObject.getMemberName();
		   Toast.makeText(getApplicationContext(), 
				   memberName + " 을 좋아하시는 군요! ", Toast.LENGTH_LONG).show();
	   }
   };
}