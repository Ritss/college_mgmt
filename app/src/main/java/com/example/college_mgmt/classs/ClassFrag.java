package com.example.college_mgmt.classs;

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
import android.widget.LinearLayout;
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
 * Created by Ritesh on 04/03/2015.
 */
public class ClassFrag extends Fragment {

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLASS = "class_mst";
    // TODO: TESTING!! REMOVE WHEN DONE
    private static String URL_CLASS = "http://10.0.2.2/college/class_mst.php";
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ListView listClass;
    ClassAdapter adapter;
    ArrayList<ClassData> arrClass;
    LinearLayout linlaHeaderProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** CAST THE LAYOUT TO A NEW VIEW INSTANCE **/
        View view = inflater.inflate(R.layout.class_list, container, false);

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

        arrClass = new ArrayList<ClassData>();
        listClass = (ListView) getView().findViewById(R.id.listClass);
        adapter = new ClassAdapter(getActivity(), arrClass);
        linlaHeaderProgress = (LinearLayout) getView().findViewById(R.id.linlaHeaderProgress);

		/* FETCH DATA */
        new getClass().execute();

    }

    /**
     * *********************************Menu Bar ******************************
     */

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
                Intent createClass = new Intent(getActivity(), CreateClassActivity.class);
                startActivityForResult(createClass, 2);


//                Toast.makeText(ClassActivity.this, "Create new Class", Toast.LENGTH_SHORT).show();

                break;
            case R.id.closeSubject:
                getActivity().finish();
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != getActivity().RESULT_CANCELED) {
//            Toast.makeText(getActivity(),requestCode, Toast.LENGTH_SHORT).show();
//            Log.e("Request Code ","");
            if (requestCode == 2) {
                /**INVALIDATE THE LIST VIEW **/
                listClass.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrClass.clear();

                /** REFRESH THE LIST **/
                new getClass().execute();
            } else if (requestCode == 103) {
                /**INVALIDATE THE LIST VIEW **/
                listClass.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrClass.clear();

                /** REFRESH THE LIST **/
                new getClass().execute();
            }
        }
    }

    private class getClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /** SHOW PROGRESS BAR WHILE LOADINF DATA **/
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }

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
                    int intClass = JAClass.length();
                    // Log.e("NUMBER OF CATEGORIES",
                    // String.valueOf(intCategories));

                    if (intClass != 0) {

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

            listClass.setAdapter(adapter);
//            HIDE PROGRESS BAR AFTER LOADING DATA

            linlaHeaderProgress.setVisibility(View.GONE);
        }

    }

    /**
     * ******************************* Class Adapter****************************
     */
    public class ClassAdapter extends BaseAdapter {
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_CLASS = "class_mst";
        String ClsId, ClsName;
        /**
         * ** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER ****
         */
        Activity activity;
        /**
         * ** LAYOUTINFLATER TO USE A CUSTOM LAYOUT ****
         */
        LayoutInflater inflater = null;
        /**
         * ** ARRAYLIST TO GET DATA FROM THE ACTIVITY ****
         */
        ArrayList<ClassData> arrClass;
        private String URL_CLASS = "http://10.0.2.2/college/delete_class.php";

        public ClassAdapter(Activity activity, ArrayList<ClassData> arrClass) {

		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
            this.activity = activity;

		/*
         * CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL
		 * INSTANCE
		 */
            this.arrClass = arrClass;

		/* INSTANTIATE THE LAYOUTINFLATER */
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arrClass.size();
        }

        @Override
        public Object getItem(int position) {
            return arrClass.get(position);
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
            if (convertView == null) {
                // CAST THE CONVERTVIEW INTO THE VIEW INSTANCE vi
                vi = inflater.inflate(R.layout.class_item, null);

                // INSTANTIATE THE VIEWHOLDER INSTANCE
                holder = new ViewHolder();

                /***** CAST THE LAYOUT ELEMENTS *****/
                holder.imgvwMenuOptions = (ImageView) vi
                        .findViewById(R.id.imgvwMenuOptions);
                holder.txtClassIdLabel = (TextView) vi
                        .findViewById(R.id.txtClassIdLabel);
                holder.txtClassId = (TextView) vi.findViewById(R.id.txtClassId);
                holder.txtClassNameLabel = (TextView) vi
                        .findViewById(R.id.txtClassNameLabel);
                holder.txtClassName = (TextView) vi.findViewById(R.id.txtClassName);

                // SET THE TAG TO "vi"
                vi.setTag(holder);
            } else {
                // CAST THE VIEWHOLDER INSTANCE
                holder = (ViewHolder) vi.getTag();
            }

		/* GET THE STUDENTS NAME */
            final String CLASS_ID = arrClass.get(position).getClassID();
            holder.txtClassId.setText(CLASS_ID);

            String CLASS_NAME = arrClass.get(position).getClassName();
            holder.txtClassName.setText(CLASS_NAME);


            /** SHOW POPUP MENU **/
            holder.imgvwMenuOptions.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    PopupMenu pm = new PopupMenu(activity, v);
                    pm.getMenuInflater().inflate(R.menu.generic_pm_options,
                            pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(
                                    activity,
                                    String.valueOf(item.getTitle()) + " "
                                            + arrClass.get(position).getClassID(),
                                    Toast.LENGTH_SHORT).show();
                            ClsId = arrClass.get(position).getClassID();
                            ClsName = arrClass.get(position).getClassName();

                            /** CHECK WHICH MENU IS CLICKED (SWITCH CASE) **/
                            switch (item.getItemId()) {

                                case R.id.menuDetails:
                                    Toast.makeText(
                                            activity,
                                            ClsId + " "
                                                    + ClsName,
                                            Toast.LENGTH_SHORT).show();
                                    Intent showDetails = new Intent(activity, ClassDisplayActivity.class);
                                    showDetails.putExtra("ClassId", ClsId);
                                    showDetails.putExtra("ClassName", ClsName);
                                    Log.e("CLS ID :", ClsId);
                                    Log.e("CLS ID :", ClsName);
                                    activity.startActivity(showDetails);
                                    break;
                                /** SHOW THE INGREDIENT DETAILS **/
                                // Intent showDetails = new Intent(activity,
                                // IngredientDetailsContainer.class);
                                // showDetails.putExtra("INGREDIENT_ID",
                                // items.getIngredientID());
                                // activity.startActivity(showDetails);


                                case R.id.menuEdit:

                                    /** EDIT THE INGREDIENT **/
                                    Intent showEdit = new Intent(activity, UpdateClassActivity.class);
                                    showEdit.putExtra("ClassId", ClsId);
                                    showEdit.putExtra("ClassName", ClsName);
                                    startActivityForResult(showEdit, 103);
                                    break;

                                case R.id.menuDelete:

                                    /** DELETE THE INGREDIENT **/


                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                                    // Setting Dialog Title
                                    alertDialog.setTitle("Confirm Delete...");

                                    // Setting Dialog Message
                                    alertDialog.setMessage("Are you sure you want delete this?");

                                    // Setting Icon to Dialog
                                    alertDialog.setIcon(R.drawable.delete);

                                    // Setting Positive "Yes" Button
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Write your code here to invoke YES event
                                            // Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                            new deleteClass().execute();
                                        }
                                    });

                                    // Setting Negative "NO" Button
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

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

        /**
         * ** THE VIEWHOLDER FOR RECYCLING LAYOUT ELEMENTS ****
         */
        private class ViewHolder {

            ImageView imgvwMenuOptions;
            TextView txtClassIdLabel;
            TextView txtClassId;
            TextView txtClassNameLabel;
            TextView txtClassName;

        }

        protected class deleteClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... arg0) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL_CLASS);

                Log.e("RESPONSE", "brfore Try");
                try {

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("class_id", ClsId));

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
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                /**INVALIDATE THE LIST VIEW **/
                listClass.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrClass.clear();

                /** REFRESH THE LIST **/
                new getClass().execute();
            }
        }

    }
}
