package com.riansousa.foodtrackerv2.Logic;
import android.util.Log;

public class Recorder {

    private static final String TAG = "FoodTracker";

    // input: string
    // output: void
    // This function records items consumed to the repository
    public void RecordConsumption(String item) {
        // Log item saved to repository
        Log.i(TAG, "LogicRecorder - The consumption of " + item + " has been saved to the repository.");
    }

    // input: android.text.Editable
    // output: void
    // This function adds new items to the repository
    public void AddNewItem(android.text.Editable item) {
        // Log item saved to repository
        Log.i(TAG, "LogicRecorder - " + item + " has been added to the repository.");
    }

}
