//package com.example.college_mgmt.classs;
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
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
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
//
//@SuppressLint("NewApi")
//public class ClassAdapter extends BaseAdapter {
//	private static String URL_CLASS = "http://10.0.2.2/college/delete_class.php";
//	private static final String TAG_SUCCESS = "success";
//	private static final String TAG_CLASS = "class_mst";
//	String ClsId,ClsName;
//
//	/***** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER *****/
//	Activity activity;
//
//	/***** LAYOUTINFLATER TO USE A CUSTOM LAYOUT *****/
//	LayoutInflater inflater = null;
//
//	/***** ARRAYLIST TO GET DATA FROM THE ACTIVITY *****/
//	ArrayList<ClassData> arrClass;
//
//	public ClassAdapter(Activity activity, ArrayList<ClassData> arrClass) {
//
//		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
//		this.activity = activity;
//
//		/*
//		 * CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL
//		 * INSTANCE
//		 */
//		this.arrClass = arrClass;
//
//		/* INSTANTIATE THE LAYOUTINFLATER */
//		inflater = (LayoutInflater) activity
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public int getCount() {
//		return arrClass.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return arrClass.get(position);
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
//		if (convertView == null) {
//			// CAST THE CONVERTVIEW INTO THE VIEW INSTANCE vi
//			vi = inflater.inflate(R.layout.class_item, null);
//
//			// INSTANTIATE THE VIEWHOLDER INSTANCE
//			holder = new ViewHolder();
//
//			/***** CAST THE LAYOUT ELEMENTS *****/
//			holder.imgvwMenuOptions = (ImageView) vi
//					.findViewById(R.id.imgvwMenuOptions);
//			holder.txtClassIdLabel = (TextView) vi
//					.findViewById(R.id.txtClassIdLabel);
//			holder.txtClassId = (TextView) vi.findViewById(R.id.txtClassId);
//			holder.txtClassNameLabel = (TextView) vi
//					.findViewById(R.id.txtClassNameLabel);
//			holder.txtClassName = (TextView) vi.findViewById(R.id.txtClassName);
//
//			// SET THE TAG TO "vi"
//			vi.setTag(holder);
//		} else {
//			// CAST THE VIEWHOLDER INSTANCE
//			holder = (ViewHolder) vi.getTag();
//		}
//
//		/* GET THE STUDENTS NAME */
//		final String CLASS_ID = arrClass.get(position).getClassID();
//		holder.txtClassId.setText(CLASS_ID);
//
//		String CLASS_NAME = arrClass.get(position).getClassName();
//		holder.txtClassName.setText(CLASS_NAME);
//
//
//
//		/** SHOW POPUP MENU **/
//		holder.imgvwMenuOptions.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				PopupMenu pm = new PopupMenu(activity, v);
//				pm.getMenuInflater().inflate(R.menu.generic_pm_options,
//						pm.getMenu());
//				pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//					@Override
//					public boolean onMenuItemClick(MenuItem item) {
//						Toast.makeText(
//								activity,
//								String.valueOf(item.getTitle()) + " "
//										+ arrClass.get(position).getClassID(),
//								Toast.LENGTH_SHORT).show();
//						 ClsId=arrClass.get(position).getClassID();
//	                		ClsName=arrClass.get(position).getClassName();
//
//						/** CHECK WHICH MENU IS CLICKED (SWITCH CASE) **/
//						switch (item.getItemId()) {
//
//						case R.id.menuDetails:
//
//							/** SHOW THE INGREDIENT DETAILS **/
//							// Intent showDetails = new Intent(activity,
//							// IngredientDetailsContainer.class);
//							// showDetails.putExtra("INGREDIENT_ID",
//							// items.getIngredientID());
//							// activity.startActivity(showDetails);
//
//							break;
//
//						case R.id.menuEdit:
//
//							/** EDIT THE INGREDIENT **/
//							Intent showEdit = new Intent(activity, UpdateClassActivity.class);
//                        	showEdit.putExtra("ClassId", ClsId);
//                        	showEdit.putExtra("ClassName", ClsName);
//                       	 activity.startActivity(showEdit);
//							break;
//
//						case R.id.menuDelete:
//
//							/** DELETE THE INGREDIENT **/
//							new deleteClass().execute();
//
//							Intent showDetails = new Intent(activity,
//									ClassActivity.class);
//
//							activity.startActivity(showDetails);
//
//							break;
//
//						default:
//							break;
//						}
//
//						return true;
//					}
//				});
//
//				/** SHOW THE POPUP **/
//				pm.show();
//			}
//		});
//
//		return vi;
//	}
//
//	/***** THE VIEWHOLDER FOR RECYCLING LAYOUT ELEMENTS *****/
//	private static class ViewHolder {
//
//		ImageView imgvwMenuOptions;
//		TextView txtClassIdLabel;
//		TextView txtClassId;
//		TextView txtClassNameLabel;
//		TextView txtClassName;
//
//	}
//
//	private class deleteClass extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected Void doInBackground(Void... arg0) {
//
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(URL_CLASS);
//
//			Log.e("RESPONSE", "brfore Try");
//			try {
//
//				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("class_id", ClsId));
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
