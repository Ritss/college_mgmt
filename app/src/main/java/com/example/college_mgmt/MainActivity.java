package com.example.college_mgmt;

import java.util.Calendar;

import com.example.college_mgmt.staff.StaffActivity;
import com.example.college_mgmt.subject.SubjectActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class MainActivity extends Activity {

/***************** Calendar button*************************//*
	
	DateFormat fmtDate = DateFormat.getDateTimeInstance();
	EditText edtDate;
	Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONDAY, monthOfYear);
			myCalendar.set(Calendar.DATE, dayOfMonth);
			updateLabel();
		}

	};
	
	protected void updateLabel() {
		// TODO Auto-generated method stub
		edtDate.setText(fmtDate.format(myCalendar.getTime()));
	}
*//***********************************Calendar button end***************************//*
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent showDash= new Intent(MainActivity.this,Dashboard.class);
        startActivity(showDash);

       /* edtDate = (EditText) findViewById(R.id.edtDate);

		Button btnDate = (Button) findViewById(R.id.btnCal);
		btnDate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_SHORT).show();
				
				new DatePickerDialog(MainActivity.this, d, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		updateLabel();*/

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

}
