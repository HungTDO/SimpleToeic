package com.framgia.simpletoeic.custom;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.database.Question;
import com.framgia.simpletoeic.ie.IReadingHandle;
import com.framgia.simpletoeic.screen.util.Debugger;

/**
 * Question layout customize class
 * 
 * @author HUNGTDO
 * */
public class QuestionLayoutItem extends LinearLayout {

	private Context context;

	private TextView tvQuestion;

	private RadioGroup radioGroup;

	private RadioButton rdoA, rdoB, rdoC, rdoD;

	private Question question;

	private IReadingHandle handle;

	public QuestionLayoutItem(Activity context, Question question) {
		super(context);
		this.context = context;
		this.handle = (IReadingHandle) context;
		this.question = question;
		init();
	}

	private void init() {
		inflate(context, R.layout.question_item, this);

		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		rdoA = (RadioButton) findViewById(R.id.radioA);
		rdoB = (RadioButton) findViewById(R.id.radioB);
		rdoC = (RadioButton) findViewById(R.id.radioC);
		rdoD = (RadioButton) findViewById(R.id.radioD);

		radioGroup.setOnCheckedChangeListener(changeListener);

		if (question != null) {
			String mQuestion = question.getQuestion();
			String mAnswerA = question.getAns_a();
			String mAnswerB = question.getAns_b();
			String mAnswerC = question.getAns_c();
			String mAnswerD = question.getAns_d();
			// Fill data
			tvQuestion.setText(mQuestion);
			rdoA.setText(mAnswerA);
			rdoB.setText(mAnswerB);
			rdoC.setText(mAnswerC);
			rdoD.setText(mAnswerD);
		}
	}

	/** On Check change listener */
	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioA:
				Debugger.d("ID:" + rdoA.getText());
				break;
			case R.id.radioB:
				Debugger.d("ID:" + rdoB.getText());
				break;
			case R.id.radioC:
				Debugger.d("ID:" + rdoC.getText());
				break;
			case R.id.radioD:
				Debugger.d("ID:" + rdoD.getText());
				break;
			default:
				Debugger.d("ID:" + checkedId);
				break;
			}
		}
	};

}
