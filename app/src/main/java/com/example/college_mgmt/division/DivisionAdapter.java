//package com.example.college_mgmt.division;
//
//import java.util.ArrayList;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.college_mgmt.R;
//import com.example.college_mgmt.classs.ClassActivity;
//
//
//@SuppressLint("NewApi")
//public class DivisionAdapter extends BaseAdapter {
//
//	 private static String URL_DIVISION = "http://10.0.2.2/college/delete_division.php";
//	private static final String TAG_SUCCESS = "success";
//	private static final String TAG_DIVISION = "division_mst";
//	 String DivId, DivName;
//
//	/***** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER *****/
//	Activity activity;
//
//	/***** LAYOUTINFLATER TO USE A CUSTOM LAYOUT *****/
//	LayoutInflater inflater = null;
//
//	/***** ARRAYLIST TO GET DATA FROM THE ACTIVITY *****/
//	ArrayList<DivisionData> arrDivision;
//
//	public DivisionAdapter(Activity activity, ArrayList<DivisionData> arrDivision) {
//
//		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
//		this.activity = activity;
//
//		/* CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL INSTANCE */
//		this.arrDivision = arrDivision;
//
//		/* INSTANTIATE THE LAYOUTINFLATER */
//		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public int getCount() {
//		return arrDivision.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return arrDivision.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public void notifyDataSetChanged() {
//		super.notifyDataSetChanged();
//	}
//
//	@SuppressLint("NewApi")
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//
//		// A VIEWHOLDER INSTANCE
//		ViewHolder holder;
//
//		// CAST THE CONVERTVIEW IN A VIEW INSTANCE
//		View vi = convertView;
//
//		// CHECK CONVERTVIEW STATUS
//		if (convertView == null)	{
//			// CAST THE CONVERTVIEW INTO THE VIEW INSTANCE vi
//			vi = inflater.inflate(R.layout.division_item, null);
//
//			// INSTANTIATE THE VIEWHOLDER INSTANCE
//			holder = new ViewHolder();
//
//			/*****	CAST THE LAYOUT ELEMENTS	*****/
//			holder.imgvwMenuOptions = (ImageView) vi.findViewById(R.id.imgvwMenuOptions);
//			holder.txtDivisionIdLabel = (TextView) vi.findViewById(R.id.txtDivisionIdLabel);
//			holder.txtDivisionId = (TextView) vi.findViewById(R.id.txtDivisionId);
//			holder.txtDivisionLabel = (TextView) vi.findViewById(R.id.txtDivisionLabel);
//			holder.txtDivision = (TextView) vi.findViewById(R.id.txtDivision);
//
//
//			// SET THE TAG TO "vi"
//			vi.setTag(holder);
//		} else {
//			// CAST THE VIEWHOLDER INSTANCE
//			holder = (ViewHolder) vi.getTag();
//		}
//
//		/* GET THE STUDENTS NAME */
//		final String Division_id = arrDivision.get(position).getDivision_id();
//		holder.txtDivisionId.setText(Division_id);
//
//		String Division = arrDivision.get(position).getDivision();
//		holder.txtDivision.setText(Division);
//
////		DivId = Division_id;
////        DivName = Division;
//
//		/** SHOW POPUP MENU **/
//		holder.imgvwMenuOptions.setOnClickListener(new OnClickListener() {
//
//
//			@Override
//			public void onClick(View v) {
//
//                PopupMenu pm = new PopupMenu(activity, v);
//                pm.getMenuInflater().inflate(R.menu.generic_pm_options, pm.getMenu());
//                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(activity, String.valueOf(item.getTitle()) +" " + arrDivision.get(position).getDivision_id(), Toast.LENGTH_SHORT).show();
//DivId=arrDivision.get(position).getDivision_id();
//                        DivName=arrDivision.get(position).getDivision();
//                        /** CHECK WHICH MENU IS CLICKED (SWITCH CASE) **/
//                        switch (item.getItemId())   {
//
//                            case R.id.menuDetails:
//
//                                /** SHOW THE INGREDIENT DETAILS **/
////                                Intent showDetails = new Intent(activity, IngredientDetailsContainer.class);
////                                showDetails.putExtra("INGREDIENT_ID", items.getIngredientID());
////                                activity.startActivity(showDetails);
//
//                                break;
//
//
//
//                            case R.id.menuEdit:
//
//                                /** EDIT THE INGREDIENT **/
//                     //       	new deleteD().execute();
//
//    							Intent showEdit = new Intent(activity,
//    									UpdateDivisionActivity.class);
//                                showEdit.putExtra("DivisionId", DivId);
//                                showEdit.putExtra("divisionName", DivName);
//
//    							activity.startActivity(showEdit);
//
//
//                                break;
//
//                            case R.id.menuDelete:
//
//                                /** DELETE THE INGREDIENT **/
//                            	new deleteDivision().execute();
//
//    							Intent showDelete = new Intent(activity,
//    									UpdateDivisionActivity.class);
//
//    							activity.startActivity(showDelete);
//
//                                break;
//
//                            default:
//                                break;
//                        }
//
//                        return true;
//                    }
//                });
//
//                /** SHOW THE POPUP **/
//                pm.show();
//            }
//        });
//
//		return vi;
//	}
//
//	/***** THE VIEWHOLDER FOR RECYCLING LAYOUT ELEMENTS *****/
//	private static class ViewHolder	{
//
//
//		ImageView imgvwMenuOptions;
//		TextView txtDivisionIdLabel;
//		TextView txtDivisionId;
//		TextView txtDivisionLabel;
//		TextView txtDivision;
//
//	}
//	private class deleteDivision extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected Void doInBackground(Void... arg0) {
//
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(URL_DIVISION);
//
//			Log.e("RESPONSE", "brfore Try");
//			try {
//
//				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("division_id", DivId));
//
//				httpPost.setEntity(new UrlEncodedFormEntity(params));
//				Log.e("PARAMS", params.toString());
//
//				HttpResponse response = httpClient.execute(httpPost);
//				HttpEntity entity = response.getEntity();
//				String result = EntityUtils.toString(entity);
//				Log.e("RESPONSE", result);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				Log.e("ERROR!! ERROR!!", e.toString());
//			}
//			return null;
//		}
//
//	}
//
//}
