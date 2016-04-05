package com.riansousa.foodtrackerv2;

import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * Class that handles all the code processing to support the activity_my_preferences view.
 */
public class MyPreferencesActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private ErrorLog _log = new ErrorLog();
    private EditText _maxDailyCalories;
    private EditText _fruitMin;
    private EditText _fruitMax;
    private EditText _grainMin;
    private EditText _grainMax;
    private EditText _proteinMin;
    private EditText _proteinMax;
    private EditText _vegetableMin;
    private EditText _vegetableMax;
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
        setContentView(R.layout.activity_my_preferences);
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
        Log.i(TAG, "MyPreferencesActivity - Menu Options Loaded");
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
                Log.i(TAG, "MyPreferencesActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "MyPreferencesActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "MyPreferencesActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "MyPreferencesActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent preference = new Intent(getApplicationContext(), MyPreferencesActivity.class);
                startActivity(preference);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "MyPreferencesActivity - The reporting icon was clicked");
                // load reporting screen
                Intent report = new Intent(getApplicationContext(), MyReportingActivity.class);
                startActivity(report);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMain() {

        try {
            /** instantiate button object */
            Button button = (Button) findViewById(R.id.btnUpdatePreferences);

            /** instantiate shared preference objects */
            final SharedPreferences pref_maxDailyCalories = getSharedPreferences("pref_maxDailyCalories", 0);
            final SharedPreferences pref_fruitMin = getSharedPreferences("pref_fruitMin", 0);
            final SharedPreferences pref_fruitMax = getSharedPreferences("pref_fruitMax", 0);
            final SharedPreferences pref_grainMin = getSharedPreferences("pref_grainMin", 0);
            final SharedPreferences pref_grainMax = getSharedPreferences("pref_grainMax", 0);
            final SharedPreferences pref_proteinMin = getSharedPreferences("pref_proteinMin", 0);
            final SharedPreferences pref_proteinMax = getSharedPreferences("pref_proteinMax", 0);
            final SharedPreferences pref_vegetableMin = getSharedPreferences("pref_vegetableMin", 0);
            final SharedPreferences pref_vegetableMax = getSharedPreferences("pref_vegetableMax", 0);
            final SharedPreferences pref_dairyMin = getSharedPreferences("pref_dairyMin", 0);
            final SharedPreferences pref_dairyMax = getSharedPreferences("pref_dairyMax", 0);

            /** instantiate EditText controls so we can manipulate data */
            _maxDailyCalories = (EditText) findViewById(R.id.txtMaxCaloriesPref);
            _fruitMin = (EditText) findViewById(R.id.txtFruitMinPref);
            _fruitMax = (EditText) findViewById(R.id.txtFruitMaxPref);
            _grainMin = (EditText) findViewById(R.id.txtGrainMinPref);
            _grainMax = (EditText) findViewById(R.id.txtGrainMaxPref);
            _proteinMin = (EditText) findViewById(R.id.txtProteinMinPref);
            _proteinMax = (EditText) findViewById(R.id.txtProteinMaxPref);
            _vegetableMin = (EditText) findViewById(R.id.txtVegetableMinPref);
            _vegetableMax = (EditText) findViewById(R.id.txtVegetableMaxPref);
            _dairyMin = (EditText) findViewById(R.id.txtDairyMinPref);
            _dairyMax = (EditText) findViewById(R.id.txtDairyMaxPref);

            /** get values from preferences or set default */
            final CharSequence value_maxDailyCalories = pref_maxDailyCalories.getString("pref_maxDailyCalories", "");
            final CharSequence value_fruitMin = pref_fruitMin.getString("pref_fruitMin", "");
            final CharSequence value_fruitMax = pref_fruitMax.getString("pref_fruitMax", "");
            final CharSequence value_grainMin = pref_grainMin.getString("pref_grainMin", "");
            final CharSequence value_grainMax = pref_grainMax.getString("pref_grainMax", "");
            final CharSequence value_proteinMin = pref_proteinMin.getString("pref_proteinMin", "");
            final CharSequence value_proteinMax = pref_proteinMax.getString("pref_proteinMax", "");
            final CharSequence value_vegetableMin = pref_vegetableMin.getString("pref_vegetableMin", "");
            final CharSequence value_vegetableMax = pref_vegetableMax.getString("pref_vegetableMax", "");
            final CharSequence value_dairyMin = pref_dairyMin.getString("pref_dairyMin", "");
            final CharSequence value_dairyMax = pref_dairyMax.getString("pref_dairyMax", "");

            /** set UI values */
            _maxDailyCalories.setText(value_maxDailyCalories, TextView.BufferType.EDITABLE);
            _fruitMin.setText(value_fruitMin, TextView.BufferType.EDITABLE);
            _fruitMax.setText(value_fruitMax, TextView.BufferType.EDITABLE);
            _grainMin.setText(value_grainMin, TextView.BufferType.EDITABLE);
            _grainMax.setText(value_grainMax, TextView.BufferType.EDITABLE);
            _proteinMin.setText(value_proteinMin, TextView.BufferType.EDITABLE);
            _proteinMax.setText(value_proteinMax, TextView.BufferType.EDITABLE);
            _vegetableMin.setText(value_vegetableMin, TextView.BufferType.EDITABLE);
            _vegetableMax.setText(value_vegetableMax, TextView.BufferType.EDITABLE);
            _dairyMin.setText(value_dairyMin, TextView.BufferType.EDITABLE);
            _dairyMax.setText(value_dairyMax, TextView.BufferType.EDITABLE);

            /** set listener for onClick event */
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "MyPreferencesActivity - Update Preferences Button Clicked");

                    /* get editors */
                    SharedPreferences.Editor edit_maxDailyCalories = pref_maxDailyCalories.edit();
                    SharedPreferences.Editor edit_fruitMin = pref_fruitMin.edit();
                    SharedPreferences.Editor edit_fruitMax = pref_fruitMax.edit();
                    SharedPreferences.Editor edit_grainMin = pref_grainMin.edit();
                    SharedPreferences.Editor edit_grainMax = pref_grainMax.edit();
                    SharedPreferences.Editor edit_proteinMin = pref_proteinMin.edit();
                    SharedPreferences.Editor edit_proteinMax = pref_proteinMax.edit();
                    SharedPreferences.Editor edit_vegetableMin = pref_vegetableMin.edit();
                    SharedPreferences.Editor edit_vegetableMax = pref_vegetableMax.edit();
                    SharedPreferences.Editor edit_dairyMin = pref_dairyMin.edit();
                    SharedPreferences.Editor edit_dairyMax = pref_dairyMax.edit();

                    /* set values */
                    edit_maxDailyCalories.putString("pref_maxDailyCalories", _maxDailyCalories.getText().toString());
                    edit_fruitMin.putString("pref_fruitMin", _fruitMin.getText().toString());
                    edit_fruitMax.putString("pref_fruitMax", _fruitMax.getText().toString());
                    edit_grainMin.putString("pref_grainMin", _grainMin.getText().toString());
                    edit_grainMax.putString("pref_grainMax", _grainMax.getText().toString());
                    edit_proteinMin.putString("pref_proteinMin", _proteinMin.getText().toString());
                    edit_proteinMax.putString("pref_proteinMax", _proteinMax.getText().toString());
                    edit_vegetableMin.putString("pref_vegetableMin", _vegetableMin.getText().toString());
                    edit_vegetableMax.putString("pref_vegetableMax", _vegetableMax.getText().toString());
                    edit_dairyMin.putString("pref_dairyMin", _dairyMin.getText().toString());
                    edit_dairyMax.putString("pref_dairyMax", _dairyMax.getText().toString());

                    /* commit changes */
                    edit_maxDailyCalories.apply();
                    edit_fruitMin.apply();
                    edit_fruitMax.apply();
                    edit_grainMin.apply();
                    edit_grainMax.apply();
                    edit_proteinMin.apply();
                    edit_proteinMax.apply();
                    edit_vegetableMin.apply();
                    edit_vegetableMax.apply();
                    edit_dairyMin.apply();
                    edit_dairyMax.apply();

                    /** send Toast confirm */
                    Toast.makeText(getApplicationContext(),"Your preferences have been updated.", Toast.LENGTH_SHORT).show();

                    /** redirect back to main */
                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(main);
                }
            });
        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "MyPreferencesActivity.initMain() - ERROR:" + e.getMessage());
            /** log to console */
            Log.i(TAG, "MyPreferencesActivity.initMain() - ERROR:" + e.getMessage());
        }
    }
}
