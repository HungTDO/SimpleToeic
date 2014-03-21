package com.framgia.simpletoeic.screen;

import android.os.Bundle;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Debugger.d("Home Screen Started");

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

	/**Close sliding menu*/
	private void closeMenu() {
		if (menu != null && menu.isShown()) {
			menu.toggle();
		}
	}

	@Override
	public void onFragToActivity(EMenu menu) {
		closeMenu();
		showShortToastMessage("Click: " + menu.toString());
	}
}
