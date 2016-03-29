package com.riansousa.foodtrackerv2;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.riansousa.foodtrackerv2.Logic.Recorder;

/**
 * Class that handles all the code processing to support the activity_main view.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";

    /**
     * Default method to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
     * @param item  The menu item selected by the user
     * @return      boolean value from super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // research on switching between activity screens from
        // http://stackoverflow.com/questions/17743094/how-to-switch-between-activities-screens-in-android
        switch(item.getItemId())
        {
            case R.id.menu_food_list:
                Log.i(TAG, "MainActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "MainActivity - The recorder icon was clicked");
                // load food list screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_alert:
                Log.i(TAG, "MainActivity - The alert icon was clicked");
                // load food list screen
                Intent alert = new Intent(getApplicationContext(), AlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_reporting:
                Log.i(TAG, "MainActivity - The reporting icon was clicked");
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
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     * research on listview from http://www.tutorialspoint.com/android/android_list_view.htm
     */
    private void initMain() {

        // Instantiate listview object
        ListView listview = (ListView) findViewById(R.id.listMainItems);

        // Create a string array for seed data
        // todo: replace with data pull from repository
        String[] values = new String[]{"Raisin Bran", "Fruit & Nuts Cup", "Coffee",
                "Banana", "Hot Dogs", "Chili", "Meatball Sub", "Pizza",
                "Steak & Cheese", "Apple Juice", "Chicken Stir Fry", "Hamburger with Cheese", "Sloppy Joe", "Sword Fish",
                "Sushi Regular"};

        // Create an adapter as the source for the list view. Note: MUST create TEXTVIEW layout for rendering
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.food_item, values);

        // Set the adapter as the list source
        listview.setAdapter(adapter);

        // Log action
        Log.i(TAG, "MainActivity - Item load complete");

        // Create listener to capture list view item click
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Implement click handle
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // get item from position
                String item = (String) parent.getItemAtPosition(position);

                // Log item click
                Log.i(TAG, "MainActivity - You clicked " + item);

                // Send item to logic layer for processing
                Recorder lr = new Recorder();
                lr.RecordConsumption(item);

                // send Toast confirm
                Toast.makeText(getApplicationContext(), "Your consumption of " + item + " has been recorded.", Toast.LENGTH_SHORT).show();
            }
        });

        // instantiate button object
        Button button = (Button) findViewById(R.id.btnAddNew);

        // set listener for onClick event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle click
                Log.i(TAG, "MainActivity - Add New Food Item Button Clicked");
                // launch pop up dialog for new item
                showAddNewDialog();
            }
        });
    }

    // code pattern on input dialog adapted from http://javatechig.com/android/android-input-dialog-example
    // customization of activity class, layout views and click implementation.
    private void showAddNewDialog() {

        // instantiate layout inflator
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

        // load the new_item input dialog view
        View newItemView  = layoutInflater.inflate(R.layout.new_item, null);

        // create modal with AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(newItemView);

        // get the edittext object from the new_item view
        final EditText editText = (EditText) newItemView.findViewById(R.id.txtNewItem);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                // add button
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    // onClick event to handle user click
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            // get the item input
                            // research on "getting" UI data from
                            // http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
                            android.text.Editable item = editText.getText();
                            Log.i(TAG, "MainActivity - " + editText.getText() + " was entered as a new item.");
                            // send item to logic layer for processing
                            Recorder lr = new Recorder();
                            lr.AddNewItem(item);

                            // send Toast confirm
                            Toast.makeText(getApplicationContext(), item + " has been added.", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.i(TAG, "MainActivity ERROR - " + e.getMessage());
                        }
                    }
                })
                // cancel button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    // onClick event to handle user click
                    public void onClick(DialogInterface dialog, int id) {
                        // close dialog
                        dialog.cancel();
                    }
                });

        // pop new_item input dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}