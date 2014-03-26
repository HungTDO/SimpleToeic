package com.framgia.simpletoeic.database;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_A;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_B;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_C;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_CORRECT;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_D;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_DIALOGID;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_QUESTION;
import static com.framgia.simpletoeic.database.DBConstants.TABLE_QUESTION;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestionDAO {

	private SQLiteDatabase db;

	private static QuestionDAO mExamObj = null;

	private QuestionDAO(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Get instance of Exam Data Access Object
	 * 
	 * @param db
	 *            access database object
	 * */
	public static QuestionDAO getInstance(SQLiteDatabase db) {
		if (mExamObj == null) {
			mExamObj = new QuestionDAO(db);
		}
		mExamObj.db = db;
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

	public Cursor getQuestionByDialogId(int dialogId) {
		
		String[] columns = { _ID, QUESTION_DIALOGID, QUESTION_QUESTION,
				QUESTION_ANS_A, QUESTION_ANS_B, QUESTION_ANS_C, QUESTION_ANS_D,
				QUESTION_ANS_CORRECT };
		String selection = QUESTION_DIALOGID + "= ?";
		String[] selectionArgs = { String.valueOf(dialogId) };
		String orderBy = _ID + " ASC";
		
		return db.query(TABLE_QUESTION, columns, selection, selectionArgs,
				null, null, orderBy);
	}
}
