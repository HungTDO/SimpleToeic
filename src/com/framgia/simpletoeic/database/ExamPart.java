package com.framgia.simpletoeic.database;

import com.framgia.simpletoeic.custom.StarLayout.EStar;

/**
 * A common class used to save data of Exam and Part table
 * @author HUNGTDO
 *
 */
public class ExamPart {

	private int id = 0;
	
	private int examId = 0;
	
	private String name;
	
	private EStar star = EStar.EMPTY;

	public ExamPart(int id, int examId, String name) {
		this.id = id;
		this.examId = examId;
		this.name = name;
	}
	
	public ExamPart(int id, int examId, String name, EStar star) {
		this.id = id;
		this.examId = examId;
		this.name = name;
		this.star = star;
	}

	public int getId() {
		return id;
	}

	public int getExamId() {
		return examId;
	}

	public String getName() {
		return name;
	}

	public EStar getStar() {
		return star;
	}
	
	
}
