package com.pyo.android.widget.expension;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class TARAGalleryAdapterActivity extends  Activity{
  private ImageView selectedImage;
	@Override
  public void onCreate(Bundle bundle){
	  super.onCreate(bundle);
	  setContentView(R.layout.gallery_layout);
	  selectedImage = (ImageView)findViewById(R.id.scale_type_fitXY);
	  
	  Gallery taraGallery = (Gallery)findViewById(R.id.tara_gallery);
	  
	  taraGallery.setAdapter(new TARAGalleryAdapter(this));
	  taraGallery.setOnItemSelectedListener(gallerySelectedListener);
  }
  private AdapterView.OnItemSelectedListener gallerySelectedListener = new
	          AdapterView.OnItemSelectedListener(){
     @Override
     public void onItemSelected(AdapterView<?> adpater, View view, int position, long id){
    	ImageView taraImage = (ImageView)view;
    	selectedImage.setImageDrawable(taraImage.getDrawable());
     }
     @Override
     public void onNothingSelected(AdapterView<?> adpater){  	 
     }
  };
  private class TARAGalleryAdapter extends BaseAdapter{
	    private Context context;
	    private int galleryBackGroundAttrs;
	    private int[] taraImageIds = { 
	           R.drawable.t_ara_icon_boram, 
	           R.drawable.t_ara_icon_eunjung, 
	           R.drawable.t_ara_icon_jiyeon, 
	           R.drawable.t_ara_icon_qri,
	           R.drawable.t_ara_icon_soyeon,
	           R.drawable.t_ara_icon_hyomin
	    }; 

	    public TARAGalleryAdapter(Context context){
	    	this.context = context;
	        TypedArray typeArray = obtainStyledAttributes(R.styleable.GalleryTheme);
	        galleryBackGroundAttrs =
	        	typeArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
	    }
	    public int getCount() {
	    	return taraImageIds.length;
	    } 
	    public Object getItem(int position) {
	    	return taraImageIds[position];
	    } 
	    public long getItemId(int position) {
	    	return position; 
	    }
	    public View getView(int position, View convertView, ViewGroup parent){ 
	        ImageView taraImageView;
	        if( convertView == null){
	        	taraImageView = new ImageView(context); 
	        }else{
	        	taraImageView = (ImageView)convertView;
	        }
	        taraImageView.setImageResource(taraImageIds[position]);
	        taraImageView.setScaleType(ImageView.ScaleType.FIT_XY); 
	        taraImageView.setLayoutParams(new Gallery.LayoutParams(96, 96));
	        taraImageView.setBackgroundResource(galleryBackGroundAttrs);
	        return taraImageView;
	    } 
	}
}