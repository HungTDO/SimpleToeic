package com.framgia.simpletoeic.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.framgia.simpletoeic.screen.util.Debugger;

public class AssetDatabaseUtil {

	private Context context;

	private static final String DB_NAME = "stoeic.db";
	
	private static AssetDatabaseUtil self;

	public AssetDatabaseUtil(Context context) {
		this.context = context;
	}

	public static AssetDatabaseUtil getDefaultInstance(Context context)
	{
		if(self == null){
			self = new AssetDatabaseUtil(context);
		}
		return self;
	}
	
	public SQLiteDatabase openDatabase() {
		// Get absolute path of database
		File dbFile = context.getDatabasePath(DB_NAME);
		// if is not exist then copy database
		if (!dbFile.exists()) {
			try {
				copyDatabase(dbFile);
				Debugger.d("DATABASE CREATED: " + dbFile.getAbsolutePath());
			} catch (IOException e) {
				throw new RuntimeException("Error creating source database", e);
			}
		}

		return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
				SQLiteDatabase.OPEN_READONLY);
	}

	/**
	 * Copy DB from Assets to Application
	 * 
	 * @throws IOException
	 */
	public final void copyDatabase(File file) throws IOException {

		String fullPath = file.getAbsolutePath();
		File dbFolder = new File(file.getParentFile().getAbsolutePath());

		// Create databases folder
		if (!dbFolder.exists())
			dbFolder.mkdirs();

		// Check DB file is exists?
		File dbFile = new File(fullPath);
		if (dbFile.exists())
			dbFile.delete();

		// Copy DB from assets
		InputStream localInputStream = context.getAssets().open(DB_NAME);
		FileOutputStream localFileOutputStream = new FileOutputStream(fullPath);
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = localInputStream.read(buffer)) > 0) {
			localFileOutputStream.write(buffer, 0, length);
		}
		// Close stream
		localFileOutputStream.flush();
		localFileOutputStream.close();
		localInputStream.close();
	}
}
