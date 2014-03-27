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
import java.util.HashMap;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.custom.QuestionLayoutItem;
import com.framgia.simpletoeic.database.Dialog;
import com.framgia.simpletoeic.database.Question;
import com.framgia.simpletoeic.ie.IReadingHandle;
import com.framgia.simpletoeic.ie.Keys;

public class ReadingScreen extends BaseSimpleToeicActivity implements IReadingHandle, OnClickListener{

	private int mCurentDialog = 0;

	private Button btnSubmit, btnBack;

	private ViewGroup layoutBar, layoutDialog, layoutQuestion;

	private TextView tvDialogContent;

	private ArrayList<Dialog> listDialog;

	private ArrayList<Question> listQuestion;
	
	private ViewFlipper viewFlipper;
	
	private int mMaxQuestion = 0;
	
	private int mCurrentIndexQuestion = 1;
	
	private SparseBooleanArray listAnswers;

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
		btnBack = (Button) findViewById(R.id.btnBack);
		layoutBar = (ViewGroup) findViewById(R.id.readingBar);
		layoutDialog = (ViewGroup) findViewById(R.id.dialogContent);
		layoutQuestion = (ViewGroup) findViewById(R.id.questionContent);
		tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		
		btnBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		btnSubmit.setText("NEXT");

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
					int correct = mCursorQuestion.getColumnIndex(QUESTION_ANS_CORRECT);

					while (mCursorQuestion.moveToNext()) {
						int mQId = mCursorQuestion.getInt(quesId);
						String mQues = mCursorQuestion.getString(quesQues);
						String mA = mCursorQuestion.getString(quesA);
						String mB = mCursorQuestion.getString(quesB);
						String mC = mCursorQuestion.getString(quesC);
						String mD = mCursorQuestion.getString(quesD);
						String mCorrect = mCursorQuestion
								.getString(correct);
						// Add item
						Question item = new Question(mQId, dialogId, mQues, mA,
								mB, mC, mD, mCorrect);
						listQuestion.add(item);
						
						QuestionLayoutItem mQuestionView = new QuestionLayoutItem(self, item);
						viewFlipper.addView(mQuestionView);
						mMaxQuestion++;
					}
					listAnswers = new SparseBooleanArray(mMaxQuestion);
					
					mCurrentIndexQuestion = 1;
					viewFlipper.invalidate();
				}

				// Close cursor
				mCursorQuestion.close();
			}
		}
	}

	private void nextQuestion() {
		viewFlipper.setInAnimation(this, R.anim.in_animation1);
		viewFlipper.setOutAnimation(this, R.anim.out_animation1);
		viewFlipper.showNext();
		
	}
	
	private void previousQuestion() {
		viewFlipper.setInAnimation(this, R.anim.in_animation);
		viewFlipper.setOutAnimation(this, R.anim.out_animation);
		viewFlipper.showPrevious();
	}

	private android.content.DialogInterface.OnClickListener onConfirm = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO: Do some thing
			goActivity(self, ResultScreen.class);
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSubmit:
			
			if(mCurrentIndexQuestion < mMaxQuestion){
				nextQuestion();
				mCurrentIndexQuestion++;
				if(mCurrentIndexQuestion == mMaxQuestion){
					btnSubmit.setText("SUBMIT");
				}
			}else{
				showDialog(R.string.app_name, R.string.message_are_you_sure,
						R.string.text_ok, R.string.text_cancel, onConfirm, null);
			}
			break;
		case R.id.btnBack:
			btnSubmit.setText("NEXT");
			if(mCurrentIndexQuestion > 1){
				previousQuestion();
				mCurrentIndexQuestion--;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onQuestionChecked(int index, boolean isCorrect) {
		
		
	}

}
