package com.pyo.custom.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class GirlsGroupContentProviderImpl extends ContentProvider {
	private static final String DEBUG_TAG = "GirlsGroupContentProviderImpl";
	public  static  UriMatcher URI_MATCHER = null;
    public  static  HashMap<String,String> PROJECTION_MAP;
    
    public  static  final int PATH_TYPE_SINGLE_1 = 1;
    public  static  final int PATH_TYPE_MULTIPLE_1 = 2;
	static{
		 // Uri�� �����ϰ� ���ν�Ų��
    	 URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    	 URI_MATCHER.addURI(GirlsGroupContentProviderInfo.AUTHORITY,
    			    GirlsGroupContentProviderInfo.PATH_SINGLE,
    			    PATH_TYPE_SINGLE_1 );
    	 URI_MATCHER.addURI(GirlsGroupContentProviderInfo.AUTHORITY,
    			GirlsGroupContentProviderInfo.PATH_MULTIPLE, PATH_TYPE_MULTIPLE_1);
    	 
    	 //���̺��� ���Ἲ�� �����ϱ� ���� �������Ǹ��� ����
    	 PROJECTION_MAP = new HashMap<String, String>();
         PROJECTION_MAP.put(BaseColumns._ID, "_id");
         PROJECTION_MAP.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME, "group_name");
         PROJECTION_MAP.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER, "group_number");
         PROJECTION_MAP.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY,"group_company");
         PROJECTION_MAP.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.CREATED_TIME, "created_time");
         PROJECTION_MAP.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.UPDATED_TIME, "updated_time");
     }
     private  SQLiteDatabase sqlDB;
     @Override 
     public  boolean  onCreate(){
    	 GirlsGroupDBOpenHelper dbHelper = new GirlsGroupDBOpenHelper(getContext());
    	 sqlDB = dbHelper.getWritableDatabase();
    	 if( this.sqlDB != null){
    		 return true;
    	 }else{
    		 Log.e(DEBUG_TAG, " sqlDB ���� �� ���� �߻�! ");
    		 return false;
    	 }
     }
     @Override
     public String getType(final Uri uri) {
         switch (URI_MATCHER.match(uri)){
             case PATH_TYPE_SINGLE_1:
                 return GirlsGroupContentProviderInfo.CUSTOM_MIME_ITEM_PATH_SINGLE;
             case PATH_TYPE_MULTIPLE_1:
                 return GirlsGroupContentProviderInfo.CUSTOM_MIME_DIR_PATH_MULTIPLE;
             default:
                 throw new IllegalArgumentException("������� ���� URI Path�Դϴ�. " + uri);
         }
     }
     @Override
     public Cursor query(final Uri uri, final String[] projection, 
    		                   final String selection, final String[] selectionArgs,
                               final String sortOrder) {
         SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
         String orderBy = null;
         switch (URI_MATCHER.match(uri)) {
             case PATH_TYPE_SINGLE_1:
                 queryBuilder.setTables(GirlsGroupDBOpenHelper.DB_TABLE);
                 queryBuilder.appendWhere("_id=" + uri.getPathSegments().get(1));
                 break;
             case PATH_TYPE_MULTIPLE_1:
                 queryBuilder.setTables(GirlsGroupDBOpenHelper.DB_TABLE);
                 queryBuilder.setProjectionMap(PROJECTION_MAP);
                 break;
             default:
                 throw new IllegalArgumentException("��ϵ��� ���� URI " + uri);
         }

         if (TextUtils.isEmpty(sortOrder)) {
             orderBy = GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.SORT_ORDER;
         } else {
             orderBy = sortOrder;
         }

         Cursor returedCursor = queryBuilder.query(this.sqlDB, projection, selection,
        		                       selectionArgs, null, null, orderBy);
         returedCursor.setNotificationUri(getContext().getContentResolver(), uri);
         return returedCursor;
     }

     @Override
     public Uri insert(final Uri uri, final ContentValues initialValues) {
         long rowId = 0L;
         ContentValues values = null;
         
         if (URI_MATCHER.match(uri) != PATH_TYPE_MULTIPLE_1){
             throw new IllegalArgumentException("������ �߰��� ������ Multiple URI ���� �մϴ�. " + uri);
         }
         if (initialValues == null) {
        	 values = new ContentValues();
         }else{
        	 values = initialValues;
         }
         Long currentTime = System.currentTimeMillis();

         //������������ ���� ����
         if (!values.containsKey(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME)){
             values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NAME, "NO_DATA");
         }
         if(!values.containsKey(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER)){
             values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_NUMBER, 1);
         }
         if(!values.containsKey(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY)){
             values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.GROUP_COMPANY, "NO_DATA");
         }
         if(!values.containsKey(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.CREATED_TIME)){
             values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.CREATED_TIME, currentTime);
         }
         if(!values.containsKey(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.UPDATED_TIME)){
             values.put(GirlsGroupContentProviderInfo.GirlsGroupColumnInfo.UPDATED_TIME, 0);
         }

         rowId = sqlDB.insert(GirlsGroupDBOpenHelper.DB_TABLE, "empty", values);
         if (rowId > 0) {
        	 //���� ó�� �Ǹ� Uri�� �߰��� _ID���� path�� �߰� �Ѵ� 
             Uri resultedURI = ContentUris.withAppendedId(
            		             GirlsGroupContentProviderInfo.CONTENT_URI, rowId);
             //�۷ι� Context�� ���� �����Ͱ� ��ȯ �Ǿ����� ���� �Ѵ�
             getContext().getContentResolver().notifyChange(resultedURI, null);
             return resultedURI;
         }
         throw new SQLException("�� �߰��� ���� �߽��ϴ�. " + uri);
     }

     @Override
     public int update(final Uri uri, final ContentValues values, 
    		               final String selection, final String[] selectionArgs) {
         int count = 0;
         switch (URI_MATCHER.match(uri)) {
             
             case PATH_TYPE_SINGLE_1:
                 String segment = uri.getPathSegments().get(1);
                 String where = "";
                 if (!TextUtils.isEmpty(selection)) {
                     where = " AND (" + selection + ")";
                 }
                 count = sqlDB.update(GirlsGroupDBOpenHelper.DB_TABLE, values, "_id=" +
                		            segment + where, selectionArgs);
                 break;
             case PATH_TYPE_MULTIPLE_1:
                 count = sqlDB.update(GirlsGroupDBOpenHelper.DB_TABLE, values, selection, selectionArgs);
                 break;
             default:
                 throw new IllegalArgumentException("���ŵ��� �ʾҳ׿�. URI " + uri);
         }
         getContext().getContentResolver().notifyChange(uri, null);
         return count;
     }

     @Override
     public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
         int count;

         switch (URI_MATCHER.match(uri)) {
             
             case PATH_TYPE_SINGLE_1:
                 String segment = uri.getPathSegments().get(1);
                 String where = "";
                 if (!TextUtils.isEmpty(selection)) {
                     where = " AND (" + selection + ")";
                 }
                 count = sqlDB.delete(GirlsGroupDBOpenHelper.DB_TABLE, "_id=" + segment + where, selectionArgs);
                 break;
             case PATH_TYPE_MULTIPLE_1:
                 count = sqlDB.delete(GirlsGroupDBOpenHelper.DB_TABLE, selection, selectionArgs);
                 break;
             default:
                 throw new IllegalArgumentException("���� ���� �ʾҳ׿� URI " + uri);
         }
         getContext().getContentResolver().notifyChange(uri, null);
         return count;
     }
}