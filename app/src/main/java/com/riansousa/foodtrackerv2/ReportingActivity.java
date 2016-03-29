package com.riansousa.foodtrackerv2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.Calendar;

/**
 * Class that handles all the code processing to support the activity_reporting view.
 */
public class ReportingActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private Calendar calSelectDate;
    private EditText txtReportDate;
    private int day;
    private int month;
    private int year;

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        initMain();
    }

    /**
     * Method to create the menu.
     * Adapted from: Lecture Notes Module 1: Example illustrating state changes
     *
     * @param menu  Takes a Menu object
     * @return      boolean value from super
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
        Log.i(TAG, "ReportingActivity - Menu Options Loaded");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Method to handle option selection event.
     * Launches a new Activity Screen based on selection.
     * Adapted from: Lecture Notes Module 1: Example illustrating state changes
     * research on switching between activity screens from
     * http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
     *
     * @param item  The menu item selected by the user
     * @return      boolean value from super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_food_list:
                Log.i(TAG, "ReportingActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "ReportingActivity - The recorder icon was clicked");
                // load food list screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_alert:
                Log.i(TAG, "ReportingActivity - The alert icon was clicked");
                // load food list screen
                Intent alert = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_reporting:
                Log.i(TAG, "ReportingActivity - The reporting icon was clicked");
                // load food list screen
                Intent report = new Intent(getApplicationContext(), ReportingActivity.class);
                startActivity(report);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * System method used for logging activity life cycle onStart()
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "ReportingActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ReportingActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "ReportingActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "ReportingActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "ReportingActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ReportingActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "ReportingActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "ReportingActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     */
    private void initMain() {
        try {
            // get an instance of the calendar object and set current time values
            calSelectDate = Calendar.getInstance();
            day = calSelectDate.get(Calendar.DAY_OF_MONTH);
            month = calSelectDate.get(Calendar.MONTH);
            year = calSelectDate.get(Calendar.YEAR);

            // set textbox with date
            txtReportDate = (EditText)findViewById(R.id.txtReportDate);
            txtReportDate.setText((month + 1) + "/" + day +"/" + year);

            // instantiate image button object
            ImageButton ibCal = (ImageButton) findViewById(R.id.ibCalendar);

            // set event listener on button to handle click
            ibCal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // handle click
                    Log.i(TAG, "ReportingActivity - Image Button Clicked");
                    // launch pop up dialog for calendar
                    PopCalendar();
                }
            });

            // instantiate image button object
            Button btnGo = (Button) findViewById(R.id.btnReportGo);

            // set event listener on button to handle click
            btnGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // handle click
                    txtReportDate = (EditText) findViewById(R.id.txtReportDate);
                    Log.i(TAG, "ReportingActivity - Go Button Clicked - Report Date " + txtReportDate.getText());
                }
            });

            // TODO: wrap this with an IF condition based on rows returned from DB. If row count = 0 then pop no data fragment
            getFragmentManager().beginTransaction().add(R.id.flNoData, new PlaceholderFragment()).commit();

        } catch(Exception e) {
        Log.i(TAG, "ReportingActivity -Error:" + e.getMessage());
    }
    }

    /**
     * Method to create and pop a DatePicker Dialog
     * Adapted from:
     * http://www.tutorialspoint.com/android/android_datepicker_control.htm
     * http://www.compiletimeerror.com/2013/07/android-date-picker-example-android.html#.Vvf8WeIrKUk
     */
    protected void PopCalendar() {
        Log.i(TAG, "ReportingActivity - Pop Calendar");
        Dialog d = new DatePickerDialog(this, datePickerListener, year, month, day);
        d.show();
    }

    /**
     * Event listener to handle button click on calendar dialog
     * Adapted from:
     * http://www.tutorialspoint.com/android/android_datepicker_control.htm
     * http://www.compiletimeerror.com/2013/07/android-date-picker-example-android.html#.Vvf8WeIrKUk
     */
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // handle the click
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            // set textbox with date
            txtReportDate = (EditText)findViewById(R.id.txtReportDate);
            txtReportDate.setText((selectedMonth + 1) + "/" + selectedDay +"/" + selectedYear);
        }
    };

    /**
     * A placeholder fragment containing a "No Data for Report" message.
     * This should be popped when the row count = 0
     * This is adapted from the lecture notes Module 2, Section Fragment Example 1
     */
    public static class PlaceholderFragment extends Fragment {
        // default constructor
        public PlaceholderFragment() {
        }

        // method to invoke the text_fragment view and show the No Data message
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = null;
            // wrapped with try/catch for error reporting
            try {
                rootView = inflater.inflate(R.layout.text_fragment, container, false);
            } catch(Exception e) {
                Log.i(TAG, "ReportingActivity -Error:" + e.getMessage());
            }
            return rootView;
        }
    }
}
