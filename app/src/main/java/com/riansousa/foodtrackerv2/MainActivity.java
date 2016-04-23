package com.riansousa.foodtrackerv2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.riansousa.foodtrackerv2.Logic.ErrorLog;
import com.riansousa.foodtrackerv2.Logic.FoodProfile;
import com.riansousa.foodtrackerv2.Logic.MyAlerts;
import com.riansousa.foodtrackerv2.Logic.MyRecord;
import com.riansousa.foodtrackerv2.Logic.SeedData;

/**
 * Class that handles all the code processing to support the activity_main view.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private FoodProfile _foodProfile = new FoodProfile();
    private ErrorLog _log = new ErrorLog();

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
    }

    /**
     * Method to create the menu.
     * Adapted from: Lecture Notes Module 1: Example illustrating state changes
     *
     * @param menu Takes a Menu object
     * @return boolean value from super
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
        Log.i(TAG, "MainActivity - Menu Options Loaded");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Method to handle option selection event.
     * Launches a new Activity Screen based on selection.
     * Adapted from: Lecture Notes Module 1: Example illustrating state changes
     * research on switching between activity screens from
     * http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
     *
     * @param item The menu item selected by the user
     * @return boolean value from super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // research on switching between activity screens from
        // http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
        switch (item.getItemId()) {
            case R.id.menu_food_profile:
                Log.i(TAG, "MainActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "MainActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "MainActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "MainActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent intentForMyPreferences = new Intent();
                intentForMyPreferences.setAction("MyPreferences");
                startActivity(intentForMyPreferences);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "MainActivity - The reporting icon was clicked");
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
        Log.i(TAG, "MainActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "MainActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "MainActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "MainActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "MainActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "MainActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "MainActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle list initialization of the activity
     * used for setting values and event listeners
     * research on listview from http://www.tutorialspoint.com/android/android_list_view.htm
     */
    private void initList() {
        try {
            /** Initialize data */
            SeedData seed = new SeedData();
            seed.Run(getApplicationContext());

            /** Instantiate listview object */
            ListView listview = (ListView) findViewById(R.id.listMainItems);

            /**  data pull from repository, use 0 for state param to pull all food items */
            String[] values = _foodProfile.GetFoodProfileList(getApplicationContext(), 0);

            if (values.length > 0) {
                /** Create an adapter as the source for the list view. Note: MUST create TEXTVIEW layout for rendering */
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.food_item, values);

                /** Set the adapter as the list source */
                listview.setAdapter(adapter);
            } else {
                /** launch pop up dialog for new item */
                showAddNewDialog();
            }

            /** Log action */
            Log.i(TAG, "MainActivity - Item load complete");

            /** Create listener to capture list view item click */
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                /** Implement click handle */
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    /** get item from position */
                    String item = (String) parent.getItemAtPosition(position);

                    /** Log item click */
                    Log.i(TAG, "MainActivity - You clicked " + item);

                    /** Send item to logic layer for processing */
                    MyRecord myRecord = new MyRecord();
                    myRecord.AddNewItem(getApplicationContext(), item);

                    /** check the alert status */
                    checkAlertStatus(item);
                }
            });

            /** instantiate button object */
            Button button = (Button) findViewById(R.id.btnAddNew);

            /** set listener for onClick event */
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "MainActivity - Add New Food Item Button Clicked");
                    /** launch pop up dialog for new item */
                    showAddNewDialog();
                }
            });

            /** print accumulated errors to console for review */
            _log.PrintErrorLog(getApplicationContext());

        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "MainActivity.initList() - ERROR: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "MainActivity.initList() - ERROR: " + e.getMessage());
        }
    }

    /*
     * code pattern on input dialog adapted from http://javatechig.com/android/android-input-dialog-example
     * customization of activity class, layout views and click implementation.
     */
    private void showAddNewDialog() {
        try {
            /** instantiate layout inflator */
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            /** load the new_item input dialog view */
            View newItemView = layoutInflater.inflate(R.layout.new_item, null);

            /** create modal with AlertDialog */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(newItemView);

            /** get the edittext object from the new_item view */
            final EditText editText = (EditText) newItemView.findViewById(R.id.txtNewItem);

            /** setup a dialog window */
            alertDialogBuilder.setCancelable(false)
                    /** add button */
                    .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                        /** onClick event to handle user click */
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                /*
                                 * get the item input
                                 * research on "getting" UI data from
                                 * http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                                 */
                                android.text.Editable item = editText.getText();
                                Log.i(TAG, "MainActivity - " + editText.getText() + " was entered as a new item.");
                                /** send item to logic layer for processing */
                                FoodProfile foodProfile = new FoodProfile();
                                foodProfile.AddNewItem(getApplicationContext(), item);

                                /** refresh & rebind the list to show new item */
                                initList();

                                /** send Toast confirm */
                                Toast.makeText(getApplicationContext(), item + " has been added.", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Log.i(TAG, "MainActivity ERROR - " + e.getMessage());
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
            _log.WriteError(getApplicationContext(), "MainActivity.showAddNewDialog() -  ERROR: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "MainActivity.showAddNewDialog() -  ERROR: " + e.getMessage());
        }
    }

    /**
     * Method to check if the user has eaten too much and to pop a message if that is the case
     * @param item
     */
    private void checkAlertStatus(String item){
        try {
            /** Check My Alert criteria */
            MyAlerts myAlerts = new MyAlerts();
            String notification = myAlerts.CheckNotification(getApplicationContext());

            /** set length for compare */
            int length = notification.length();

            /** check for notification */
            if (length > 0) {

                /** pop alert dialog with notification message */
                /** code adapted from lecture notes module 6, Alerts > Example */
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setTitle("Alert Notification")
                        .setMessage(notification)
                        .setCancelable(false)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        /** close the dialog */
                                        dialog.cancel();
                                    }
                                }
                        );
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                /** instantiate vibrator and vibrate for 300 milli
                 * research from: http://android.konreu.com/developer-how-to/vibration-examples-for-android-phone-development/
                 * */
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(300);
            } else {
                /** send Toast confirm */
                Toast.makeText(getApplicationContext(), "Your consumption of " + item + " has been recorded.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "MainActivity.checkAlertStatus() -  ERROR: " + e.getMessage());
            /** log to console */
            Log.i(TAG, "MainActivity.checkAlertStatus() -  ERROR: " + e.getMessage());
        }
    }
}