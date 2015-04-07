package com.example.college_mgmt.staffwise_subject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class staff_wise_subject extends Activity {
	
	// TODO: TESTING!! REMOVE WHEN DONE
		private static String URL_staff_wise_subject = "http://localhost/college/Staff_wise_subject.php";
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_staff_wise_subject = "staff_wise_subject";

		// Creating JSON Parser object
		JSONParser jParser = new JSONParser();

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.staff_wise_subject);

			new testDetails().execute();

		}

		private class testDetails extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... arg0) {

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(URL_staff_wise_subject, "GET",
						params);
				// Log.e("MAIN OBJECT: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						JSONArray JASubject = json.getJSONArray(TAG_staff_wise_subject);

						/** SET THE NUMBER OF OFFERS AVAILABLE **/
						int intChickenDishes = JASubject.length();
						// Log.e("NUMBER OF CATEGORIES",
						// String.valueOf(intCategories));

						if (intChickenDishes != 0) {

							StaffWiseSubjectData staff_wise_subject;

							for (int i = 0; i < JASubject.length(); i++) {
								JSONObject JOstaff_wise_subject = JASubject.getJSONObject(i);
								Log.e("INTERNAL OBJECTS", JOstaff_wise_subject.toString());

								staff_wise_subject = new StaffWiseSubjectData();

								if (JOstaff_wise_subject.has("Staff_wise_subjectID")) {
									String strStaff_wise_subjectID = JOstaff_wise_subject
											.getString("Staff_wise_subjectID");
									staff_wise_subject.setSubject_id(strStaff_wise_subjectID);
									Log.e("STAFF_WISE_SUBJECT_ID", strStaff_wise_subjectID);
								}

								if (JOstaff_wise_subject.has("StaffID")) {

									String strStaffID = JOstaff_wise_subject
											.getString("StaffID");
									staff_wise_subject.setStaff_id(strStaffID);
									Log.e("STAFF ID", strStaffID);
								}

								if (JOstaff_wise_subject.has("SubjectID")) {

									String strSubjectID = JOstaff_wise_subject
											.getString("SubjectID");
									staff_wise_subject.setSubject_id(strSubjectID);
									Log.e("SUBJECT ID", strSubjectID);
								}

								if (JOstaff_wise_subject.has("ClassID")) {

									String strClassId = JOstaff_wise_subject
											.getString("ClassId");
									staff_wise_subject.setClass_id(strClassId);
									Log.e("CLASS ID", strClassId);
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

//		@Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			// Inflate the menu; this adds items to the action bar if it is present.
//			getMenuInflater().inflate(R.menu.activity_main, menu);
//			return true;
//		}

	}



