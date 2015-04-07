package com.example.college_mgmt.classs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ClassActivity extends ActionBarActivity {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_CLASS = "http://10.0.2.2/college/class_mst.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLASS = "class_mst";

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	
	ListView listClass;
	//ClassAdapter adapter;
	ArrayList<ClassData> arrClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.class_list);
		
		arrClass = new ArrayList<ClassData>();
		listClass = (ListView) findViewById(R.id.listClass);
	//	adapter = new ClassAdapter(ClassActivity.this, arrClass);
		
		/* FETCH DATA */
		new testDetails().execute();

	}

	private class testDetails extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(URL_CLASS, "GET",
					params);
			// Log.e("MAIN OBJECT: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray JAClass = json.getJSONArray(TAG_CLASS);

					/** SET THE NUMBER OF OFFERS AVAILABLE **/
					int intChickenDishes = JAClass.length();
					// Log.e("NUMBER OF CATEGORIES",
					// String.valueOf(intCategories));

					if (intChickenDishes != 0) {

						ClassData classs;

						for (int i = 0; i < JAClass.length(); i++) {
							JSONObject JOClass = JAClass.getJSONObject(i);
//							Log.e("INTERNAL OBJECTS", JOClasss.toString());

							classs = new ClassData();

							if (JOClass.has("ClassID")) {
								String strClassID = JOClass
										.getString("ClassID");
								classs.setClassID(strClassID);
//								Log.e("CLASS ID", strClassID);
							}

							if (JOClass.has("ClassName")) {

								String strClassName = JOClass
										.getString("ClassName");
								classs.setClassName(strClassName);
//								Log.e("CLASS NAME", strClassName);
							}

							
							
							arrClass.add(classs);

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
			
			//listClass.setAdapter(adapter);
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
            	Intent createClass = new Intent(ClassActivity.this, CreateClassActivity.class);
            	startActivity(createClass);
            	ClassActivity.this.finish();
//                Toast.makeText(ClassActivity.this, "Create new Class", Toast.LENGTH_SHORT).show();

                break;
            case R.id.closeSubject:
            	ClassActivity.this.finish();
            	break;
            default:
                break;
        }

        return false;
    }
	
	
}
