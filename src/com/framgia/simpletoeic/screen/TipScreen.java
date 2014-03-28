package com.framgia.simpletoeic.screen;

import android.os.Bundle;
import android.widget.TextView;

import com.framgia.simpletoeic.BaseSimpleToeicActivity;
import com.framgia.simpletoeic.R;

public class TipScreen extends BaseSimpleToeicActivity {
	private TextView tvReadingTipContent, tvReadingTipPart1Content,
			tvReadingTipPart2Content, tvReadingTipPart3Content,

			tvListeningContent, tvListeningTipPart1Content,
			tvListeningTipPart2Content, tvListeningTipPart3Content,
			tvListeningTipPart4Content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tip);
		tvReadingTipContent = (TextView) findViewById(R.id.tvReadingTipContent);
		tvReadingTipPart1Content = (TextView) findViewById(R.id.tvReadingTipPart1Content);
		tvReadingTipPart2Content = (TextView) findViewById(R.id.tvReadingTipPart2Content);
		tvReadingTipPart3Content = (TextView) findViewById(R.id.tvReadingTipPart3Content);
		tvListeningContent = (TextView) findViewById(R.id.tvListeningTipContent);
		tvListeningTipPart1Content = (TextView) findViewById(R.id.tvListeningTipPart1Content);
		tvListeningTipPart2Content = (TextView) findViewById(R.id.tvListeningTipPart2Content);
		tvListeningTipPart3Content = (TextView) findViewById(R.id.tvListeningTipPart3Content);
		tvListeningTipPart4Content = (TextView) findViewById(R.id.tvListeningTipPart4Content);

		tvReadingTipContent.setText(R.string.reading_direction);
		tvReadingTipPart1Content.setText(R.string.reading_direction_part1);
		tvReadingTipPart2Content.setText(R.string.reading_direction_part2);
		tvReadingTipPart3Content.setText(R.string.reading_direction_part3);

		tvListeningContent.setText(R.string.listening_direction);
		tvListeningTipPart1Content.setText(R.string.listening_direction_part1);
		tvListeningTipPart2Content.setText(R.string.listening_direction_part2);
		tvListeningTipPart3Content.setText(R.string.listening_direction_part3);
		tvListeningTipPart4Content.setText(R.string.listening_direction_part4);
	}

}
