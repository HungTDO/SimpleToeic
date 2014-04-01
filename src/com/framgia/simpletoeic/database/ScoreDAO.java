package com.framgia.simpletoeic.database;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.SCORE_BESTSCORE;
import static com.framgia.simpletoeic.database.DBConstants.SCORE_PARTID;
import static com.framgia.simpletoeic.database.DBConstants.SCORE_TOTAL_QUESTION;
import static com.framgia.simpletoeic.database.DBConstants.TABLE_SCORE;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ScoreDAO {

	private SQLiteDatabase db;

	private static ScoreDAO mExamObj = null;

	private ScoreDAO(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Get instance of Exam Data Access Object
	 * 
	 * @param db
	 *            access database object
	 * */
	public static ScoreDAO getInstance(SQLiteDatabase db) {
		if (mExamObj == null) {
			mExamObj = new ScoreDAO(db);
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

	public boolean isPartExist(int partId){
		boolean isExist = false;
		
		String[] columns = { _ID, SCORE_PARTID, SCORE_BESTSCORE };
		String selection = SCORE_PARTID + " = ?";
		String[] selectionArgs = { String.valueOf(partId) };
		Cursor c = db.query(TABLE_SCORE, columns, selection, selectionArgs,
				null, null, null);
		if (c != null && c.getCount() > 0) {
			isExist = true;
		}
		return isExist;
	}
	
	public long updateScore(int partId, int newHighScore) {
		String whereClause = SCORE_PARTID + " = ?";
		String[] whereArgs = { String.valueOf(partId) };
		ContentValues values = new ContentValues();
		values.put(SCORE_BESTSCORE, newHighScore);
		return db.update(TABLE_SCORE, values, whereClause, whereArgs);
	}

	public int addNewScore(int partID, int totalQuestion, int newHighScore) {
		ContentValues values = new ContentValues();
		values.put(SCORE_PARTID, partID);
		values.put(SCORE_TOTAL_QUESTION, totalQuestion);
		values.put(SCORE_BESTSCORE, newHighScore);
		return ((int) db.insert(TABLE_SCORE, null, values));
	}

	public int bestScore(int partId) {
		int best = 0;
		String[] columns = {SCORE_BESTSCORE };
		String selection = SCORE_PARTID + " = ?";
		String[] selectionArgs = { String.valueOf(partId) };
		Cursor c = db.query(TABLE_SCORE, columns, selection, selectionArgs,
				null, null, null);
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			best = c.getInt(c.getColumnIndex(SCORE_BESTSCORE));
		}
		return best;
	}
	
	public Cursor getScore(int partId)
	{
		String[] columns = {SCORE_TOTAL_QUESTION, SCORE_BESTSCORE};
		String selection = SCORE_PARTID + " = ?";
		String[] selectionArgs = { String.valueOf(partId) };
		return db.query(TABLE_SCORE, columns, selection, selectionArgs,
				null, null, null);
	}
}
