package com.example.college_mgmt.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends Activity {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_STAFF = "http://10.0.2.2/college/staff_details.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STAFF = "staff_details";

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	
	ListView listStaff;
	StaffAdapter adapter;
	ArrayList<StaffData> arrStaff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_list);
		

		arrStaff = new ArrayList<StaffData>();
		listStaff = (ListView) findViewById(R.id.listStaff);
		adapter = new StaffAdapter(StaffActivity.this, arrStaff);

		new testDetails().execute();

		

	}

	private class testDetails extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(URL_STAFF, "GET", params);
			// Log.e("MAIN OBJECT: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray JAStaff = json.getJSONArray(TAG_STAFF);

					/** SET THE NUMBER OF OFFERS AVAILABLE **/
					int intStaffCount = JAStaff.length();
					// Log.e("NUMBER OF CATEGORIES",
					// String.valueOf(intCategories));

					if (intStaffCount != 0) {

						StaffData staff;

						for (int i = 0; i < JAStaff.length(); i++) {
							JSONObject JOStaff = JAStaff.getJSONObject(i);
						//	Log.e("INTERNAL OBJECTS", JOStaff.toString());

							staff = new StaffData();

							if (JOStaff.has("StaffID")) {
								String strStaffID = JOStaff
										.getString("StaffID");
								staff.setStaffId(strStaffID);
							//	Log.e("STAFF ID", strStaffID);
							}

							if (JOStaff.has("StaffName")) {

								String strStaffName = JOStaff
										.getString("StaffName");
								staff.setStaffName(strStaffName);
								//Log.e("STAFF NAME", strStaffName);
							}

							if (JOStaff.has("ContactNo")) {
								String strContact = JOStaff
										.getString("ContactNo");
					//			Log.e("CONTACT", strContact);
								staff.setContactNo(strContact);
							}

							if (JOStaff.has("UserID")) {

								String strUserID = JOStaff
										.getString("UserID");
						//		Log.e("USER ID", strUserID);
								staff.setUserID(strUserID);
							}
							arrStaff.add(staff);
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
			
			listStaff.setAdapter(adapter);
		}

	}

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
            	Intent createStaff = new Intent(StaffActivity.this, CreateStaffActivity.class);
            	startActivity(createStaff);
            	StaffActivity.this.finish();
//                Toast.makeText(SubjectActivity.this, "Create new Subject", Toast.LENGTH_SHORT).show();

                break;
            case R.id.closeSubject:
            	StaffActivity.this.finish();

            default:
                break;
        }

        return false;
    }

}
