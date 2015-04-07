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
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


import com.example.college_mgmt.R;

public class CreateStaffActivity extends ActionBarActivity {

	// TODO: TESTING!! REMOVE WHEN DONE
	private static String URL_STAFF = "http://10.0.2.2/college/add_staff.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STAFF = "staff_mst";

	EditText edtStName, edtStMNO, edtStUid, edtStPwd;
	AutoCompleteTextView acStaffId;
    //Button btnStaffSave;

	String strFlag;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.staffdetails);

            acStaffId = (AutoCompleteTextView) findViewById(R.id.acStaffId);
            edtStName = (EditText) findViewById(R.id.edtStName);
            edtStMNO = (EditText) findViewById(R.id.edtMNO);
            edtStUid = (EditText) findViewById(R.id.edtStUid);
            edtStPwd = (EditText) findViewById(R.id.edtStPwd);
            //  btnStaffSave= (Button) findViewById(R.id.btnStaffSave);
            acStaffId.setEnabled(false);

            Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
            setSupportActionBar(myToolbar);
            myToolbar.setNavigationIcon(R.drawable.ic_launcher);
    /*    btnStaffSave.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
        new newstaff().execute();

        Toast.makeText(CreateStaffActivity.this, "Data Saved",
                Toast.LENGTH_SHORT).show();

        CreateStaffActivity.this.finish();

    }
});*/


       }catch (Exception e){
            e.printStackTrace();
            Log.e("ERROR!! ERROR!!", e.toString());
       }
    }




    private class newstaff extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL_STAFF);

		//	Log.e("RESPONSE", "brfore Try");
			try {

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("staff_name", edtStName
						.getText().toString()));
				params.add(new BasicNameValuePair("contact_no", edtStMNO
						.getText().toString()));
				params.add(new BasicNameValuePair("user_id", edtStUid.getText()
						.toString()));

				httpPost.setEntity(new UrlEncodedFormEntity(params));
			//	Log.e("PARAMS", params.toString());

				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				//Log.e("RESPONSE", result);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("ERROR!! ERROR!!", e.toString());
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			edtStName.setText(" ");
			edtStMNO.setText("");
			edtStUid.setText("");
			edtStPwd.setText("");
			//CreateStaffActivity.this.finish();

		}

	}

	/************************************** Menu Bar *******************************/
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.closeCreateStaff:

			/** SHOW THE HELP POPUP **/
			Toast.makeText(CreateStaffActivity.this, "close2 button clicked",
					Toast.LENGTH_SHORT).show();
			CreateStaffActivity.this.finish();
			break;
			
		case R.id.saveCreateStaff:
			new newstaff().execute();
			Intent showDisp = new Intent(CreateStaffActivity.this,
					StaffActivity.class);
			startActivity(showDisp);
			Toast.makeText(CreateStaffActivity.this, "Data Saved",
					Toast.LENGTH_SHORT).show();

			return true;
			
		default:
			break;
		}

		return false;
	}

}
