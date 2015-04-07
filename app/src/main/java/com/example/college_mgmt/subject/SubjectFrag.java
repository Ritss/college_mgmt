package com.example.college_mgmt.subject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritesh on 03/03/2015.
 */
public class SubjectFrag extends Fragment {

    // TODO: TESTING!! REMOVE WHEN DONE
    private static String URL_SUBJECT = "http://10.0.2.2/college/subject_mst.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SUBJECT = "subject_mst";

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ListView listSubject;
    SubjectAdapter adapter;
    ArrayList<SubjectData> arrSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** CAST THE LAYOUT TO A NEW VIEW INSTANCE **/
        View view = inflater.inflate(R.layout.subject_list, container, false);

        /** RETURN THE VIEW INSTANCE TO SETUP THE LAYOUT **/
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /** INDICATE THAT THE FRAGMENT SHOULD RETAIN IT'S STATE **/
        setRetainInstance(true);

        /** INDICATE THAT THE FRAGMENT HAS AN OPTIONS MENU **/
        setHasOptionsMenu(true);

        /** INVALIDATE THE EARLIER OPTIONS MENU SET IN OTHER FRAGMENTS / ACTIVITIES **/
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrSubject = new ArrayList<SubjectData>();
        listSubject = (ListView) getView().findViewById(R.id.listSubject);
        adapter = new SubjectAdapter(getActivity(), arrSubject);

		/* FETCH DATA */
        new testDetails().execute();

    }

    private class testDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_SUBJECT, "GET",
                    params);
            // Log.e("MAIN OBJECT: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray JASubject = json.getJSONArray(TAG_SUBJECT);

                    /** SET THE NUMBER OF OFFERS AVAILABLE **/
                    int intChickenDishes = JASubject.length();
                    // Log.e("NUMBER OF CATEGORIES",
                    // String.valueOf(intCategories));

                    if (intChickenDishes != 0) {

                        SubjectData subject;

                        for (int i = 0; i < JASubject.length(); i++) {
                            JSONObject JOSubject = JASubject.getJSONObject(i);
//							Log.e("INTERNAL OBJECTS", JOSubject.toString());

                            subject = new SubjectData();

                            if (JOSubject.has("SubjectId")) {
                                String strSubjectID = JOSubject
                                        .getString("SubjectId");
                                subject.setSubject_id(strSubjectID);
//								Log.e("SUBJECT ID", strSubjectID);
                            }

                            if (JOSubject.has("SubjectName")) {

                                String strSubjectName = JOSubject
                                        .getString("SubjectName");
                                subject.setSubject_name(strSubjectName);
//								Log.e("SUBJECT NAME", strSubjectName);
                            }

                            if (JOSubject.has("SubjectCode")) {

                                String strSubjectCode = JOSubject
                                        .getString("SubjectCode");
                                subject.setSubject_code(strSubjectCode);
//								Log.e("SUBJECT CODE", strSubjectCode);
                            }

                            if (JOSubject.has("ClassId")) {

                                String strClassId = JOSubject
                                        .getString("ClassId");
                                subject.setClass_id(strClassId);
//								Log.e("CLASS ID", strClassId);
                            }

                            arrSubject.add(subject);

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

            listSubject.setAdapter(adapter);
        }

    }
    /************************************Menu Bar *******************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.generic_menu_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newSubject:

                /** SHOW THE HELP POPUP **/
                Intent createSubject = new Intent(getActivity(), CreateSubject.class);
                startActivity(createSubject);
//                Toast.makeText(SubjectActivity.this, "Create new Subject", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED)
        {
            if(resultCode==2)
            {
                /**INVALIDATE THE LIST VIEW **/
                listSubject.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrSubject.clear();

                /** REFRESH THE LIST **/
                new testDetails().execute();
            }
        }
    }
/************************************SUJECT ADAPTER******************/

public class SubjectAdapter extends BaseAdapter {
    private String URL_SUBJECT = "http://10.0.2.2/college/delete_subject.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SUBJECT = "subject_mst";
    String SubId;

    /***** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER *****/
    Activity activity;

    /***** LAYOUTINFLATER TO USE A CUSTOM LAYOUT *****/
    LayoutInflater inflater = null;

    /***** ARRAYLIST TO GET DATA FROM THE ACTIVITY *****/
    ArrayList<SubjectData> arrSubject;

    public SubjectAdapter(Activity activity, ArrayList<SubjectData> arrSubject) {

		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
        this.activity = activity;

		/* CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL INSTANCE */
        this.arrSubject = arrSubject;

		/* INSTANTIATE THE LAYOUTINFLATER */
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrSubject.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSubject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // A VIEWHOLDER INSTANCE
        ViewHolder holder;

        // CAST THE CONVERTVIEW IN A VIEW INSTANCE
        View vi = convertView;

        // CHECK CONVERTVIEW STATUS
        if (convertView == null)	{
            // CAST THE CONVERTVIEW INTO THE VIEW INSTANCE vi
            vi = inflater.inflate(R.layout.subject_item, null);

            // INSTANTIATE THE VIEWHOLDER INSTANCE
            holder = new ViewHolder();

            /*****	CAST THE LAYOUT ELEMENTS	*****/
            holder.imgvwMenuOptions = (ImageView) vi.findViewById(R.id.imgvwMenuOptions);
            holder.txtSubjectNameLabel = (TextView) vi.findViewById(R.id.txtSubjectNameLabel);
            holder.txtSubjectName = (TextView) vi.findViewById(R.id.txtSubjectName);
            holder.txtSubjectCodeLabel = (TextView) vi.findViewById(R.id.txtSubjectCodeLabel);
            holder.txtSubjectCode = (TextView) vi.findViewById(R.id.txtSubjectCode);
            holder.txtClassNameLabel = (TextView) vi.findViewById(R.id.txtClassNameLabel);
            holder.txtClassName = (TextView) vi.findViewById(R.id.txtClassName);

            // SET THE TAG TO "vi"
            vi.setTag(holder);
        } else {
            // CAST THE VIEWHOLDER INSTANCE
            holder = (ViewHolder) vi.getTag();
        }

		/* GET THE STUDENTS NAME */
        final String SUBJECT_NAME = arrSubject.get(position).getSubject_name();
        holder.txtSubjectName.setText(SUBJECT_NAME);

        String SUBJECT_CODE = arrSubject.get(position).getSubject_code();
        holder.txtSubjectCode.setText(SUBJECT_CODE);

        String CLASS_NAME = arrSubject.get(position).getClass_id();

        holder.txtClassName.setText(CLASS_NAME);

        /** SHOW POPUP MENU **/
        holder.imgvwMenuOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu pm = new PopupMenu(activity, v);
                pm.getMenuInflater().inflate(R.menu.generic_pm_options, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(activity, String.valueOf(item.getTitle()) + " " + arrSubject.get(position).getSubject_id(), Toast.LENGTH_SHORT).show();

                        SubId=arrSubject.get(position).getSubject_id();
                        /** CHECK WHICH MENU IS CLICKED (SWITCH CASE) **/
                        switch (item.getItemId())   {

                            case R.id.menuDetails:

                                /** SHOW THE INGREDIENT DETAILS **/
//                                Intent showDetails = new Intent(activity, IngredientDetailsContainer.class);
//                                showDetails.putExtra("INGREDIENT_ID", items.getIngredientID());
//                                activity.startActivity(showDetails);

                                break;



                            case R.id.menuEdit:

                                /** EDIT THE INGREDIENT **/

                                break;

                            case R.id.menuDelete:

                                /** DELETE THE INGREDIENT **/

                                Intent showDetails = new Intent(activity,
                                        SubjectActivity.class);

                                activity.startActivity(showDetails);

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                                // Setting Dialog Title
                                alertDialog.setTitle("Confirm Delete...");

                                // Setting Dialog Message
                                alertDialog.setMessage("Are you sure you want delete this?");

                                // Setting Icon to Dialog
                                alertDialog.setIcon(R.drawable.delete);

                                // Setting Positive "Yes" Button
                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int which) {

                                        // Write your code here to invoke YES event
                                        // Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                        new deleteSubject().execute();
                                    }
                                });

                                // Setting Negative "NO" Button
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int which) {

                                        // Write your code here to invoke NO event
                                        //  Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });

                                // Showing Alert Message
                                alertDialog.show();

                                break;

                            default:
                                break;
                        }

                        return true;
                    }
                });

                /** SHOW THE POPUP **/
                pm.show();
            }
        });

        return vi;
    }

    /***** THE VIEWHOLDER FOR RECYCLING LAYOUT ELEMENTS *****/
    private class ViewHolder	{

        ImageView imgvwMenuOptions;
        TextView txtSubjectNameLabel;
        TextView txtSubjectName;
        TextView txtSubjectCodeLabel;
        TextView txtSubjectCode;
        TextView txtClassNameLabel;
        TextView txtClassName;
    }

    private class deleteSubject extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL_SUBJECT);

            Log.e("RESPONSE", "brfore Try");
            try {

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("subject_id", SubId));

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

    }

}


}
