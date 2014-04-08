package com.framgia.simpletoeic.custom;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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

	private boolean mCorrect = false;

	private Context context;

	private ImageView imgConfirm;

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
		imgConfirm = (ImageView) findViewById(R.id.imgConfirm);
		rdoA = (RadioButton) findViewById(R.id.radioA);
		rdoB = (RadioButton) findViewById(R.id.radioB);
		rdoC = (RadioButton) findViewById(R.id.radioC);
		rdoD = (RadioButton) findViewById(R.id.radioD);

		imgConfirm.setOnClickListener(onConfirm);
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
			if(!TextUtils.isEmpty(mAnswerD)){
				rdoD.setText(mAnswerD);
				rdoD.setVisibility(View.VISIBLE);
			}
			else {
				rdoD.setVisibility(View.GONE);
			}
		}
	}

	/** On Check change listener */
	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			imgConfirm.setVisibility(View.VISIBLE);
			mCorrect = false;

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

	/** Confirm listener to Disable selection again*/
	private OnClickListener onConfirm = new OnClickListener() {

		@Override
		public void onClick(View v) {
			imgConfirm.setImageResource(R.drawable.ic_lock_transparent);
			
			String trueAnswer = question.getAns_correct().toLowerCase();
			
			if(trueAnswer.equals("a")){
				rdoA.setTextColor(getResources().getColor(R.color.color_blue));
				if(rdoA.isChecked()) mCorrect = true;
				
			}
			else if(trueAnswer.equals("b")){
				rdoB.setTextColor(getResources().getColor(R.color.color_blue));
				if(rdoB.isChecked()) mCorrect = true;
			}
			else if(trueAnswer.equals("c")){
				rdoC.setTextColor(getResources().getColor(R.color.color_blue));
				if(rdoC.isChecked()) mCorrect = true;
			}
			else if(trueAnswer.equals("d")){
				rdoD.setTextColor(getResources().getColor(R.color.color_blue));
				if(rdoD.isChecked()) mCorrect = true;
			}
			
			//Disable selection again
			int mChildCount = radioGroup.getChildCount();
			for(int i = 0; i < mChildCount; i++){
	            ((RadioButton)radioGroup.getChildAt(i)).setEnabled(false);
	        }
			
		}
	};

	/**
	 * @return true if is confirmed
	 * */
	public boolean isCorrect() {
		return mCorrect;
	}

}
