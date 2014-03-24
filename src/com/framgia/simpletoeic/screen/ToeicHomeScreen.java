package com.framgia.simpletoeic.screen;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.adapter.ListExamAdapter;
import com.framgia.simpletoeic.custom.Rotate3dAnimation;
import com.framgia.simpletoeic.database.DBConstants;
import com.framgia.simpletoeic.database.ExamPart;
import com.framgia.simpletoeic.fragment.ToeicMenuFragment;
import com.framgia.simpletoeic.ie.EMenu;
import com.framgia.simpletoeic.ie.IMenuProcessing;
import com.framgia.simpletoeic.screen.util.Debugger;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ToeicHomeScreen extends BaseSimpleToeicActivity implements
		IMenuProcessing, OnClickListener {

	private SlidingMenu menu;

	private ViewGroup mContainer;

	private LinearLayout layoutExam, layoutPart;

	private ListView lvExam, lvPart;

	private TextView tvPartHeader, tvPartName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Debugger.i("Home Screen Started");

		init();

		Cursor mCursorExamShowAll = examDAO.getAllExam();
		if (mCursorExamShowAll != null) {
			int count = mCursorExamShowAll.getCount();
			Debugger.i("DB -> Exam table count: " + count);
			ArrayList<ExamPart> listObject = new ArrayList<ExamPart>();

			while (mCursorExamShowAll.moveToNext()) {
				int id = mCursorExamShowAll.getInt(mCursorExamShowAll
						.getColumnIndex(DBConstants._ID));
				int examID = mCursorExamShowAll.getInt(mCursorExamShowAll
						.getColumnIndex(DBConstants.EXAM_EXAMID));
				String name = mCursorExamShowAll.getString(mCursorExamShowAll
						.getColumnIndex(DBConstants.EXAM_NAME));
				ExamPart item = new ExamPart(id, examID, name);
				listObject.add(item);
			}
			mCursorExamShowAll.close();
			ListExamAdapter adapter = new ListExamAdapter(self, listObject);
			lvExam.setAdapter(adapter);
			Debugger.i("DB -> Exam adapter count: "
					+ lvExam.getAdapter().getCount());
		}

	}

	private void init() {
		mContainer = (ViewGroup) findViewById(R.id.container);
		layoutExam = (LinearLayout) findViewById(R.id.layoutExam);
		layoutPart = (LinearLayout) findViewById(R.id.layoutPart);
		tvPartHeader = (TextView) findViewById(R.id.tvPartHeader);
		tvPartName = (TextView) findViewById(R.id.tvPartName);
		lvExam = (ListView) findViewById(R.id.listExam);
		lvPart = (ListView) findViewById(R.id.listPart);

		layoutPart.setVisibility(View.GONE);
		layoutExam.setVisibility(View.VISIBLE);
		lvExam.setOnItemClickListener(onExamclick);
		lvPart.setOnItemClickListener(onPartClick);
		
		tvPartName.setClickable(true);
		tvPartName.setFocusable(true);
		tvPartName.setOnClickListener(this);

		// configure the SlidingMenu
		menu = new SlidingMenu(self);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.sliding_shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(self, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);

		// Add layout to menu
		frgManager.beginTransaction()
				.replace(R.id.menu_frame, new ToeicMenuFragment()).commit();

		// Set values
		String strRead = getString(R.string.text_menu_read).toUpperCase();
		tvPartHeader.setText(strRead);
	}

	/** Close sliding menu */
	private void closeMenu() {
		if (menu != null && menu.isShown()) {
			menu.toggle();
		}
	}

	@Override
	public void onFragToActivity(EMenu menu) {

		closeMenu();

		// set Exam name
		String part = menu.toString();
		tvPartHeader.setText(part);
		// showShortToastMessage("Click: " + part);

		switch (menu) {
		case READING:

			break;
		case LISTENING:

			break;
		case TIP:

			break;
		case SETTINGS:

			break;
		case ABOUT:

			break;

		default:
			break;
		}
	}

	private OnItemClickListener onExamclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			int examID = ((ExamPart) ((ListExamAdapter) parent.getAdapter())
					.getItem(position)).getExamId();
			// Get all part by exam id
			Cursor mCursorPart = partDAO.getAllPart(examID);
			showPart(position, mCursorPart);
		}
	};
	
	private OnItemClickListener onPartClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			int partID = ((ExamPart) ((ListExamAdapter) parent.getAdapter())
					.getItem(position)).getId();

		}
	};

	private void showPart(int position, Cursor mCursorPart) {
		if (mCursorPart != null) {
			Debugger.i("DB -> Part cursor count: " + mCursorPart.getCount());
			ArrayList<ExamPart> list = new ArrayList<ExamPart>();
			while (mCursorPart.moveToNext()) {
				int id = mCursorPart.getInt(mCursorPart
						.getColumnIndex(DBConstants._ID));
				int examID = mCursorPart.getInt(mCursorPart
						.getColumnIndex(DBConstants.PART_EXAMID));
				String name = mCursorPart.getString(mCursorPart
						.getColumnIndex(DBConstants.PART_NAME));
				ExamPart item = new ExamPart(id, examID, name);
				list.add(item);
			}
			mCursorPart.close();
			ListExamAdapter adapter = new ListExamAdapter(self, list);
			lvPart.setAdapter(adapter);

			applyRotation(position, 0, 90);
		}
	}

	/**
	 * Setup a new 3D rotation on the container view.
	 * 
	 * @param position
	 *            the item that was clicked to show a picture, or -1 to show the
	 *            list
	 * @param start
	 *            the start angle at which the rotation must begin
	 * @param end
	 *            the end angle of the rotation
	 */
	private void applyRotation(int position, float start, float end) {
		// Find the center of the container
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, 310.0f, true);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));

		mContainer.startAnimation(rotation);
	}

	/**
	 * This class listens for the end of the first half of the animation. It
	 * then posts a new action that effectively swaps the views when the
	 * container is rotated 90 degrees and thus invisible.
	 */
	private final class DisplayNextView implements Animation.AnimationListener {
		private final int mPosition;

		private DisplayNextView(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {

		}

		public void onAnimationEnd(Animation animation) {
			if (mPosition > -1) {
				 layoutExam.setVisibility(View.GONE);
				 layoutPart.setVisibility(View.VISIBLE);
				 lvPart.setVisibility(View.GONE);
				 layoutPart.requestFocus();
			}
			else {
				 layoutPart.setVisibility(View.GONE);
				 layoutExam.setVisibility(View.VISIBLE);
				 layoutExam.requestFocus();
			}
			
			mContainer.post(new SwapViews(mPosition));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}
	
	/**
	 * This class listens for the end of the first half of the animation. It
	 * then posts a new action that effectively swaps the views when the
	 * container is rotated 90 degrees and thus invisible.
	 */
	private final class DisplayNextView1 implements Animation.AnimationListener {
		private final int mPosition;

		private DisplayNextView1(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {

		}

		public void onAnimationEnd(Animation animation) {
			lvPart.setVisibility(View.VISIBLE);
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * This class is responsible for swapping the views and start the second
	 * half of the animation.
	 */
	private final class SwapViews implements Runnable {
		private final int mPosition;

		public SwapViews(int position) {
			mPosition = position;
		}

		public void run() {
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			Rotate3dAnimation rotation;

			if (mPosition > -1) {
//				 layoutExam.setVisibility(View.GONE);
//				 layoutPart.setVisibility(View.VISIBLE);
//				 layoutPart.requestFocus();

				rotation = new Rotate3dAnimation(90, 180, centerX, centerY,
						310.0f, false);
			} else {
//				 layoutPart.setVisibility(View.GONE);
//				 layoutExam.setVisibility(View.VISIBLE);
//				 layoutExam.requestFocus();

				rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
						310.0f, false);
			}

			rotation.setDuration(500);
			rotation.setFillAfter(false);
			rotation.setInterpolator(new DecelerateInterpolator());
			rotation.setAnimationListener(new DisplayNextView1(mPosition));
			mContainer.startAnimation(rotation);
			
		}
	}

	
	@Override
	protected void onDestroy() {
		// Close DB
		sDB.close();

		super.onDestroy();

	}

	@Override
	public void onClick(View v) {
		applyRotation(-1, 0, -90);
	}
}
