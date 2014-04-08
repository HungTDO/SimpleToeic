package com.framgia.simpletoeic.database;

import android.provider.BaseColumns;

public final class DBConstants implements BaseColumns {

	// VERSION TABLE CONSTANTS
	public static final String TABLE_VERSION = "VERSION";

	public static final String VERSION_VER = "ver";

	// EXAM TABLE CONSTANTS
	public static final String TABLE_EXAM = "EXAM";

	public static final String EXAM_EXAMID = "examId";

	public static final String EXAM_NAME = "name";
	
	public static final String EXAM_TYPE = "type";

	// PART TABLE CONSTANTS
	public static final String TABLE_PART = "PART";

	public static final String PART_NAME = "name";

	public static final String PART_EXAMID = "examId";

	// DIALOG TABLE CONSTANT
	public static final String TABLE_DIALOG = "DIALOG";

	public static final String DIALOG_PARTID = "partId";

	public static final String DIALOG_CONTENT = "content";

	public static final String DIALOG_IMAGEURL = "imageUrl";
	
	public static final String DIALOG_AUDIOURL = "audioUrl";

	// QUESTION TABLE CONSTANT
	public static final String TABLE_QUESTION = "QUESTION";

	public static final String QUESTION_DIALOGID = "dialogId";

	public static final String QUESTION_QUESTION = "question";

	public static final String QUESTION_ANS_A = "ans_a";

	public static final String QUESTION_ANS_B = "ans_b";

	public static final String QUESTION_ANS_C = "ans_c";

	public static final String QUESTION_ANS_D = "ans_d";

	public static final String QUESTION_ANS_CORRECT = "ans_correct";

	// SCORE TABLE CONSTANT
	public static final String TABLE_SCORE = "SCORE";

	public static final String SCORE_PARTID = "partId";
	
	public static final String SCORE_TOTAL_QUESTION = "total_question";

	public static final String SCORE_BESTSCORE = "best_score";

}
