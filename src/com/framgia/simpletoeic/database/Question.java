package com.framgia.simpletoeic.database;

public class Question {

	private int id = 0;
	
	private int dialogId = 0;
	
	private String question;
	
	private String ans_a;
	
	private String ans_b;
	
	private String ans_c;
	
	private String ans_d;
	
	private String ans_correct;

	public Question(int id, int dialogId, String question, String ans_a,
			String ans_b, String ans_c, String ans_d, String ans_correct) {
		this.id = id;
		this.dialogId = dialogId;
		this.question = question;
		this.ans_a = ans_a;
		this.ans_b = ans_b;
		this.ans_c = ans_c;
		this.ans_d = ans_d;
		this.ans_correct = ans_correct;
	}

	public int getId() {
		return id;
	}

	public int getDialogId() {
		return dialogId;
	}

	public String getQuestion() {
		return question;
	}

	public String getAns_a() {
		return ans_a;
	}

	public String getAns_b() {
		return ans_b;
	}

	public String getAns_c() {
		return ans_c;
	}

	public String getAns_d() {
		return ans_d;
	}

	public String getAns_correct() {
		return ans_correct;
	}
	
	
}
