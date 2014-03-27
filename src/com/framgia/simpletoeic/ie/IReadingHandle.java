package com.framgia.simpletoeic.ie;

/**
 * Interface helping interact reading parts
 * 
 * @author HUNGTDO
 * */
public interface IReadingHandle {

	/**
	 * Listener to notify when answers checked
	 * 
	 * @param index
	 *            current index
	 * @param isCorrect
	 * */
	public void onQuestionChecked(int index, boolean isCorrect);

}
