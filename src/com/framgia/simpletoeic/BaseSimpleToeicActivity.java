package com.framgia.simpletoeic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author HUNGTDO Base Simple Toeic class. Extended to another activity
 * 
 * */
public class BaseSimpleToeicActivity extends Activity {

	private int modifier;
	
	protected BaseSimpleToeicActivity self;

	protected SimpleToeicAppplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		self = this;
		app = (SimpleToeicAppplication) getApplication();
	}

	protected void goActivity(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
	}

	protected void goActivity(Context context, Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(context, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	protected void goActivity(Context context, Class<?> cls, int flag) {
		Intent intent = new Intent(context, cls);
		intent.addFlags(flag);
		startActivity(intent);
	}

	protected void goActivity(Context context, Class<?> cls, int flag,
			Bundle bundle) {
		Intent intent = new Intent(context, cls);
		intent.addFlags(flag);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
