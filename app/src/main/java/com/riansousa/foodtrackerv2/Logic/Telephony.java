package com.riansousa.foodtrackerv2.Logic;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

/**
 * This code is excerpted from the lecture notes Module 5
 */
public class Telephony {

    /** declare global variables */
    private static final String TAG = "FoodTracker";

    /**
     * this is a method to check the telephony stats and report via post
     * @param context
     * @return
     */
    public String showTelStatus(android.content.Context context) {
        String result = "";
        try {
            /** instantiate telephony manager */
            final TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            /** callState = state of the call (idle, ringing, off the hook etc.) */
            int callState = telMgr.getCallState();
            String callStateString = "NA";
            switch (callState) {
                case TelephonyManager.CALL_STATE_IDLE:
                    callStateString = "IDLE";
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    callStateString = "OFFHOOK";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    callStateString = "RINGING";
                    break;
            }

            /** return concatenation of the above data, with labels */
            result = "Your phone is: " + callStateString;

            /** Toast message */
            Toast.makeText(context.getApplicationContext(), result, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.i(TAG, "ReportingActivity.showTelStatus() - Error: " + e.getMessage());
        }
        return result;
    }

}
