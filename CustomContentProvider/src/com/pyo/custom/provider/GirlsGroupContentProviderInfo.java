package com.pyo.custom.provider;

import  android.net.Uri;
import  android.provider.BaseColumns;

public class GirlsGroupContentProviderInfo {

	public  static  final String AUTHORITY = 
		  "com.pyo.custom.provider.uri";
	public  static  final String CUSTOM_MIME_ITEM_PATH_SINGLE =
		    "vnd.android.cursor.item/vnd.girls.group";
	public  static  final String CUSTOM_MIME_DIR_PATH_MULTIPLE =
		   "vnd.android.cursor.dir/vnd.girls.group";
	
	public  static  final String PATH_SINGLE_GROUP_ID = "group_id";
	public  static  final String PATH_SINGLE = "group_id/#";
    public  static  final String PATH_MULTIPLE = "group_list";
    
    public  static  final  Uri CONTENT_URI = 
    	Uri.parse("content://" + AUTHORITY + "/");
    
    public static final  class GirlsGroupColumnInfo  implements BaseColumns{
 
    	private  GirlsGroupColumnInfo(){}
        
        public  static  final String  GROUP_NAME = "group_name";
        public  static  final String  GROUP_NUMBER = "group_number";
        public  static  final String  GROUP_COMPANY = "group_company";
        public  static  final String  CREATED_TIME= "created_time";
        public  static  final String  UPDATED_TIME = "updated_time";
        public  static  final String  SORT_ORDER = "updated_time DESC";
    }
}