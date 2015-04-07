package com.example.college_mgmt.attendance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;
import com.example.college_mgmt.attend_trans.AttendanceTransaction;
import com.example.college_mgmt.classs.ClassData;
import com.example.college_mgmt.division.DivisionData;
import com.example.college_mgmt.staff.StaffData;
import com.example.college_mgmt.subject.SubjectData;

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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AttendanceMst extends Activity implements OnItemSelectedListener {


    private static String URL_DIV = "http://10.0.2.2/college/division_mst.php";
    private static String URL_SUBJECT = "http://10.0.2.2/college/subject_mst.php";



    private static final String TAG_SUBJECT = "subject_mst";
    private static final String TAG_DIV = "division_mst";


    private static String URL_CLASS = "http://10.0.2.2/college/class_mst.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLASS = "class_mst";

    private static String URL_STAFF = "http://10.0.2.2/college/staff_details.php";
    private static final String TAG_SUCCESS1 = "success";
    private static final String TAG_STAFF = "staff_details";

    private static String URL_ATTENDANCE = "http://10.0.2.2/college/add_attendance.php";

    private static final String TAG_ATTENDANCE = "attendance_mst";

    EditText edtDate,edtTime;
    AutoCompleteTextView acAttendanceId;
    Spinner spStaffName, spClassName, spDivisionName, spSubjectName;
    Button btnAttSave, btnAttCancel, btnCal;
    String StName, StID, Div, DivID, SubName, SubID, ClsID, ClsName;
    String result;
    InputFilter timeFilter;


    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private ArrayList<ClassData> List;
    private ArrayList<StaffData> StaffList;
    private ArrayList<SubjectData> subList;
    private ArrayList<DivisionData> divList;
    //private ArrayList<StaffData> staffList;
    List<String> clsLabel, staffLabel, divLabel, subLabel;

    /**
     * ************** Calendar button ************************
     */

    DateFormat fmtDate = DateFormat.getDateInstance();
    DateFormat fmtTime=DateFormat.getTimeInstance();



    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DATE, dayOfMonth);
            updateLabel();
        }


    };

    protected void updateLabel() {
        // TODO Auto-generated method stub
        edtDate.setText(fmtDate.format(myCalendar.getTime()));
    }

    /**
     * ******************************** Calendar button end **************************
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_mst);

        acAttendanceId = (AutoCompleteTextView) findViewById(R.id.acAttendanceId);
        spStaffName = (Spinner) findViewById(R.id.spStaffName);
        spClassName = (Spinner) findViewById(R.id.spClassName);
        spDivisionName = (Spinner) findViewById(R.id.spDivisionName);
        edtDate = (EditText) findViewById(R.id.edtDate);
        spSubjectName = (Spinner) findViewById(R.id.spSubjectName);
edtTime= (EditText) findViewById(R.id.edtTime);
        btnAttSave = (Button) findViewById(R.id.btnAttSave);
        btnAttCancel = (Button) findViewById(R.id.btnAttCancel);
        btnCal = (Button) findViewById(R.id.btnCal);
        List = new ArrayList<ClassData>();
        StaffList = new ArrayList<StaffData>();
        subList = new ArrayList<SubjectData>();
        divList = new ArrayList<DivisionData>();


        new spclass().execute();
        spClassName.setOnItemSelectedListener(this);

        new spStaff().execute();
        spStaffName.setOnItemSelectedListener(this);

        new spDiv().execute();
        spDivisionName.setOnItemSelectedListener(this);

        new spSub().execute();
        spSubjectName.setOnItemSelectedListener(this);
        Log.e("Check", "before executes");



        btnCal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "CLICK",
                // Toast.LENGTH_SHORT).show();

                new DatePickerDialog(AttendanceMst.this, d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateLabel();

        btnAttSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  new createAttendance().execute();
                Intent showAtt=new Intent(AttendanceMst.this, AttendanceTransaction.class);
                showAtt.putExtra("ClassID",ClsID);
                showAtt.putExtra("DivID",DivID);
                startActivity(showAtt);
            }
        });



    }


    private class spclass extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_CLASS, "GET", params);
            // Log.e("MAIN OBJECT: ", json.toString());

            //		Log.e("Check", "Test Details");
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
                        //					Log.e("Check", "IntClass Has value");
                        ClassData cls;

                        for (int i = 0; i < JAClass.length(); i++) {
                            JSONObject JOClass = JAClass.getJSONObject(i);
                            //Log.e("INTERNAL OBJECTS", JODiv.toString());

                            cls = new ClassData();

                            if (JOClass.has("ClassID")) {
                                String strClassID = JOClass
                                        .getString("ClassID");
                                cls.setClassID(strClassID);
                                //Log.e("CLASS ID", strClassID);
                            }

                            if (JOClass.has("ClassName")) {

                                String strClassName = JOClass
                                        .getString("ClassName");
                                cls.setClassName(strClassName);
                                //	Log.e("CLASS NAME", strDivision);

                            }

                            List.add(cls);

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
            populateSpinnerClass();
        }
    }

    /**
     * Adding spinner data
     */
    private void populateSpinnerClass() {

        clsLabel = new ArrayList<String>();

        //	Log.e(" checking", "before for loop");
        for (int i = 0; i < List.size(); i++) {
            //	Log.e(" checking", "after for loop");
            clsLabel.add(List.get(i).getClassName());
            //	Log.e("CLASS", List.get(i).getDivision());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clsLabel);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spClassName.setAdapter(spinnerAdapter);
    }


/*******************************************END*******************************************************/

    /**
     * ***********************************STAFF SPINNER**********************************************
     */

    private class spStaff extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_STAFF, "GET", params);
            // Log.e("MAIN OBJECT: ", json.toString());

            Log.e("Check", "Staff Test Details");
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS1);
                if (success == 1) {
                    JSONArray JAStaff = json.getJSONArray(TAG_STAFF);

                    /** SET THE NUMBER OF OFFERS AVAILABLE **/
                    int intStaff = JAStaff.length();
                    // Log.e("NUMBER OF CATEGORIES",
                    // String.valueOf(intCategories));

                    if (intStaff != 0) {
                       // Log.e("Check", "IntStaff Has value");
                        StaffData staff;

                        for (int i = 0; i < JAStaff.length(); i++) {
                            JSONObject JOStaff = JAStaff.getJSONObject(i);
                            //Log.e("INTERNAL OBJECTS", JODiv.toString());

                            staff = new StaffData();

                            if (JOStaff.has("StaffID")) {
                                String strStaffID = JOStaff
                                        .getString("StaffID");
                                staff.setStaffId(strStaffID);
                                //Log.e("Staff ID", strStaffID);
                            }

                            if (JOStaff.has("StaffName")) {

                                String strStaffName = JOStaff
                                        .getString("StaffName");
                                staff.setStaffName(strStaffName);
                                //	Log.e("CLASS NAME", strDivision);

                            }

                            StaffList.add(staff);

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
            populateSpinnerStaff();
        }
    }

    /**
     * Adding spinner data
     */
    private void populateSpinnerStaff() {

        staffLabel = new ArrayList<String>();

        //Log.e(" checking", "before for loop");
        for (int i = 0; i < StaffList.size(); i++) {
          //  Log.e(" checking", "after for loop");
            staffLabel.add(StaffList.get(i).getStaffName());
            //Log.e("CLASS", StaffList.get(i).getStaffName());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, staffLabel);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spStaffName.setAdapter(spinnerAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
//        // TODO Auto-generated method stub
        arg0.getItemAtPosition(position);
        switch (arg0.getId()) {
            case R.id.spDivisionName:
                //Log.e("Value of Div", divLabel.get(position).toString());

                Div = divLabel.get(position);
              //  Log.e("Value of Division", Div);
                for (int i = 0; i < divList.size(); i++) {

                    String tempDId, tempDiv;
                    //tempSubId, tempSubName;

                    tempDId = divList.get(i).getDivision_id();
                    tempDiv = divList.get(i).getDivision();


                  //  Log.e("Value of tempId", tempDId);
                   // Log.e("Value of tempDiv", tempDiv);

                    if (tempDiv == Div) {
                        DivID = tempDId;
                     //   Log.e("Value of DivId", DivID);
                        break;

                    }
                }
                break;

            case R.id.spSubjectName:

                //Log.e("Value of SubjectName", subLabel.get(position).toString());

                SubName = subLabel.get(position);
              //  Log.e("Value of SubjectName", SubName);
                for (int i = 0; i < subList.size(); i++) {

                    String tempSubID, tempSubName;


                    tempSubID = subList.get(i).getSubject_id();
                    tempSubName = subList.get(i).getSubject_name();

                   // Toast.makeText(AttendanceMst.this,"First "+SubID, Toast.LENGTH_SHORT).show();
//                    Log.e("Value of tempId", tempSubID);
  //                  Log.e("Value of tempsubject", tempSubName);

                    if (tempSubName == SubName) {
                        SubID = tempSubID;
//                        Log.e("Value of subId", SubID);
//                        Toast.makeText(AttendanceMst.this,SubID, Toast.LENGTH_SHORT).show();

                        break;

                    }
                }
                break;


            case R.id.spStaffName:

                //Log.e("Value of StaffName", staffLabel.get(position).toString());

                StName = staffLabel.get(position);
                //Log.e("Value of StaffName", StName);
                for (int i = 0; i < StaffList.size(); i++) {

                    String tempStID, tempStName;


                    tempStID = StaffList.get(i).getStaffId();
                    Toast.makeText(AttendanceMst.this,"First "+tempStID, Toast.LENGTH_SHORT).show();
                    tempStName = StaffList.get(i).getStaffName();


//                    Log.e("Value of tempId", tempStID);
//                    Log.e("Value of tempstaff", tempStName);

                    if (tempStName == StName) {
                        StID = tempStID;
                        //Log.e("Value of StaffId", StID);
                        break;

                    }
                }
                break;

            case R.id.spClassName:

                //Log.e("Value of ClassName", clsLabel.get(position).toString());

                ClsName = clsLabel.get(position);
                //Log.e("Value of ClassName", ClsName);
                for (int i = 0; i < List.size(); i++) {

                    String tempCId, tempCname;
                    //tempSubId, tempSubName;

                    tempCId = List.get(i).getClassID();
                    tempCname = List.get(i).getClassName();


                  //  Log.e("Value of tempId", tempCId);
                  //  Log.e("Value of tempclass", tempCname);

                    if (tempCname == ClsName) {
                        ClsID = tempCId;
                    //    Log.e("Value of ClassId", ClsID);
                        break;

                    }
                }
                break;

        }
    }

    /*****************************************END******************************************************/
    /**
     * ***********************************DIVISION SPINNER******************************************
     */
    private class spDiv extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_DIV, "GET", params);
            // Log.e("MAIN OBJECT: ", json.toString());

            //		Log.e("Check", "Test Details");
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray JADiv = json.getJSONArray(TAG_DIV);

                    /** SET THE NUMBER OF OFFERS AVAILABLE **/
                    int intDiv = JADiv.length();
                    // Log.e("NUMBER OF CATEGORIES",
                    // String.valueOf(intCategories));

                    if (intDiv != 0) {
                        //					Log.e("Check", "IntClass Has value");
                        DivisionData div;

                        for (int i = 0; i < JADiv.length(); i++) {
                            JSONObject JODiv = JADiv.getJSONObject(i);
                            //Log.e("INTERNAL OBJECTS", JODiv.toString());

                            div = new DivisionData();

                            if (JODiv.has("DivisionID")) {
                                String strDivisionID = JODiv
                                        .getString("DivisionID");
                                div.setDivision_id(strDivisionID);

                            }

                            if (JODiv.has("Division")) {

                                String strDivision = JODiv
                                        .getString("Division");
                                div.setDivision(strDivision);


                            }

                            divList.add(div);

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
            populateSpinnerDiv();
        }
    }

    /**
     * Adding spinner data
     */
    private void populateSpinnerDiv() {

        divLabel = new ArrayList<String>();

        //	Log.e(" checking", "before for loop");
        for (int i = 0; i < divList.size(); i++) {
            //	Log.e(" checking", "after for loop");
            divLabel.add(divList.get(i).getDivision());
            //	Log.e("CLASS", List.get(i).getDivision());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, divLabel);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spDivisionName.setAdapter(spinnerAdapter);
    }


    /*****************************************END*****************************************************/
    /**
     * ***********************************SUBJECT SPINNER******************************************
     */
    private class spSub extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_SUBJECT, "GET", params);
            // Log.e("MAIN OBJECT: ", json.toString());

            //		Log.e("Check", "Test Details");
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray JASub = json.getJSONArray(TAG_SUBJECT);

                    /** SET THE NUMBER OF OFFERS AVAILABLE **/
                    int intSub = JASub.length();
                    // Log.e("NUMBER OF CATEGORIES",
                    // String.valueOf(intCategories));

                    if (intSub != 0) {
                        //					Log.e("Check", "IntClass Has value");
                        SubjectData sub;

                        for (int i = 0; i < JASub.length(); i++) {
                            JSONObject JOSub = JASub.getJSONObject(i);
                            //Log.e("INTERNAL OBJECTS", JODiv.toString());

                            sub = new SubjectData();

                            if (JOSub.has("SubjectId")) {
                                String strSubjectID = JOSub
                                        .getString("SubjectId");
                                sub.setSubject_id(strSubjectID);
                               // Log.e("Subject ID in ayncTask", strSubjectID);
                            }

                            if (JOSub.has("SubjectName")) {

                                String strSubjectName = JOSub
                                        .getString("SubjectName");
                                sub.setSubject_name(strSubjectName);
                                //	Log.e("CLASS NAME", strDivision);

                            }

                            subList.add(sub);

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
            populateSpinnerSub();
        }
    }

    /**
     * Adding spinner data
     */
    private void populateSpinnerSub() {

        subLabel = new ArrayList<String>();

        //	Log.e(" checking", "before for loop");
        for (int i = 0; i < subList.size(); i++) {
            //	Log.e(" checking", "after for loop");
            subLabel.add(subList.get(i).getSubject_name());
            //	Log.e("CLASS", List.get(i).getDivision());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subLabel);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spSubjectName.setAdapter(spinnerAdapter);
    }
/*******************************************END*******************************/
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
/**********************************Create atten Class***********************/

private class createAttendance extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... arg0) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_ATTENDANCE);

        Log.e("RESPONSE", "brfore Try");
        try {

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("staff_id",StID));
            params.add(new BasicNameValuePair("class_id",ClsID));
            params.add(new BasicNameValuePair("division_id",DivID));
            params.add(new BasicNameValuePair("subject_id",SubID));
            params.add(new BasicNameValuePair("date",edtDate.getText().toString()));
            params.add(new BasicNameValuePair("time",edtTime.getText().toString()));


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


        AttendanceMst.this.finish();

    }

}
    /**********************************END***********************************/
//
//    private void validateTime()
//{
//    /**********************************TIME VALIDATION***********************/
//    //TODO
//    timeFilter  = new InputFilter() {
//        @Override
//        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
//                                   int dstart, int dend) {
//            if (source.length() == 0) {
//                return null;// deleting, keep original editing
//            }
//            String result = "";
//            result += dest.toString().substring(0, dstart);
//            result += source.toString().substring(start, end);
//            result += dest.toString().substring(dend, dest.length());
//
//            if (result.length() > 5) {
//                return "";// do not allow this edit
//            }
//            boolean allowEdit = true;
//            char c;
//            if (result.length() > 0) {
//                c = result.charAt(0);
//                allowEdit &= (c >= '0' && c <= '2');
//            }
//            if (result.length() > 1) {
//                c = result.charAt(1);
//                if(result.charAt(0) == '0' || result.charAt(0) == '1')
//                    allowEdit &= (c >= '0' && c <= '9');
//                else
//                    allowEdit &= (c >= '0' && c <= '3');
//            }
//            if (result.length() > 2) {
//                c = result.charAt(2);
//                allowEdit &= (c == ':');
//            }
//            if (result.length() > 3) {
//                c = result.charAt(3);
//                allowEdit &= (c >= '0' && c <= '5');
//            }
//            if (result.length() > 4) {
//                c = result.charAt(4);
//                allowEdit &= (c >= '0' && c <= '9');
//            }
//            return allowEdit ? null : "";
//        }
//
//    };
//
//    EditText edtTime = (EditText) findViewById(R.id.edtTime);
//    edtTime.setFilters(new InputFilter[]{timeFilter});
//}
//}
///*********************************END***********************************/
//
//}
}



