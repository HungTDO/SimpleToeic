package com.framgia.simpletoeic.database;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.PART_EXAMID;
import static com.framgia.simpletoeic.database.DBConstants.PART_NAME;
import static com.framgia.simpletoeic.database.DBConstants.TABLE_PART;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PartDAO {

	private SQLiteDatabase db;

	private static PartDAO mExamObj = null;

	private PartDAO(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Get instance of Exam Data Access Object
	 * 
	 * @param db
	 *            access database object
	 * */
	public static PartDAO getInstance(SQLiteDatabase db) {
		if (mExamObj == null) {
			mExamObj = new PartDAO(db);
		}

		return mExamObj;
	}

	/**
	 * Close database access
	 * */
	public void close() {
		if (db != null && db.isOpen()) {
			db.close();
		}
	}

	/**
	 * Get all Part in Exam 
	 * 
	 * @return Cursor data filled
	 * */
	public Cursor getAllPart(int examID) {
		Cursor cursor = null;
		String[] columns = { _ID, PART_EXAMID, PART_NAME };
		String orderBy = _ID + " ASC";
		String selection = PART_EXAMID + "= ?"; 
		String[] selectionArgs= {String.valueOf(examID)};
		cursor = db.query(TABLE_PART, columns, selection, selectionArgs, null, null, orderBy);
		return cursor;
	}
}
