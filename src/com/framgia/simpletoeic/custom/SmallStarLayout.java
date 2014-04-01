package com.framgia.simpletoeic.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.custom.StarLayout.EStar;

public class SmallStarLayout extends LinearLayout{

	private static final int[] IMG_STAR = { R.drawable.btn_star_on,
			R.drawable.btn_star_off };

	private ImageView imgStar1, imgStar2, imgStar3;

	public SmallStarLayout(Context context) {
		super(context);
		init();
	}

	public SmallStarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SmallStarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.layout_small_star, this);

		imgStar1 = (ImageView) findViewById(R.id.star1);
		imgStar2 = (ImageView) findViewById(R.id.star2);
		imgStar3 = (ImageView) findViewById(R.id.star3);
	}

	private void resetStar() {
		imgStar1.setImageResource(IMG_STAR[1]);
		imgStar2.setImageResource(IMG_STAR[1]);
		imgStar3.setImageResource(IMG_STAR[1]);
	}

	public void setStar(EStar star) {
		resetStar();

		switch (star) {
		case LOW:
			imgStar1.setImageResource(IMG_STAR[0]);
			break;
		case MEDIUM:
			imgStar1.setImageResource(IMG_STAR[0]);
			imgStar2.setImageResource(IMG_STAR[0]);
			break;
		case HIGH:
			imgStar1.setImageResource(IMG_STAR[0]);
			imgStar2.setImageResource(IMG_STAR[0]);
			imgStar3.setImageResource(IMG_STAR[0]);
			break;

		default:
			resetStar();
			break;
		}
	}

}
