package com.riansousa.foodtrackerv2;

import android.app.Activity;
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
import com.riansousa.foodtrackerv2.Logic.FoodProfile;
import java.util.Hashtable;

/**
 * Class that handles all the code processing to support the activity_food_list_detail view.
 */
public class FoodProfileDetailActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private FoodProfile _foodProfile = new FoodProfile();
    private ErrorLog _log = new ErrorLog();
    private static final String TAG = "FoodTracker";
    private EditText _itemName;
    private EditText _fruitCalories;
    private EditText _grainCalories;
    private EditText _vegetableCalories;
    private EditText _proteinCalories;
    private EditText _dairyCalories;
    private EditText _fruitPortion;
    private EditText _grainPortion;
    private EditText _vegetablePortion;
    private EditText _proteinPortion;
    private EditText _dairyPortion;

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_detail);
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
        Log.i(TAG, "FoodProfileDetailActivity - Menu Options Loaded");
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
                Log.i(TAG, "FoodProfileDetailActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "FoodProfileDetailActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "FoodProfileDetailActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "FoodProfileDetailActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent intentForMyPreferences = new Intent();
                intentForMyPreferences.setAction("MyPreferences");
                startActivity(intentForMyPreferences);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "FoodProfileDetailActivity - The reporting icon was clicked");
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
        Log.i(TAG, "FoodProfileDetailActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodProfileDetailActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodProfileDetailActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodProfileDetailActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodProfileDetailActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodProfileDetailActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodProfileDetailActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodProfileDetailActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     */
    private void initMain() {

        try {
            /** instantiate button objects */
            Button updateButton = (Button) findViewById(R.id.btnUpdateFoodItem);
            Button deleteButton = (Button) findViewById(R.id.btnDeleteFoodItem);

            /*
             * assign values to global variables
             * research on "getting" UI data from
             * http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
             */
            _itemName = (EditText)findViewById(R.id.txtItemName);
            _fruitCalories = (EditText)findViewById(R.id.txtFruitCalories);
            _grainCalories = (EditText)findViewById(R.id.txtGrainCalories);
            _vegetableCalories = (EditText)findViewById(R.id.txtVegetableCalories);
            _proteinCalories = (EditText)findViewById(R.id.txtProteinCalories);
            _dairyCalories = (EditText)findViewById(R.id.txtDairyCalories);
            _fruitPortion = (EditText)findViewById(R.id.txtFruitPortion);
            _grainPortion = (EditText)findViewById(R.id.txtGrainPortion);
            _vegetablePortion = (EditText)findViewById(R.id.txtVegetablePortion);
            _proteinPortion = (EditText)findViewById(R.id.txtProteinPortion);
            _dairyPortion = (EditText)findViewById(R.id.txtDairyPortion);

            /* get any key/value data pairs for this activity */
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                /*
                 * Research on setting UI Element Data from
                 * http://stackoverflow.com/questions/4590957/how-to-set-text-in-an-edittext
                 */
                String value = extras.getString("itemSelected").replace(" - NEW","").trim();
                _itemName.setText(value, TextView.BufferType.EDITABLE);
                _itemName.setFocusable(false);

                /* pull from db and set values */
                Hashtable<String, String> record = _foodProfile.GetFoodProfileByName(getApplicationContext(), _itemName.getText().toString());
                _fruitCalories.setText(record.get("fruitCalories"), TextView.BufferType.EDITABLE);
                _fruitPortion.setText(record.get("fruitPortion"), TextView.BufferType.EDITABLE);
                _grainCalories.setText(record.get("grainCalories"), TextView.BufferType.EDITABLE);
                _grainPortion.setText(record.get("grainPortion"), TextView.BufferType.EDITABLE);
                _vegetableCalories.setText(record.get("vegetableCalories"), TextView.BufferType.EDITABLE);
                _vegetablePortion.setText(record.get("vegetablePortion"), TextView.BufferType.EDITABLE);
                _proteinCalories.setText(record.get("proteinCalories"), TextView.BufferType.EDITABLE);
                _proteinPortion.setText(record.get("proteinPortion"), TextView.BufferType.EDITABLE);
                _dairyCalories.setText(record.get("dairyCalories"), TextView.BufferType.EDITABLE);
                _dairyPortion.setText(record.get("dairyPortion"), TextView.BufferType.EDITABLE);
            }

            /** set listener for update button onClick event */
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** handle click */
                    Log.i(TAG, "FoodProfileDetailActivity - Update Food Item Button Clicked");
                    try {

                        /** log form details */
                        Log.i(TAG, "FoodProfileDetailActivity - Form Item Name:" + _itemName.getText());

                        /** send Toast confirm */
                        Toast.makeText(getApplicationContext(),  _itemName.getText() +  " has been updated.", Toast.LENGTH_SHORT).show();

                        /** set input values & defaults */
                        String fruitCalories = (_fruitCalories.getText().toString().length() > 0 ? _fruitCalories.getText().toString() : "0");
                        String grainCalories = (_grainCalories.getText().toString().length() > 0 ? _grainCalories.getText().toString() : "0");
                        String vegetableCalories = (_vegetableCalories.getText().toString().length() > 0 ? _vegetableCalories.getText().toString() : "0");
                        String proteinCalories = (_proteinCalories.getText().toString().length() > 0 ? _proteinCalories.getText().toString() : "0");
                        String dairyCalories = (_dairyCalories.getText().toString().length() > 0 ? _dairyCalories.getText().toString() : "0");
                        String fruitPortion = (_fruitPortion.getText().toString().length() > 0 ? _fruitPortion.getText().toString() : "0");
                        String grainPortion = (_grainPortion.getText().toString().length() > 0 ? _grainPortion.getText().toString() : "0");
                        String vegetablePortion = (_vegetablePortion.getText().toString().length() > 0 ? _vegetablePortion.getText().toString() : "0");
                        String proteinPortion = (_proteinPortion.getText().toString().length() > 0 ? _proteinPortion.getText().toString() : "0");
                        String dairyPortion = (_dairyPortion.getText().toString().length() > 0 ? _dairyPortion.getText().toString() : "0");

                        /** instantiate and configure updatable content values */
                        ContentValues profile = new ContentValues();
                        profile.put("fruitCalories", fruitCalories);
                        profile.put("grainCalories", grainCalories);
                        profile.put("vegetableCalories", vegetableCalories);
                        profile.put("proteinCalories", proteinCalories);
                        profile.put("dairyCalories", dairyCalories);
                        profile.put("fruitPortion", fruitPortion);
                        profile.put("grainPortion", grainPortion);
                        profile.put("vegetablePortion", vegetablePortion);
                        profile.put("proteinPortion", proteinPortion);
                        profile.put("dairyPortion", dairyPortion);
                        /* hard code value to 2, indicating updated for list purposes */
                        profile.put("isNew", "2");

                        /** call into middle tier and update repository */
                        _foodProfile.UpdateItem(getApplicationContext(), _itemName.getText().toString(), profile);

                    } catch (Exception e) {
                        /** log to file */
                        _log.WriteError(getApplicationContext(), "FoodProfileDetailActivity ERROR - " + e.getMessage());
                        /** log to console */
                        Log.i(TAG, "FoodProfileDetailActivity ERROR - " + e.getMessage());
                    }

                    /** communicate back to intent initiator */
                    Intent output = new Intent();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            });

            /** set listener for delete button onClick event */
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** call into middle tier and DELETE from repository */
                    _foodProfile.DeleteItem(getApplicationContext(), _itemName.getText().toString());

                    /** send Toast confirm */
                    Toast.makeText(getApplicationContext(), _itemName.getText().toString() + " has been deleted.", Toast.LENGTH_SHORT).show();

                    /** redirect back to food list screen */
                    Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                    startActivity(foodListScreen);
                }
            });
        } catch (Exception e) {
            /** log to file */
            _log.WriteError(getApplicationContext(), "FoodProfileDetailActivity.initMain() - ERROR:" + e.getMessage());
            /** log to console */
            Log.i(TAG, "FoodProfileDetailActivity.initMain() - ERROR:" + e.getMessage());
        }
    }
}
