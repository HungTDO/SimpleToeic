package com.framgia.simpletoeic.screen;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_CONTENT;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_IMAGEURL;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_PARTID;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_A;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_B;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_C;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_CORRECT;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_D;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_QUESTION;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.database.Dialog;
import com.framgia.simpletoeic.database.Question;
import com.framgia.simpletoeic.ie.Keys;

public class ReadingScreen extends BaseSimpleToeicActivity {

	private int mCurentDialog = 0;

	private Button btnSubmit;

	private ViewGroup layoutBar, layoutDialog, layoutQuestion;

	private TextView tvDialogContent;

	private ArrayList<Dialog> listDialog;

	private ArrayList<Question> listQuestion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reading);

		init();

		Bundle b = getIntent().getExtras();
		if (b != null) {
			int partID = b.getInt(Keys.BKEY_PARTID);
			Cursor cursor = dialogDAO.getDialogByPartID(partID);
			if (cursor != null) {

				listDialog = new ArrayList<Dialog>();
				listQuestion = new ArrayList<Question>();

				int indexId = cursor.getColumnIndex(_ID);
				int partId = cursor.getColumnIndex(DIALOG_PARTID);
				int content = cursor.getColumnIndex(DIALOG_CONTENT);
				int imgUrl = cursor.getColumnIndex(DIALOG_IMAGEURL);
				// Get Dialog by Part
				while (cursor.moveToNext()) {
					int id = cursor.getInt(indexId);
					int mDialogId = cursor.getInt(partId);
					String dContent = cursor.getString(content);
					String imageUrl = cursor.getString(imgUrl);
					Dialog item = new Dialog(id, mDialogId, dContent, imageUrl);
					// Add dialog to List
					listDialog.add(item);

				}
				cursor.close();
				showShortToastMessage("Dialog Count:" + listDialog.size());

				nextDialog(mCurentDialog);
			}
		}
	}

	private void init() {
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		layoutBar = (ViewGroup) findViewById(R.id.readingBar);
		layoutDialog = (ViewGroup) findViewById(R.id.dialogContent);
		layoutQuestion = (ViewGroup) findViewById(R.id.questionContent);

		tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);

	}

	private void nextDialog(int nextDialog) {

		if (nextDialog < 0)
			return;

		if (listDialog != null) {
			int size = listDialog.size();
			if (size > mCurentDialog) {
				int dialogId = listDialog.get(mCurentDialog).getId();
				String content = listDialog.get(mCurentDialog).getContent();
				tvDialogContent.setText(content);

				Cursor mCursorQuestion = questionDAO
						.getQuestionByDialogId(dialogId);
				if (mCursorQuestion != null) {
					int quesId = mCursorQuestion.getColumnIndex(_ID);
					int quesQues = mCursorQuestion
							.getColumnIndex(QUESTION_QUESTION);
					int quesA = mCursorQuestion.getColumnIndex(QUESTION_ANS_A);
					int quesB = mCursorQuestion.getColumnIndex(QUESTION_ANS_B);
					int quesC = mCursorQuestion.getColumnIndex(QUESTION_ANS_C);
					int quesD = mCursorQuestion.getColumnIndex(QUESTION_ANS_D);
					int quesCorrect = mCursorQuestion
							.getColumnIndex(QUESTION_ANS_CORRECT);

					while (mCursorQuestion.moveToNext()) {
						int mQId = mCursorQuestion.getInt(quesId);
						String mQues = mCursorQuestion.getString(quesQues);
						String mA = mCursorQuestion.getString(quesA);
						String mB = mCursorQuestion.getString(quesB);
						String mC = mCursorQuestion.getString(quesC);
						String mD = mCursorQuestion.getString(quesD);
						String mCorrect = mCursorQuestion
								.getString(quesCorrect);
						// Add item
						Question item = new Question(mQId, dialogId, mQues, mA,
								mB, mC, mD, mCorrect);
						listQuestion.add(item);

					}
				}

				// Close cursor
				mCursorQuestion.close();
			}
		}
	}

	private void nextQuestion(int nextQuestion) {

	}

}
