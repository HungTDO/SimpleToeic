package com.framgia.simpletoeic.screen.util;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;

import android.util.Log;

/**
 * Debug class. Add TAG to LogCat: 'Debugger' to filter and search by Class name
 * 
 * @author HUNGTDO
 * */
public final class Debugger {

	private static final String DEBUG_TAG = "Debugger";

	public static void d(String msg) {
		if (BaseSimpleToeicActivity.DEBUG_MODE) {
			Log.d(DEBUG_TAG, BaseSimpleToeicActivity.TAG + " > " + msg);
		}
	}

	public static void e(String msg) {
		if (BaseSimpleToeicActivity.DEBUG_MODE) {
			Log.e(DEBUG_TAG, BaseSimpleToeicActivity.TAG + " > " + msg);
		}
	}

	public static void w(String msg) {
		if (BaseSimpleToeicActivity.DEBUG_MODE) {
			Log.w(DEBUG_TAG, BaseSimpleToeicActivity.TAG + " > " + msg);
		}
	}

	public static void i(String msg) {
		if (BaseSimpleToeicActivity.DEBUG_MODE) {
			Log.i(DEBUG_TAG, BaseSimpleToeicActivity.TAG + " > " + msg);
		}
	}
}
