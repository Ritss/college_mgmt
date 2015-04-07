package com.example.college_mgmt;



import com.example.college_mgmt.classs.ClassActivity;
import com.example.college_mgmt.division.DivisionActivity;
import com.example.college_mgmt.staff.StaffActivity;
import com.example.college_mgmt.subject.SubjectActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import com.example.college_mgmt.JSONParser;


public class Dashboard extends Activity {
	Button btn_STUDENT,btn_DIVISION,btn_STAFF,btn_REPORTS,btn_EXIT,btn_SUBJECT,btn_CLASS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		btn_CLASS=(Button)findViewById(R.id.btnCLASS);
		btn_STUDENT=(Button) findViewById(R.id.btnSTUDENT);
		btn_DIVISION=(Button) findViewById(R.id.btnDIVISION);
		btn_STAFF=(Button) findViewById(R.id.btnSTAFF);
		btn_REPORTS=(Button) findViewById(R.id.btnREPORTS);
		btn_SUBJECT=(Button) findViewById(R.id.btnSUBJECT);
		btn_EXIT=(Button) findViewById(R.id.btnEXIT);
		
		
		btn_SUBJECT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent showSubject = new Intent(Dashboard.this, SubjectActivity.class);
				
				startActivity(showSubject);
				
			}
		});
		
		btn_CLASS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent showClass = new Intent(Dashboard.this, ClassActivity.class);
				
				startActivity(showClass);
				
			}
		});
		
		btn_STAFF.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent showStaff = new Intent(Dashboard.this, StaffActivity.class);
				
				startActivity(showStaff);
				
			}
		});
		
		btn_DIVISION.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent showDiv = new Intent(Dashboard.this, DivisionActivity.class);
			
				startActivity(showDiv);
				
			}
		});
		}
	}


