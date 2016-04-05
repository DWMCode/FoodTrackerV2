package com.riansousa.foodtrackerv2;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.riansousa.foodtrackerv2.Logic.ErrorLog;
import com.riansousa.foodtrackerv2.Logic.MyAlerts;
import java.util.Hashtable;

/**
 * Class that handles all the code processing to support the activity_alert view.
 */
public class MyAlertActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private MyAlerts _myAlerts = new MyAlerts();
    private static final String TAG = "FoodTracker";
    private ErrorLog _log = new ErrorLog();
    private EditText _maxDailyCalories;
    private EditText _fruitMin;
    private EditText _fruitMax;
    private EditText _grainMin;
    private EditText _grainMax;
    private EditText _proteinMin;
    private EditText _proteinMax;
    private EditText _vegetablesMin;
    private EditText _vegetablesMax;
    private EditText _dairyMin;
    private EditText _dairyMax;

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
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
        Log.i(TAG, "AlertActivity - Menu Options Loaded");
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
        // research on switching between activity screens from
        // http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
        switch(item.getItemId())
        {
            case R.id.menu_food_profile:
                Log.i(TAG, "AlertActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "AlertActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "AlertActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "AlertActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent preference = new Intent(getApplicationContext(), MyPreferencesActivity.class);
                startActivity(preference);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "AlertActivity - The reporting icon was clicked");
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
        Log.i(TAG, "AlertActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "AlertActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "AlertActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "AlertActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "AlertActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "AlertActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "AlertActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "AlertActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     */
    private void initMain() {
        try {
            /** instantiate button object */
            Button button = (Button) findViewById(R.id.btnUpdateMyAlerts);

            /** instantiate EditText controls so we can manipulate data */
            _maxDailyCalories = (EditText)findViewById(R.id.txtMaxDailyCalories);
            _fruitMin = (EditText)findViewById(R.id.txtFruitMin);
            _fruitMax = (EditText)findViewById(R.id.txtFruitMax);
            _grainMin = (EditText)findViewById(R.id.txtGrainMin);
            _grainMax =(EditText)findViewById(R.id.txtGrainMax);
            _proteinMin = (EditText)findViewById(R.id.txtProteinMin);
            _proteinMax = (EditText)findViewById(R.id.txtProteinMax);
            _vegetablesMin = (EditText)findViewById(R.id.txtVegetableMin);
            _vegetablesMax = (EditText)findViewById(R.id.txtVegetableMax);
            _dairyMin = (EditText)findViewById(R.id.txtDairyMin);
            _dairyMax = (EditText)findViewById(R.id.txtDairyMax);

            /* pull from db and set UI values */
            Hashtable<String, String> record = _myAlerts.GetMyAlerts(getApplicationContext());
            _maxDailyCalories.setText(record.get("maxDaily"), TextView.BufferType.EDITABLE);
            _fruitMin.setText(record.get("fruitMin"), TextView.BufferType.EDITABLE);
            _fruitMax.setText(record.get("fruitMax"), TextView.BufferType.EDITABLE);
            _grainMin.setText(record.get("grainMin"), TextView.BufferType.EDITABLE);
            _grainMax.setText(record.get("grainMax"), TextView.BufferType.EDITABLE);
            _proteinMin.setText(record.get("proteinMin"), TextView.BufferType.EDITABLE);
            _proteinMax.setText(record.get("proteinMax"), TextView.BufferType.EDITABLE);
            _vegetablesMin.setText(record.get("vegetableMin"), TextView.BufferType.EDITABLE);
            _vegetablesMax.setText(record.get("vegetableMax"), TextView.BufferType.EDITABLE);
            _dairyMin.setText(record.get("dairyMin"), TextView.BufferType.EDITABLE);
            _dairyMax.setText(record.get("dairyMax"), TextView.BufferType.EDITABLE);

            /** set listener for onClick event */
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "AlertActivity - Update Food Item Button Clicked");

                    try {
                        /** send Toast confirm */
                        Toast.makeText(getApplicationContext(), "My Alerts have been updated!", Toast.LENGTH_SHORT).show();

                        /**
                         * assign values to global variables
                         * research on "getting" UI data from
                         * http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                         */
                        String maxDaily = (_maxDailyCalories.getText().toString().length() > 0 ? _maxDailyCalories.getText().toString() : "0");
                        String fruitMin = (_fruitMin.getText().toString().length() > 0 ? _fruitMin.getText().toString() : "0");
                        String fruitMax = (_fruitMax.getText().toString().length() > 0 ? _fruitMax.getText().toString() : "0");
                        String grainsMin = (_grainMin.getText().toString().length() > 0 ? _grainMin.getText().toString() : "0");
                        String grainsMax = (_grainMax.getText().toString().length() > 0 ? _grainMax.getText().toString() : "0");
                        String proteinMin = (_proteinMin.getText().toString().length() > 0 ? _proteinMin.getText().toString() : "0");
                        String proteinMax = (_proteinMax.getText().toString().length() > 0 ? _proteinMax.getText().toString() : "0");
                        String vegetablesMin = (_vegetablesMin.getText().toString().length() > 0 ? _vegetablesMin.getText().toString() : "0");
                        String vegetablesMax = (_vegetablesMax.getText().toString().length() > 0 ? _vegetablesMax.getText().toString() : "0");
                        String dairyMin = (_dairyMin.getText().toString().length() > 0 ? _dairyMin.getText().toString() : "0");
                        String dairyMax = (_dairyMax.getText().toString().length() > 0 ? _dairyMax.getText().toString() : "0");

                        /** instantiate and configure updatable content values */
                        ContentValues profile = new ContentValues();
                        profile.put("maxDaily", maxDaily);
                        profile.put("fruitMin", fruitMin);
                        profile.put("fruitMax", fruitMax);
                        profile.put("grainMin", grainsMin);
                        profile.put("grainMax", grainsMax);
                        profile.put("proteinMin", proteinMin);
                        profile.put("proteinMax", proteinMax);
                        profile.put("vegetableMin", vegetablesMin);
                        profile.put("vegetableMax", vegetablesMax);
                        profile.put("dairyMin", dairyMin);
                        profile.put("dairyMax", dairyMax);

                        /** call into middle tier and update repository */
                        _myAlerts.UpdateAlerts(getApplicationContext(), profile);

                    } catch (Exception e) {
                        /** log to file */
                        _log.WriteError(getApplicationContext(), "AlertActivity ERROR - " + e.getMessage());
                        /** log to console */
                        Log.i(TAG, "AlertActivity ERROR - " + e.getMessage());
                    }

                    /** redirect back to main */
                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(main);
                }
            });
        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "AlertActivity.initMain() - ERROR:" + e.getMessage());
            /** log to console */
            Log.i(TAG, "AlertActivity.initMain() - ERROR:" + e.getMessage());
        }
    }
}
