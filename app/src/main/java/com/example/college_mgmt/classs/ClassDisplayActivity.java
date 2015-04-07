package com.example.college_mgmt.classs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.college_mgmt.R;

/**
 * Created by Shweta on 29/03/2015.
 */
public class ClassDisplayActivity extends ActionBarActivity {

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLASS = "class_mst";
    private static String URL_CLASS = "http://10.0.2.2/college/update_class.php";

    String ClsId, ClsName;
    EditText edtClassId, edtClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_mst);


        edtClassId = (EditText) findViewById(R.id.edtClassId);
        edtClassName = (EditText) findViewById(R.id.edtClassName);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);

        Bundle bundle = getIntent().getExtras();
        ClsId = bundle.getString("ClassId");
        ClsName = bundle.getString("ClassName");

        Toast.makeText(
               ClassDisplayActivity.this,
               ClsId + " "
                        +ClsName,
                Toast.LENGTH_SHORT).show();
        edtClassId.setText(ClsId);
        edtClassId.setEnabled(false);
        edtClassName.setText(ClsName);
        edtClassName.setEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menubar, menu);
        return true;

  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
//

            case R.id.edit:
               Toast.makeText(ClassDisplayActivity.this,item.getItemId(), Toast.LENGTH_SHORT).show();
                /** SHOW THE HELP POPUP **/
                Intent showEdit = new Intent(ClassDisplayActivity.this, UpdateClassActivity.class);
                showEdit.putExtra("ClassId", ClsId);
                showEdit.putExtra("ClassName", ClsName);
                //startActivityForResult(showEdit,3);
                startActivity(showEdit);
                break;

            case R.id.menuDelete:
                finish();

                break;


            default:
                break;
        }

        return false;
    }

}