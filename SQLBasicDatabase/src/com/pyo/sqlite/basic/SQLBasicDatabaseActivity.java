/*
 *  SQLite 기본 예제
 *  pyo.in.soo
 */
package com.pyo.sqlite.basic;

import java.sql.Date;
import java.util.Arrays;
import java.util.Locale;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;

public class SQLBasicDatabaseActivity extends Activity {

	private static final String DEBUG_TAG = "SQLBasicDatabaseActivity";

	private static final String DATABASE_NAME = "simpleTestDB.db";

	// 테이블  이름
	private static final String TABLE_BOOK = "tbl_books";
	private static final String TABLE_AUTHOR = "tbl_authors";

	//책저자에 대한 정보(_id, 이름, 별명)
	private static final String CREATE_AUTHOR_TABLE = 
			"CREATE TABLE tbl_authors ( " +
					"  _id INTEGER PRIMARY KEY AUTOINCREMENT ," + 
					"  authorname TEXT," +
					"  authoralias TEXT);";
	//책에 대한 정보(id, 제목, 출판일자)
	private static final String CREATE_BOOK_TABLE = 
			"CREATE TABLE tbl_books ( " + 
					" _id INTEGER PRIMARY KEY AUTOINCREMENT ," +
					" title TEXT," +
					" inserted_date DATE," +
					" authorid INTEGER NOT NULL);";

	// 테이블  레코드 CRUD를 제어하는 메소드 존재
	private SQLiteDatabase mDatabase;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//테스트 시작
		executeDatabaseExample();
	}
	
	public void executeDatabaseExample() {

		Log.i(DEBUG_TAG, "-- 본 예제 샘플 시작 --");

		//현재 이 응용 프로그램에서 사용하는 DB 인스턴스가 있는가?
		if (Arrays.binarySearch(databaseList(), DATABASE_NAME) >= 0) {
			//있다면 삭제해 버린다
			deleteDatabase(DATABASE_NAME);
		}
		//없다면 DB 인스턴스를 생성.
		mDatabase = openOrCreateDatabase(DATABASE_NAME,
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		// DB 환경 설정
		//현재 안드로이드 플랫폼의 로케일 반환
		mDatabase.setLocale(Locale.getDefault());
		//Isolation을 사용하겠는가?
		mDatabase.setLockingEnabled(true);
		//현재 DB의 버전 설정
		mDatabase.setVersion(1); 

		// LogCat에서 확인 하기 바람.
		Log.i(DEBUG_TAG, "DB 패스: " + mDatabase.getPath());
		Log.i(DEBUG_TAG, "DB 버전: " + mDatabase.getVersion());
		Log.i(DEBUG_TAG, "DB 사이즈: " + mDatabase.getPageSize());
		Log.i(DEBUG_TAG, "DB 최대사이즈: " + mDatabase.getMaximumSize());

		Log.i(DEBUG_TAG, "DB 오픈?  " + mDatabase.isOpen());
		Log.i(DEBUG_TAG, "DB readonly?  " + mDatabase.isReadOnly());
		Log.i(DEBUG_TAG, "DB Locked by current thread ? "+ mDatabase.isDbLockedByCurrentThread());

		// 테이블 생성
		//SQLiteDatabase.execSQL(,)을 사용한 DB 테이블 생성
		Log.i(DEBUG_TAG, " -- execSQL(,)을 사용한 tbl_authors table 생성 -- ");
		mDatabase.execSQL(CREATE_AUTHOR_TABLE);
		//SQLiteStatement.execute()을 사용한 DB테이블 생성, 별로 추천하지 않음..
		Log.i(DEBUG_TAG,	"--SQLiteStatement.execute()을 사용한 DB테이블 생성--");
		SQLiteStatement sqlSelect = mDatabase.compileStatement(CREATE_BOOK_TABLE);
		sqlSelect.execute();

		//각 테이블에 행들을 추가 함
		insertSomeBooks();

		// select * from tbl_books 쿼리 실행 후
		Log.i(DEBUG_TAG, "--  select * from tbl_books; 실행 결과 --");
		Cursor booksSelect = mDatabase.query(
				TABLE_BOOK, null, null, null, null, null,null);
		//결과 집합을 네비게이션한후 LogCat으로 출력
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		//SELECT title, _id  FROM tbl_books ORDER BY title ASC 쿼리 실행 후
		Log.i(DEBUG_TAG, "-- SELECT title, id  FROM tbl_books ORDER BY title ASC; 실행 결과 --");					
		String returedColumns2 [] = { "title", "_id" };
		String strSortOrder2 = "title ASC";
		booksSelect = mDatabase.query(
				"tbl_books", returedColumns2, null, null, null, null, strSortOrder2);
		//결과 집합을 네비게이션 하면서 획득하는 메소드
		logCatDisplayCursorInfo(booksSelect); 
		booksSelect.close();

		// SQLiteQueryBuilder를 이용한 쿼리 빌더
		Log.i(DEBUG_TAG, "-- SELECT tbl_books.title, tbl_books._id," +
				" tbl_authors.authorname, tbl_authors.authoralias, " +
				" tbl_books.authorid FROM tbl_books INNER JOIN tbl_authors " +
				" on tbl_books.authorid=tbl_authors._id ORDER BY title ASC --");
		//조인 등 복잡한 질의 사용시
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		//테이블 조인
		queryBuilder.setTables(TABLE_BOOK + ", " + TABLE_AUTHOR);							
		queryBuilder.appendWhere(TABLE_BOOK + ".authorid" + "=" + TABLE_AUTHOR	+ "._id");	
		String returedColumns4[] = {
				TABLE_BOOK + ".title",
				TABLE_BOOK + "._id",
				TABLE_AUTHOR + ".authorname",
				TABLE_BOOK + ".authorid"
		};
		String strSortOrder = "title ASC";
		booksSelect = queryBuilder.query(
				mDatabase, returedColumns4, null, null, null, null, strSortOrder);
		//결과 집합을 네비게이션 하면서 획득하는 메소드
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		Log.i(DEBUG_TAG, "-- " +
				" SELECT tbl_books.title, tbl_books._id, " +
				"  tbl_authors.authorname, tbl_authors.authoralias, tbl_books.authorid " +
				" FROM tbl_books INNER JOIN tbl_authors on tbl_books.authorid=tbl_authors.id" +
				"  WHERE title LIKE '%on%' ORDER BY title ASC;--");
		SQLiteQueryBuilder queryBuilder2 = new SQLiteQueryBuilder();	
		//테이블 조인
		queryBuilder2.setTables(TABLE_BOOK + ", " + TABLE_AUTHOR);
		//조인 필드
		queryBuilder2.appendWhere("("+TABLE_BOOK + ".authorid = " +TABLE_AUTHOR	+ "._id" + ")");	
		//WHERE 조건
		queryBuilder2.appendWhere(" AND (" + TABLE_BOOK + ".title" + " LIKE '%안드로이드%'" + ")");

		//쿼리 빌더로 쿼리 완성
		booksSelect = queryBuilder2.query(
				mDatabase, returedColumns4, null, null, null, null, strSortOrder);
		//결과 집합을 네비게이션 하면서 획득하는 메소드
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		Log.i(DEBUG_TAG, "-- " +
				" SELECT title AS Name, 'tbl_books' AS OriginalTable " +
				" From tbl_books WHERE Name LIKE '%ow%' " +
				" UNION SELECT (authorname||' '|| authoralias) AS Name," +
				" 'tbl_authors' AS OriginalTable from tbl_authors " +
				" WHERE Name LIKE '%ow%' ORDER BY Name ASC; --");
		String sqlUnionExample = 
				" SELECT title AS Name, 'tbl_books' AS OriginalTable " +
						" FROM tbl_books WHERE Name LIKE ? " +
						" UNION " +
						" SELECT (authorname||' '|| authoralias) AS Name, 'tbl_authors' AS OriginalTable " +
						" FROM tbl_authors " +
						" WHERE Name LIKE ? ORDER BY Name ASC;";

		//raw 쿼리..
		booksSelect = mDatabase.rawQuery(
				sqlUnionExample, new String[]{ "%안드", "%표인%"});
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// SIMPLE QUERY: select * from tbl_books WHERE _id=3;
		Log.i(DEBUG_TAG, 	"-- select * from tbl_books WHERE _id=3; --");
		booksSelect = mDatabase.query(
				TABLE_BOOK, null, "_id=?", new String[]{"3"}, null, null, null);
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// 업데이트 
		Log.i(DEBUG_TAG, "-- 업데이트 하기 --");
		updateBookTitle("안드로이드 기본", 5);

		// 업데이트 확인
		Log.i(DEBUG_TAG, "-- 업데이트 결과 보기 : select * from tbl_books WHERE _id=5; --");
		booksSelect = mDatabase.query(
				TABLE_BOOK, null, "_id=?", new String[]{"5"}, null, null, null);
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// 삭제
		Log.i(DEBUG_TAG, "--  Delete하기 _id 2 -- ");
		deleteBook(2);	

		// 삭제 확인
		Log.i(DEBUG_TAG, 	"-- select * from tbl_books WHERE _id=2; --");
		booksSelect = mDatabase.query(
				TABLE_BOOK, null, "_id=?", new String[]{"2"}, null, null, null);
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// Close the database
		Log.i(DEBUG_TAG, "-- Close DB --");
		mDatabase.close();
		Log.i(DEBUG_TAG, "DB Open ?  " + mDatabase.isOpen());

		Log.i(DEBUG_TAG, "-- SQLite3 샘플예제 끝 --");
	}
	
	//Cursor SELECT 데이터 네비게이션 하기
	public void logCatDisplayCursorInfo(Cursor selectQuery) {
		Log.i(DEBUG_TAG, "*** Cursor Begin *** " + " Results:" + 
				selectQuery.getCount() +" Columns: " + selectQuery.getColumnCount());

		String rowColumnHeaders = "|| ";
		
		//컬럼의 갯수
		int rowCount = selectQuery.getColumnCount();
		for (int i = 0; i < rowCount ; i++) {
			//컬럼의 이름을 출력한다
			rowColumnHeaders = rowColumnHeaders.concat(selectQuery.getColumnName(i) + " || ");
		}
		Log.i(DEBUG_TAG, "-- 현재 Cursor 결과집합의 컬럼이름=> " + rowColumnHeaders);

		// 각 행을 프린트 한다
		selectQuery.moveToFirst(); //결과집합의 처음으로 이동
		while (selectQuery.isAfterLast() == false) {
			String rowResults = "|| ";
			
			int rowSize = selectQuery.getColumnCount();
			for (int i = 0; i < rowSize; i++) {
				rowResults = rowResults.concat(selectQuery.getString(i) + " || ");
			}
			Log.i(DEBUG_TAG, "-- 커서의 현지 위치 [" + selectQuery.getPosition() + "] : " + rowResults);
			selectQuery.moveToNext();
		}
		Log.i(DEBUG_TAG, "*** Cursor End ***");
	}

	//레코드 추가
	public void insertSomeBooks() {

		//트랜잭션 지원 유무 체크 ?
		Log.i(DEBUG_TAG, "DB Transaction?  "	+ mDatabase.inTransaction());

		//오늘 날짜
		Date today = new Date(java.lang.System.currentTimeMillis());
		//DB는 한글 지원(UTF-8)
		TBLAuthor author1 = new TBLAuthor("표인수", "핸섬가이");
		addAuthor(author1); 

		addBook(new TBLBook("자바코어 프로그래밍", today,	author1));
		addBook(new TBLBook("자바네트워크 프로그래밍", today,	author1));
		addBook(new TBLBook("자바엔터프라이즈 프로그래밍", today,author1));
		addBook(new TBLBook("자바ME 프로그래밍", today,author1));
		addBook(new TBLBook("안데로이드 기본",today, author1));
		addBook(new TBLBook("안드로이드 응용", today,author1));
		addBook(new TBLBook("안드로이드 C2DM", today,author1));

		TBLAuthor author2 = new TBLAuthor("표현준", "내조카");
		addAuthor(author2); 

		addBook(new TBLBook("삼촌에게 용돈 받아내는법", today, author2));

		TBLAuthor author3 = new TBLAuthor("표현준", "둘째조카");
		addAuthor(author3); 

		addBook(new TBLBook("삼촌에게 장난감 얻는방법", today,	author3));

		Log.i(DEBUG_TAG, "--DB Insert Transaction 종료--");
	}

	//저자 입력
	//트랜잭션 사용..
	private void addAuthor(TBLAuthor newAuthor) {
		Log.i(DEBUG_TAG, "--DB Table Insert Transaction Start --");
		//이런 식으로 예외처리 해줘야 한다.
		/*mSQLDB.beginTransaction();
		try {
		//여기서 삽입, 삭제, 갱신 등등의 단위 작업 진행 
		//트랜잭션 성공시
		mSQLDB.setTransactionSuccessful();
		}
		catch(SQLException e) { 
		//트랜잭션 실패시 조치 }
		finally { 
		mSQLDB.endTransaction();  //트랜잭션 종료 
		}*/
		mDatabase.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put("authorname", newAuthor.mAuthorName);
		values.put("authoralias", newAuthor.mAuthorAlias);
		
		//행 추가후에 _id값을 리턴함
		newAuthor.mAuthorId = mDatabase.insertOrThrow(TABLE_AUTHOR, null, values);
		Log.i(DEBUG_TAG, "--Author 추가 :  " + newAuthor.mAuthorName + " " +
				newAuthor.mAuthorAlias + "(_ID=" + newAuthor.mAuthorId + ") --");
		//트랜잭션 커밋
		mDatabase.setTransactionSuccessful();
		//트랜잭션 끝
		mDatabase.endTransaction();
	}
	
	// 책 추가
	private void addBook(TBLBook newBook) {
		ContentValues values = new ContentValues();
		values.put("title", newBook.mTitle);
		values.put("inserted_date", newBook.mDateAdded.toLocaleString());
		values.put("authorid", newBook.mAuthor.mAuthorId);
		
		newBook.mBookId = mDatabase.insertOrThrow(TABLE_BOOK, null, values);
		
		Log.i(DEBUG_TAG, "--Book 추가 :  " +	newBook.mTitle + "(_id=" + newBook.mBookId + ")--");
	}

	// 업데이트
	private void updateBookTitle(String newtitle, Integer bookId) {
		mDatabase.beginTransaction();

		ContentValues values = new ContentValues();
		//책 제목 변경
		values.put("title", newtitle);
		mDatabase.update(TABLE_BOOK, values, "_id=?",  new String[]{bookId.toString()});

		mDatabase.setTransactionSuccessful();
		mDatabase.endTransaction();
	}

	// 해당 ID로 제거
	public void deleteBook(Integer bookId) {
		mDatabase.beginTransaction();

		mDatabase.delete(TABLE_BOOK, "_id=?", new String[] { bookId.toString() });
		Log.i(DEBUG_TAG, "Deleted Book Id:  " + bookId.toString());

		mDatabase.setTransactionSuccessful();
		mDatabase.endTransaction();
	}
	
	// 테이블 정보를 나타내는 Helper 클래스
	class TBLAuthor {
		String mAuthorName;
		String mAuthorAlias;
		long mAuthorId;

		public TBLAuthor(String authorName, String authorAlias) {
			mAuthorName = authorName;
			mAuthorAlias = authorAlias;
			mAuthorId = -1;
		}
	}
	class TBLBook {
		String mTitle;
		Date mDateAdded;
		TBLAuthor mAuthor;
		long mBookId;

		public TBLBook(String title, Date dateAdded, TBLAuthor author) {
			mTitle = title;
			mDateAdded = dateAdded;
			mAuthor = author;
			mBookId = -1;
		}
	}
	
}