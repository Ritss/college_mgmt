package com.example.college_mgmt;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.college_mgmt.attendance.AttendanceMst;

public class Staff_mst_trn extends Activity {
	
	Button btnAttendance, btnReports, btnExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_mst_trn);
		btnAttendance=(Button)findViewById(R.id.btnAttendance);
		btnReports=(Button) findViewById(R.id.btnReports);
		btnExit=(Button) findViewById(R.id.btnExit);

		btnAttendance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent showAttendance = new Intent(Staff_mst_trn.this, AttendanceMst.class);
				showAttendance.putExtra("FLAG", "ATTENDANCE");
				startActivity(showAttendance);
				
				
				
			}
		});
		
		btnReports.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent showReports = new Intent(Staff_mst_trn.this, reports.class);
				showReports.putExtra("FLAG", "REPORTS");
				startActivity(showReports);
				
				
				
			}
		});
		
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				
				
				
			}
		});
}
}
