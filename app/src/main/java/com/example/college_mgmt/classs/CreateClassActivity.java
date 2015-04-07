package com.example.college_mgmt.classs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class CreateClassActivity extends ActionBarActivity {

    // TODO: TESTING!! REMOVE WHEN DONE
    private static String URL_CLASS = "http://10.0.2.2/college/add_class.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLASS = "class_mst";

    EditText edtClassName, edtClassId;
    String strFlag;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_mst);
        edtClassName = (EditText) findViewById(R.id.edtClassName);
        edtClassId = (EditText) findViewById(R.id.edtClassId);
        edtClassId.setEnabled(false);

        Toolbar myToolbar=(Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);

    }


    private class createClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL_CLASS);

            Log.e("RESPONSE", "brfore Try");
            try {

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("class_name", edtClassName
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

            edtClassName.setText(" ");

            Intent done=new Intent();
            setResult(RESULT_OK,done);
            CreateClassActivity.this.finish();

        }

    }

    /**
     * ******************************MENU BAR*************************************
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.generic_menu, menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveCreateStaff:
                new createClass().execute();



                return true;

            case R.id.closeCreateStaff:

                /** SHOW THE HELP POPUP **/
             finish();
                // Toast.makeText(CreateClassActivity.this, "close clicked", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }

        return false;
    }



}