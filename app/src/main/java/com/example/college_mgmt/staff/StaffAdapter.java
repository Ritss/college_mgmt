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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.college_mgmt.R;

public class StaffAdapter extends BaseAdapter {
	private static String URL_STAFF = "http://10.0.2.2/college/delete_staff.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLASS = "staff_mst";
	String StaffId, StaffName, ContactNo, UserId;

	/***** THE ACTIVITY INSTANCE FOR USE IN THE ADAPTER *****/
	Activity activity;

	/***** LAYOUTINFLATER TO USE A CUSTOM LAYOUT *****/
	LayoutInflater inflater = null;

	/***** ARRAYLIST TO GET DATA FROM THE ACTIVITY *****/
	ArrayList<StaffData> arrStaff;

	public StaffAdapter(Activity activity, ArrayList<StaffData> arrStaff) {

		/* CAST THE ACTIVITY FROM THE METHOD TO THE LOCAL ACTIVITY INSTANCE */
		this.activity = activity;

		/*
		 * CAST THE CONTENTS OF THE ARRAYLIST IN THE METHOD TO THE LOCAL
		 * INSTANCE
		 */
		this.arrStaff = arrStaff;

		/* INSTANTIATE THE LAYOUTINFLATER */
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return arrStaff.size();
	}

	@Override
	public Object getItem(int position) {
		return arrStaff.get(position);
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
			vi = inflater.inflate(R.layout.staff_item, null);

			// INSTANTIATE THE VIEWHOLDER INSTANCE
			holder = new ViewHolder();

			/***** CAST THE LAYOUT ELEMENTS *****/
			holder.imgvwMenuOptions = (ImageView) vi
					.findViewById(R.id.imgvwMenuOptions);
			holder.txtStaffNameLabel = (TextView) vi
					.findViewById(R.id.txtStaffNameLabel);
			holder.txtStaffName = (TextView) vi.findViewById(R.id.txtStaffName);
			holder.txtContactNoLabel = (TextView) vi
					.findViewById(R.id.txtContactNoLabel);
			holder.txtContactNo = (TextView) vi.findViewById(R.id.txtContactNo);
			holder.txtUserIdLabel = (TextView) vi
					.findViewById(R.id.txtUserIdLabel);
			holder.txtUserId = (TextView) vi.findViewById(R.id.txtUserId);

			TextView txtStaffName;
			TextView txtContactNoLabel;
			TextView txtContactNo;
			TextView txtUserIdLabel;
			TextView txtUserId;

			/*
			 * holder.imgvwMenuOptions = (ImageView)
			 * vi.findViewById(R.id.imgvwMenuOptions); holder.txtStaffNameLabel
			 * = vi.findViewById(R.id.) holder.txtStaffName = (TextView)
			 * vi.findViewById(R.id.txtSubjectName); holder.txtSubjectCodeLabel
			 * = (TextView) vi.findViewById(R.id.txtSubjectCodeLabel);
			 * holder.txtSubjectCode = (TextView)
			 * vi.findViewById(R.id.txtSubjectCode); holder.txtClassNameLabel =
			 * (TextView) vi.findViewById(R.id.txtClassNameLabel);
			 * holder.txtClassName = (TextView)
			 * vi.findViewById(R.id.txtClassName);
			 */

			// SET THE TAG TO "vi"
			vi.setTag(holder);
		} else {
			// CAST THE VIEWHOLDER INSTANCE
			holder = (ViewHolder) vi.getTag();
		}

		/* GET THE STUDENTS NAME */
		final String STAFF_NAME = arrStaff.get(position).getStaffName();
		holder.txtStaffName.setText(STAFF_NAME);

		String CONTACT_NO = arrStaff.get(position).getContactNo();
		holder.txtContactNo.setText(CONTACT_NO);

		String USER_ID = arrStaff.get(position).getUserID();

		holder.txtUserId.setText(USER_ID);

		/** SHOW POPUP MENU **/
		holder.imgvwMenuOptions.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				PopupMenu pm1 = new PopupMenu(activity, v);

				// PopupMenu pm = new PopupMenu(activity, v);
				pm1.getMenuInflater().inflate(R.menu.generic_pm_options,
						pm1.getMenu());
				pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Toast.makeText(
								activity,
								String.valueOf(item.getTitle()) + " "
										+ arrStaff.get(position).getStaffId(),
								Toast.LENGTH_SHORT).show();
						StaffId = arrStaff.get(position).getStaffId();
						StaffName = arrStaff.get(position).getStaffName();
						ContactNo = arrStaff.get(position).getContactNo();
						UserId = arrStaff.get(position).getUserID();
						/** CHECK WHICH MENU IS CLICKED (SWITCH CASE) **/
						switch (item.getItemId()) {

						case R.id.menuDetails:

							/** SHOW THE INGREDIENT DETAILS **/
							// Intent showDetails = new Intent(activity,
							// IngredientDetailsContainer.class);
							// showDetails.putExtra("INGREDIENT_ID",
							// items.getIngredientID());
							// activity.startActivity(showDetails);

							break;

						case R.id.menuEdit:

							/** EDIT THE INGREDIENT **/
							Intent showEdit = new Intent(activity, UpdateStaffActivity.class);
                        	showEdit.putExtra("StId", StaffId);
                        	showEdit.putExtra("StName", StaffName);
                        	showEdit.putExtra("Contact", ContactNo);
                        	showEdit.putExtra("UID", UserId);
                       	 activity.startActivity(showEdit);
                       	 activity.finish();

							break;

						case R.id.menuDelete:

							/** DELETE THE INGREDIENT **/
							new deleteStaff().execute();

							Intent showDetails = new Intent(activity,
									StaffActivity.class);

							activity.startActivity(showDetails);

							break;

						default:
							break;
						}

						return true;
					}
				});

				/** SHOW THE POPUP **/
				pm1.show();
			}
		});

		return vi;
	}

	/***** THE VIEWHOLDER FOR RECYCLING LAYOUT ELEMENTS *****/
	private static class ViewHolder {

		ImageView imgvwMenuOptions;
		TextView txtStaffNameLabel;
		TextView txtStaffName;
		TextView txtContactNoLabel;
		TextView txtContactNo;
		TextView txtUserIdLabel;
		TextView txtUserId;
	}

	private class deleteStaff extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL_STAFF);

			Log.e("RESPONSE", "brfore Try");
			try {

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("staff_id", StaffId));

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
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			activity.finish();
		}

	}

}
