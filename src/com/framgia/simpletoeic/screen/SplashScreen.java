package com.framgia.simpletoeic.screen;

import android.os.Bundle;
import android.os.Handler;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;

/**
 * Simple Toeic Splash screen
 * 
 * @author HUNGTDO
 * 
 */
public class SplashScreen extends BaseSimpleToeicActivity {

	private Handler handler;

	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		closeDatabase();
		setContentView(R.layout.splash_screen);
		handler = new Handler();

		runnable = new Runnable() {

			@Override
			public void run() {
				// Go to Home activity
				goActivity(self, ToeicHomeScreen.class);
				finish();
			}
		};
		//Delay thread
		handler.postDelayed(runnable, 2000);
	}

	@Override
	public void onBackPressed() {
		
		//Remove callback if back pressed
		if (handler != null) {
			handler.removeCallbacks(runnable);
		}
		super.onBackPressed();
	}

}
