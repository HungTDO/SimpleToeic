package com.framgia.simpletoeic.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.custom.StarLayout;

public class ResultScreen extends BaseSimpleToeicActivity {

	private TextView tvTestName, tvScore, tvBestScore, tvTimeSpent;

	private Button btnPreview, btnDone;

	private StarLayout mStar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_result);

		tvTestName = (TextView) findViewById(R.id.tvTestName);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBestScore = (TextView) findViewById(R.id.tvBestScore);
		tvTimeSpent = (TextView) findViewById(R.id.tvTimeSpent);
		btnPreview = (Button) findViewById(R.id.btnPreview);
		btnDone = (Button) findViewById(R.id.btnDone);
		mStar = (StarLayout) findViewById(R.id.starScore);

		btnDone.setOnClickListener(onDone);
		btnPreview.setOnClickListener(onPreview);
	}

	private OnClickListener onDone = new OnClickListener() {

		@Override
		public void onClick(View v) {
			goActivity(self, ToeicHomeScreen.class,
					Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
	};
	
	private OnClickListener onPreview = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
		}
	};

}
