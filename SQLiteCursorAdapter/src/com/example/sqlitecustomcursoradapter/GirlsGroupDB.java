package com.example.sqlitecustomcursoradapter;

import android.provider.BaseColumns;

public final class GirlsGroupDB {
	
   private GirlsGroupDB() {}
   
   //�ɱ׷쿡 ���� �⺻ ����
   public static final class GirlsGroupInfo implements BaseColumns {
	   private GirlsGroupInfo() {}
	   
	   public static final String TABLE_NAME = "tbl_girls_group_info";
	   public static final String TEAM_NAME = "col_team_name" ;
	   public static final String SORT_ORDER = "col_team_name ASC";
   }
   //�ɱ׷��� �θ� �뷡�� �����ϴ� ���̺�
   public static final class GirlsGroupMusic implements BaseColumns {
	   private GirlsGroupMusic() {}
	   
	   public static final String TABLE_NAME = "tbl_girls_group_music";
	   public static final String MUSIC_TITLE = "col_music_title";
	   public static final String GIRLS_GROUP_ID = "col_girls_group_id";
	   public static final String SORT_ORDER = "col_music_title ASC" ;
   }
   
}
