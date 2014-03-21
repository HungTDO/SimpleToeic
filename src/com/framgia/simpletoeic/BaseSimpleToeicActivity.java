package com.framgia.simpletoeic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author HUNGTDO Base Simple Toeic class. Extended to another activity
 * 
 * */
public class BaseSimpleToeicActivity extends FragmentActivity {

	public static final boolean DEBUG_MODE = true;
	
	protected BaseSimpleToeicActivity self;

	protected SimpleToeicAppplication app;
	
	protected FragmentManager frgManager;
	
	public static String TAG = "";

	{
		TAG = this.getClass().getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		self = this;
		app = (SimpleToeicAppplication) getApplication();
		frgManager = getSupportFragmentManager();
		
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
	
	// ======================= KEYBOARD MANAGER =======================
	/**
	 * Show soft keyboard
	 * 
	 * @param editText
	 */
	protected void showKeyboard(EditText editText) {
		InputMethodManager inputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
					0);
		}
	}

	/**
	 * Hide soft keyboard
	 * 
	 * @param editText
	 */
	protected void hideKeyboard(EditText editText) {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}
	
	// ======================= DIALOG MANAGER =======================


		/**
		 * Show confirm dialog
		 * 
		 * @param title
		 * @param messageId
		 * @param positiveLabelId
		 * @param negativeLabelId
		 * @param positiveOnClick
		 * @param negativeOnClick
		 */
		public void showDialog(int titleId, int messageId, int positiveLabelId,
				int negativeLabelId,
				DialogInterface.OnClickListener positiveOnClick,
				DialogInterface.OnClickListener negativeOnClick) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(titleId));
			builder.setMessage(getString(messageId));
			builder.setPositiveButton(getString(positiveLabelId), positiveOnClick);
			builder.setNegativeButton(getString(negativeLabelId), negativeOnClick);
			AlertDialog alert = builder.create();
			alert.show();
		}

		/**
		 * Get dialog confirm
		 * 
		 * @param title
		 * @param messageId
		 * @param positiveLabelId
		 * @param negativeLabelId
		 * @param positiveOnClick
		 * @param negativeOnClick
		 */
		public void showDialog(String title, String message, String positiveLabel,
				String negativeLabel,
				DialogInterface.OnClickListener positiveOnClick,
				DialogInterface.OnClickListener negativeOnClick, boolean isCancelled) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(title);
			builder.setMessage(message);
			builder.setPositiveButton(positiveLabel, positiveOnClick);
			builder.setNegativeButton(negativeLabel, negativeOnClick);
			builder.setCancelable(isCancelled);
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		/**
		 * Show information dialog
		 * 
		 * @param messageId
		 * @param positiveLabelId
		 * @param positiveOnClick
		 */
		public void showDialog(int messageId, int positiveLabelId,
				DialogInterface.OnClickListener positiveOnClick, boolean isCancelled) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(""/* getString(R.string.app_name) */);
			builder.setMessage(getString(messageId));
			builder.setPositiveButton(getString(positiveLabelId), positiveOnClick);
			builder.setCancelable(isCancelled);
			AlertDialog alert = builder.create();
			alert.show();
		}

		// ======================= TOAST MANAGER =======================

		/**
		 *  Show long toast message
		 * @param str alert message
		 */
		public void showToastMessage(String str) {
			Toast.makeText(this, str, Toast.LENGTH_LONG).show();
		}

		/**
		 *  Show short toast message
		 * @param str alert message
		 */
		public void showShortToastMessage(String str) {
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		}

		/**
		 *  Show toast message
		 * @param str alert message
		 * @param time long or short time
		 *           
		 */
		public void showToastMessage(String str, int time) {
			Toast.makeText(this, str, time).show();
		}

		/**
		 *  Show toast message
		 * @param resId resource string
		 * @param time long or short time
		 *           
		 */
		public void showToastMessage(int resId, int time) {
			Toast.makeText(this, resId, time).show();
		}

}
