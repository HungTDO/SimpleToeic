package com.framgia.simpletoeic.screen;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.fragment.ToeicMenuFragment;
import com.framgia.simpletoeic.ie.EMenu;
import com.framgia.simpletoeic.ie.IMenuProcessing;
import com.framgia.simpletoeic.screen.util.Debugger;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ToeicHomeScreen extends BaseSimpleToeicActivity implements
		IMenuProcessing {

	private SlidingMenu menu;

	private ListView lvExam;
	
	private TextView tvPart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Debugger.i("Home Screen Started");

		init();
		
		Cursor mCursorExamShowAll= examDAO.getAllExam();
		if(mCursorExamShowAll != null)
		{
			int count = mCursorExamShowAll.getCount();
			Debugger.i("DB -> Exam table count: " + count);
			while(mCursorExamShowAll.moveToNext()){
				
			}
		}
		
		
	}
	
	
	private void init()
	{
		tvPart = (TextView) findViewById(R.id.tvPart);
		lvExam = (ListView) findViewById(R.id.listExam);

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
		String part = menu.toString();
		tvPart.setText(part);
		showShortToastMessage("Click: " + part);
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
}
