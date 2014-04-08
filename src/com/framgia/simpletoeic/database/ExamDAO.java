package com.framgia.simpletoeic.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.framgia.simpletoeic.database.DBConstants._ID;
import static com.framgia.simpletoeic.database.DBConstants.TABLE_EXAM;
import static com.framgia.simpletoeic.database.DBConstants.EXAM_NAME;
import static com.framgia.simpletoeic.database.DBConstants.EXAM_EXAMID;
import static com.framgia.simpletoeic.database.DBConstants.EXAM_TYPE;

/**
 * Exam Data Access Object class
 * 
 * @author HUNGTDO
 */
public class ExamDAO {

	private SQLiteDatabase db;

	private static ExamDAO mExamObj = null;

	private ExamDAO() {
	}

	/**
	 * Get instance of Exam Data Access Object
	 * 
	 * @param db
	 *            access database object
	 * */
	public static ExamDAO getInstance(SQLiteDatabase db) {
		if (mExamObj == null) {
			mExamObj = new ExamDAO();
		}
		mExamObj.db = db;
		return mExamObj;
	}

	/**
	 * Close database access
	 * */
	public void close() {
		if (db != null) {
			db.close();
		}
	}

	/**
	 * Get all exam in database
	 * 
	 * @return Cursor data filled
	 * 
	 * */
	public Cursor getAllExam(int type) {
		String[] columns = { _ID, EXAM_EXAMID, EXAM_NAME, EXAM_TYPE };
		String orderBy = EXAM_EXAMID + " ASC";
		String selections = EXAM_TYPE + " = ?";
		String[] selectionArgs = {String.valueOf(type)};
				
		return db.query(TABLE_EXAM, columns, selections, selectionArgs, null, null, orderBy);
	}
}
