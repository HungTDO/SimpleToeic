package com.framgia.simpletoeic.screen;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.custom.StarLayout;
import com.framgia.simpletoeic.custom.StarLayout.EStar;
import com.framgia.simpletoeic.ie.Keys;

public class ResultScreen extends BaseSimpleToeicActivity {

	private TextView tvTestName, tvScore, tvBestScore, tvTimeSpent;

//	private Button btnPreview;
	
	private Button btnDone;

	private StarLayout mStar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_result);

		tvTestName = (TextView) findViewById(R.id.tvTestName);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBestScore = (TextView) findViewById(R.id.tvBestScore);
//		tvTimeSpent = (TextView) findViewById(R.id.tvTimeSpent);
//		btnPreview = (Button) findViewById(R.id.btnPreview);
		btnDone = (Button) findViewById(R.id.btnDone);
		mStar = (StarLayout) findViewById(R.id.starScore);

		btnDone.setOnClickListener(onDone);
//		btnPreview.setOnClickListener(onPreview);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			//Fill data
			String name = bundle.getString(Keys.BKEY_PART_NAME);
			int partId = bundle.getInt(Keys.BKEY_PARTID, 0);
			int maxQuestion = bundle.getInt(Keys.BKEY_TOTAL_QUESTION, 0);
			int correct = bundle.getInt(Keys.BKEY_TRUE_ANSWER, 0);
			int bestScore = scoreDAO.bestScore(partId);
			
			String score = String.format(getString(R.string.text_score), correct, maxQuestion);
			String best = String.format(getString(R.string.text_best_score), bestScore, maxQuestion);
			
			if(bestScore < correct){
				score += " (High Score)";
				if(!scoreDAO.isPartExist(partId)){
					scoreDAO.addNewScore(partId, maxQuestion, correct);
				}
				else{
					scoreDAO.updateScore(partId, correct);
				}
			}
			
			//Set star
			int percent = (int)(correct * 100) / maxQuestion;
			EStar start = EStar.EMPTY;
			if(percent >= 50 && percent < 70){
				start = EStar.LOW;
			}
			else if(percent >= 70 && percent < 90){
				start = EStar.MEDIUM;
			}
			else if(percent >= 90 && percent <= 100){
				start = EStar.HIGH;
			}
			
			//Fill data
			tvScore.setText(score);
			tvBestScore.setText(best);
			mStar.setStar(start);
			tvTestName.setText(name);
		}
			
		
		
	}

	private OnClickListener onDone = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
//	private OnClickListener onPreview = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			
//		}
//	};

}
