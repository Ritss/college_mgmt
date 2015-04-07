package com.example.college_mgmt.attend_trans;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.college_mgmt.R;

public class StudentAdapter extends BaseAdapter{
	/***** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER *****/
	Activity activity;
	
	/***** LAYOUTINFLATER TO USE A CUSTOM LAYOUT *****/
	LayoutInflater inflater = null;
	
	/***** ARRAYLIST TO GET DATA FROM THE ACTIVITY *****/
	ArrayList<StudentData> arrStudents;
	
 public StudentAdapter(Activity activity, ArrayList<StudentData> arrStudents) {
		
		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
		this.activity = activity;
		
		/* CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL INSTANCE */
		this.arrStudents = arrStudents;
		
		/* INSTANTIATE THE LAYOUTINFLATER */
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrStudents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrStudents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		// A VIEWHOLDER INSTANCE
		ViewHolder holder;

		// CAST THE CONVERTVIEW IN A VIEW INSTANCE
		View vi = convertView;

		// CHECK CONVERTVIEW STATUS
		if (convertView == null)	{
			// CAST THE CONVERTVIEW INTO THE VIEW INSTANCE vi
			vi = inflater.inflate(R.layout.attendance_item, null);
			
			// INSTANTIATE THE VIEWHOLDER INSTANCE
			holder = new ViewHolder();
			
			/*****	CAST THE LAYOUT ELEMENTS	*****/
			holder.txtName = (TextView) vi.findViewById(R.id.txtName);
			
			// SET THE TAG TO "vi"
			vi.setTag(holder);
		} else {
			// CAST THE VIEWHOLDER INSTANCE
			holder = (ViewHolder) vi.getTag();
		}
		
		/* GET THE STUDENTS NAME */
		final String STUDENT_NAME = arrStudents.get(position).getStudentName();
		holder.txtName.setText(STUDENT_NAME);
		
		return vi;
	}
	
	private static class ViewHolder{
		TextView txtName;
	}

}
