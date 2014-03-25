package com.framgia.simpletoeic.screen;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_CONTENT;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_IMAGEURL;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_PARTID;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.database.Dialog;
import com.framgia.simpletoeic.ie.Keys;

public class ReadingScreen extends BaseSimpleToeicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reading);
		
		Bundle b = getIntent().getExtras();
		if(b != null){
			int partID = b.getInt(Keys.BKEY_PARTID);
			Cursor cursor = dialogDAO.getDialogByPartID(partID);
			if(cursor != null){
				
				ArrayList<Dialog> listDialog = new ArrayList<Dialog>();
				int indexId = cursor.getColumnIndex(_ID);
				int partId = cursor.getColumnIndex(DIALOG_PARTID);
				int content = cursor.getColumnIndex(DIALOG_CONTENT); 
				int imgUrl = cursor.getColumnIndex(DIALOG_IMAGEURL);		
				//Get Dialog by Part 
				while(cursor.moveToNext()){
					int id = cursor.getInt(indexId);
					int mDialogId = cursor.getInt(partId);
					String dContent = cursor.getString(content);
					String imageUrl = cursor.getString(imgUrl);
					Dialog item = new Dialog(id, mDialogId, dContent, imageUrl);
					//Add dialog to List
					listDialog.add(item);
					
				}
				cursor.close();
				showShortToastMessage("Dialog Count:" + listDialog.size());
			}
		}
	}
}
