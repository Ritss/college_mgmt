package com.example.college_mgmt.subject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SubjectActivity extends Activity {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_SUBJECT = "http://10.0.2.2/college/subject_mst.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SUBJECT = "subject_mst";

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	
	ListView listSubject;
	//SubjectAdapter adapter;
	ArrayList<SubjectData> arrSubject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_list);
		
		arrSubject = new ArrayList<SubjectData>();
		listSubject = (ListView) findViewById(R.id.listSubject);
		//adapter = new SubjectAdapter(SubjectActivity.this, arrSubject);
		
		/* FETCH DATA */
		new testDetails().execute();

	}

	private class testDetails extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(URL_SUBJECT, "GET",
					params);
			// Log.e("MAIN OBJECT: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray JASubject = json.getJSONArray(TAG_SUBJECT);

					/** SET THE NUMBER OF OFFERS AVAILABLE **/
					int intChickenDishes = JASubject.length();
					// Log.e("NUMBER OF CATEGORIES",
					// String.valueOf(intCategories));

					if (intChickenDishes != 0) {

						SubjectData subject;

						for (int i = 0; i < JASubject.length(); i++) {
							JSONObject JOSubject = JASubject.getJSONObject(i);
//							Log.e("INTERNAL OBJECTS", JOSubject.toString());

							subject = new SubjectData();

							if (JOSubject.has("SubjectId")) {
								String strSubjectID = JOSubject
										.getString("SubjectId");
								subject.setSubject_id(strSubjectID);
//								Log.e("SUBJECT ID", strSubjectID);
							}

							if (JOSubject.has("SubjectName")) {

								String strSubjectName = JOSubject
										.getString("SubjectName");
								subject.setSubject_name(strSubjectName);
//								Log.e("SUBJECT NAME", strSubjectName);
							}

							if (JOSubject.has("SubjectCode")) {

								String strSubjectCode = JOSubject
										.getString("SubjectCode");
								subject.setSubject_code(strSubjectCode);
//								Log.e("SUBJECT CODE", strSubjectCode);
							}

							if (JOSubject.has("ClassId")) {

								String strClassId = JOSubject
										.getString("ClassId");
								subject.setClass_id(strClassId);
//								Log.e("CLASS ID", strClassId);
							}
							
							arrSubject.add(subject);

						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		//	listSubject.setAdapter(adapter);
		}

	}
/************************************Menu Bar *******************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_menu_bar, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newSubject:

                /** SHOW THE HELP POPUP **/
            	Intent createSubject = new Intent(SubjectActivity.this, CreateSubject.class);
            	startActivity(createSubject);
//                Toast.makeText(SubjectActivity.this, "Create new Subject", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }

        return false;
    }
}
