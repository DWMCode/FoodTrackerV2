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

import com.riansousa.foodtrackerv2.Logic.Recorder;

public class RecorderActivity extends AppCompatActivity {

    private static final String TAG = "FoodTracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        initMain();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
        Log.i(TAG, "RecorderActivity - Menu Options Loaded");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.wrench_icon:
                Log.i(TAG, "RecorderActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.home_icon:
                Log.i(TAG, "RecorderActivity - The recorder icon was clicked");
                // load food list screen
                Intent recorder = new Intent(getApplicationContext(), RecorderActivity.class);
                startActivity(recorder);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "RecorderActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "RecorderActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "RecorderActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "RecorderActivity - onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "RecorderActivity - onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "RecorderActivity - onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "RecorderActivity - onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "RecorderActivity - onRestoreInstanceState");
    }

    private void initMain() {

        // Instantiate listview object
        ListView listview = (ListView) findViewById(R.id.listFood);

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
        Log.i(TAG, "RecorderActivity - Item load complete");

        // Create listener to capture list view item click
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Implement click handle
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // get item from position
                String item = (String) parent.getItemAtPosition(position);

                // Log item click
                Log.i(TAG, "RecorderActivity - You clicked " + item);

                // Send item to logic layer for processing
                Recorder lr = new Recorder();
                lr.RecordConsumption(item);

                // Create alert
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RecorderActivity.this);

                // Set properties
                builder1.setMessage("Your consumption of " + item + " has been recorded.");
                builder1.setCancelable(true);

                // Add OK button
                builder1.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // close the dialog
                                dialog.cancel();
                            }
                        });

                // Pop confirm alert
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        // instantiate button object
        Button button = (Button) findViewById(R.id.btnAddNew);

        // set listener for onClick event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle click
                Log.i(TAG, "RecorderActivity - Add New Food Item Button Clicked");
                // launch pop up dialog for new item
                showAddNewDialog();
            }
        });
    }

    private void showAddNewDialog() {

        // instantiate layout inflator
        LayoutInflater layoutInflater = LayoutInflater.from(RecorderActivity.this);

        // load the new_item input dialog view
        View newItemView  = layoutInflater.inflate(R.layout.new_item, null);

        // create modal with AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RecorderActivity.this);
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
                            android.text.Editable item = editText.getText();
                            Log.i(TAG, "RecorderActivity - " + editText.getText() + " was entered as a new item.");
                            // send item to logic layer for processing
                            Recorder lr = new Recorder();
                            lr.AddNewItem(item);
                        } catch (Exception e) {
                            Log.i(TAG, "RecorderActivity ERROR - " + e.getMessage());
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
