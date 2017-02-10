package com.pyo.android.widget.expension;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TARAArrayAdapterActivity extends Activity{
    @Override
    public void onCreate(Bundle bundle){
    	super.onCreate(bundle);
    	setContentView(R.layout.tara_base_adapter_list_layout);
    	
    	ListView taraListView = (ListView)findViewById(R.id.tara_list);
    	
    	ArrayList<TARAIconValueObject> taraList = new ArrayList<TARAIconValueObject>();
    	Resources resource = getResources();
    	
    	taraList.add(new TARAIconValueObject("SOYEON",resource.getDrawable(R.drawable.t_ara_icon_soyeon)));
    	taraList.add(new TARAIconValueObject("JIYEON", resource.getDrawable(R.drawable.t_ara_icon_jiyeon)));
    	taraList.add(new TARAIconValueObject("QRI", resource.getDrawable(R.drawable.t_ara_icon_qri)));
    	taraList.add(new TARAIconValueObject("EUNJUNG",resource.getDrawable(R.drawable.t_ara_icon_eunjung)));
    	taraList.add(new TARAIconValueObject("HYOMIN",resource.getDrawable(R.drawable.t_ara_icon_hyomin)));
    	taraList.add(new TARAIconValueObject("BORAM",resource.getDrawable(R.drawable.t_ara_icon_boram)));
    	
    	TARAArrayAdapter listAdapter = new TARAArrayAdapter(this, taraList);
        taraListView.setAdapter(listAdapter);
        taraListView.setOnItemClickListener(itemListener);
    }
    private OnItemClickListener itemListener = new OnItemClickListener(){
 	   @Override
 	   public void onItemClick(AdapterView<?> parent, View view, int position, long id){
 		  TARAIconValueObject  taraObject = (TARAIconValueObject)parent.getItemAtPosition(position);
 		   String memberName = taraObject.getMemberName();
 		   Toast.makeText(getApplicationContext(), 
 				   memberName + " 을 좋아하시는 군요! ", Toast.LENGTH_LONG).show();
 	   }
    };
    private class TARAViewHolder{
        public ImageView taraMemberImage;
        public TextView   taraMemberName;
    }
	private class TARAArrayAdapter extends ArrayAdapter<TARAIconValueObject>{
	   private LayoutInflater inflater;
	   private View adapterView;
	   private TARAViewHolder viewHolder;
       public TARAArrayAdapter(Context context, List<TARAIconValueObject> lists){
	      super(context,0, lists);
	      inflater = LayoutInflater.from(TARAArrayAdapterActivity.this);
       }
       @Override
   	   public View getView(int position, View convertView, ViewGroup parent){
   		  TARAIconValueObject  taraValueObject = getItem(position);
   		  if( convertView == null){
   		     adapterView = inflater.inflate(R.layout.array_adapter_layout, null);
   		     viewHolder = new TARAViewHolder();
   		     viewHolder.taraMemberImage = (ImageView)adapterView.findViewById(R.id.tara_member_image);
   		     viewHolder.taraMemberName = (TextView)adapterView.findViewById(R.id.tara_member_name);
   		     adapterView.setTag(viewHolder);
   		  }else{
   			  adapterView = convertView;
   			  viewHolder = (TARAViewHolder)adapterView.getTag();
   		  }
   		  viewHolder.taraMemberImage.setImageDrawable(taraValueObject.getMemberIcon());
   		  viewHolder.taraMemberName.setText(taraValueObject.getMemberName());
   		 
   		  return adapterView;
   	   }
	}
}