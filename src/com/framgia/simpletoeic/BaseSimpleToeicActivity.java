package com.framgia.simpletoeic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.simpletoeic.database.AssetDatabaseUtil;
import com.framgia.simpletoeic.database.DialogDAO;
import com.framgia.simpletoeic.database.ExamDAO;
import com.framgia.simpletoeic.database.PartDAO;
import com.framgia.simpletoeic.database.QuestionDAO;
import com.framgia.simpletoeic.database.ScoreDAO;

/**
 * @author HUNGTDO Base Simple Toeic class. Extended to another activity
 * 
 * */
public class BaseSimpleToeicActivity extends FragmentActivity {

	public static final boolean DEBUG_MODE = true;

	protected BaseSimpleToeicActivity self;

	protected SimpleToeicAppplication app;
	
	protected Resources res;

	protected FragmentManager frgManager;

	private SQLiteDatabase sDB = null;

	/** Exam data access object */
	protected ExamDAO examDAO;

	/** Part data access object */
	protected PartDAO partDAO;

	/** Dialog data access object */
	protected DialogDAO dialogDAO;
	
	/** Question data access object */
	protected QuestionDAO questionDAO;
	
	/** Question data access object */
	protected ScoreDAO scoreDAO;

	public static String TAG = "";

	{
		TAG = this.getClass().getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// App content
		self = this;
		res = getResources();
		app = (SimpleToeicAppplication) getApplication();
		frgManager = getSupportFragmentManager();
		openDatabase();
	}

	/**
	 * Method to management database, first: check and close current DB, then open database again.<br/>
	 * <b>Note</b>: Always use this method before access to database
	 * */
	protected void openDatabase() {
		closeDatabase();
		// Database
		AssetDatabaseUtil db = AssetDatabaseUtil.getDefaultInstance(self);
		this.sDB = db.openDatabase();
		examDAO = ExamDAO.getInstance(sDB);
		partDAO = PartDAO.getInstance(sDB);
		dialogDAO = DialogDAO.getInstance(sDB);
		questionDAO = QuestionDAO.getInstance(sDB);
		scoreDAO = ScoreDAO.getInstance(sDB);

	}

	protected void closeDatabase(){
		if(sDB != null && sDB.isOpen())
		{
			sDB.close();
		}
		sDB = null;
	}
	
	protected boolean isDbOpen(){
		return (sDB != null) ? sDB.isOpen() : false;
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
	 * Show hint dialog when go to Reading / Listening screen
	 * */
	protected void showHintDialog() {

		View mHint = LayoutInflater.from(self).inflate(
				R.layout.layout_confirm_hint, null);
		final CheckBox cbShowHint = (CheckBox) mHint.findViewById(R.id.cbShowHint);
		OnClickListener onOKDialog = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				boolean isCheck = cbShowHint.isChecked();
				//is do not show hint again
				putShowHint(!isCheck);
			}
		};

		boolean isShowHint = getShowHint();
		if(isShowHint)
		{
			cbShowHint.setChecked(!isShowHint);
			AlertDialog.Builder builder = new AlertDialog.Builder(self);
			builder.setView(mHint);
			builder.setPositiveButton("OK", onOKDialog);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
	
	/**
	 * Show help dialog when click Help button
	 * @see BaseSimpleToeicActivity#showHintDialog
	 * */
	protected void showHintDialogOnBar() {

		View mHint = LayoutInflater.from(self).inflate(
				R.layout.layout_confirm_hint, null);
		final CheckBox cbShowHint = (CheckBox) mHint
				.findViewById(R.id.cbShowHint);
		cbShowHint.setVisibility(View.GONE);

		AlertDialog.Builder builder = new AlertDialog.Builder(self);
		builder.setView(mHint);
		builder.setPositiveButton("OK", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	
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
	 * Show long toast message
	 * 
	 * @param str
	 *            alert message
	 */
	public void showToastMessage(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	/**
	 * Show short toast message
	 * 
	 * @param str
	 *            alert message
	 */
	public void showShortToastMessage(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Show toast message
	 * 
	 * @param str
	 *            alert message
	 * @param time
	 *            long or short time
	 * 
	 */
	public void showToastMessage(String str, int time) {
		Toast.makeText(this, str, time).show();
	}

	/**
	 * Show toast message
	 * 
	 * @param resId
	 *            resource string
	 * @param time
	 *            long or short time
	 * 
	 */
	public void showToastMessage(int resId, int time) {
		Toast.makeText(this, resId, time).show();
	}

	// ============SHAREPREFERCENCES=====================
	
	protected void putShowHint(Boolean values)
	{
		SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("SHOW_HINT", values);
		editor.commit();
	}
	
	public boolean getShowHint()
	{
		SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
		return preferences.getBoolean("SHOW_HINT", true);
	}
}
