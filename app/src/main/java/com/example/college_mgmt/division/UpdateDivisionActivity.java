package com.example.college_mgmt.division;

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

import com.example.college_mgmt.R;

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

public class UpdateDivisionActivity extends ActionBarActivity{
    private static String URL_DIVISION = "http://10.0.2.2/college/update_division.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DIVISION = "division_mst";

    String DivisionId,DivisionName;

    EditText edtDivisionName;
    AutoCompleteTextView acDivisionId;
   // Button btnDivSave, btnDivCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.division_mst);


        edtDivisionName=(EditText) findViewById(R.id.edtDivisionName);
        acDivisionId= (AutoCompleteTextView) findViewById(R.id.acDivisionId);
        acDivisionId.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        DivisionId=bundle.getString("DivisionId");
        DivisionName=bundle.getString("Division");

        acDivisionId.setText(DivisionId);
        edtDivisionName.setText(DivisionName);

        Toolbar myToolbar=(Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);



    }




    private class updateDivision extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL_DIVISION);

            Log.e("RESPONSE", "before Try");
            try {

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("division", edtDivisionName
                        .getText().toString()));
                params.add(new BasicNameValuePair("division_id", acDivisionId
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

            Intent done = new Intent();
            setResult(RESULT_OK, done);

			/* CLOSE THE ACTIVITY */

            finish();

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
                new updateDivision().execute();

//                Intent showDivision = new Intent(UpdateDivisionActivity.this, DivisionActivity.class);
//                startActivity(showDivision);
//                UpdateDivisionActivity.this.finish();
                break;

            case R.id.closeCreateStaff:

                /** SHOW THE HELP POPUP **/
                finish();

                break;

            default:
                break;
        }

        return false;
    }


}
