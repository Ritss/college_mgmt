package com.example.college_mgmt.subject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;
import com.example.college_mgmt.classs.ClassData;

public class CreateSubject extends ActionBarActivity implements OnItemSelectedListener {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_SUBJECT = "http://10.0.2.2/college/add_subject.php";
	private static String URL_CLASS = "http://10.0.2.2/college/class_mst.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLASS = "class_mst";
	private static final String TAG_SUBJECT = "subject_mst";

	EditText edtSubname, edtSubcode;
	Spinner spClassName;
	AutoCompleteTextView actSubid;

	String strFlag;
	String clsName, clsID;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	private ArrayList<ClassData> categoriesList;
	List<String> lables;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_mst);

		edtSubname = (EditText) findViewById(R.id.edtsubname);
		actSubid = (AutoCompleteTextView) findViewById(R.id.actSubid);
		edtSubcode = (EditText) findViewById(R.id.edtSubcode);
		spClassName = (Spinner) findViewById(R.id.spClassName);
		actSubid.setEnabled(false);

		categoriesList = new ArrayList<ClassData>();

		new spclass().execute();
		spClassName.setOnItemSelectedListener(this);
        Toolbar myToolbar=(Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);

	}

	private class addSubject extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL_SUBJECT);

			Log.e("RESPONSE", "brfore Try");
			try {

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("subject_name", edtSubname
						.getText().toString()));

				params.add(new BasicNameValuePair("subject_code", edtSubcode
						.getText().toString()));

				params.add(new BasicNameValuePair("class_id", clsID.toString()));

				httpPost.setEntity(new UrlEncodedFormEntity(params));
				Log.e("PARAMS", params.toString());

				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				Log.e("RESPONSE", result);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("ERROR!! ERROR!!", e.toString());
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			edtSubname.setText(" ");
			edtSubcode.setText(" ");
			CreateSubject.this.finish();

		}

	}

	/*********************************** Spinner ****************************************/

	private class spclass extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(URL_CLASS, "GET", params);
			// Log.e("MAIN OBJECT: ", json.toString());

			Log.e("Check", "Test Details");
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray JAClass = json.getJSONArray(TAG_CLASS);

					/** SET THE NUMBER OF OFFERS AVAILABLE **/
					int intClass = JAClass.length();
					// Log.e("NUMBER OF CATEGORIES",
					// String.valueOf(intCategories));

					if (intClass != 0) {
						// Log.e("Check", "IntClass Has value");
						ClassData cls;

						for (int i = 0; i < JAClass.length(); i++) {
							JSONObject JOClass = JAClass.getJSONObject(i);
							Log.e("INTERNAL OBJECTS", JOClass.toString());

							cls = new ClassData();

							if (JOClass.has("ClassID")) {
								String strClassID = JOClass
										.getString("ClassID");
								cls.setClassID(strClassID);
								Log.e("CLASS ID", strClassID);
							}

							if (JOClass.has("ClassName")) {

								String strClassName = JOClass
										.getString("ClassName");
								cls.setClassName(strClassName);
								Log.e("CLASS NAME", strClassName);
							}

							categoriesList.add(cls);
						}
					}
				} else {
					Log.e("JSON Data", "Didn't receive any data from server!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			populateSpinner();
		}
	}

	/**
	 * Adding spinner data
	 * */
	private void populateSpinner() {

		lables = new ArrayList<String>();

		for (int i = 0; i < categoriesList.size(); i++) {
			lables.add(categoriesList.get(i).getClassName());
			// Log.e("CLASS", categoriesList.get(i).getClassName());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spClassName.setAdapter(spinnerAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub

		Toast.makeText(CreateSubject.this, lables.get(position),
				Toast.LENGTH_SHORT).show();
		clsName = lables.get(position);
		Log.e("Value of clsName", clsName);
		for (int i = 0; i < categoriesList.size(); i++) {
			String tempId, tempclass;

			tempId = categoriesList.get(i).getClassID();
			tempclass = categoriesList.get(i).getClassName();

			/*
			 * Log.e("Value of tempId",tempId);
			 * Log.e("Value of tempclass",tempclass);
			 */
			if (tempclass == clsName) {
				clsID = tempId;
				Log.e("Value of clsId", clsID);
				break;

			}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/***************************************** MenuBar **********************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.saveCreateStaff:
			new addSubject().execute();

			Intent showClass = new Intent(CreateSubject.this,
					SubjectActivity.class);
			startActivity(showClass);

			return true;

		case R.id.closeCreateStaff:

			/** SHOW THE HELP POPUP **/
			Intent createClass = new Intent(CreateSubject.this,
					SubjectActivity.class);
			startActivity(createClass);
			// Toast.makeText(CreateClassActivity.this, "close clicked",
			// Toast.LENGTH_SHORT).show();

			break;

		default:
			break;
		}

		return false;
	}

}
