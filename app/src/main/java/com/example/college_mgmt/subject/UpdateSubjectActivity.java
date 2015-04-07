package com.example.college_mgmt.subject;

import com.example.college_mgmt.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateSubjectActivity extends ActionBarActivity {
	private static String URL_SUBJECT = "http://10.0.2.2/college/update_subject.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SUBJECT = "subject_mst";
	
	String SubId,SubName,SubCode,ClassId;
	
	AutoCompleteTextView actSubid;
	EditText edtsubname,edtSubcode;
	Spinner spClassName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_mst);
		
		actSubid=(AutoCompleteTextView) findViewById(R.id.actSubid);
		edtSubcode=(EditText) findViewById(R.id.edtSubcode);
        Toolbar myToolbar=(Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);
		//edtSub
	}
}
