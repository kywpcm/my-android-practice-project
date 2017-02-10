package com.pyo.sqlite.fianalsqliteproject;

import android.provider.BaseColumns;

public class GirlsGroupDB3 {
	   private GirlsGroupDB3(){}
	   //�ɱ׷쿡 ���� �⺻ ����
	   public static final class GirlsGroupInfo implements BaseColumns{
		   private GirlsGroupInfo(){}
		   
		   public static final String TABLE_NAME = "tbl_girls_group_info";
		   public static final String TEAM_NAME = "col_team_name" ;
		   public static final String SORT_ORDER = "col_team_name ASC";
	   }
	   //�ɱ׷��� �θ� �뷡�� �����ϴ� ���̺�
	   public static final class GirlsGroupMusic implements BaseColumns{
		   private GirlsGroupMusic(){}
		   
		   public static final String TABLE_NAME = "tbl_girls_group_music";
		   public static final String MUSIC_TITLE = "col_music_title";
		   public static final String GIRLS_GROUP_ID = "col_girls_group_id";
		   public static final String SORT_ORDER = "col_music_title ASC" ;
		   
		   //���� �߰��� �κ�
		   //Media Uri Path
		   public static final String GROUP_IMAGE_URI="group_image_uri";
		   //SD Card Uri id
		   public static final String GROUP_IMAGE_ID="group_image_id";
		   
	   }
}
