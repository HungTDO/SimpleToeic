package com.framgia.simpletoeic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.framgia.simpletoeic.R;
import com.framgia.simpletoeic.custom.SmallStarLayout;
import com.framgia.simpletoeic.database.ExamPart;

public class ListExamAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	
	private ArrayList<ExamPart> listExam;
	
	private boolean isPart = false;
	
	public ListExamAdapter(Context context, ArrayList<ExamPart> objects, boolean isPart) {
		inflater = LayoutInflater.from(context);
		this.listExam = objects;
		this.isPart = isPart;
	}
	
	@Override
	public int getCount() {
		return (listExam != null) ? listExam.size() : 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.list_exam_item, null);
			ViewHolder holder = new ViewHolder();
			holder.tvExamName = (TextView) convertView.findViewById(R.id.tvExamName);
			holder.starScore = (SmallStarLayout) convertView.findViewById(R.id.starScore);
			convertView.setTag(holder);
		}
		
		final ExamPart item = listExam.get(position);
		if(item != null)
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tvExamName.setText(item.getName());
			if(isPart){
				holder.starScore.setVisibility(View.VISIBLE);
				holder.starScore.setStar(item.getStar());
			}
			else{
				holder.starScore.setVisibility(View.GONE);
			}
		}
		
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		ExamPart item = (listExam != null) ? listExam.get(position) : null;
		return item;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	private class ViewHolder{
		private TextView tvExamName;
		private SmallStarLayout starScore;
	}

}
