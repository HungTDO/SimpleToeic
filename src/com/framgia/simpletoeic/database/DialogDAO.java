package com.framgia.simpletoeic.database;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_CONTENT;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_IMAGEURL;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_PARTID;
import static com.framgia.simpletoeic.database.DBConstants.TABLE_DIALOG;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DialogDAO {

	private SQLiteDatabase db;

	private static DialogDAO mExamObj = null;

	private DialogDAO(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Get instance of Exam Data Access Object
	 * 
	 * @param db
	 *            access database object
	 * */
	public static DialogDAO getInstance(SQLiteDatabase db) {
		if (mExamObj == null) {
			mExamObj = new DialogDAO(db);
		}
		mExamObj.db = db;
		return mExamObj;
	}
	
	public Cursor getDialogByPartID(int partId){
		Cursor cursor = null;
		String[] columns = {_ID, DIALOG_PARTID, DIALOG_CONTENT, DIALOG_IMAGEURL};
		String selection = DIALOG_PARTID + " = ?";
		String[] selectionArgs = {String.valueOf(partId)};
		String orderBy = _ID + " ASC";
		cursor = db.query(TABLE_DIALOG, columns, selection, selectionArgs, null, null, orderBy);
		return cursor;
	}
}