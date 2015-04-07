package com.example.college_mgmt;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.college_mgmt.classs.ClassFrag;
import com.example.college_mgmt.division.DivisionFrag;
import com.example.college_mgmt.subject.SubjectFrag;
import com.example.college_mgmt.staff.StaffFrag;
/**
 * Created by Ritesh on 03/03/2015.
 */
public class AdminLanding extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    /** DECLARE THE SIDEBAR ICONS **/
    private RelativeLayout rellaDashboard;
    private ImageView imgvwDashboard;
    private TextView txtDashboard;
    private LinearLayout linlaDashboardIndicator;

    private RelativeLayout rellaSubject;
    private ImageView imgvwSubject;
    private TextView txtSubject;
    private LinearLayout linlaSubjectIndicator;

    private RelativeLayout rellaClass;
    private ImageView imgvwClass;
    private TextView txtClass;
    private LinearLayout linlaClassIndicator;

    private RelativeLayout rellaDiv;
    private ImageView imgvwDiv;
    private TextView txtDiv;
    private LinearLayout linlaDivIndicator;

    private RelativeLayout rellaReports;
    private ImageView imgvwReports;
    private TextView txtReports;
    private LinearLayout linlaReportsIndicator;

    private RelativeLayout rellaStaff;
    private ImageView imgvwStaff;
    private TextView txtStaff;
    private LinearLayout linlaStaffIndicator;

    private RelativeLayout rellaStudent;
    private ImageView imgvwStudent;
    private TextView txtStudent;
    private LinearLayout linlaStudentIndicator;

    private RelativeLayout rellaTaxes;
    private ImageView imgvwTaxes;
    private TextView txtTaxes;
    private LinearLayout linlaTaxesIndicator;

    /** DECLARE THE CUSTOM FONTS **/
    private Typeface fontMainLabels;

    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.be_admin_landing_frag);

        /** CAST THE LAYOUT ELEMENTS **/
        castLayoutElements();

        if (savedInstanceState == null) {
            mContent = new DashBoardFrag();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, mContent, "KEY_FRAG")
                    .commit();
        }
//        else {
//            DashboardAdminActivity dashFrag = (DashboardAdminActivity) getSupportFragmentManager().findFragmentByTag("KEY_FRAG");
//        }

        /***** TESTING THE USE OF FRAGMENTS ******/

		/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLORS */
        rellaDashboard.setBackgroundResource(R.drawable.pattern_tile);
        int clrSelected = getResources().getColor(R.color.dash_selected);
        txtDashboard.setTextColor(clrSelected);
        imgvwDashboard.setImageResource(R.drawable.dash_home_selected);
        linlaDashboardIndicator.setVisibility(View.VISIBLE);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_launcher);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close);

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    /** CAST THE LAYOUT ELEMENTS **/
    private void castLayoutElements() {

        /** CAST THE SIDEBAR ICONS **/
        rellaDashboard = (RelativeLayout) findViewById(R.id.rellaDashboard);
        imgvwDashboard = (ImageView) findViewById(R.id.imgvwDashboard);
        txtDashboard = (TextView) findViewById(R.id.txtDashboard);
        linlaDashboardIndicator = (LinearLayout) findViewById(R.id.linlaDashboardIndicator);

        rellaSubject = (RelativeLayout) findViewById(R.id.rellaSubject);
        imgvwSubject = (ImageView) findViewById(R.id.imgvwSubject);
        txtSubject = (TextView) findViewById(R.id.txtSubject);
        linlaSubjectIndicator = (LinearLayout) findViewById(R.id.linlaSubjectIndicator);

        rellaClass = (RelativeLayout) findViewById(R.id.rellaClass);
        imgvwClass = (ImageView) findViewById(R.id.imgvwClass);
        txtClass = (TextView) findViewById(R.id.txtClass);
        linlaClassIndicator = (LinearLayout) findViewById(R.id.linlaClassIndicator);

        rellaDiv = (RelativeLayout) findViewById(R.id.rellaDiv);
        imgvwDiv = (ImageView) findViewById(R.id.imgvwDiv);
        txtDiv = (TextView) findViewById(R.id.txtDiv);
        linlaDivIndicator = (LinearLayout) findViewById(R.id.linlaDivIndicator);

        rellaReports = (RelativeLayout) findViewById(R.id.rellaReports);
        imgvwReports = (ImageView) findViewById(R.id.imgvwReports);
        txtReports = (TextView) findViewById(R.id.txtReports);
        linlaReportsIndicator = (LinearLayout) findViewById(R.id.linlaReportsIndicator);

        rellaStaff = (RelativeLayout) findViewById(R.id.rellaStaff);
        imgvwStaff = (ImageView) findViewById(R.id.imgvwStaff);
        txtStaff = (TextView) findViewById(R.id.txtStaff);
        linlaStaffIndicator = (LinearLayout) findViewById(R.id.linlaStaffIndicator);

        rellaStudent = (RelativeLayout) findViewById(R.id.rellaStudent);
        imgvwStudent = (ImageView) findViewById(R.id.imgvwStudent);
        txtStudent = (TextView) findViewById(R.id.txtStudent);
        linlaStudentIndicator = (LinearLayout) findViewById(R.id.linlaStudentIndicator);

        rellaTaxes = (RelativeLayout) findViewById(R.id.rellaTaxes);
        imgvwTaxes = (ImageView) findViewById(R.id.imgvwTaxes);
        txtTaxes = (TextView) findViewById(R.id.txtTaxes);
        linlaTaxesIndicator = (LinearLayout) findViewById(R.id.linlaTaxesIndicator);
        /*****/

        /** SET THE CUSTOM FONTS **/
        txtDashboard.setTypeface(fontMainLabels);
        txtClass.setTypeface(fontMainLabels);
        txtSubject.setTypeface(fontMainLabels);
        txtReports.setTypeface(fontMainLabels);
        txtStaff.setTypeface(fontMainLabels);
        txtStudent.setTypeface(fontMainLabels);
        txtTaxes.setTypeface(fontMainLabels);

        /** SHOW THE DASHBOARD FRAGMENT **/
        rellaDashboard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DashBoardFrag showDashboard = new DashBoardFrag();
                switchFragment(showDashboard);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaDashboard.setBackgroundResource(R.drawable.pattern_tile);
                txtDashboard.setTextColor(clrSelected);
                imgvwDashboard.setImageResource(R.drawable.dash_home_selected);
                linlaDashboardIndicator.setVisibility(View.VISIBLE);

                /** RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S **/
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);

                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtTaxes.setTextColor(clrNormal);
                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });

        /** SHOW THE MENU FRAGMENT **/
        rellaSubject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SubjectFrag showSubject = new SubjectFrag();
                switchFragment(showSubject);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaSubject.setBackgroundResource(R.drawable.pattern_tile);
                txtSubject.setTextColor(clrSelected);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.VISIBLE);

				/* RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S */
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);

                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtTaxes.setTextColor(clrNormal);
                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });


        /** SHOW THE MENU FRAGMENT **/
        rellaStaff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StaffFrag showStaff = new StaffFrag();
                switchFragment(showStaff);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaStaff.setBackgroundResource(R.drawable.pattern_tile);
                txtStaff.setTextColor(clrSelected);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.VISIBLE);

				/* RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S */
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);

                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtTaxes.setTextColor(clrNormal);
                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });

        /** SHOW THE Class FRAGMENT **/
        rellaClass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClassFrag showClass = new ClassFrag();
                switchFragment(showClass);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaClass.setBackgroundResource(R.drawable.pattern_tile);
                txtClass.setTextColor(clrSelected);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.VISIBLE);

				/* RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S */
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);

                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtTaxes.setTextColor(clrNormal);
                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });

        /** SHOW THE Div FRAGMENT **/
        rellaDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DivisionFrag showDiv = new DivisionFrag();
                switchFragment(showDiv);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaDiv.setBackgroundResource(R.drawable.pattern_tile);
                txtDiv.setTextColor(clrSelected);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.VISIBLE);

                /** RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S **/
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);

//                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
//                txtTaxes.setTextColor(clrNormal);
//                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
//                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });

        /** SHOW THE REPORTS FRAGMENT **/
        rellaReports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             //  ReportFrag showReport = new ReportFrag();
           //     switchFragment(showReport);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaReports.setBackgroundResource(R.drawable.pattern_tile);
                txtReports.setTextColor(clrSelected);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.VISIBLE);

				/* RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S */
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);

//                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
//                txtTaxes.setTextColor(clrNormal);
//                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
//                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });



        /** SHOW THE TABLES FRAGMENT **/
        rellaStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //StudentFrag showSubject = new StudentFrag();
              //  switchFragment(showSubject);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaStudent.setBackgroundResource(R.drawable.pattern_tile);
                txtStudent.setTextColor(clrSelected);
                imgvwStudent.setImageResource(R.drawable.dash_tables_selected);
                linlaStudentIndicator.setVisibility(View.VISIBLE);

                /** RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S **/
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaTaxes.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtTaxes.setTextColor(clrNormal);
                imgvwTaxes.setImageResource(R.drawable.dash_taxes);
                linlaTaxesIndicator.setVisibility(View.GONE);
            }
        });

        /** SHOW THE TAXES FRAGMENT **/
        rellaTaxes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                TaxesAdminActivity showTaxesManager = new TaxesAdminActivity();
//                switchFragment(showTaxesManager);

				/* CHANGE THE BACKGROUND, LABEL ICON AND TEXT COLOR */
                int clrSelected = getResources().getColor(R.color.dash_selected);
                rellaTaxes.setBackgroundResource(R.drawable.pattern_tile);
                txtTaxes.setTextColor(clrSelected);
                imgvwTaxes.setImageResource(R.drawable.dash_taxes_selected);
                linlaTaxesIndicator.setVisibility(View.VISIBLE);

                /** RESET THE OTHER BACKGROUNDS, LABEL AND ICON'S **/
                int clrNormal = getResources().getColor(R.color.dash_normal);
                rellaDashboard.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDashboard.setTextColor(clrNormal);
                imgvwDashboard.setImageResource(R.drawable.dash_home);
                linlaDashboardIndicator.setVisibility(View.GONE);

                rellaClass.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtClass.setTextColor(clrNormal);
                imgvwClass.setImageResource(R.drawable.btn_class);
                linlaClassIndicator.setVisibility(View.GONE);

                rellaDiv.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtDiv.setTextColor(clrNormal);
                imgvwDiv.setImageResource(R.drawable.btn_div);
                linlaDivIndicator.setVisibility(View.GONE);

                rellaSubject.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtSubject.setTextColor(clrNormal);
                imgvwSubject.setImageResource(R.drawable.btn_subj1);
                linlaSubjectIndicator.setVisibility(View.GONE);

                rellaReports.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtReports.setTextColor(clrNormal);
                imgvwReports.setImageResource(R.drawable.btn_reports);
                linlaReportsIndicator.setVisibility(View.GONE);

                rellaStaff.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStaff.setTextColor(clrNormal);
                imgvwStaff.setImageResource(R.drawable.btn_staff1);
                linlaStaffIndicator.setVisibility(View.GONE);

                rellaStudent.setBackgroundResource(R.drawable.admin_sidebar_bg);
                txtStudent.setTextColor(clrNormal);
                imgvwStudent.setImageResource(R.drawable.btn_stud1);
                linlaStudentIndicator.setVisibility(View.GONE);
            }
        });
    }

    /** METHOD TO CHANGE THE FRAGMENT **/
    public void switchFragment(Fragment fragment) {

        /** HIDE THE NAV DRAWER **/
        mDrawerLayout.closeDrawer(Gravity.LEFT);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
