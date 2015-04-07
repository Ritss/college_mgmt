package com.example.college_mgmt.staff;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.college_mgmt.R;


public class UpdateStaffActivity extends ActionBarActivity{

	private static String URL_STAFF = "http://10.0.2.2/college/update_staff.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLASS = "staff_mst";
	
	String StaffId,StaffName,ContactNo,UserId;
	AutoCompleteTextView acStaffId;
	
	EditText edtStName,edtStUid,edtMNO,edtPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staffdetails);
		
		acStaffId=(AutoCompleteTextView) findViewById(R.id.acStaffId);
		edtStName=(EditText) findViewById(R.id.edtStName);
		edtStUid=(EditText) findViewById(R.id.edtStUid);
		edtMNO=(EditText) findViewById(R.id.edtMNO);
		edtPwd=(EditText) findViewById(R.id.edtStPwd);
		
		Bundle bundle = getIntent().getExtras();
		StaffId=bundle.getString("StId");
		StaffName=bundle.getString("StName");
		ContactNo=bundle.getString("Contact");
		UserId=bundle.getString("UID");
		
		acStaffId.setText(StaffId);
		edtStName.setText(StaffName);
		edtMNO.setText(ContactNo);
		edtStUid.setText(UserId);
		
		acStaffId.setEnabled(false);
		edtPwd.setEnabled(false);

        Toolbar myToolbar=(Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);
	}
	private class updateClass extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL_STAFF);

			Log.e("RESPONSE", "brfore Try");
			try {

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("staff_name", edtStName
						.getText().toString()));
				params.add(new BasicNameValuePair("contact_no", edtMNO
						.getText().toString()));
				params.add(new BasicNameValuePair("user_id", edtStUid
						.getText().toString()));
				params.add(new BasicNameValuePair("staff_id", acStaffId
						.getText().toString()));

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

			
			UpdateStaffActivity.this.finish();

		}

	}
	/*********************************MENU BAR**************************************/
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
            	new updateClass().execute();
            	
            	Intent showClass = new Intent(UpdateStaffActivity.this, StaffActivity.class);
            	startActivity(showClass);
            	UpdateStaffActivity.this.finish(); 
             break;

            case R.id.closeCreateStaff:

                /** SHOW THE HELP POPUP **/
           	Intent createClass = new Intent(UpdateStaffActivity.this, StaffActivity.class);
            	startActivity(createClass);
            // Toast.makeText(CreateClassActivity.this, "close clicked", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }

        return false;
    }




}
