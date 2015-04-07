package com.example.college_mgmt.division;

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

public class division_mst extends Activity {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_DIVISION = "http://localhost/college/division_mst.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DIVISION = "division";

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.division_mst);

		new testDetails().execute();

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
					// Log.e("NUMBER OF CATEGORIES",
					// String.valueOf(intCategories));

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
								Log.e("DIVISION ID", strDivisionID);
							}

							if (JODivision.has("Division")) {

								String strDivision = JODivision
										.getString("Division");
								division.setDivision(strDivision);
								Log.e("DIVISION", strDivision);
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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
//
}
