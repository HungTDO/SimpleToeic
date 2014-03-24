package com.framgia.simpletoeic.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExamDAO {

	private SQLiteDatabase db;

	private static ExamDAO mExamObj = null;

	private ExamDAO(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Get instance of Exam Data Access Object
	 * 
	 * @param db
	 *            access database object
	 * */
	public static ExamDAO getInstance(SQLiteDatabase db) {
		if (mExamObj == null) {
			mExamObj = new ExamDAO(db);
		}

		return mExamObj;
	}
	
	public Cursor getAllExam()
	{
		Cursor cursor = null;
		String[] comlumns = {};
		
		
		return cursor;
	}
}
