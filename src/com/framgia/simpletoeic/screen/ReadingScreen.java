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

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.custom.QuestionLayoutItem;
import com.framgia.simpletoeic.database.Dialog;
import com.framgia.simpletoeic.database.Question;
import com.framgia.simpletoeic.ie.IReadingHandle;
import com.framgia.simpletoeic.ie.Keys;
import com.framgia.simpletoeic.screen.util.Debugger;

public class ReadingScreen extends BaseSimpleToeicActivity implements IReadingHandle, OnClickListener{

	/**count questions in a Dialog*/
	private int mTotalQuestionDialog = 0;
	
	/**Current index of dialog in total Dialog*/
	private int mCurentDialog = 0;
	
	/**Total questions in a Part*/
	private int mMaxQuestion = 0;
	
	/**Current index of question in total Question. @See : mTotalQuestionDialog*/
	private int mCurrentIndexQuestion = 1;

	private Button btnSubmit, btnBack;

	private ScrollView scrollView;
	
	private ViewGroup layoutBar, layoutDialog, layoutQuestion;

	private TextView tvDialogContent;

	private ArrayList<Dialog> listDialog;

	private ArrayList<Question> listQuestion;
	
	private ViewFlipper viewFlipper;
	
	private ArrayList<Boolean> listAnswers;
	
	private int partID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reading);

		init();
		//Save true answer
		listAnswers = new ArrayList<Boolean>();
		
		Bundle b = getIntent().getExtras();
		if (b != null) {
			partID = b.getInt(Keys.BKEY_PARTID);
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
				
				nextDialog();
			}
		}
	}

	private void init() {
		
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnBack = (Button) findViewById(R.id.btnBack);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		layoutBar = (ViewGroup) findViewById(R.id.readingBar);
		layoutDialog = (ViewGroup) findViewById(R.id.dialogContent);
		layoutQuestion = (ViewGroup) findViewById(R.id.questionContent);
		tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		
		btnBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		btnSubmit.setText("NEXT");
		
		

	}

	private void nextDialog() {
		
		if (listDialog != null) {
			int size = listDialog.size();
			if (size > mCurentDialog) {
				int dialogId = listDialog.get(mCurentDialog).getId();
				String content = listDialog.get(mCurentDialog).getContent();
				tvDialogContent.setText(content);

				Cursor mCursorQuestion = questionDAO
						.getQuestionByDialogId(dialogId);
				if (mCursorQuestion != null) {
					
					this.mTotalQuestionDialog = mCursorQuestion.getCount();
					mCurrentIndexQuestion = 1;//Default question index
					viewFlipper.removeAllViews();//Remove all old Questions 
					listQuestion = new ArrayList<Question>();
					
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
					
					//Default
					mCurrentIndexQuestion = 1;
					viewFlipper.invalidate();
				}

				// Close cursor
				mCursorQuestion.close();
				
				//Increase current index
				mCurentDialog++;
			}
			else
			{
				int countCorrect = 0;
				int count = listAnswers.size();
				for(int i=0; i<count; i++)
				{
					boolean isCorrect = listAnswers.get(i);
					if(isCorrect) countCorrect++;
				}
				
				Debugger.d("RESULT >" + mMaxQuestion + " ;Correct:" + countCorrect) ;
				// End Part, go to Result screen
				Bundle bundle = new Bundle();
				bundle.putInt(Keys.BKEY_PARTID, partID);
				bundle.putInt(Keys.BKEY_TOTAL_QUESTION, mMaxQuestion);
				bundle.putInt(Keys.BKEY_TRUE_ANSWER, countCorrect);
				goActivity(self, ResultScreen.class, bundle);
				
				finish();
			}
		}
	}

	private void nextQuestion() {
		viewFlipper.setInAnimation(this, R.anim.in_animation1);
		viewFlipper.setOutAnimation(this, R.anim.out_animation1);
		viewFlipper.showNext();
		viewFlipper.invalidate();
		
	}
	
	private void previousQuestion() {
		viewFlipper.setInAnimation(this, R.anim.in_animation);
		viewFlipper.setOutAnimation(this, R.anim.out_animation);
		viewFlipper.showPrevious();
		viewFlipper.invalidate();
	}

//	private android.content.DialogInterface.OnClickListener onConfirm = new DialogInterface.OnClickListener() {
//
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			
//			int countCorrect = 0;
//			int size = viewFlipper.getChildCount();
//			for(int i=0; i< size; i++){
//				boolean confirm = ((QuestionLayoutItem) viewFlipper.getChildAt(i)).isCorrect();
//				if(confirm) countCorrect++;
//			}
//			
//			showShortToastMessage("Checked: " + countCorrect + " / " + mMaxQuestion);
//			// TODO: Do some thing
////			goActivity(self, ResultScreen.class);
//		}
//	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSubmit:
			
			if(mCurrentIndexQuestion < mTotalQuestionDialog){
				nextQuestion();
				mCurrentIndexQuestion++;
			}
			else{
				
				//Calculate dialog score and saved
				int countCorrect = 0;
				int size = viewFlipper.getChildCount();
				for(int i=0; i< size; i++){
					QuestionLayoutItem item = (QuestionLayoutItem) viewFlipper.getChildAt(i);
					boolean confirm = item.isCorrect();
					listAnswers.add(confirm);
					if(confirm) countCorrect++;
					Debugger.d("CONFIRM: " + confirm);
				}
				
				int msize = listAnswers.size();
				showShortToastMessage("Checked: " + countCorrect + " / " + msize);
				
				showToastMessage("Next Dialog. No way back");
				nextDialog();
				//Go to head layout
				scrollView.scrollTo(0, 0);
				
//				showDialog(R.string.app_name, R.string.message_are_you_sure,
//						R.string.text_ok, R.string.text_cancel, onConfirm, null);
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
