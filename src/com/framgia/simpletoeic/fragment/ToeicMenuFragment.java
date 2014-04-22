package com.framgia.simpletoeic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.ie.EMenu;
import com.framgia.simpletoeic.ie.IMenuProcessing;

public class ToeicMenuFragment extends Fragment implements OnClickListener {

	private IMenuProcessing menuProcessing;

	private FragmentActivity parent;

	private Button btnRead, btnListen, btnTip, btnAbout;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			menuProcessing = (IMenuProcessing) activity;
		} catch (ClassCastException ex) {
			throw new ClassCastException(activity.toString()
					+ " must implement IMenuProcessing");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		parent = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_menu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btnRead = (Button) parent.findViewById(R.id.btnRead);
		btnListen = (Button) parent.findViewById(R.id.btnListen);
		btnTip = (Button) parent.findViewById(R.id.btnTip);
//		btnSetting = (Button) parent.findViewById(R.id.btnSetting);
		btnAbout = (Button) parent.findViewById(R.id.btnAbout);

		btnRead.setOnClickListener(this);
		btnListen.setOnClickListener(this);
		btnTip.setOnClickListener(this);
//		btnSetting.setOnClickListener(this);
		btnAbout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnRead:
			menuProcessing.onFragToActivity(EMenu.READING);
			break;
		case R.id.btnListen:
			menuProcessing.onFragToActivity(EMenu.LISTENING);
			break;
		case R.id.btnTip:
			menuProcessing.onFragToActivity(EMenu.TIP);
			break;
//		case R.id.btnSetting:
//			menuProcessing.onFragToActivity(EMenu.SETTINGS);
//			break;
		case R.id.btnAbout:
			menuProcessing.onFragToActivity(EMenu.ABOUT);
			break;
		default:
			break;
		}

	}
}
