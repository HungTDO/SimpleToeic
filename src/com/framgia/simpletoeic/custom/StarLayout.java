package com.framgia.simpletoeic.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.framgia.simpletoeic.R;

public class StarLayout extends LinearLayout {

	private static final int[] IMG_STAR = { R.drawable.btn_star_on,
			R.drawable.btn_star_off };;

	private ImageView imgStar1, imgStar2, imgStar3;

	public StarLayout(Context context) {
		super(context);
		init();
	}

	public StarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public StarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.layout_star, this);

		imgStar1 = (ImageView) findViewById(R.id.star1);
		imgStar2 = (ImageView) findViewById(R.id.star2);
		imgStar3 = (ImageView) findViewById(R.id.star3);
	}

	private void resetStar() {
		imgStar1.setImageResource(IMG_STAR[1]);
		imgStar2.setImageResource(IMG_STAR[1]);
		imgStar3.setImageResource(IMG_STAR[1]);
	}

	public void startScore(EStar star) {
		resetStar();

		switch (star) {
		case LOW:
			imgStar1.setImageResource(IMG_STAR[0]);
			break;
		case HIGH:
			imgStar1.setImageResource(IMG_STAR[0]);
			imgStar2.setImageResource(IMG_STAR[0]);
			break;
		case MEDIUM:
			imgStar1.setImageResource(IMG_STAR[0]);
			imgStar2.setImageResource(IMG_STAR[0]);
			imgStar3.setImageResource(IMG_STAR[0]);
			break;

		default:
			break;
		}
	}

	public enum EStar {
		EMPTY, LOW, MEDIUM, HIGH;
	}
}
