package com.pyo.sqlite.fianalsqliteproject;

public class GirlsGroupGalleryInfo implements Comparable<GirlsGroupGalleryInfo>{
	
	    public static final long INVALID_IMAGE_ID = -1;
	    private String mImageUriPath; 
	    private long mImageUriId = INVALID_IMAGE_ID; 
	    private boolean mSelectable = true; 
	    
	    public GirlsGroupGalleryInfo(String strImageUriPath, Long imageId) {  
	    	mImageUriPath = strImageUriPath;
	    	mImageUriId = imageId; 
	    } 
	     
	    public boolean isSelectable() { 
	         return mSelectable; 
	    } 
	     
	    public void setSelectable(boolean selectable) { 
	         mSelectable = selectable; 
	    } 
	     
	    public String getImageUriPath() { 
	         return mImageUriPath; 
	    } 
	     
	    public void setImageUriPath(String text) { 
	    	mImageUriPath = text; 
	    } 
	    
	    public long getImageId() { 
	         return mImageUriId; 
	    } 
	     
	    public void setImageId(long id) { 
	    	mImageUriId = id; 
	    } 
	    public int compareTo(GirlsGroupGalleryInfo other) { 
	         return (int)((this.mImageUriId)-(other.mImageUriId));
	    } 
} 