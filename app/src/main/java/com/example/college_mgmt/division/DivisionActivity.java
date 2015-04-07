package com.example.college_mgmt.division;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;

public class DivisionActivity extends Activity {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_DIVISION = "http://10.0.2.2/college/division_mst.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DIVISION = "division_mst";

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ListView listDivision;
	//DivisionAdapter adapter;
	ArrayList<DivisionData> arrDivision;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.division_list);

		arrDivision = new ArrayList<DivisionData>();
		listDivision = (ListView) findViewById(R.id.listDivision);
		//adapter = new DivisionAdapter(DivisionActivity.this, arrDivision);


		
		Log.e("Check"," In onCreate");
		new testDetails().execute();
		Log.e("Check"," In onCreate after execute fun");
	}

	private class testDetails extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(URL_DIVISION, "GET",
					params);
			// Log.e("MAIN OBJECT: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray JADivision = json.getJSONArray(TAG_DIVISION);

					/** SET THE NUMBER OF OFFERS AVAILABLE **/
					int intDivision = JADivision.length();
					 Log.e("NUMBER OF CATEGORIES",
					 String.valueOf(intDivision));

					if (intDivision != 0) {

						DivisionData division;

						for (int i = 0; i < JADivision.length(); i++) {
							JSONObject JODivision = JADivision.getJSONObject(i);
						Log.e("INTERNAL OBJECTS", JODivision.toString());

							division = new DivisionData();

							if (JODivision.has("DivisionID")) {
								String strDivisionID = JODivision
										.getString("DivisionID");
								division.setDivision_id(strDivisionID);
		//						Log.e("DIVISION ID", strDivisionID);
							}

							if (JODivision.has("DivisionName")) {

								String strDivision = JODivision
										.getString("DivisionName");
								division.setDivision(strDivision);
								Log.e("DIVISION", strDivision);
							}
							arrDivision.add(division);

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
			
			//listDivision.setAdapter(adapter);
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
            	Intent createDiv = new Intent(DivisionActivity.this, CreateDivisionActivity.class);
            	startActivity(createDiv);
//                Toast.makeText(SubjectActivity.this, "Create new Subject", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }

        return false;
    }

	}

