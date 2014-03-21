package com.framgia.simpletoeic.ie;

/**
 * Interface to interact between Fragment with Activity
 * 
 * @author HUNGTDO
 * */
public interface IMenuProcessing {

	/**
	 * Send values to Activity
	 * 
	 * @param menu
	 *            {@link EMenu} type
	 * */
	public void onFragToActivity(EMenu menu);
}
