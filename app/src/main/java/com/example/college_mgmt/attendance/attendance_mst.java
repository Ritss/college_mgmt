package com.example.college_mgmt.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;



public class attendance_mst extends Activity {
	
	// TODO: TESTING!! REMOVE WHEN DONE
			private static String URL_ATTENDANCE = "http://localhost/college/attendance_mst.php";
			private static final String TAG_SUCCESS = "success";
			private static final String TAG_ATTENDANCE = "attendance_mst";
			
			// Creating JSON Parser object
			JSONParser jParser = new JSONParser();

			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.attendance_mst);
				

				new testDetails().execute();

				

			}

			private class testDetails extends AsyncTask<Void, Void, Void> {

				@Override
				protected Void doInBackground(Void... arg0) {

					// Building Parameters
					List<NameValuePair> params = new ArrayList<NameValuePair>();

					// getting JSON string from URL
					JSONObject json = jParser.makeHttpRequest(URL_ATTENDANCE, "GET", params);
					// Log.e("MAIN OBJECT: ", json.toString());

					try {
						// Checking for SUCCESS TAG
						int success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							JSONArray JAAtten = json.getJSONArray(TAG_ATTENDANCE);

							/** SET THE NUMBER OF OFFERS AVAILABLE **/
							int intChickenDishes = JAAtten.length();
							// Log.e("NUMBER OF CATEGORIES",
							// String.valueOf(intCategories));

							if (intChickenDishes != 0) {

								AttendanceData attendance;

								for (int i = 0; i < JAAtten.length(); i++) {
									JSONObject JOAtten = JAAtten.getJSONObject(i);
									Log.e("INTERNAL OBJECTS", JOAtten.toString());

									attendance = new AttendanceData();

									if (JOAtten.has("AttendanceID")) {
										String strAttendanceID = JOAtten
												.getString("AttendanceID");
										attendance.setAttendance_id(strAttendanceID);
										Log.e("ATTENDANCE ID", strAttendanceID);
									}

									if (JOAtten.has("StaffID")) {

										String strStaffID = JOAtten
												.getString("StaffID");
										attendance.setStaff_id(strStaffID);
										Log.e("STAFF ID", strStaffID);
									}

									if (JOAtten.has("ClassID")) {

										String strClassID = JOAtten
												.getString("ClassID");
										attendance.setClass_id(strClassID);
										Log.e("CLASS ID", strClassID);
									}
									
									if (JOAtten.has("DivisionID")) {

										String strDivisionID = JOAtten
												.getString("DivisionID");
										attendance.setDivision_id(strDivisionID);
										Log.e("DIVISION ID", strDivisionID);
									}
									
									if (JOAtten.has("SubjectID")) {

										String strSubjectID = JOAtten
												.getString("SubjectID");
										attendance.setSubject_id(strSubjectID);
										Log.e("SUBJECT ID", strSubjectID);
									}
									
									if (JOAtten.has("Date")) {

										String strDate = JOAtten
												.getString("Date");
										attendance.setDate(strDate);
										Log.e("Date", strDate);
									}
									
									if (JOAtten.has("Time")) {

										String strTime = JOAtten
												.getString("Time");
										attendance.setTime(strTime);
										Log.e("Time", strTime);
									}


									
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					return null;
				}

			}

//			@Override
//			public boolean onCreateOptionsMenu(Menu menu) {
//				// Inflate the menu; this adds items to the action bar if it is present.
//				getMenuInflater().inflate(R.menu.activity_main, menu);
//				return true;
//			}

		}

	
	


