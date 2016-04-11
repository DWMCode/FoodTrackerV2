package com.riansousa.foodtrackerv2.Logic;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ErrorLog {


    private static final String TAG = "FoodTracker";

    /** default constructor */
    public ErrorLog() {

    }

    /** method to write error messages to the file log */
    public void WriteError(android.content.Context context, String message) {
        try {
            /** open the file output steam */
            FileOutputStream fos = context.openFileOutput("errorLog.txt", Context.MODE_APPEND);

            /** write the message with a marker for parsing text*/
            message = message + "|";
            fos.write(message.getBytes());

            Log.i(TAG, "ErrorLog.WriteError() - data written to file in:" + context.getFilesDir());
        }
        catch(Exception e){
            /** log error to console */
            Log.i(TAG, "ErrorLog.WriteError() - " + e.getMessage());
        }
    }

    /** method to read error messages to the file log */
    public void PrintErrorLog(android.content.Context context) {
        try {
            /** open the file input steam */
            FileInputStream fis = context.openFileInput("errorLog.txt");

            /** instantiate new byte array & read file */
            byte[] reader = new byte[fis.available()];
            while (fis.read(reader) != -1) {}

            /** convert to readable text */
            CharSequence errorLog = new String(reader);

            Log.i(TAG, "ErrorLog.PrintErrorLog() - " + errorLog);
        }
        catch(Exception e){
            // log error to console
            Log.i(TAG, "ErrorLog.PrintErrorLog() - " + e.getMessage());
        }
    }
}
