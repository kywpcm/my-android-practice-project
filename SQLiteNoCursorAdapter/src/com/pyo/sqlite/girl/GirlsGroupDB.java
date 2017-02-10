package com.pyo.sqlite.girl;

import android.provider.BaseColumns;

//DB 정보 상수화를 위한 클래스
public final class GirlsGroupDB {

	private GirlsGroupDB(){}

	//BaseColumns를 상속받아 _ID란 static 변수를 쓸 수 있다.
	//걸그룹 정보를 위한 테이블 관련 정보
	public static final class GirlsGroupInfo implements BaseColumns{

		private GirlsGroupInfo(){}

		public static final String TABLE_NAME = "tbl_girls_group_info";
		public static final String TEAM_NAME = "col_team_name" ;
		public static final String SORT_ORDER = "col_team_name ASC";
	}

	//걸그룹 노래 정보를 위한 테이블 관련 정보
	public static final class GirlsGroupMusic implements BaseColumns{

		private GirlsGroupMusic(){}

		public static final String TABLE_NAME = "tbl_girls_group_music";
		public static final String MUSIC_TITLE = "col_music_title";
		public static final String GIRLS_GROUP_ID = "col_girls_group_id";
		public static final String SORT_ORDER = "col_music_title ASC" ;
	}
	
}
