package com.riansousa.foodtrackerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FoodListDetailActivity extends AppCompatActivity {

    private static final String TAG = "FoodTracker";
    private EditText _itemName;
    private EditText _fruitCalories;
    private EditText _grainCalories;
    private EditText _vegetableCalories;
    private EditText _proteinCalories;
    private EditText _dairyCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_detail);
        initMain();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
        Log.i(TAG, "FoodListDetailActivity - Menu Options Loaded");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.wrench_icon:
                Log.i(TAG, "FoodListDetailActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.home_icon:
                Log.i(TAG, "FoodListDetailActivity - The recorder icon was clicked");
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
        Log.i(TAG, "FoodListDetailActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodListDetailActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodListDetailActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodListDetailActivity - onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodListDetailActivity - onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodListDetailActivity - onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodListDetailActivity - onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodListDetailActivity - onRestoreInstanceState");
    }

    private void initMain() {
        // instantiate button object
        Button button = (Button) findViewById(R.id.btnUpdateItem);
        _itemName = (EditText)findViewById(R.id.txtItemName);
        _fruitCalories = (EditText)findViewById(R.id.txtFruitCalories);
        _grainCalories = (EditText)findViewById(R.id.txtGrainCalories);
        _vegetableCalories = (EditText)findViewById(R.id.txtVegetableCalories);
        _proteinCalories = (EditText)findViewById(R.id.txtProteinCalories);
        _dairyCalories = (EditText)findViewById(R.id.txtDairyCalories);

        // get any key/value data pairs for this activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("itemSelected");
            _itemName.setText(value, TextView.BufferType.EDITABLE);
        }

        // set listener for onClick event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle click
                Log.i(TAG, "FoodListDetailActivity - Update Food Item Button Clicked");

                try {
                    // log form details
                    Log.i(TAG, "FoodListDetailActivity - Form Item Name:" + _itemName.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Fruit Calories:" + _fruitCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Grain Calories:" + _grainCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Vegetable Calories:" + _vegetableCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Protein Calories:" + _proteinCalories.getText());
                    Log.i(TAG, "FoodListDetailActivity - Form Dairy Calories:" + _dairyCalories.getText());

                    // todo: record to repository

                } catch (Exception e) {
                    Log.i(TAG, "FoodListDetailActivity ERROR - " + e.getMessage());
                }

                // redirect back to food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(foodListScreen);
            }
        });
    }
}
