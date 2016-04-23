package com.riansousa.foodtrackerv2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.riansousa.foodtrackerv2.Logic.DownloadWebPageTask;
import com.riansousa.foodtrackerv2.Logic.FoodProfile;
import com.riansousa.foodtrackerv2.Logic.Internet;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Hashtable;

/**
 * class to handle all of the code for the FoodProfileLookUpActivity
 */
public class FoodProfileLookUpActivity extends AppCompatActivity {

    /**
     * private global variables
     */
    private static final String TAG = "FoodTracker";
    private ListView _listLookupItems;
    private String lookupItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_profile_look_up);
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
        Log.i(TAG, "FoodProfileLookUpActivity - Menu Options Loaded");
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
                Log.i(TAG, "FoodProfileLookUpActivity - The food list icon was clicked");
                // load food list screen
                Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                startActivity(foodListScreen);
                break;
            case R.id.menu_recorder:
                Log.i(TAG, "FoodProfileLookUpActivity - The main icon was clicked");
                // load main screen
                Intent recorder = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(recorder);
                break;
            case R.id.menu_my_alert:
                Log.i(TAG, "FoodProfileLookUpActivity - The alert icon was clicked");
                // load alert screen
                Intent alert = new Intent(getApplicationContext(), MyAlertActivity.class);
                startActivity(alert);
                break;
            case R.id.menu_my_preferences:
                Log.i(TAG, "FoodProfileLookUpActivity - The preferences icon was clicked");
                // load my preferences screen
                Intent intentForMyPreferences = new Intent();
                intentForMyPreferences.setAction("MyPreferences");
                startActivity(intentForMyPreferences);
                break;
            case R.id.menu_my_reports:
                Log.i(TAG, "FoodProfileLookUpActivity - The reporting icon was clicked");
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
        Log.i(TAG, "FoodProfileLookUpActivity - onStart");
    }

    /**
     * System method used for logging activity life cycle onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "FoodProfileLookUpActivity - onResume");
    }

    /**
     * System method used for logging activity life cycle onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "FoodProfileLookUpActivity - onPause");
    }

    /**
     * System method used for logging activity life cycle onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "FoodProfileLookUpActivity - onStop");
    }

    /**
     * System method used for logging activity life cycle onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "FoodProfileLookUpActivity - onRestart");
    }

    /**
     * System method used for logging activity life cycle onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FoodProfileLookUpActivity - onDestroy");
    }

    /**
     * System method used for saving state instance
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FoodProfileLookUpActivity - onSaveInstanceState");
    }

    /**
     * System method used for restoring state instance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FoodProfileLookUpActivity - onRestoreInstanceState");
    }

    /**
     * Private method to handle the initial load of the activity
     * used for setting default values and event listeners
     * research on listview from http://www.tutorialspoint.com/android/android_list_view.htm
     * research on Async and DownLoadWebPage from http://developer.android.com/training/basics/network-ops/connecting.html#AsyncTask
     */
    private void initMain() {
        try {
            /* get any key/value data pairs for this activity */
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                /** instantiate ListView */
                _listLookupItems = (ListView)findViewById(R.id.listLookupItems);

                /** get the item for update */
                String value = extras.getString("itemSelected").replace(" - NEW", "").trim();

                /** instantiate shared preference objects */
                final SharedPreferences pref_lookup_item = getSharedPreferences("pref_lookup_item", 0);
                SharedPreferences.Editor lookup_item = pref_lookup_item.edit();

                /** set value */
                lookup_item.putString("pref_lookup_item", value);
                lookup_item.apply();

                /** replace spaces with plus sign for url string */
                value = value.replace(" ","+").trim();

                /** Create 1st listener to capture item click on NEW list */
                _listLookupItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    /** Implement click handle */
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        /** get item from position */
                        lookupItem = (String) parent.getItemAtPosition(position);

                        /** get values from preferences or set default */
                        final CharSequence itemSelected = pref_lookup_item.getString("pref_lookup_item", "");

                        /** update name of item */
                        FoodProfile foodProfile = new FoodProfile();
                        ContentValues profile = new ContentValues();
                        profile.put("name", lookupItem);
                        foodProfile.UpdateItem(getApplicationContext(), itemSelected.toString(), profile);

                        /** instantiate connectivity manager for iNet access */
                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                        /** get calories from calorie count, async request */
                        /** test network connectivity */
                        if (networkInfo != null && networkInfo.isConnected()) {
                            /** instantiate DownLoadWebPageTask */
                            DownloadWebPageTask getdata = new DownloadWebPageTask();
                            String url= "/";

                            /** get url from shared preferences */
                            Hashtable<String, String> itemDetails = null;
                            try
                            {
                                /**
                                 * serialization research from http://beginnersbook.com/2013/12/how-to-serialize-hashmap-in-java/
                                 */
                                FileInputStream fis = getApplicationContext().openFileInput("hashtable.ser");
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                itemDetails = (Hashtable) ois.readObject();
                                ois.close();
                                fis.close();

                                /** delete file, 1 time use only */
                                getApplicationContext().deleteFile("hashtable.ser");

                                /** set url */
                                url = itemDetails.get(lookupItem);

                            } catch(Exception e){
                                Log.i(TAG, "FoodProfileLookUpActivity.initMain() - ERROR: " + e.getMessage());
                                return;
                            }

                            Toast.makeText(getApplicationContext(), "One minute while I get the calorie count.", Toast.LENGTH_SHORT).show();

                            /** set listener */
                            getdata.setDataDownloadListener(new DownloadWebPageTask.DataDownloadListener() {
                                @SuppressWarnings("unchecked")
                                @Override
                                public void dataDownloadedSuccessfully(Object data) {
                                    /** handle download success */
                                    Internet iNet = new Internet();

                                    /** get calorie count */
                                    String calories = iNet.getCaloriesFromPage(getApplicationContext(), data.toString());

                                    /** create new intent */
                                    Intent foodListDetailScreen = new Intent(getApplicationContext(), FoodProfileDetailActivity.class);

                                    /** set key/value data pair to pass selected item */
                                    foodListDetailScreen.putExtra("itemSelected", lookupItem);
                                    foodListDetailScreen.putExtra("calorieCount", calories);

                                    /** load food list detail screen */
                                    startActivity(foodListDetailScreen);
                                }

                                @Override
                                public void dataDownloadFailed() {
                                    /** notify of failure */
                                    Toast.makeText(getApplicationContext(), "The page download failed.", Toast.LENGTH_SHORT).show();

                                    /** redirect to main list */
                                    Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                                    startActivity(foodListScreen);
                                }
                            });
                            getdata.execute("https://www.caloriecount.com" + url);
                        } else {
                            /** Log item click */
                            Log.i(TAG, "FoodProfileListActivity - You clicked " + lookupItem + " from food list.");

                            /** create intent */
                            Intent foodListDetailScreen = new Intent(getApplicationContext(), FoodProfileDetailActivity.class);

                            /** set key/value data pair to pass selected item */
                            foodListDetailScreen.putExtra("itemSelected", lookupItem);

                            /** load food list detail screen */
                            startActivity(foodListDetailScreen);
                        }
                    }
                });

                /** instantiate connectivity manager for iNet access */
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                /** test network connectivity */
                if (networkInfo != null && networkInfo.isConnected()) {
                    /** instantiate DownLoadWebPageTask */
                    DownloadWebPageTask getdata = new DownloadWebPageTask();

                    /** set listener */
                    getdata.setDataDownloadListener(new DownloadWebPageTask.DataDownloadListener() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public void dataDownloadedSuccessfully(Object data) {
                            /** handle download success */
                            Internet iNet = new Internet();

                            /** get string array of list items */
                            String[] items = iNet.getItemsFromPage(getApplicationContext(),data.toString());

                            /** Create an adapter as the source for the list view. Note: MUST create TEXTVIEW layout for rendering */
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.food_item, items);

                            /** Set the adapter as the list source */
                            _listLookupItems.setAdapter(adapter);
                        }

                        @Override
                        public void dataDownloadFailed() {
                            /** notify of failure */
                            Toast.makeText(getApplicationContext(), "The page download failed.", Toast.LENGTH_SHORT).show();

                            /** redirect to main list */
                            Intent foodListScreen = new Intent(getApplicationContext(), FoodProfileListActivity.class);
                            startActivity(foodListScreen);
                        }
                    });
                    getdata.execute("https://www.caloriecount.com/cc/search.php?searchpro=" + value);
                }
            }
        } catch (Exception e) {
            /** log to console */
            Log.i(TAG, "FoodProfileLookUpActivity.initMain() - ERROR: " + e.getMessage());
        }
    }
}
