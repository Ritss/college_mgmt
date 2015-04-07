package com.example.college_mgmt;

/**
 * Created by Ritesh on 03/03/2015.
 */import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.college_mgmt.classs.ClassActivity;
import com.example.college_mgmt.division.DivisionActivity;
import com.example.college_mgmt.staff.StaffActivity;
import com.example.college_mgmt.subject.SubjectActivity;

public class DashBoardFrag extends Fragment {

    Button btn_STUDENT,btn_DIVISION,btn_STAFF,btn_REPORTS,btn_EXIT,btn_SUBJECT,btn_CLASS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** CAST THE LAYOUT TO A NEW VIEW INSTANCE **/
        View view = inflater.inflate(R.layout.dashboard, container, false);

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

        btn_CLASS=(Button)getView().findViewById(R.id.btnCLASS);
        btn_STUDENT=(Button)getView().findViewById(R.id.btnSTUDENT);
        btn_DIVISION=(Button)getView().findViewById(R.id.btnDIVISION);
        btn_STAFF=(Button)getView().findViewById(R.id.btnSTAFF);
        btn_REPORTS=(Button)getView().findViewById(R.id.btnREPORTS);
        btn_SUBJECT=(Button)getView().findViewById(R.id.btnSUBJECT);
        btn_EXIT=(Button)getView().findViewById(R.id.btnEXIT);


        btn_SUBJECT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent showSubject = new Intent(getActivity(), SubjectActivity.class);

                startActivity(showSubject);

            }
        });

        btn_CLASS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent showClass = new Intent(getActivity(), ClassActivity.class);

                startActivity(showClass);

            }
        });

        btn_STAFF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent showStaff = new Intent(getActivity(), StaffActivity.class);

                startActivity(showStaff);

            }
        });

        btn_DIVISION.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent showDiv = new Intent(getActivity(), DivisionActivity.class);

                startActivity(showDiv);

            }
        });
    }

    }
