package com.pyo.sqlite.fianalsqliteproject;

public class GirlsGroupMusicInfo implements Comparable<GirlsGroupMusicInfo>{ 
    
    public static final long INVALID_GIRLS_GROUP_ID = -1;
    public static final long INVALID_GIRLS_GROUP_IMAGE_ID = -1;
    private String mMusicName; 
    private String mGroupName; 
    private String mImageUriPath; 
    private long mImageId = INVALID_GIRLS_GROUP_IMAGE_ID; //(Uri tag)
    private long mId = INVALID_GIRLS_GROUP_ID; //(DB pk record id)
    
    private boolean mSelectable = true; 

    public GirlsGroupMusicInfo(String musicName, String groupName,
   		   String strImageUriPath, Long imageId, Long id) { 
	     mMusicName = musicName; 
	     mGroupName = groupName; 
	     mImageUriPath = strImageUriPath;
	     mImageId = imageId; 
	     mId = id;  
    } 
     
    public boolean isSelectable() { 
         return mSelectable; 
    } 
     
    public void setSelectable(boolean selectable) { 
         mSelectable = selectable; 
    } 
     
    public String getMusicName() { 
         return mMusicName; 
    } 
     
    public void setMusicName(String text) { 
   	    mMusicName = text; 
    } 
    
    public String getImageUriPath() { 
         return mImageUriPath; 
    } 
     
    public void setImageUriPath(String text) { 
    	mImageUriPath = text; 
    } 
    
    public String getGroupName() { 
         return mGroupName; 
    } 
     
    public void setGroupName(String text) { 
    	mGroupName = text; 
    } 
    public long getImageId() { 
         return mImageId; 
    } 
     
    public void setImageId(long id) { 
   	    mImageId = id; 
    } 
    
    public long getId() { 
         return mId; 
    } 
     
    public void setId(long id) { 
   	  mId = id; 
    } 

    public int compareTo(GirlsGroupMusicInfo other) { 
         return (int)((this.mId)-(other.mId));
    } 
}
