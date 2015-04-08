package com.example.college_mgmt.attend_trans;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.college_mgmt.JSONParser;
import com.example.college_mgmt.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceTransaction extends Activity {
	
	// TODO: TESTING!! REMOVE WHEN DONE
				private static String URL_STUDENT = "http://10.0.2.2/college/student_mst.php";
				private static final String TAG_SUCCESS = "success";
				private static final String TAG_STUDENT = "student_mst";

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ListView listStudent;
    StudentAdapter adapter;
    ArrayList<StudentData> arrStudent;
    String ClsId,DivId;
				@Override
				protected void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					setContentView(R.layout.attendance_transaction_mst);

                    arrStudent = new ArrayList<StudentData>();
                    listStudent = (ListView) findViewById(R.id.listview);
                    listStudent.setItemsCanFocus(false);
                    listStudent.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    adapter = new StudentAdapter(AttendanceTransaction.this, arrStudent);


                    Bundle bundle=getIntent().getExtras();
                   ClsId= bundle.getString("ClassID");
					DivId=bundle.getString("DivID");
                    new getStudent().execute();
				}

				private class getStudent extends AsyncTask<Void, Void, Void> {

					@Override
					protected Void doInBackground(Void... arg0) {

						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("class_id", ClsId));
                        params.add(new BasicNameValuePair("division_id", DivId));
                        // getting JSON string from URL
						JSONObject json = jParser.makeHttpRequest(URL_STUDENT, "GET", params);
						Log.e("MAIN LOG: ", json.toString());

						try {
							// Checking for SUCCESS TAG
							int success = json.getInt(TAG_SUCCESS);
							if (success == 1) {
								JSONArray JAStudent = json.getJSONArray(TAG_STUDENT);

								/** SET THE NUMBER OF OFFERS AVAILABLE **/
								int intStudent = JAStudent.length();
								// Log.e("NUMBER OF CATEGORIES",
								// String.valueOf(intCategories));

								if (intStudent != 0) {

									StudentData studentMst;

									for (int i = 0; i < JAStudent.length(); i++) {
										JSONObject JOStudent = JAStudent.getJSONObject(i);
										Log.e("INTERNAL OBJECTS", JOStudent.toString());

                                        studentMst = new StudentData();

										if (JOStudent.has("StudentID")) {
											String strStudentId = JOStudent
													.getString("StudentID");
											studentMst.setStudentID(strStudentId);
											Log.e("Student ID", strStudentId);
										}

										if (JOStudent.has("StudentName")) {

											String strStudentName = JOStudent
													.getString("StudentName");
											studentMst.setStudentName(strStudentName);
											Log.e("Student Name :", strStudentName);
										}

										if (JOStudent.has("PRN")) {

											String strPrn = JOStudent
													.getString("PRN");
											studentMst.setPRN(strPrn);
											Log.e("PRN", strPrn);
										}
										
										if (JOStudent.has("ClassID")) {

											String strClassId = JOStudent
													.getString("ClassID");
											studentMst.setClassID(strClassId);
											Log.e("Class ID :", strClassId);
										}
                                        if (JOStudent.has("DivisionID")) {

                                            String strDivisionId = JOStudent
                                                    .getString("DivisionID");
                                            studentMst.setClassID(strDivisionId);
                                            Log.e("Division ID :", strDivisionId);
                                        }
										
                                        arrStudent.add(studentMst);

										
									}
								}
							}
						} catch (Exception e) {
							e.getMessage();
						}

						return null;
					}

                    @Override
                    protected void onPostExecute(Void result) {
                        // TODO Auto-generated method stub
                        super.onPostExecute(result);

                        listStudent.setAdapter(adapter);
                    }

				}

//				@Override
//				public boolean onCreateOptionsMenu(Menu menu) {
//					// Inflate the menu; this adds items to the action bar if it is present.
//					getMenuInflater().inflate(R.menu.activity_main, menu);
//					return true;
//				}

			}



