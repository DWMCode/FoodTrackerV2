package com.riansousa.foodtrackerv2;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.riansousa.foodtrackerv2.Logic.MyAlerts;
import com.riansousa.foodtrackerv2.Logic.Telephony;
import com.riansousa.foodtrackerv2.Model.Record;
import com.riansousa.foodtrackerv2.Model.RecordAdapter;
import com.riansousa.foodtrackerv2.Logic.ErrorLog;
import com.riansousa.foodtrackerv2.Logic.MyRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * Class that handles all the code processing to support the activity_reporting view.
 */
public class MyReportingActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private Telephony _telephony = new Telephony();
    private ErrorLog _log = new ErrorLog();
    private Calendar calSelectDate;
    private EditText txtReportDate;
    private int day;
    private int month;
    private int year;
    private String emailMessage = "";
    private String reportDate = "";

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
     * @return boolean value from super
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
     * @return boolean value from super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // research on switching between activity screens from
        // http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
        switch (item.getItemId()) {
            case R.id.menu_food_profile:
                Log.i(TAG, "ReportingActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "ReportingActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "ReportingActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "AlertActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent intentForMyPreferences = new Intent();
                intentForMyPreferences.setAction("MyPreferences");
                startActivity(intentForMyPreferences);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "ReportingActivity - The reporting icon was clicked");
                // load reporting screen
                Intent report = new Intent(getApplicationContext(), MyReportingActivity.class);
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
            /** get an instance of the calendar object and set current time values */
            calSelectDate = Calendar.getInstance();
            day = calSelectDate.get(Calendar.DAY_OF_MONTH);
            month = calSelectDate.get(Calendar.MONTH);
            year = calSelectDate.get(Calendar.YEAR);

            /** set textbox with date */
            txtReportDate = (EditText) findViewById(R.id.txtReportDate);
            txtReportDate.setText((month + 1) + "/" + day + "/" + year);

            /** instantiate image button objects */
            ImageButton ibCal = (ImageButton) findViewById(R.id.ibCalendar);
            ImageButton ibEmail = (ImageButton) findViewById(R.id.ibEmail);
            ImageButton ibCall = (ImageButton) findViewById(R.id.ibCall);
            ImageButton ibSms = (ImageButton) findViewById(R.id.ibSms);

            /** set event listener on buttons to handle clicks */
            ibCal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "ReportingActivity - Calendar Button Clicked");
                    /** launch pop up dialog for calendar */
                    PopCalendar();
                }
            });
            ibEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "ReportingActivity - Email Button Clicked");
                    /** launch pop up dialog for email client */
                    popEmail();
                }
            });
            ibCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "ReportingActivity - Call Button Clicked");

                    /** toast the telephony status */
                    _telephony.showTelStatus(getApplicationContext());

                    /** perform permission test, if failure stop execution */
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        /** alert */
                        Toast.makeText(getApplicationContext(), "you do not have permission to make a call", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    /** get phone number from shared pref */
                    final SharedPreferences pref_phone = getSharedPreferences("pref_phone", 0);
                    final CharSequence value_phone = pref_phone.getString("pref_phone", "");

                    /** make call with implicit intent */
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+value_phone.toString()));
                    startActivity(intent);
                }
            });
            ibSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "ReportingActivity - Sms Button Clicked");
                    /** pop sms dialog */
                    showSmsDialog();
                }
            });

            /** instantiate image button object */
            Button btnGo = (Button) findViewById(R.id.btnReportGo);

            /** set event listener on button to handle click */
            btnGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    txtReportDate = (EditText) findViewById(R.id.txtReportDate);

                    /** get today's date */
                    Date today = new Date();
                    /** initialize compare date with today's date as well */
                    Date newDate = new Date();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        newDate = sdf.parse(txtReportDate.getText().toString());
                    } catch (Exception e) {
                        Log.i(TAG, "ReportingActivity.onDateSet() - Error: " + e.getMessage());
                    }

                    /** if the new date is > than today pop toast else set date */
                    if (newDate.after(today)) {
                        /** alert */
                        Toast.makeText(getApplicationContext(), "please type a date from the past", Toast.LENGTH_SHORT).show();

                        /** reset date */
                        txtReportDate = (EditText) findViewById(R.id.txtReportDate);
                        txtReportDate.setText((month + 1) + "/" + day + "/" + year);

                        /** set the report for current date */
                        setReport(today);
                    } else {
                        /** set textbox with date */
                        txtReportDate = (EditText) findViewById(R.id.txtReportDate);
                        txtReportDate.setText(txtReportDate.getText().toString());

                        /** set the report for the new date */
                        setReport(newDate);
                    }

                    Log.i(TAG, "ReportingActivity - Go Button Clicked - Report Date " + txtReportDate.getText());
                }
            });

            /** set the max daily calories */
            MyAlerts _myAlerts = new MyAlerts();
            Hashtable<String, String> record = _myAlerts.GetMyAlerts(getApplicationContext());
            EditText dailyMax = (EditText) findViewById(R.id.txtDailyMax);
            dailyMax.setText(record.get("maxDaily"), TextView.BufferType.EDITABLE);

            /** set the report for the current date */
            Date newDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            newDate = sdf.parse(txtReportDate.getText().toString());
            setReport(newDate);

        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "ReportingActivity.initMain() - Error: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "ReportingActivity.initMain() - Error: " + e.getMessage());
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
     * This method is adapted from research on sending emails from Android
     * http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
     */
    private void popEmail() {
        try {
            /** get nutricianist email from preferences */
            final SharedPreferences pref_email = getSharedPreferences("pref_email", 0);
            final CharSequence value_email = pref_email.getString("pref_email", "");

            /** construct email */
            String[] emails = {value_email.toString()};
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, emails);
            email.putExtra(Intent.EXTRA_SUBJECT, "Food Log for " + reportDate);
            email.putExtra(Intent.EXTRA_TEXT, emailMessage);

            /** need this to prompts email client only */
            email.setType("message/rfc822");

            /** send to an email client */
            startActivity(Intent.createChooser(email, "Choose an Email client :"));

        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "ReportingActivity.popEmail() - Error: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "ReportingActivity.popEmail() - Error: " + e.getMessage());
            /** send toast to user */
            Toast.makeText(getApplicationContext(), "please configure an email client on your phone", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Event listener to handle button click on calendar dialog
     * Adapted from:
     * http://www.tutorialspoint.com/android/android_datepicker_control.htm
     * http://www.compiletimeerror.com/2013/07/android-date-picker-example-android.html#.Vvf8WeIrKUk
     */
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // handle the click
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            /** get today's date */
            Date today = new Date();
            /** initialize compare date with today's date as well */
            Date newDate = new Date();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                newDate = sdf.parse((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
            } catch (Exception e) {
                Log.i(TAG, "ReportingActivity.onDateSet() - Error: " + e.getMessage());
            }

            /** if the new date is > than today pop toast else set date */
            if (newDate.after(today)) {
                /** alert */
                Toast.makeText(getApplicationContext(), "please select a date from the past", Toast.LENGTH_SHORT).show();
            } else {
                /** set textbox with date */
                txtReportDate = (EditText) findViewById(R.id.txtReportDate);
                txtReportDate.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
            }
        }
    };

    /**
     * A placeholder fragment containing a "No Data for Report" message.
     * This should be popped when the row count = 0
     * This is adapted from the lecture notes Module 2, Section Fragment Example 1
     */
    public static class PlaceholderFragment extends Fragment {
        /** default constructor */
        public PlaceholderFragment() {
        }

        /** method to invoke the text_fragment view and show the No Data message */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = null;
            /** wrapped with try/catch for error reporting */
            try {
                rootView = inflater.inflate(R.layout.text_fragment, container, false);
            } catch (Exception e) {
                Log.i(TAG, "ReportingActivity.PlaceholderFragment() - Error: " + e.getMessage());
            }
            return rootView;
        }
    }

    /**
     * This method creates the rows that make up the report
     * input: selectedDate
     * output: void
     */
    private void setReport(Date selectedDate) {
        try {
            /** create date */
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            /** get records from the repository for today's date */
            MyRecord _myRecord = new MyRecord();
            ArrayList<Record> records = _myRecord.getByDate(getApplicationContext(), sdf.format(selectedDate).toString());

            /** set report date for message */
            reportDate = sdf.format(selectedDate).toString();

            /** create the email message using the record array list */
            createMessage(records);

            /** get gridview object */
            GridView gvReportResults = (GridView) findViewById(R.id.gvReportResults);

            /** Populate grid */
            RecordAdapter adapter = new RecordAdapter(MyReportingActivity.this, records);
            gvReportResults.setAdapter(adapter);

            /** set the total calories*/
            int totalCalories = _myRecord.getTotalCalories(records);
            EditText txtTotalCalories = (EditText) findViewById(R.id.txtTotalCalories);
            txtTotalCalories.setText(Integer.toString(totalCalories), TextView.BufferType.EDITABLE);

            /** wrap this with an IF condition based on rows returned from DB. If row count = 0 then pop no data fragment */
            if (records.size() == 0) {
                /** show no data message */
                getFragmentManager().beginTransaction().add(R.id.flNoData, new PlaceholderFragment()).commit();
                gvReportResults.setVisibility(View.GONE);
            } else {
                gvReportResults.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.i(TAG, "ReportingActivity.setReport() - Error: " + e.getMessage());
        }
    }

    /**
     * This method creates an email message using the record ArrayList
     * it is used when the email client is popped
     * input: ArrayList<Record>
     * output: void
     */
    private void createMessage(ArrayList<Record> records) {
        try {
            /** check for the size before constructing message headers*/
            if (records.size() > 0) {
                /** define table structure */
                for (int i = 0; i < records.size(); i++) {
                    /** add rows */
                    Record record = records.get(i);
                    String[] time = record.getTime().split(" ");
                    emailMessage = emailMessage + time[1] + " ";
                    emailMessage = emailMessage + record.getItem() + " (";
                    emailMessage = emailMessage + record.getGroup() + ")\r\n";
                    emailMessage = emailMessage + record.getPortion() + " Portion ";
                    emailMessage = emailMessage + record.getCalories() + " Cal ";
                    emailMessage = emailMessage + "\r\n\r\n";
                }

            }
        } catch (Exception e) {
            Log.i(TAG, "ReportingActivity.createMessage() - Error: " + e.getMessage());
        }
    }

    /*
     * code pattern on input dialog adapted from http://javatechig.com/android/android-input-dialog-example
     * customization of activity class, layout views and click implementation.
     */
    private void showSmsDialog() {
        try {
            /** instantiate layout inflator */
            LayoutInflater layoutInflater = LayoutInflater.from(MyReportingActivity.this);

            /** load the new_item input dialog view */
            View newItemView = layoutInflater.inflate(R.layout.sms_msg, null);

            /** create modal with AlertDialog */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyReportingActivity.this);
            alertDialogBuilder.setView(newItemView);

            /** get the edittext object from the new_item view */
            final EditText editText = (EditText) newItemView.findViewById(R.id.txtSmsMsg);

            /** get shared preferences */
            final SharedPreferences pref_sms = getSharedPreferences("pref_sms", 0);
            final CharSequence value_sms = pref_sms.getString("pref_sms", "");

            /** set the message on the dialog from shared pref value */
            editText.setText(value_sms);

            /** setup a dialog window */
            alertDialogBuilder.setCancelable(false)
                    /** add button */
                    .setPositiveButton("Send Text", new DialogInterface.OnClickListener() {
                        /** onClick event to handle user click */
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                /*
                                 * get the item input
                                 * research on "getting" UI data from
                                 * http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                                 * research on SMS from
                                 * http://www.tutorialspoint.com/android/android_sending_sms.htm
                                 */
                                final SharedPreferences pref_phone = getSharedPreferences("pref_phone", 0);
                                final CharSequence value_phone = pref_phone.getString("pref_phone", "");

                                android.text.Editable item = editText.getText();
                                Log.i(TAG, "SMS - " + editText.getText());

                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(value_phone.toString(), null, editText.getText().toString(), null, null);
                                Log.i(TAG, "SMS Sent!" + editText.getText());
                                Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Log.i(TAG, "ReportingActivity ERROR - " + e.getMessage());
                                Toast.makeText(getApplicationContext(), "SMS failed:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                            /** cancel button */
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        /** onClick event to handle user click */
                        public void onClick(DialogInterface dialog, int id) {
                            /** close dialog */
                            dialog.cancel();
                        }
                    });

            /** pop new_item input dialog */
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "ReportingActivity.showAddNewDialog() -  ERROR: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "ReportingActivity.showAddNewDialog() -  ERROR: " + e.getMessage());
        }
    }
}

