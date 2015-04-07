package com.example.college_mgmt.division;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
public class DivisionFrag extends Fragment {

    // TODO: TESTING!! REMOVE WHEN DONE
    private static String URL_DIVISION = "http://10.0.2.2/college/division_mst.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DIVISION = "division_mst";

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ListView listDivision;
    DivisionAdapter adapter;
    ArrayList<DivisionData> arrDivision;
    LinearLayout linlaHeaderProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** CAST THE LAYOUT TO A NEW VIEW INSTANCE **/
        View view = inflater.inflate(R.layout.division_list, container, false);

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

        arrDivision = new ArrayList<DivisionData>();
        listDivision = (ListView) getView().findViewById(R.id.listDivision);
        adapter = new DivisionAdapter(getActivity(), arrDivision);
        linlaHeaderProgress= (LinearLayout) getView().findViewById(R.id.linlaHeaderProgress);

		/* FETCH DATA */
        new getDivision().execute();

    }



    private class getDivision extends AsyncTask<Void, Void, Void> {
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
            JSONObject json = jParser.makeHttpRequest(URL_DIVISION, "GET",
                    params);
            // Log.e("MAIN OBJECT: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray JADivision = json.getJSONArray(TAG_DIVISION);

                    /** SET THE NUMBER OF OFFERS AVAILABLE **/
                    int intDiv = JADivision.length();
                    // Log.e("NUMBER OF CATEGORIES",
                    // String.valueOf(intCategories));

                    if (intDiv != 0) {

                        DivisionData division;

                        for (int i = 0; i < JADivision.length(); i++) {
                            JSONObject JODivision = JADivision.getJSONObject(i);
//							Log.e("INTERNAL OBJECTS", JOClasss.toString());

                            division = new DivisionData();

                            if (JODivision.has("DivisionID")) {
                                String strDivisionID = JODivision
                                        .getString("DivisionID");
                                division.setDivision_id(strDivisionID);
//								Log.e("CLASS ID", strClassID);
                            }

                            if (JODivision.has("Division")) {

                                String strDivisionName = JODivision
                                        .getString("Division");
                                division.setDivision(strDivisionName);
//								Log.e("CLASS NAME", strClassName);
                            }


                            arrDivision.add(division);

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

            listDivision.setAdapter(adapter);
//            HIDE PROGRESS BAR AFTER LOADING DATA

            linlaHeaderProgress.setVisibility(View.GONE);


        }

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
                Intent createDivision = new Intent(getActivity(), CreateDivisionActivity.class);
                startActivityForResult(createDivision, 11);

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
        if (resultCode != getActivity().RESULT_CANCELED)
        {
            if(requestCode==11)
            {
                /**INVALIDATE THE LIST VIEW **/
                listDivision.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrDivision.clear();

                /** REFRESH THE LIST **/
                new getDivision().execute();
            }


            else if(requestCode==101)
            {
                /**INVALIDATE THE LIST VIEW **/
                listDivision.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrDivision.clear();

                /** REFRESH THE LIST **/
                new getDivision().execute();
            }
        }
    }


    /**
     * ******************************* DIVISION Adapter****************************
     */
    public class DivisionAdapter extends BaseAdapter {
        private String URL_DIVISION = "http://10.0.2.2/college/delete_division.php";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_DIVISION = "division_mst";
        String DivId, DivName;

        /***** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER *****/
        Activity activity;

        /***** LAYOUTINFLATER TO USE A CUSTOM LAYOUT *****/
        LayoutInflater inflater = null;

        /***** ARRAYLIST TO GET DATA FROM THE ACTIVITY *****/
        ArrayList<DivisionData> arrDivision;

        public DivisionAdapter(Activity activity, ArrayList<DivisionData> arrDivision) {

		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
            this.activity = activity;

		/* CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL INSTANCE */
            this.arrDivision = arrDivision;

		/* INSTANTIATE THE LAYOUTINFLATER */
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arrDivision.size();
        }

        @Override
        public Object getItem(int position) {
            return arrDivision.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // A VIEWHOLDER INSTANCE
            ViewHolder holder;

            // CAST THE CONVERTVIEW IN A VIEW INSTANCE
            View vi = convertView;

            // CHECK CONVERTVIEW STATUS
            if (convertView == null)	{
                // CAST THE CONVERTVIEW INTO THE VIEW INSTANCE vi
                vi = inflater.inflate(R.layout.division_item, null);

                // INSTANTIATE THE VIEWHOLDER INSTANCE
                holder = new ViewHolder();

                /*****	CAST THE LAYOUT ELEMENTS	*****/
                holder.imgvwMenuOptions = (ImageView) vi.findViewById(R.id.imgvwMenuOptions);
                holder.txtDivisionIdLabel = (TextView) vi.findViewById(R.id.txtDivisionIdLabel);
                holder.txtDivisionId = (TextView) vi.findViewById(R.id.txtDivisionId);
                holder.txtDivisionLabel = (TextView) vi.findViewById(R.id.txtDivisionLabel);
                holder.txtDivision = (TextView) vi.findViewById(R.id.txtDivision);


                // SET THE TAG TO "vi"
                vi.setTag(holder);
            } else {
                // CAST THE VIEWHOLDER INSTANCE
                holder = (ViewHolder) vi.getTag();
            }

		/* GET THE STUDENTS NAME */
            final String Division_id = arrDivision.get(position).getDivision_id();
            holder.txtDivisionId.setText(Division_id);

            String Division = arrDivision.get(position).getDivision();
            holder.txtDivision.setText(Division);

//            DivId = Division_id;
//            DivName = Division;

            /** SHOW POPUP MENU **/
            holder.imgvwMenuOptions.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    PopupMenu pm = new PopupMenu(activity, v);
                    pm.getMenuInflater().inflate(R.menu.generic_pm_options, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(activity, String.valueOf(item.getTitle()) +" " + arrDivision.get(position).getDivision_id(), Toast.LENGTH_SHORT).show();

                            DivId =  arrDivision.get(position).getDivision_id();
                            DivName = arrDivision.get(position).getDivision();

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
                                    //       	new deleteD().execute();

                                    Intent showEdit = new Intent(activity,
                                            UpdateDivisionActivity.class);
                                    showEdit.putExtra("DivisionId", DivId);
                                    showEdit.putExtra("Division", DivName);

                                   startActivityForResult(showEdit,101);


                                    break;

                                case R.id.menuDelete:

                                    /** DELETE THE INGREDIENT **/


                                  //  Intent showDelete = new Intent(activity,
                                    //        UpdateDivisionActivity.class);

                              //      activity.startActivity(showDelete);

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
                                            new deleteDivision().execute();
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
            TextView txtDivisionIdLabel;
            TextView txtDivisionId;
            TextView txtDivisionLabel;
            TextView txtDivision;

        }
        private class deleteDivision extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... arg0) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL_DIVISION);

                Log.e("RESPONSE", "brfore Try");
                try {

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("division_id", DivId));

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
                listDivision.invalidate();

                /**NOTIFY DATASET CHANGED ON THE ADPATER **/
                adapter.notifyDataSetChanged();

                /** CLEAR THE ARRAY LSIT **/
                arrDivision.clear();

                /** REFRESH THE LIST **/
                new getDivision().execute();
            }
        }

    }
}
