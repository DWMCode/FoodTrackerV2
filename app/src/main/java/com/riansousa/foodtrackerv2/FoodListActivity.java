package com.riansousa.foodtrackerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FoodListActivity extends AppCompatActivity {

    private static final String TAG = "FoodTracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        initMain();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
        Log.i(TAG, "FoodListActivity - Menu Options Loaded");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.wrench_icon:
                Log.i(TAG, "FoodListActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.home_icon:
                Log.i(TAG, "FoodListActivity - The recorder icon was clicked");
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
        Log.i(TAG, "FoodListActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodListActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodListActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodListActivity - onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodListActivity - onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodListActivity - onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodListActivity - onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodListActivity - onRestoreInstanceState");
    }

    private void initMain() {
        try {
            // Instantiate listview object
            ListView listview = (ListView) findViewById(R.id.listFoodProfile);

            // Create a string array for seed data
            // todo: replace with data pull from repository
            String[] values = new String[]{"Orange Juice - NEW", "Raisin Bran", "Fruit & Nuts Cup", "Coffee",
                    "Banana", "Hot Dogs", "Chili", "Meatball Sub", "Pizza",
                    "Steak & Cheese", "Apple Juice", "Chicken Stir Fry", "Hamburger with Cheese", "Sloppy Joe", "Sword Fish",
                    "Sushi Regular"};

            // Create an adapter as the source for the list view. Note: MUST create TEXTVIEW layout for rendering
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.food_item, values);

            // Set the adapter as the list source
            listview.setAdapter(adapter);

            // Log action
            Log.i(TAG, "FoodListActivity - Food Profile load complete");

            // Create listener to capture list view item click
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                // Implement click handle
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    // get item from position
                    String item = (String) parent.getItemAtPosition(position);

                    // Log item click
                    Log.i(TAG, "FoodListActivity - You clicked " + item + " from food list.");

                    // create intent
                    Intent foodListDetailScreen = new Intent(getApplicationContext(), FoodListDetailActivity.class);

                    // set key/value data pair to pass selected item
                    foodListDetailScreen.putExtra("itemSelected", item);

                    // load food list detail screen
                    startActivity(foodListDetailScreen);
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "FoodListActivity ERROR - " + e.getMessage());
        }
    }
}
