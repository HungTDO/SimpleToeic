package com.framgia.simpletoeic.screen;

import static android.provider.BaseColumns._ID;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_AUDIOURL;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_CONTENT;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_IMAGEURL;
import static com.framgia.simpletoeic.database.DBConstants.DIALOG_PARTID;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_A;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_B;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_C;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_CORRECT;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_ANS_D;
import static com.framgia.simpletoeic.database.DBConstants.QUESTION_QUESTION;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class ListeningScreen extends BaseSimpleToeicActivity implements IReadingHandle, OnClickListener{

	/**count questions in a Dialog*/
	private int mTotalQuestionDialog = 0;
	
	/**Current index of dialog in total Dialog*/
	private int mCurentDialog = 0;
	
	/**Total questions in a Part*/
	private int mMaxQuestion = 0;
	
	/**Current index of question in total Question. @See : mTotalQuestionDialog*/
	private int mCurrentIndexQuestion = 1;
	
	/**Current part Id*/
	private int partID = 0;
	
	/**Media player is pause?*/
	private boolean isPause = false;
	
	private int audioDuration = 0;

	private Button btnSubmit, btnBack;

	private ScrollView scrollView;
	
	private ViewGroup layoutDialog;
	
	private ImageButton btnPlay;
	
	private ProgressBar prgAudio;

	private TextView tvDialogContent, tvReadingHeader, tvAudioTime;

	private ArrayList<Dialog> listDialog;

	private ArrayList<Question> listQuestion;
	
	private ViewFlipper viewFlipper;
	
	private ArrayList<Boolean> listAnswers;
	
	private ImageView imgDialog, btnHelp;

	private PhotoViewAttacher mAttacher;
	
	private MediaPlayer mp;
	
	private Handler mMediaHandler = null;
	
	private Runnable trackMediaInfo = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listening);

		init();
		//Save true answer
		listAnswers = new ArrayList<Boolean>();
		
		Bundle b = getIntent().getExtras();
		if (b != null) {
			String partName = b.getString(Keys.BKEY_PART_NAME);
			partID = b.getInt(Keys.BKEY_PARTID);
			tvReadingHeader.setText(partName);
			Cursor cursor = dialogDAO.getDialogByPartID(partID);
			if (cursor != null) {

				listDialog = new ArrayList<Dialog>();
				listQuestion = new ArrayList<Question>();

				int indexId = cursor.getColumnIndex(_ID);
				int partId = cursor.getColumnIndex(DIALOG_PARTID);
				int content = cursor.getColumnIndex(DIALOG_CONTENT);
				int imgUrl = cursor.getColumnIndex(DIALOG_IMAGEURL);
				int audioUrl = cursor.getColumnIndex(DIALOG_AUDIOURL);
				// Get Dialog by Part
				while (cursor.moveToNext()) {
					int id = cursor.getInt(indexId);
					int mDialogId = cursor.getInt(partId);
					String dContent = cursor.getString(content);
					String imageUrl = cursor.getString(imgUrl);
					String audio = cursor.getString(audioUrl);
					
					Dialog item = new Dialog(id, mDialogId, dContent, imageUrl, audio);
					// Add dialog to List
					listDialog.add(item);

				}
				cursor.close();
				//Load Dialog
				nextDialog();
				//Show Hint confirm dialog
				//showHintDialog();
			}
		}
	}

	private void init() {
		
		btnHelp = (ImageView) findViewById(R.id.btnHelp);
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		prgAudio = (ProgressBar) findViewById(R.id.prgAudio);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnBack = (Button) findViewById(R.id.btnBack);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		layoutDialog = (ViewGroup) findViewById(R.id.dialogContent);
		tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		imgDialog = (ImageView) findViewById(R.id.imgDialog);
		tvReadingHeader = (TextView) findViewById(R.id.tvReadingHeader);
		tvAudioTime = (TextView) findViewById(R.id.tvAudioTime);
		
		btnHelp.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		btnSubmit.setText("NEXT");
		
		scrollView.setOnTouchListener(new View.OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	        	scrollView.getParent().requestDisallowInterceptTouchEvent(true);
	            return false;
	        }
	    });
		
		mAttacher = new PhotoViewAttacher(imgDialog);
		mAttacher.setMaximumScale(3.0f);
		mAttacher.setOnViewTapListener(tapListener);
		
		mp = new MediaPlayer();
	}
	
	/**
	 * Single Tap image event
	 * */
	OnViewTapListener tapListener = new OnViewTapListener() {
		
		@Override
		public void onViewTap(View view, float x, float y) {
			//Un-zoom
			unZoom(x,y);
		}
	};
	
	/**
	 * Un-zoom for PhotoView
	 * @param x
	 * @param y
	 * */
	private void unZoom(float x, float y)
	{
		float mMinScale = PhotoViewAttacher.DEFAULT_MIN_SCALE;
		if(mAttacher.getScale() != mMinScale){
			mAttacher.setScale(mMinScale, x, y, true);
		}
	}
	

	private void removeAudio()
	{
		if(mp != null && mp.isPlaying())
		{
			mp.stop();
			mp.release();
			mp = null;
			Debugger.i("AUDIO_RELEASED");
		}
	}
	
	/**
	 * Track duration of Media player
	 * */
	private void currentMediaDuration()
	{
		trackMediaInfo = new Runnable() {
			
			@Override
			public void run() {
				if(mp != null && mp.isPlaying())
				{
					int duration = (mp.getCurrentPosition()/1000);
					String currentDuration = duration + "/" +  audioDuration;
					//Set media info
					prgAudio.setProgress(duration);
					tvAudioTime.setText(currentDuration);
					//Recursively track media info
					currentMediaDuration();
					
					Debugger.i("AUDIO_PLAYING > " + currentDuration);
				}
				else {
					if(mMediaHandler != null)
					{
						//Set default status
						String currentDuration = "0/" +  audioDuration;
						prgAudio.setProgress(0);
						tvAudioTime.setText(currentDuration);
						//Remove handler
						mMediaHandler.removeCallbacks(trackMediaInfo);
						mMediaHandler = null;
					}
				}
				
			}
		};

		mMediaHandler = new Handler();
		mMediaHandler.postDelayed(trackMediaInfo, 1000);//1s
		
	}
	
	/**
	 * Next dialog, show Dialog and Questions
	 * @return 
	 * */
	private void nextDialog() {
		
		if (listDialog != null) {
			int size = listDialog.size();
			if (size > mCurentDialog) {
				Dialog item = listDialog.get(mCurentDialog);
				//Remove current audio and new player
				removeAudio();
				
				try
				{
					//Load audio
					AssetFileDescriptor descriptor = getAssets().openFd(item.getAudioUrl());
					long start = descriptor.getStartOffset();
					long end = descriptor.getLength();
					
					mp = new MediaPlayer();
					mp.setDataSource(descriptor.getFileDescriptor(), start, end);
					mp.prepare();
					//Start audio
					mp.setVolume(1.0f, 1.0f);
					mp.start();
					
					//Set audio info
					audioDuration = mp.getDuration() / 1000;
					prgAudio.setMax(audioDuration);
					currentMediaDuration();
					
					Debugger.d("AUDIO_STARTED > " + item.getAudioUrl());
					
				}catch(IOException ex){
					ex.printStackTrace();
					showShortToastMessage("Audio load failed");
				}
				
				int dialogId = item.getId();
				String content = item.getContent();
				tvDialogContent.setText(content);
				
				try {
					String url = item.getImgUrl();
					if(!TextUtils.isEmpty(url))
					{
						InputStream is = getAssets().open(url);
						Bitmap img = BitmapFactory.decodeStream(is);
						imgDialog.setImageBitmap(img);
						imgDialog.setVisibility(View.VISIBLE);
						Debugger.d("IMAGE > " + url);
					}
					else{
						imgDialog.setVisibility(View.GONE);
						if(TextUtils.isEmpty(content)) 
							layoutDialog.setVisibility(View.GONE);
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
					imgDialog.setVisibility(View.GONE);
				}
				
				//Load Questions
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
					int count = 1;
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
						Question mQuestion = new Question(mQId, dialogId, mQues, mA,
								mB, mC, mD, mCorrect);
						listQuestion.add(mQuestion);
						
						QuestionLayoutItem mQuestionView = new QuestionLayoutItem(self, mQuestion);
						mQuestionView.setCurrentPage(count + "/" + mTotalQuestionDialog);
						viewFlipper.addView(mQuestionView);
						mMaxQuestion++;
						count++;
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
				bundle.putString(Keys.BKEY_PART_NAME, tvReadingHeader.getText().toString());
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnHelp:
			showHintDialogOnBar();
			break;
		case R.id.btnPlay:
			if (mp != null && !mp.isPlaying()) {
				// ReStart and track media player
				mp.start();
				currentMediaDuration();
			}
			break;
		case R.id.btnSubmit:

			if (mCurrentIndexQuestion < mTotalQuestionDialog) {
				nextQuestion();
				mCurrentIndexQuestion++;
			} else {

				// Calculate dialog score and saved
				int size = viewFlipper.getChildCount();
				for (int i = 0; i < size; i++) {
					QuestionLayoutItem item = (QuestionLayoutItem) viewFlipper
							.getChildAt(i);
					boolean confirm = item.isCorrect();
					listAnswers.add(confirm);
					Debugger.d("CONFIRM: " + confirm);
				}

				showToastMessage("Next Dialog. No way back");
				nextDialog();

				// Go to head layout
				scrollView.scrollTo(0, 0);

				// showDialog(R.string.app_name, R.string.message_are_you_sure,
				// R.string.text_ok, R.string.text_cancel, onConfirm, null);
			}
			break;
		case R.id.btnBack:
			btnSubmit.setText("NEXT");
			if (mCurrentIndexQuestion > 1) {
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
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(mp != null && isPause)
		{
			mp.start();
			Debugger.i("AUDIO_RESTART");
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(mp != null & mp.isPlaying())
		{
			mp.pause();
			isPause = true;
			Debugger.i("AUDIO_PAUSE");
		}
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		removeAudio();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeAudio();
	}
}
