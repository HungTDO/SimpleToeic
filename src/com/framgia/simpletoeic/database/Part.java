package com.framgia.simpletoeic.database;

public class Part {
	
	private int id = 0;

	private int examId = 0;

	private String name;

	public Part(int id, int examId, String name) {
		this.id = id;
		this.examId = examId;
		this.name = name;
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
}
