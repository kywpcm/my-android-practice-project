/*
 *  SQLite �⺻ ����
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

	// ���̺�  �̸�
	private static final String TABLE_BOOK = "tbl_books";
	private static final String TABLE_AUTHOR = "tbl_authors";

	//å���ڿ� ���� ����(_id, �̸�, ����)
	private static final String CREATE_AUTHOR_TABLE = 
			"CREATE TABLE tbl_authors ( " +
					"  _id INTEGER PRIMARY KEY AUTOINCREMENT ," + 
					"  authorname TEXT," +
					"  authoralias TEXT);";
	//å�� ���� ����(id, ����, ��������)
	private static final String CREATE_BOOK_TABLE = 
			"CREATE TABLE tbl_books ( " + 
					" _id INTEGER PRIMARY KEY AUTOINCREMENT ," +
					" title TEXT," +
					" inserted_date DATE," +
					" authorid INTEGER NOT NULL);";

	// ���̺�  ���ڵ� CRUD�� �����ϴ� �޼ҵ� ����
	private SQLiteDatabase mDatabase;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//�׽�Ʈ ����
		executeDatabaseExample();
	}
	
	public void executeDatabaseExample() {

		Log.i(DEBUG_TAG, "-- �� ���� ���� ���� --");

		//���� �� ���� ���α׷����� ����ϴ� DB �ν��Ͻ��� �ִ°�?
		if (Arrays.binarySearch(databaseList(), DATABASE_NAME) >= 0) {
			//�ִٸ� ������ ������
			deleteDatabase(DATABASE_NAME);
		}
		//���ٸ� DB �ν��Ͻ��� ����.
		mDatabase = openOrCreateDatabase(DATABASE_NAME,
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		// DB ȯ�� ����
		//���� �ȵ���̵� �÷����� ������ ��ȯ
		mDatabase.setLocale(Locale.getDefault());
		//Isolation�� ����ϰڴ°�?
		mDatabase.setLockingEnabled(true);
		//���� DB�� ���� ����
		mDatabase.setVersion(1); 

		// LogCat���� Ȯ�� �ϱ� �ٶ�.
		Log.i(DEBUG_TAG, "DB �н�: " + mDatabase.getPath());
		Log.i(DEBUG_TAG, "DB ����: " + mDatabase.getVersion());
		Log.i(DEBUG_TAG, "DB ������: " + mDatabase.getPageSize());
		Log.i(DEBUG_TAG, "DB �ִ������: " + mDatabase.getMaximumSize());

		Log.i(DEBUG_TAG, "DB ����?  " + mDatabase.isOpen());
		Log.i(DEBUG_TAG, "DB readonly?  " + mDatabase.isReadOnly());
		Log.i(DEBUG_TAG, "DB Locked by current thread ? "+ mDatabase.isDbLockedByCurrentThread());

		// ���̺� ����
		//SQLiteDatabase.execSQL(,)�� ����� DB ���̺� ����
		Log.i(DEBUG_TAG, " -- execSQL(,)�� ����� tbl_authors table ���� -- ");
		mDatabase.execSQL(CREATE_AUTHOR_TABLE);
		//SQLiteStatement.execute()�� ����� DB���̺� ����, ���� ��õ���� ����..
		Log.i(DEBUG_TAG,	"--SQLiteStatement.execute()�� ����� DB���̺� ����--");
		SQLiteStatement sqlSelect = mDatabase.compileStatement(CREATE_BOOK_TABLE);
		sqlSelect.execute();

		//�� ���̺� ����� �߰� ��
		insertSomeBooks();

		// select * from tbl_books ���� ���� ��
		Log.i(DEBUG_TAG, "--  select * from tbl_books; ���� ��� --");
		Cursor booksSelect = mDatabase.query(
				TABLE_BOOK, null, null, null, null, null,null);
		//��� ������ �׺���̼����� LogCat���� ���
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		//SELECT title, _id  FROM tbl_books ORDER BY title ASC ���� ���� ��
		Log.i(DEBUG_TAG, "-- SELECT title, id  FROM tbl_books ORDER BY title ASC; ���� ��� --");					
		String returedColumns2 [] = { "title", "_id" };
		String strSortOrder2 = "title ASC";
		booksSelect = mDatabase.query(
				"tbl_books", returedColumns2, null, null, null, null, strSortOrder2);
		//��� ������ �׺���̼� �ϸ鼭 ȹ���ϴ� �޼ҵ�
		logCatDisplayCursorInfo(booksSelect); 
		booksSelect.close();

		// SQLiteQueryBuilder�� �̿��� ���� ����
		Log.i(DEBUG_TAG, "-- SELECT tbl_books.title, tbl_books._id," +
				" tbl_authors.authorname, tbl_authors.authoralias, " +
				" tbl_books.authorid FROM tbl_books INNER JOIN tbl_authors " +
				" on tbl_books.authorid=tbl_authors._id ORDER BY title ASC --");
		//���� �� ������ ���� ����
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		//���̺� ����
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
		//��� ������ �׺���̼� �ϸ鼭 ȹ���ϴ� �޼ҵ�
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		Log.i(DEBUG_TAG, "-- " +
				" SELECT tbl_books.title, tbl_books._id, " +
				"  tbl_authors.authorname, tbl_authors.authoralias, tbl_books.authorid " +
				" FROM tbl_books INNER JOIN tbl_authors on tbl_books.authorid=tbl_authors.id" +
				"  WHERE title LIKE '%on%' ORDER BY title ASC;--");
		SQLiteQueryBuilder queryBuilder2 = new SQLiteQueryBuilder();	
		//���̺� ����
		queryBuilder2.setTables(TABLE_BOOK + ", " + TABLE_AUTHOR);
		//���� �ʵ�
		queryBuilder2.appendWhere("("+TABLE_BOOK + ".authorid = " +TABLE_AUTHOR	+ "._id" + ")");	
		//WHERE ����
		queryBuilder2.appendWhere(" AND (" + TABLE_BOOK + ".title" + " LIKE '%�ȵ���̵�%'" + ")");

		//���� ������ ���� �ϼ�
		booksSelect = queryBuilder2.query(
				mDatabase, returedColumns4, null, null, null, null, strSortOrder);
		//��� ������ �׺���̼� �ϸ鼭 ȹ���ϴ� �޼ҵ�
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

		//raw ����..
		booksSelect = mDatabase.rawQuery(
				sqlUnionExample, new String[]{ "%�ȵ�", "%ǥ��%"});
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// SIMPLE QUERY: select * from tbl_books WHERE _id=3;
		Log.i(DEBUG_TAG, 	"-- select * from tbl_books WHERE _id=3; --");
		booksSelect = mDatabase.query(
				TABLE_BOOK, null, "_id=?", new String[]{"3"}, null, null, null);
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// ������Ʈ 
		Log.i(DEBUG_TAG, "-- ������Ʈ �ϱ� --");
		updateBookTitle("�ȵ���̵� �⺻", 5);

		// ������Ʈ Ȯ��
		Log.i(DEBUG_TAG, "-- ������Ʈ ��� ���� : select * from tbl_books WHERE _id=5; --");
		booksSelect = mDatabase.query(
				TABLE_BOOK, null, "_id=?", new String[]{"5"}, null, null, null);
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// ����
		Log.i(DEBUG_TAG, "--  Delete�ϱ� _id 2 -- ");
		deleteBook(2);	

		// ���� Ȯ��
		Log.i(DEBUG_TAG, 	"-- select * from tbl_books WHERE _id=2; --");
		booksSelect = mDatabase.query(
				TABLE_BOOK, null, "_id=?", new String[]{"2"}, null, null, null);
		logCatDisplayCursorInfo(booksSelect);
		booksSelect.close();

		// Close the database
		Log.i(DEBUG_TAG, "-- Close DB --");
		mDatabase.close();
		Log.i(DEBUG_TAG, "DB Open ?  " + mDatabase.isOpen());

		Log.i(DEBUG_TAG, "-- SQLite3 ���ÿ��� �� --");
	}
	
	//Cursor SELECT ������ �׺���̼� �ϱ�
	public void logCatDisplayCursorInfo(Cursor selectQuery) {
		Log.i(DEBUG_TAG, "*** Cursor Begin *** " + " Results:" + 
				selectQuery.getCount() +" Columns: " + selectQuery.getColumnCount());

		String rowColumnHeaders = "|| ";
		
		//�÷��� ����
		int rowCount = selectQuery.getColumnCount();
		for (int i = 0; i < rowCount ; i++) {
			//�÷��� �̸��� ����Ѵ�
			rowColumnHeaders = rowColumnHeaders.concat(selectQuery.getColumnName(i) + " || ");
		}
		Log.i(DEBUG_TAG, "-- ���� Cursor ��������� �÷��̸�=> " + rowColumnHeaders);

		// �� ���� ����Ʈ �Ѵ�
		selectQuery.moveToFirst(); //��������� ó������ �̵�
		while (selectQuery.isAfterLast() == false) {
			String rowResults = "|| ";
			
			int rowSize = selectQuery.getColumnCount();
			for (int i = 0; i < rowSize; i++) {
				rowResults = rowResults.concat(selectQuery.getString(i) + " || ");
			}
			Log.i(DEBUG_TAG, "-- Ŀ���� ���� ��ġ [" + selectQuery.getPosition() + "] : " + rowResults);
			selectQuery.moveToNext();
		}
		Log.i(DEBUG_TAG, "*** Cursor End ***");
	}

	//���ڵ� �߰�
	public void insertSomeBooks() {

		//Ʈ����� ���� ���� üũ ?
		Log.i(DEBUG_TAG, "DB Transaction?  "	+ mDatabase.inTransaction());

		//���� ��¥
		Date today = new Date(java.lang.System.currentTimeMillis());
		//DB�� �ѱ� ����(UTF-8)
		TBLAuthor author1 = new TBLAuthor("ǥ�μ�", "�ڼ�����");
		addAuthor(author1); 

		addBook(new TBLBook("�ڹ��ھ� ���α׷���", today,	author1));
		addBook(new TBLBook("�ڹٳ�Ʈ��ũ ���α׷���", today,	author1));
		addBook(new TBLBook("�ڹٿ����������� ���α׷���", today,author1));
		addBook(new TBLBook("�ڹ�ME ���α׷���", today,author1));
		addBook(new TBLBook("�ȵ����̵� �⺻",today, author1));
		addBook(new TBLBook("�ȵ���̵� ����", today,author1));
		addBook(new TBLBook("�ȵ���̵� C2DM", today,author1));

		TBLAuthor author2 = new TBLAuthor("ǥ����", "����ī");
		addAuthor(author2); 

		addBook(new TBLBook("���̿��� �뵷 �޾Ƴ��¹�", today, author2));

		TBLAuthor author3 = new TBLAuthor("ǥ����", "��°��ī");
		addAuthor(author3); 

		addBook(new TBLBook("���̿��� �峭�� ��¹��", today,	author3));

		Log.i(DEBUG_TAG, "--DB Insert Transaction ����--");
	}

	//���� �Է�
	//Ʈ����� ���..
	private void addAuthor(TBLAuthor newAuthor) {
		Log.i(DEBUG_TAG, "--DB Table Insert Transaction Start --");
		//�̷� ������ ����ó�� ����� �Ѵ�.
		/*mSQLDB.beginTransaction();
		try {
		//���⼭ ����, ����, ���� ����� ���� �۾� ���� 
		//Ʈ����� ������
		mSQLDB.setTransactionSuccessful();
		}
		catch(SQLException e) { 
		//Ʈ����� ���н� ��ġ }
		finally { 
		mSQLDB.endTransaction();  //Ʈ����� ���� 
		}*/
		mDatabase.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put("authorname", newAuthor.mAuthorName);
		values.put("authoralias", newAuthor.mAuthorAlias);
		
		//�� �߰��Ŀ� _id���� ������
		newAuthor.mAuthorId = mDatabase.insertOrThrow(TABLE_AUTHOR, null, values);
		Log.i(DEBUG_TAG, "--Author �߰� :  " + newAuthor.mAuthorName + " " +
				newAuthor.mAuthorAlias + "(_ID=" + newAuthor.mAuthorId + ") --");
		//Ʈ����� Ŀ��
		mDatabase.setTransactionSuccessful();
		//Ʈ����� ��
		mDatabase.endTransaction();
	}
	
	// å �߰�
	private void addBook(TBLBook newBook) {
		ContentValues values = new ContentValues();
		values.put("title", newBook.mTitle);
		values.put("inserted_date", newBook.mDateAdded.toLocaleString());
		values.put("authorid", newBook.mAuthor.mAuthorId);
		
		newBook.mBookId = mDatabase.insertOrThrow(TABLE_BOOK, null, values);
		
		Log.i(DEBUG_TAG, "--Book �߰� :  " +	newBook.mTitle + "(_id=" + newBook.mBookId + ")--");
	}

	// ������Ʈ
	private void updateBookTitle(String newtitle, Integer bookId) {
		mDatabase.beginTransaction();

		ContentValues values = new ContentValues();
		//å ���� ����
		values.put("title", newtitle);
		mDatabase.update(TABLE_BOOK, values, "_id=?",  new String[]{bookId.toString()});

		mDatabase.setTransactionSuccessful();
		mDatabase.endTransaction();
	}

	// �ش� ID�� ����
	public void deleteBook(Integer bookId) {
		mDatabase.beginTransaction();

		mDatabase.delete(TABLE_BOOK, "_id=?", new String[] { bookId.toString() });
		Log.i(DEBUG_TAG, "Deleted Book Id:  " + bookId.toString());

		mDatabase.setTransactionSuccessful();
		mDatabase.endTransaction();
	}
	
	// ���̺� ������ ��Ÿ���� Helper Ŭ����
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